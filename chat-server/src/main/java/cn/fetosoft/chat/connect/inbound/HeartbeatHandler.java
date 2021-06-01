package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.chat.BaseMessage;
import cn.fetosoft.chat.connect.chat.ChatMode;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 负责处理心跳
 * @author guobingbing
 * @wechat t_gbinb
 * @version 1.0
 * @create 2020/12/24 16:52
 */
@Component
@ChannelHandler.Sharable
public class HeartbeatHandler extends AbstractMessageHandler<BaseMessage> {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext context, ReceiveMessage receiveMessage) throws Exception {
		if(ChatMode.Heart.getCode() == receiveMessage.getMode()){
			receiveMessage.setRt(System.currentTimeMillis());
			List<ReceiveMessage> messages = Collections.singletonList(receiveMessage);
			context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(messages)));
		}else{
			context.fireChannelRead(receiveMessage);
		}
	}

	/**
	 * 会话断开时处理
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel,所以下面的remove不用我们再手写
		this.channelFactory.removeChannel(ctx.channel().id().asLongText());
		logger.info("客户端断开，channle对应的短id为：{}", ctx.channel().id().asLongText());
		logger.info("容器中存活的Channel数量：{}", this.channelFactory.containerSize());
		ctx.channel().close();
	}

}
