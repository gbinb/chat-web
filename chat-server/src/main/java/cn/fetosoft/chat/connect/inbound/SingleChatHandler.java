package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.chat.ChatMessage;
import cn.fetosoft.chat.connect.chat.ChatMode;
import cn.fetosoft.chat.connect.chat.MessageStatus;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import cn.fetosoft.chat.queue.MessageQueue;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 只负责处理单人聊天
 * @author guobingbing
 * @create 2020/12/24 15:37
 */
@Component
@ChannelHandler.Sharable
public class SingleChatHandler extends AbstractMessageHandler<ChatMessage> {

	private static final Logger logger = LoggerFactory.getLogger(SingleChatHandler.class);
	@Autowired
	private MessageQueue<ChatMessage> messageQueue;

	/**
	 * 业务处理
	 * @param context
	 * @param receiveMessage
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext context, ReceiveMessage<ChatMessage> receiveMessage) throws Exception {
		if(ChatMode.Single.getCode() == receiveMessage.getMode()){
			//单聊
			String sid = this.channelFactory.getUserId(context.channel());
			logger.info("sid >>> {} >>> {}", sid, context.channel().id().asLongText());
			receiveMessage.getChatMessage().setSid(sid);
			if(this.isFriend(sid, receiveMessage.getChatMessage().getRid())) {
				Channel channel = channelFactory.getChannel(receiveMessage.getChatMessage().getRid());
				receiveMessage.getChatMessage().setStatus(MessageStatus.SUCCESS);
				receiveMessage.setRt(System.currentTimeMillis());
				List<ReceiveMessage> list = Collections.singletonList(receiveMessage);
				if (channel != null) {
					channel.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
				} else {
					messageQueue.offer(receiveMessage);
				}
				context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
			}else{
				receiveMessage.getChatMessage().setStatus(MessageStatus.NOT_FRIEND);
				List<ReceiveMessage> list = Collections.singletonList(receiveMessage);
				context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
			}
		}else{
			context.fireChannelRead(receiveMessage);
		}
	}
}
