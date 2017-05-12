package com.pactrex.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
/**
 * Handler在netty中，无疑占据着非常重要的地位。Handler与Servlet中的filter很像，通过Handler可以完成通讯报文的解码编码、拦截指定的报文、统一对日志错误进行处理、统一对请求进行计数、控制Handler执行与否。一句话，没有它做不到的只有你想不到的。
 * <br>
 * Netty中的所有handler都实现自ChannelHandler接口。按照输出输出来分，分为ChannelInboundHandler、ChannelOutboundHandler两大类。ChannelInboundHandler对从客户端发往服务器的报文进行处理，一般用来执行解码、读取客户端数据、进行业务处理等；ChannelOutboundHandler对从服务器发往客户端的报文进行处理，一般用来进行编码、发送报文到客户端。
 * <br>
 * Netty中，可以注册多个handler。ChannelInboundHandler按照注册的先后顺序执行；ChannelOutboundHandler按照注册的先后顺序逆序执行，如下图所示，按照注册的先后顺序对Handler进行排序，request进入Netty后的执行顺序为：
 * @author Di.Lei
 * @date 2017年5月12日
 */
public class Server {

	public static void main(String[] args) {
		// 服务类
		ServerBootstrap bootstrap = new ServerBootstrap();

		// boss和worker
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		try {
			// 设置线程池
			bootstrap.group(boss, worker);

			// 设置socket工厂、
			bootstrap.channel(NioServerSocketChannel.class);

			// 设置管道工厂
			bootstrap.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new StringEncoder());
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
			ChannelFuture future = bootstrap.bind(10101);

			System.out.println("start");

			// 等待服务端关闭
			future.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
