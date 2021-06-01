package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.chat.ChatMode;
import cn.fetosoft.chat.connect.chat.MessageStatus;
import cn.fetosoft.chat.connect.chat.NoticeMessage;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 消息通知
 * @author guobingbing
 * @create 2020/12/28 20:45
 */
@Component
@ChannelHandler.Sharable
public class NotifyMessageHandler extends AbstractMessageHandler<NoticeMessage> {

	@Autowired
	private GroupMemberService groupMemberService;

	/**
	 * 处理通知
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext context, ReceiveMessage<NoticeMessage> receiveMessage) throws Exception {
		if(ChatMode.AddFriend.getCode() == receiveMessage.getMode() ||
				ChatMode.AcceptFriend.getCode() == receiveMessage.getMode()){
			this.singleNotify(context, receiveMessage, true);
		}else if(ChatMode.CreateGroup.getCode() == receiveMessage.getMode()){
			this.groupNotify(context, receiveMessage);
		}else if(ChatMode.Call.getCode() == receiveMessage.getMode()) {
			this.singleNotify(context, receiveMessage, false);
		}else if(ChatMode.Recall.getCode() == receiveMessage.getMode()){
			String rid = receiveMessage.getChatMessage().getRid();
			if(rid.startsWith("G")){
				this.groupNotify(context, receiveMessage);
			}else{
				this.singleNotify(context, receiveMessage, true);
			}
		}else if(ChatMode.Video.getCode() == receiveMessage.getMode()){
			this.singleNotify(context, receiveMessage, false);
		}else{
			context.fireChannelRead(receiveMessage);
		}
	}

	/**
	 * 单人通知
	 * @param context
	 * @param receiveMessage
	 */
	private void singleNotify(ChannelHandlerContext context, ReceiveMessage<NoticeMessage> receiveMessage, boolean offerQueue){
		String sid = this.channelFactory.getUserId(context.channel());
		receiveMessage.getChatMessage().setSid(sid);
		receiveMessage.getChatMessage().setStatus(MessageStatus.SUCCESS);
		List<ReceiveMessage> list = this.pushMessage(receiveMessage, offerQueue);
		context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
	}

	/**
	 * 群通知
	 * @param context
	 * @param receiveMessage
	 */
	private void groupNotify(ChannelHandlerContext context, ReceiveMessage<NoticeMessage> receiveMessage){
		String sid = this.channelFactory.getUserId(context.channel());
		receiveMessage.getChatMessage().setSid(sid);
		String groupId = receiveMessage.getChatMessage().getRid();
		String ownerId = sid;
		if(ChatMode.Recall.getCode() == receiveMessage.getMode()){
			ownerId = null;
		}
		List<String> members = groupMemberService.selectMembersUid(ownerId, groupId);
		if(members!=null && members.size()>0){
			receiveMessage.getChatMessage().setStatus(MessageStatus.SUCCESS);
			for(String userId : members){
				if(!userId.equals(sid)){
					this.pushMessage(userId, receiveMessage);
				}
			}
		}else{
			receiveMessage.getChatMessage().setStatus(MessageStatus.FAILURE);
		}
		context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(Collections.singletonList(receiveMessage))));
	}
}
