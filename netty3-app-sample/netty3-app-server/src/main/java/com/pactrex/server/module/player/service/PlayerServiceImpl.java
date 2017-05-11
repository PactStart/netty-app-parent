package com.pactrex.server.module.player.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pactrex.common.exception.ErrorCodeException;
import com.pactrex.common.model.ResultCode;
import com.pactrex.common.session.Session;
import com.pactrex.common.session.SessionManager;
import com.pactrex.model.player.response.PlayerResponse;
import com.pactrex.server.module.player.dao.PlayerDao;
import com.pactrex.server.module.player.dao.entity.Player;
/**
 * 玩家服务
 * @author Di.Lei
 * @date 2017年5月11日
 */
@Component
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao playerDao;

	@Override
	public PlayerResponse registerAndLogin(Session session, String playerName, String password) {

		Player existplayer = playerDao.getPlayerByName(playerName);

		// 玩家名已被占用
		if (existplayer != null) {
			throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
		}

		// 创建新帐号
		Player player = new Player();
		player.setPlayerName(playerName);
		player.setPassword(password);
		player = playerDao.createPlayer(player);

		//顺便登录
		return login(session, playerName, password);
	}

	@Override
	public PlayerResponse login(Session session, String playerName, String password) {

		// 判断当前会话是否已登录
		if (session.getAttachment() != null) {
			throw new ErrorCodeException(ResultCode.HAS_LOGIN);
		}

		// 玩家不存在
		Player player = playerDao.getPlayerByName(playerName);
		if (player == null) {
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}

		// 密码错误
		if (!player.getPassword().equals(password)) {
			throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
		}

		// 判断是否在其他地方登录过
		boolean onlinePlayer = SessionManager.isOnlinePlayer(player.getPlayerId());
		if (onlinePlayer) {
			Session oldSession = SessionManager.removeSession(player.getPlayerId());
			oldSession.removeAttachment();
			// 踢下线
			oldSession.close();
		}

		// 加入在线玩家会话
		if (SessionManager.putSession(player.getPlayerId(), session)) {
			session.setAttachment(player);
		} else {
			throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
		}

		// 创建Response传输对象返回
		PlayerResponse playerResponse = new PlayerResponse();
		playerResponse.setPlayerId(player.getPlayerId());
		playerResponse.setPlayerName(player.getPlayerName());
		playerResponse.setLevel(player.getLevel());
		playerResponse.setExp(player.getExp());
		return playerResponse;
	}

}
