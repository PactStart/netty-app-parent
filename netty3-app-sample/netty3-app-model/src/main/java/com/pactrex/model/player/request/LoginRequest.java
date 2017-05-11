package com.pactrex.model.player.request;

import com.pactrex.common.serial.Serializer;

/**
 * 登录请求
 * @author Di.Lei
 * @date 2017年5月11日
 */
public class LoginRequest extends Serializer{
	
	/**
	 * 用户名
	 */
	private String playerName;
	
	/**
	 * 密码
	 */
	private String passward;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	@Override
	protected void read() {
		this.playerName = readString();
		this.passward = readString();
	}

	@Override
	protected void write() {
		writeString(playerName);
		writeString(passward);
	}
}
