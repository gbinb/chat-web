package cn.fetosoft.chat.connect.chat;

/**
 * 消息状态
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/4 20:14
 */
public abstract class MessageStatus {

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;

	/**
	 * 失败或错误
	 */
	public static final int FAILURE = 2;

	/**
	 * 发送中
	 */
	public static final int SENDING = 3;

	/**
	 * 不是好友
	 */
	public static final int NOT_FRIEND = 4;
}
