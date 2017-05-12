package com.pactrex.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(7777);
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		while (true) {
			final Socket socket = serverSocket.accept();
			System.out.println("新的客户进来了");
			newCachedThreadPool.execute(new Runnable() {
				public void run() {
					handle(socket);
				}
			});
		}
	}

	public static void handle(Socket socket) {
		try {
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while (true) {
				line = br.readLine();//internal read()阻塞
				System.out.println("服务端接受数据" + line);
				if (line.equals("break")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
