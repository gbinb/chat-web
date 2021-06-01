package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.config.Constant;
import cn.fetosoft.chat.connect.chat.*;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 群成员处理
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:31
 */
@Component
@ChannelHandler.Sharable
public class GroupMemberHandler extends AbstractMessageHandler<NoticeMessage> {

	@Autowired
	private RedissonTemplate redissonTemplate;

	/**
	 * 处理退群，解散群等相关信息
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext context, ReceiveMessage<NoticeMessage> receiveMessage) throws Exception {
		if(receiveMessage.getMode() == ChatMode.DismissGroup.getCode()){
			String sid = this.channelFactory.getUserId(context.channel());
			NoticeMessage notice = receiveMessage.getChatMessage();
			notice.setSid(sid);
			notice.setStatus(MessageStatus.SUCCESS);
			if(notice.getDetailType() == GroupDetailType.Dismiss.getCode() ||
					notice.getDetailType() == GroupDetailType.Remove.getCode()){
				List<String> list = null;
				if(notice.getDetailType() == GroupDetailType.Dismiss.getCode()){
					list = redissonTemplate.getList(Constant.REDIS_GROUP_DISMISS + notice.getRid());
				}else if(notice.getDetailType() == GroupDetailType.Remove.getCode()){
					list = redissonTemplate.getList(Constant.REDIS_GROUP_REMOVE + notice.getRid());
				}
				if(!CollectionUtils.isEmpty(list)){
					for(String userId : list) {
						if(!sid.equals(userId)) {
							this.pushMessage(userId, receiveMessage);
						}
					}
				}
			}else if(notice.getDetailType() == GroupDetailType.Quit.getCode()){
				this.pushMessage(receiveMessage);
			}
			context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(Collections.singletonList(receiveMessage))));
		}else{
			context.fireChannelRead(receiveMessage);
		}
	}
}
