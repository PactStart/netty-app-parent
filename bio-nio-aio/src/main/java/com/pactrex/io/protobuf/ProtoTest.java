package com.pactrex.io.protobuf;

import java.util.Arrays;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pactrex.io.protobuf.PlayerModule.PBPlayer;
import com.pactrex.io.protobuf.PlayerModule.PBPlayer.Builder;

public class ProtoTest {
	
	public static void main(String[] args) throws InvalidProtocolBufferException {
//		deSer(ser());
		System.out.println(Integer.toBinaryString(201));
		System.out.println(Integer.toBinaryString(~0x7F));
		System.out.println(201 & 0x7F | 0x80);
	}


	public static byte[] ser(){
		Builder builder = PlayerModule.PBPlayer.newBuilder();
		builder.setPlayerId(1).setAge(24).setName("leidi").addSkills(1001);
		PBPlayer player = builder.build();
		byte[] byteArray = player.toByteArray();
		System.out.println(Arrays.toString(byteArray));
		return byteArray;
	}
	
	public static void deSer(byte[] ayteArr) throws InvalidProtocolBufferException{
		PBPlayer player = PlayerModule.PBPlayer.parseFrom(ayteArr);
		System.out.println("playerId:"+player.getPlayerId());
		System.out.println("age:"+player.getAge());
		System.out.println("name:"+player.getName());
		System.out.println("skills:"+Arrays.toString(player.getSkillsList().toArray()));
	}
}
