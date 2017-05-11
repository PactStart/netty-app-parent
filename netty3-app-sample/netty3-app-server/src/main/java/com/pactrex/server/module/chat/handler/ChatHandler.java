package com.pactrex.server.module.chat.handler;
import com.pactrex.common.annotations.SocketCommand;
import com.pactrex.common.annotations.SocketModule;
import com.pactrex.common.model.Result;
import com.pactrex.model.ModuleId;
import com.pactrex.model.chat.ChatCmd;
import com.pactrex.model.chat.request.PrivateChatRequest;
import com.pactrex.model.chat.request.PublicChatRequest;
/**
 * 聊天
 * @author Di.Lei
 * @date 2017年5月11日
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {
	
	
	/**
	 * 广播消息
	 * @param playerId
	 * @param data {@link PublicChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public Result<?> publicChat(long playerId, byte[] data);
	
	
	
	/**
	 * 私人消息
	 * @param playerId
	 * @param data {@link PrivateChatRequest}
	 * @return
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public Result<?> privateChat(long playerId, byte[] data);
}
