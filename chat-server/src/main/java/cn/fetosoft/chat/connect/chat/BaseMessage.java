package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/4 18:47
 */
@Setter
@Getter
public class BaseMessage {

	/**
	 * 消息ID
	 */
	private String mid;

	/**
	 * 发送信息用户
	 */
	private String sid;

	/**
	 * 接收信息的用户
	 */
	private String rid;

	/**
	 * 存活时间，单位为秒
	 */
	private int at;

	/**
	 * 消息发送状态：1-成功 2-失败 3-发送中 4-好友验证
	 */
	private int status;
}
