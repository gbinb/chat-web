package cn.fetosoft.chat.connect;

import cn.fetosoft.chat.config.SystemConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Chat服务
 * @author guobingbing
 * @create 2020/12/11 11:55
 */
@Component
public class ChatServer implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
	private final EventLoopGroup parentGroup = new NioEventLoopGroup(2);
	private final EventLoopGroup childGroup = new NioEventLoopGroup();
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private SocketChannelInitializer socketChannelInitializer;

	/**
	 * 启动服务
	 */
	private void start() {
		ChannelFuture future = null;
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(parentGroup, childGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childHandler(socketChannelInitializer);
			future = b.bind(new InetSocketAddress(systemConfig.getChatPort())).syncUninterruptibly();
			if(future.isSuccess()) {
				logger.info("Chat server start success. ");
			}
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			logger.error("socket server start error", e);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		this.start();
	}
}
