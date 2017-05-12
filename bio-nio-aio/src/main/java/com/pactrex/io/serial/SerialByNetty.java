package com.pactrex.io.serial;

import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class SerialByNetty {

	public static void main(String[] args) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(101);
		buffer.writeDouble(80.1);
		byte[] bytes = new byte[buffer.writerIndex()];
		buffer.readBytes(bytes);
		System.out.println(Arrays.toString(bytes));
		
		ChannelBuffer buffer2 = ChannelBuffers.wrappedBuffer(bytes);
		System.out.println(buffer2.readInt());
		System.out.println(buffer2.readDouble());
	}
}
