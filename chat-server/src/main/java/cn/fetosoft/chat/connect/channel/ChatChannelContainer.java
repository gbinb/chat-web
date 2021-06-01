package cn.fetosoft.chat.connect.channel;

import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户会话Channel容器
 * @author guobingbing
 * @wechat t_gbinb
 * @version 1.0
 * @create 2020/12/21 10:20
 */
@Component
public class ChatChannelContainer implements ChatChannelFactory {

	private static final Logger logger = LoggerFactory.getLogger(ChatChannelContainer.class);
	/**
	 * Channel会话，用于个人会话
	 */
	private final Map<String, Channel> channelMap = new ConcurrentHashMap<>(256);
	/**
	 * 用户ID与channelId的映射关系
	 */
	private final Map<String, String> userMap = new ConcurrentHashMap<>(256);

	/**
	 * 获取Channel
	 *
	 * @param userId
	 * @return io.netty.channel.Channel
	 * @author guobingbing
	 * @date 2020/12/21 11:02
	 */
	@Override
	public Channel getChannel(String userId) {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		return this.channelMap.get(userId);
	}

	/**
	 * 新增Channel
	 *
	 * @param userId
	 * @param channel
	 * @return void
	 * @author guobingbing
	 * @date 2020/12/21 11:04
	 */
	@Override
	public void addChannel(String userId, Channel channel) {
		synchronized (channelMap){
			boolean isAddChannel = true;
			if(this.channelMap.containsKey(userId)){
				Channel oldChannel = this.channelMap.get(userId);
				String channelId = oldChannel.id().asLongText();
				if(!channelId.equals(channel.id().asLongText())){
					this.channelMap.remove(userId);
					this.userMap.remove(channelId);
					oldChannel.close();
					logger.info("Close old channel ==> {}", channelId);
				}else{
					isAddChannel = false;
				}
			}
			if(isAddChannel) {
				this.channelMap.put(userId, channel);
				this.userMap.put(channel.id().asLongText(), userId);
			}
		}
	}

	/**
	 * 移除用户会话
	 *
	 * @param channelId
	 * @return void
	 * @author guobingbing
	 * @date 2020/12/21 15:30
	 */
	@Override
	public void removeChannel(String channelId) {
		String userId = this.userMap.get(channelId);
		if(StringUtils.isNotBlank(userId)) {
			synchronized (channelMap) {
				this.channelMap.remove(userId);
				this.userMap.remove(channelId);
			}
		}
	}

	/**
	 * 通过ChannelId获取UserId
	 *
	 * @param channel
	 * @return java.lang.String
	 * @author guobingbing
	 * @date 2020/12/22 14:09
	 */
	@Override
	public String getUserId(Channel channel) {
		if(channel==null){
			return StringUtils.EMPTY;
		}
		String channelId = channel.id().asLongText();
		return this.userMap.get(channelId);
	}

	/**
	 * 获取容器中Channel的数量
	 * @return
	 */
	@Override
	public int containerSize() {
		return this.channelMap.size();
	}
}
