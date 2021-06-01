package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息内容
 * @author guobingbing
 * @create 2020/12/22 18:27
 */
@Setter
@Getter
public class ChatContent {

	/**
	 * 文本信息
	 */
	private String text;

	/**
	 * 图片消息
	 */
	private ChatImage image;

	/**
	 * 语音信息
	 */
	private ChatAudio audio;
}
