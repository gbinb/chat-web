package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.chat.BaseMessage;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import cn.fetosoft.chat.connect.events.UserOnlineEvent;
import cn.fetosoft.chat.queue.MessageQueue;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户上线
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/31 10:05
 */
@Component
@ChannelHandler.Sharable
public class UserOnlineHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserOnlineHandler.class);
	@Autowired
	private MessageQueue<BaseMessage> messageQueue;

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof UserOnlineEvent) {
			try{
				UserOnlineEvent userEvent = (UserOnlineEvent)evt;
				List<ReceiveMessage> messageList = messageQueue.pollForRecMessage(userEvent.getUserId());
				if(messageList.size()>0) {
					ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageList)));
				}
			}catch(Exception e){
				logger.error("pushMessage", e);
			}
		}else {
			ctx.fireUserEventTriggered(evt);
		}
	}
}
