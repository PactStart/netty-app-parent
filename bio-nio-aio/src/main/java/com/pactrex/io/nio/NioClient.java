package com.pactrex.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClient {

	static int flag = 1;
	static Selector selector;
	private static int blockSize = 4096;
	static ByteBuffer receiveBuf = ByteBuffer.allocate(blockSize);
	static ByteBuffer sendBuf = ByteBuffer.allocate(blockSize);
	
	public static void main(String[] args) throws IOException {
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);

		Selector selector = Selector.open();
		//为该通道注册SelectionKey.OP_CONNECT事件
		channel.register(selector, SelectionKey.OP_CONNECT);
		//客户端连接服务器，需要调用channel.finishConnect();才能实际完成连接。
		channel.connect(new InetSocketAddress("127.0.0.1", 8070));
		
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SocketChannel client;
		String recieveText;
		String sendText;
		int count;
		while (true) {
			//选择注册过的io操作的事件(第一次为SelectionKey.OP_CONNECT)
			selector.select();
			selectionKeys = selector.selectedKeys();
			iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				if (selectionKey.isConnectable()) {
					System.out.println("client connect");
					client = (SocketChannel) selectionKey.channel();
					if (client.isConnectionPending()) {
						client.finishConnect();
						System.out.println("client connect finish");
					}
					client.configureBlocking(false);
					sendBuf.clear();
					sendBuf.put("Hello,Server".getBytes());
					sendBuf.flip();
					client.write(sendBuf);
					 //连接成功后，注册接收服务器消息的事件
//					client.register(selector, SelectionKey.OP_READ);
					client.register(selector, SelectionKey.OP_WRITE);
				}  if (selectionKey.isReadable()) {
					client = (SocketChannel) selectionKey.channel();
					count = client.read(receiveBuf);
					if (count > 0) {
						recieveText = new String(receiveBuf.array(), 0, count);
						System.out.println("客户端接收到服务端信息>>>" + recieveText);
					}
					client.register(selector, SelectionKey.OP_WRITE);
				}  if (selectionKey.isWritable()) {
					sendBuf.clear();
					client = (SocketChannel) selectionKey.channel();
					sendText = "msg send to server:" + flag++;
					sendBuf.put(sendText.getBytes());
					sendBuf.flip();
					client.write(sendBuf);
					System.out.println("客户端发送数据给服务端>>>" + sendText);
					client.register(selector, SelectionKey.OP_READ);
				}
			}
			selectionKeys.clear();
		}
	}
}