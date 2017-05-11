package com.pactrex.server.module.player.handler;

import com.pactrex.common.annotations.SocketCommand;
import com.pactrex.common.annotations.SocketModule;
import com.pactrex.common.model.Result;
import com.pactrex.common.session.Session;
import com.pactrex.model.ModuleId;
import com.pactrex.model.player.PlayerCmd;
import com.pactrex.model.player.proto.PlayerModule.PlayerResponse;

/**
 * 玩家模块
 * @author Di.Lei
 * @date 2017年5月11日
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {
	
	
	/**
	 * 创建并登录帐号
	 * @param session
	 * @param data {@link RegisterRequest}
	 * @return
	 */
	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data);
	

	/**
	 * 登录帐号
	 * @param session
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public Result<PlayerResponse> login(Session session, byte[] data);

}
