package com.pactrex.client.module.player.handler;
import com.pactrex.common.annotations.SocketCommand;
import com.pactrex.common.annotations.SocketModule;
import com.pactrex.model.ModuleId;
import com.pactrex.model.player.PlayerCmd;
/**
 * 玩家模块
 * @author Di.Lei
 * @date 2017年5月11日
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {
	
	
	/**
	 * 创建并登录帐号
	 * @param resultCode
	 * @param data {@link null}
	 */
	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public void registerAndLogin(int resultCode, byte[] data);
	

	/**
	 * 创建并登录帐号
	 * @param resultCode
	 * @param data {@link null}
	 */
	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public void login(int resultCode, byte[] data);

}
