package com.pactrex.server.module.player.service;
import com.pactrex.common.session.Session;
import com.pactrex.model.player.response.PlayerResponse;

/**
 * 玩家服务
 * @author Di.Lei
 * @date 2017年5月11日
 */
public interface PlayerService {
	
	
	/**
	 * 登录注册用户
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public PlayerResponse registerAndLogin(Session session, String playerName, String password);
	
	
	/**
	 * 登录
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public PlayerResponse login(Session session, String playerName, String password);

}
