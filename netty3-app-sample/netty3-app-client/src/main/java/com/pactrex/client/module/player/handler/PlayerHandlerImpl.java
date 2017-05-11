package com.pactrex.client.module.player.handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pactrex.client.swing.ResultCodeTip;
import com.pactrex.client.swing.Swingclient;
import com.pactrex.common.model.ResultCode;
import com.pactrex.model.player.response.PlayerResponse;
@Component
public class PlayerHandlerImpl implements PlayerHandler{
	
	@Autowired
	private Swingclient swingclient;
	@Autowired
	private ResultCodeTip resultCodeTip;

	@Override
	public void registerAndLogin(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			PlayerResponse playerResponse = new PlayerResponse();
			playerResponse.readFromBytes(data);
			
			swingclient.setPlayerResponse(playerResponse);
			swingclient.getTips().setText("注册登录成功");
		}else{
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void login(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			PlayerResponse playerResponse = new PlayerResponse();
			playerResponse.readFromBytes(data);
			
			swingclient.setPlayerResponse(playerResponse);
			swingclient.getTips().setText("登录成功");
		}else{
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}
}
