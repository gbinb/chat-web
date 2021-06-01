package cn.fetosoft.chat.connect.channel;

import io.netty.channel.Channel;

/**
 * 会话工厂
 * @author guobingbing
 * @create 2020/12/21 11:00
 */
public interface ChatChannelFactory {

	/**
	 * 获取Channel
	 * @author guobingbing 
	 * @date 2020/12/21 11:02
	 * @param userId
	 * @return io.netty.channel.Channel		
	 */	
	Channel getChannel(String userId);

	/**
	 * 新增Channel
	 * @author guobingbing
	 * @date 2020/12/21 11:04
	 * @param userId
	 * @param channel
	 * @return void
	 */
	void addChannel(String userId, Channel channel);

	/**
	 * 移除用户会话
	 * @author guobingbing
	 * @date 2020/12/21 15:30
	 * @param channelId
	 * @return void
	 */
	void removeChannel(String channelId);

	/**
	 * 通过Channel获取UserId
	 * @author guobingbing
	 * @date 2020/12/22 14:09
	 * @param channel
	 * @return java.lang.String
	 */
	String getUserId(Channel channel);

	/**
	 * 获取容器中Channel的数量
	 * @author guobingbing
	 * @date 2020/12/25 16:16
	 * @param
	 * @return int
	 */
	int containerSize();
}
