package com.pactrex.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioClient {
	
	AsynchronousSocketChannel channel = null;

	public AioClient(String addr,final int port) throws IOException, InterruptedException, ExecutionException {
		channel = AsynchronousSocketChannel.open();
		Future<Void> future = channel.connect(new InetSocketAddress(addr, port));
		System.out.println(future.get());
	}
	
	public void write(byte b){
		ByteBuffer buf = ByteBuffer.allocate(32);
		buf.put(b);
		buf.flip();
		channel.write(buf);
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		new AioClient("localhost",7080).write((byte)11);
	}
}
