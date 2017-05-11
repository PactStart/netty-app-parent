package com.pactrex.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.stereotype.Component;

import com.pactrex.common.codec.RequestDecoder;
import com.pactrex.common.codec.ResponseEncoder;
import com.pactrex.server.handler.ServerHandler;


@Component
public class Netty3Server {

	public void start(){
		
		//服务类
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		//boss线程监听端口，worker线程负责数据读写
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		
		//设置niosocket工厂
		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));
		
		//设置管道的工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new RequestDecoder());
				pipeline.addLast("encoder", new ResponseEncoder());
				pipeline.addLast("helloHandler", new ServerHandler());
				return pipeline;
			}
		});
		
		bootstrap.setOption("backlog", 1024);
		
		//绑定端口
		bootstrap.bind(new InetSocketAddress(10102));
		
		System.out.println("netty3 server start!");
	}
}
