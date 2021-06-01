package cn.fetosoft.chat.connect;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.connect.inbound.*;
import cn.fetosoft.chat.connect.outbound.ResponseMessage;
import cn.fetosoft.chat.connect.outbound.WebSocketEncode;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 初始化inbound及outbound通道
 * @author guobingbing
 * @wechat t_gbinb
 * @create 2020/12/11 11:58
 */
@Component
public class SocketChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {

	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private RequestAuthenHandler requestAuthenHandler;
	@Autowired
	private WebSocketDecode webSocketDecode;
	@Autowired
	private HeartbeatHandler heartbeatHandler;
	@Autowired
	private UserOnlineHandler userOnlineHandler;
	@Autowired
	private SingleChatHandler singleChatHandler;
	@Autowired
	private GroupChatHandler groupChatHandler;
	@Autowired
	private NotifyMessageHandler notifyMessageHandler;
	@Autowired
	private GroupMemberHandler groupMemberHandler;

	/**
	 * 初始化管道
	 * @param socketChannel
	 * @throws Exception
	 */
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		if(SocketType.Socket.getCode().equalsIgnoreCase(this.systemConfig.getSocketType())){
			this.initSocketChannel(pipeline);
		}else if(SocketType.WebSocket.getCode().equalsIgnoreCase(this.systemConfig.getSocketType())){
			pipeline.addLast(new WebSocketEncode());
			this.initWebsocketChannel(pipeline);
		}
		pipeline.addLast(heartbeatHandler);
		pipeline.addLast(userOnlineHandler);
		pipeline.addLast(singleChatHandler);
		pipeline.addLast(groupChatHandler);
		pipeline.addLast(notifyMessageHandler);
		pipeline.addLast(groupMemberHandler);
	}

	/**
	 * 初始化Socket
	 * @param pipeline
	 */
	private void initSocketChannel(ChannelPipeline pipeline){
		pipeline.addLast(new ResponseMessage());
		pipeline.addLast(new MessageDecode());
	}

	/**
	 * 初始化WebSocket
	 * @param pipeline
	 */
	private void initWebsocketChannel(ChannelPipeline pipeline){
		//websocket 基于http协议，所以要有http编解码器 服务端用HttpServerCodec
		pipeline.addLast(new HttpServerCodec());
		//对写大数据流的支持
		pipeline.addLast(new ChunkedWriteHandler());
		/**
		 * 我们通常接收到的是一个http片段，如果要想完整接受一次请求的所有数据，我们需要绑定HttpObjectAggregator，然后我们
		 * 就可以收到一个FullHttpRequest-是一个完整的请求信息。
		 * 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
		 * 几乎在netty中的编程，都会使用到此hanler
		 */
		pipeline.addLast(new HttpObjectAggregator(1024*64));
		pipeline.addLast(requestAuthenHandler);
		pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
		pipeline.addLast(webSocketDecode);

	}
}
