package com.pactrex.server.module.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pactrex.common.exception.ErrorCodeException;
import com.pactrex.common.model.Result;
import com.pactrex.common.model.ResultCode;
import com.pactrex.model.chat.proto.ChatModule;
import com.pactrex.model.chat.proto.ChatModule.PrivateChatRequest;
import com.pactrex.model.chat.proto.ChatModule.PublicChatRequest;
import com.pactrex.server.module.chat.service.ChatService;


@Component
public class ChatHandlerImpl implements ChatHandler{
	
	@Autowired
	private ChatService chatService;

	@Override
	public Result<?> publicChat(long playerId, byte[] data) {
		try {
			//反序列化
			PublicChatRequest request = ChatModule.PublicChatRequest.parseFrom(data);
			
			//参数校验
			if(StringUtils.isEmpty(request.getContext())){
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.publicChat(playerId, request.getContext());
		}catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		}catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}

	@Override
	public Result<?> privateChat(long playerId, byte[] data) {
		try {
			//反序列化
			PrivateChatRequest request = ChatModule.PrivateChatRequest.parseFrom(data);
			
			//参数校验
			if(StringUtils.isEmpty(request.getContext()) || request.getTargetPlayerId() <= 0){
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.privateChat(playerId, request.getTargetPlayerId(), request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}
}
