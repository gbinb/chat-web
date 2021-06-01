package cn.fetosoft.chat.connect.chat;

/**
 * 聊天模式
 * @author guobingbing
 * @create 2020/12/22 10:37
 */
public enum ChatMode {

	/**
	 * 心跳
	 */
	Heart(0),

	/**
	 * 单人
	 */
	Single(1),

	/**
	 * 多人
	 */
	Many(2),

	/**
	 * 广播
	 */
	Broadcast(3),

	/**
	 * 加好友通知
	 */
	AddFriend(4),

	/**
	 * 接受好友申请
	 */
	AcceptFriend(5),

	/**
	 * 创建群聊
	 */
	CreateGroup(6),

	/**
	 * 解散群
	 */
	DismissGroup(7),

	/**
	 * 呼叫
	 */
	Call(8),

	/**
	 * 消息撤回
	 */
	Recall(9),

	/**
	 * 视频相关
	 */
	Video(10);

	private int code;

	ChatMode(int code){
		this.code = code;
	}

	public int getCode(){
		return this.code;
	}
}
