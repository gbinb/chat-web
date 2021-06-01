package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息
 * 消息发送状态，1-成功 2-已删除好友 500-发送失败
 * @author guobingbing
 * @create 2020/12/22 18:07
 */
@Setter
@Getter
public class ChatMessage extends BaseMessage {

	/**
	 * 消息类型：10-文本，20-图片，30-语音，40-视频 50-定位 60-文件
	 */
	private int type;

	/**
	 * 0-未读，1-已读
	 */
	private int read;

	/**
	 * 消息场景
	 */
	private int detailType;

	/**
	 * 消息内容
	 */
	private ChatContent content;
}
