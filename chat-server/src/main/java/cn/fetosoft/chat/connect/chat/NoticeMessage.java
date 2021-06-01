package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * 通知消息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/4 18:43
 */
@Setter
@Getter
public class NoticeMessage extends BaseMessage {

	/**
	 * 消息内容
	 */
	private String message;

	/**
	 *
	 */
	private String sname;

	/**
	 * mode-6：1-创建群，2-加入群,
	 * mode-7：3-解散群，4-退群，5-踢出群
	 * mode-9： 10-撤消息
	 * mode-8: 12-接听，13-挂断  14-切换销毁时间 15-连续呼叫
	 */
	private int detailType;

	/**
	 * 申请结果 1-接受，2-拒绝
	 */
	private Integer applyResult;

	/**
	 * 通道ID
	 */
	private String channelName;

	/**
	 * 媒体token
	 */
	private String mediaToken;
}
