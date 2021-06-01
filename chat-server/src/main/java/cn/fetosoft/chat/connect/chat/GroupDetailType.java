package cn.fetosoft.chat.connect.chat;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:37
 */
public enum GroupDetailType {

	/**
	 * 创建
	 */
	Create(1),

	/**
	 * 加群
	 */
	Join(2),

	/**
	 * 解散
	 */
	Dismiss(3),

	/**
	 * 退群
	 */
	Quit(4),

	/**
	 * 移出群
	 */
	Remove(5);

	private int code;

	GroupDetailType(int code){
		this.code = code;
	}

	public int getCode(){
		return this.code;
	}
}
