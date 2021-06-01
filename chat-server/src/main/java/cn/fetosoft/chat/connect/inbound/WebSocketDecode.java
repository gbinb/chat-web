package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.chat.BaseMessage;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * WebSocket消息解码器
 * @author guobingbing
 * @wechat t_gbinb
 * @version 1.0
 * @create 2020/12/24 15:32
 */
@Component
@ChannelHandler.Sharable
public class WebSocketDecode extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketDecode.class);

	@Override
	protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame textFrame) throws Exception {
		String message = textFrame.text();
		logger.info("接受到的数据：{}", message);
		if(StringUtils.isBlank(message)){
			context.writeAndFlush(new TextWebSocketFrame("empty"));
		}else {
			String decMessage = this.decryptMessage(message);
			ReceiveMessage<BaseMessage> receiveMessage = ReceiveMessage.toRecMessage(decMessage);
			context.fireChannelRead(receiveMessage);
		}
	}

	/**
	 * 解密消息
	 * @param message
	 * @return
	 */
	private String decryptMessage(String message){
		return message;
	}
}
