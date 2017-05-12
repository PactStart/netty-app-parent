package com.pactrex.io.serial;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SerialByNio {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putInt(101);
		buffer.putDouble(80.1);
		byte[] bytes = buffer.array();
		System.out.println(Arrays.toString(bytes));
		
		ByteBuffer buffer2 = ByteBuffer.wrap(bytes);
		System.out.println(buffer2.getInt());
		System.out.println(buffer2.getDouble());
		
	}
}
