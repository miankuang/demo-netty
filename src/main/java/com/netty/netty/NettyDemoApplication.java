package com.netty.netty;

import com.netty.netty.handler.DiscardProtocolHandler;
import com.netty.netty.handler.EchoProtocolHandler;
import com.netty.netty.handler.TimeProtocolHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyDemoApplication {

	public static void main(String[] args) {
		//SpringApplication.run(NettyDemoApplication.class, args);
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							//ch.pipeline().addLast(new DiscardProtocolHandler());
							//ch.pipeline().addLast(new EchoProtocolHandler());
							ch.pipeline().addLast(new TimeProtocolHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			System.out.println("第一步 完成");
			ChannelFuture future = serverBootstrap.bind(8088).sync();
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("第二步 完成");
				}
			});
			ChannelFuture closeFuture = future.channel().closeFuture().sync();
			closeFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("第三步 完成");
				}
			});
		} catch (InterruptedException e) {
			System.out.println("InterruptedException: " + e);
		}
	}

}
