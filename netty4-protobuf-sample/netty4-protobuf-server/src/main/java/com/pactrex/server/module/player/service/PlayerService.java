package com.pactrex.server.module.player.service;

import com.pactrex.common.session.Session;
import com.pactrex.model.player.proto.PlayerModule.PlayerResponse;

public interface PlayerService {
	
	
	/**
	 * 登录注册用户
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public PlayerResponse registerAndLogin(Session session, String playerName, String passward);
	
	
	/**
	 * 登录
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public PlayerResponse login(Session session, String playerName, String passward);

}
