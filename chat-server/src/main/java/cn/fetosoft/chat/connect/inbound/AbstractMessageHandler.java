package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.channel.ChatChannelFactory;
import cn.fetosoft.chat.connect.chat.BaseMessage;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.queue.MessageQueue;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * 消息处理
 * @author guobingbing
 * @wechat t_gbinb
 * @version 1.0
 * @create 2020/12/22 11:06
 */
public abstract class AbstractMessageHandler<T extends BaseMessage> extends SimpleChannelInboundHandler<ReceiveMessage<T>> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractMessageHandler.class);
	/**
	 * 会话工厂
	 */
	@Autowired
	protected ChatChannelFactory channelFactory;
	@Autowired
	private FriendService friendService;
	@Autowired
	private MessageQueue<T> messageQueue;

	/**
	 * 加密信息
	 * @param messages
	 * @return
	 */
	protected String encryptMessage(List<ReceiveMessage> messages){
		return JSON.toJSONString(messages);
	}

	/**
	 * 判断用户之间是否为好友
	 * @param sid
	 * @param rid
	 * @return
	 */
	protected boolean isFriend(String sid, String rid){
		return friendService.isFriend(sid, rid);
	}

	/**
	 * Push消息
	 * @param receiveMessage 发送的消息
	 */
	protected List<ReceiveMessage> pushMessage(ReceiveMessage<T> receiveMessage){
		return this.pushMessage(receiveMessage, true);
	}

	/**
	 * Push消息
	 * @param receiveMessage 发送的消息
	 * @param offerQueue 是否保存到队列
	 */
	protected List<ReceiveMessage> pushMessage(ReceiveMessage<T> receiveMessage, boolean offerQueue){
		List<ReceiveMessage> list = Collections.singletonList(receiveMessage);
		String rid = receiveMessage.getChatMessage().getRid();
		Channel channel = channelFactory.getChannel(rid);
		if (channel != null) {
			channel.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
		} else {
			if(offerQueue) {
				messageQueue.offer(receiveMessage);
			}
		}
		return list;
	}

	/**
	 * Push消息
	 * @param receiveMessage 发送的消息
	 */
	protected List<ReceiveMessage> pushMessage(String receiveId, ReceiveMessage<T> receiveMessage){
		List<ReceiveMessage> list = Collections.singletonList(receiveMessage);
		Channel channel = channelFactory.getChannel(receiveId);
		if (channel != null) {
			channel.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
		} else {
			messageQueue.offer(receiveId, receiveMessage);
		}
		return list;
	}

	/**
	 * 捕获异常
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel channel = ctx.channel();
		if(channel.isActive()){
			this.channelFactory.removeChannel(ctx.channel().id().asLongText());
			channel.close();
		}
		logger.error(this.getClass().getName(), cause);
	}
}
