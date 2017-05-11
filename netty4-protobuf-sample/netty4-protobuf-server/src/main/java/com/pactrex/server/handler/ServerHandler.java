package com.pactrex.server.handler;

import com.google.protobuf.GeneratedMessage;
import com.pactrex.common.invoker.Invoker;
import com.pactrex.common.invoker.InvokerHoler;
import com.pactrex.common.model.Request;
import com.pactrex.common.model.Response;
import com.pactrex.common.model.Result;
import com.pactrex.common.model.ResultCode;
import com.pactrex.common.serial.Serializer;
import com.pactrex.common.session.Session;
import com.pactrex.common.session.SessionImpl;
import com.pactrex.common.session.SessionManager;
import com.pactrex.model.ModuleId;
import com.pactrex.server.module.player.dao.entity.Player;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 消息接受处理类
 * @author Di.Lei
 * @date 2017年5月11日
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request> {
	
	/**
	 * 接收消息
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {

		handlerMessage(new SessionImpl(ctx.channel()), request);
	}
	
	
	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerMessage(Session session, Request request){
		
		Response response = new Response(request);
		
		System.out.println("module:"+request.getModule() + "   " + "cmd：" + request.getCmd());
		
		//获取命令执行器
		Invoker invoker = InvokerHoler.getInvoker(request.getModule(), request.getCmd());
		if(invoker != null){
			try {
				Result<?> result = null;
				//假如是玩家模块传入channel参数，否则传入playerId参数
				if(request.getModule() == ModuleId.PLAYER){
					result = (Result<?>)invoker.invoke(session, request.getData());
				}else{
					Object attachment = session.getAttachment();
					if(attachment != null){
						Player player = (Player) attachment;
						result = (Result<?>)invoker.invoke(player.getPlayerId(), request.getData());
					}else{
						//会话未登录拒绝请求
						response.setResultCode(ResultCode.LOGIN_PLEASE);
						session.write(response);
						return;
					}
				}
				
				//判断请求是否成功
				if(result.getResultCode() == ResultCode.SUCCESS){
					//回写数据
					Object object = result.getContent();
					if(object != null){
						if(object instanceof Serializer){
							Serializer content = (Serializer)object;
							response.setData(content.getBytes());
						}else if(object instanceof GeneratedMessage){
							GeneratedMessage content = (GeneratedMessage)object;
							response.setData(content.toByteArray());
						}else{
							System.out.println(String.format("不可识别传输对象:%s", object));
						}
					}
					session.write(response);
				}else{
					//返回错误码
					response.setResultCode(result.getResultCode());
					session.write(response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				//系统未知异常
				response.setResultCode(ResultCode.UNKOWN_EXCEPTION);
				session.write(response);
			}
		}else{
			//未找到执行者
			response.setResultCode(ResultCode.NO_INVOKER);
			session.write(response);
			return;
		}
	}
	
	/**
	 * 断线移除会话
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Session session = new SessionImpl(ctx.channel());
		Object object = session.getAttachment();
		if(object != null){
			Player player = (Player)object;
			SessionManager.removeSession(player.getPlayerId());
		}
	}
}

