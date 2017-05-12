package com.pactrex.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServer {

	static int flag = 1;
	Selector selector;
	private int blockSize = 4096;
	ByteBuffer receiveBuf = ByteBuffer.allocate(blockSize);
	ByteBuffer sendBuf = ByteBuffer.allocate(blockSize);

	static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	static ServerSocketChannel channel;

	public NioServer(final int port) throws IOException {
		ServerSocketChannel channel = ServerSocketChannel.open();
		
		channel.configureBlocking(false);
		
		ServerSocket socket = channel.socket();
		socket.bind(new InetSocketAddress("127.0.0.1", port));
		
		selector = Selector.open();
		// 将通道管理器与通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，
		// 只有当该事件到达时，Selector.select()会返回，否则一直阻塞。
		channel.register(selector, SelectionKey.OP_ACCEPT);
		
		System.out.println("Server start >>>" + port);
	}

	public void listen() throws IOException {
		while (true) {
			// 当有注册的事件到达时，方法返回，否则阻塞。
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			while (iter.hasNext()) {
				final SelectionKey key = iter.next();
				iter.remove();
				//多线程存在 重复消费
				/*cachedThreadPool.submit(new Runnable() {

					public void run() {
						try {
							handleKey(key);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});*/
				handleKey(key);
			}
		}
	}

	private void handleKey(SelectionKey key) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String recieveText;
		String sendText;
		int count;
		if (key.isAcceptable()) {
			server = (ServerSocketChannel) key.channel();
//			System.out.println(channel == server);
			// 获得客户端连接通道
			client = server.accept();
			client.configureBlocking(false);
			// 在与客户端连接成功后，为客户端通道注册SelectionKey.OP_READ事件。
			client.register(selector, SelectionKey.OP_READ);
			
		} else if (key.isReadable()) {
			client = (SocketChannel) key.channel();
			count = client.read(receiveBuf);
			if (count > 0) {
				recieveText = new String(receiveBuf.array(), 0, count);
				System.out.println("服务端接收到客户端信息>>>" + recieveText);
//				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (key.isWritable()) {
			sendBuf.clear();
			client = (SocketChannel) key.channel();
			sendText = "msg send to client:" + flag++;
			sendBuf.put(sendText.getBytes());
			sendBuf.flip();
			client.write(sendBuf);
			System.out.println("服务端发送数据给客户端>>>" + sendText);
		}
	}

	public static void main(String[] args) throws IOException {
		new NioServer(8070).listen();
	}
}
