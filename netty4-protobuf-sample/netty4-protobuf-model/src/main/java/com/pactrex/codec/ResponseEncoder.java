package com.pactrex.codec;
import com.pactrex.common.codec.ConstantValue;
import com.pactrex.common.model.Response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * |  包头	|  模块号      |  命令号    |  结果码    |  长度       |   数据     |  
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * </pre>
 *
 */
public class ResponseEncoder extends MessageToByteEncoder<Response>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Response response, ByteBuf buffer) throws Exception {
		
		System.out.println("返回请求:" + "module:" +response.getModule() +" cmd:" + response.getCmd() + " resultCode:" + response.getResultCode());
		
		//包头
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		//module和cmd
		buffer.writeShort(response.getModule());
		buffer.writeShort(response.getCmd());
		//结果码
		buffer.writeInt(response.getResultCode());
		//长度
		int lenth = response.getData()==null? 0 : response.getData().length;
		if(lenth <= 0){
			buffer.writeInt(lenth);
		}else{
			buffer.writeInt(lenth);
			buffer.writeBytes(response.getData());
		}
	}
}
