package com.pactrex.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class AioServer {
	
	public AioServer(final int port) throws IOException {
		final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open()
				.bind(new InetSocketAddress(port));
		listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
			public void completed(AsynchronousSocketChannel ch, Void att) {
				// accept the next connection
				listener.accept(null, this);
				// handle this connection
				handle(ch);
			}

			public void failed(Throwable exc, Void att) {

			}
		});
	}

	public void handle(AsynchronousSocketChannel ch){
		ByteBuffer buf = ByteBuffer.allocate(32);
		try {
			ch.read(buf).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buf.flip();
		System.out.println("服务器端接受"+buf.get());
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new AioServer(7080);
		Thread.currentThread().join();
	}

}
