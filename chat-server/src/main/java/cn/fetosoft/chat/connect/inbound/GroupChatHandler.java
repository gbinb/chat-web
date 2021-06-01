package cn.fetosoft.chat.connect.inbound;

import cn.fetosoft.chat.connect.chat.ChatMessage;
import cn.fetosoft.chat.connect.chat.ChatMode;
import cn.fetosoft.chat.connect.chat.MessageStatus;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import cn.fetosoft.chat.core.data.entity.GroupMember;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import cn.fetosoft.chat.queue.MessageQueue;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 只负责处理单群聊天
 * @author guobingbing
 * @create 2020/12/24 15:54
 */
@Component
@ChannelHandler.Sharable
public class GroupChatHandler extends AbstractMessageHandler<ChatMessage> {

	private static final Logger logger = LoggerFactory.getLogger(GroupChatHandler.class);
	@Autowired
	private GroupMemberService groupMemberService;
	@Autowired
	private MessageQueue<ChatMessage> messageQueue;

	@Override
	protected void channelRead0(ChannelHandlerContext context, ReceiveMessage<ChatMessage> receiveMessage) throws Exception {
		if(ChatMode.Many.getCode() == receiveMessage.getMode()){
			//群聊
			String sid = this.channelFactory.getUserId(context.channel());
			logger.info("sid >>> {} >>> {}", sid, context.channel().id().asLongText());
			receiveMessage.getChatMessage().setSid(sid);
			List<ReceiveMessage> list = Collections.singletonList(receiveMessage);

			//判断是否为群成员，否则不可发送信息
			String groupId = receiveMessage.getChatMessage().getRid();
			GroupMemberForm form = new GroupMemberForm();
			form.setUserId(sid);
			form.setGroupId(groupId);
			int total = groupMemberService.selectCountByForm(form);
			if(total<=0){
				receiveMessage.getChatMessage().setStatus(MessageStatus.NOT_FRIEND);
				context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
				return;
			}

			List<GroupMember> members = this.getMembers(groupId);
			//群成员已经不存在
			if(CollectionUtils.isEmpty(members)){
				receiveMessage.getChatMessage().setStatus(MessageStatus.FAILURE);
				context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
				return;
			}

			receiveMessage.getChatMessage().setStatus(MessageStatus.SUCCESS);
			receiveMessage.setRt(System.currentTimeMillis());
			for(GroupMember member : members){
				if(member.getUserId().equals(receiveMessage.getChatMessage().getSid())){
					continue;
				}
				Channel channel = channelFactory.getChannel(member.getUserId());
				if(channel!=null){
					channel.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
				}else{
					messageQueue.offer(member.getUserId(), receiveMessage);
				}
			}
			context.writeAndFlush(new TextWebSocketFrame(this.encryptMessage(list)));
		}else{
			context.fireChannelRead(receiveMessage);
		}
	}

	/**
	 * 获取群成员
	 * @param groupId
	 * @return
	 */
	private List<GroupMember> getMembers(String groupId){
		GroupMemberForm form = new GroupMemberForm();
		form.setGroupId(groupId);
		form.setRows(0);
		return groupMemberService.selectListByForm(form);
	}
}
