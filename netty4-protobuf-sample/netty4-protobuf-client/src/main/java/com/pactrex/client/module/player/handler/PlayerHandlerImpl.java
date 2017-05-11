package com.pactrex.client.module.player.handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pactrex.client.swing.ResultCodeTip;
import com.pactrex.client.swing.Swingclient;
import com.pactrex.common.model.ResultCode;
import com.pactrex.model.player.proto.PlayerModule;
import com.pactrex.model.player.proto.PlayerModule.PlayerResponse;

@Component
public class PlayerHandlerImpl implements PlayerHandler{
	
	@Autowired
	private Swingclient swingclient;
	@Autowired
	private ResultCodeTip resultCodeTip;

	@Override
	public void registerAndLogin(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			try {
				//反序列化
				PlayerResponse playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
				
				swingclient.setPlayerResponse(playerResponse);
				swingclient.getTips().setText("注册登录成功");
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
				swingclient.getTips().setText("反序列化异常");
			}
		}else{
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void login(int resultCode, byte[] data) {
		if(resultCode == ResultCode.SUCCESS){
			try {
				//反序列化
				PlayerResponse playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
				
				swingclient.setPlayerResponse(playerResponse);
				swingclient.getTips().setText("登录成功");
			} catch (InvalidProtocolBufferException e) {
				e.printStackTrace();
				swingclient.getTips().setText("反序列化异常");
			}
		}else{
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}
}
