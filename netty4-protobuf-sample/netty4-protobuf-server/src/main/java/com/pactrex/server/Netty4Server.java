package com.pactrex.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import com.pactrex.common.codec.RequestDecoder;
import com.pactrex.common.codec.ResponseEncoder;
import com.pactrex.server.handler.ServerHandler;

@Component
public class Netty4Server {

	/**
	 * 启动
	 */
	public void start() {

		// 服务类
		ServerBootstrap bootstrap = new ServerBootstrap();

		// 创建boss和worker
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			// 设置循环线程组事例
			bootstrap.group(bossGroup, workerGroup);

			// 设置channel工厂
			bootstrap.channel(NioServerSocketChannel.class);

			// 设置管道
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new RequestDecoder());
					ch.pipeline().addLast(new ResponseEncoder());
					ch.pipeline().addLast(new ServerHandler());
				}
			});

			// netty3中对应设置如下
			// bootstrap.setOption("backlog", 1024);
			// bootstrap.setOption("tcpNoDelay", true);
			// bootstrap.setOption("keepAlive", true);
			// 设置参数，TCP参数
			bootstrap.option(ChannelOption.SO_BACKLOG, 2048);// serverSocketchannel的设置，链接缓冲池的大小
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);// socketchannel的设置,维持链接的活跃，清除死链接
			bootstrap.childOption(ChannelOption.TCP_NODELAY, true);// socketchannel的设置,关闭延迟发送

			// 绑定端口
			bootstrap.bind(10102).sync();

			System.out.println("netty4 server start!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
