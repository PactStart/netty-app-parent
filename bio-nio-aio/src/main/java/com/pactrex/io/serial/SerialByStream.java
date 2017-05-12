package com.pactrex.io.serial;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SerialByStream {

	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(int2bytes(101));
	}
	
	public static byte[] int2bytes(int i){
		return null;
	}
	
	public static byte[] bytes2int(byte[] bytes){
		return null;
	}
}
