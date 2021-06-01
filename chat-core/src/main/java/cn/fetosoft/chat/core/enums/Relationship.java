package cn.fetosoft.chat.core.enums;

/**
 * 用户关系
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/6 9:44
 */
public enum Relationship {

	/**
	 * 本人
	 */
	Myself(1),

	/**
	 * 好友
	 */
	Friend(2),

	/**
	 * 陌生人
	 */
	Stranger(3);

	private int code;

	Relationship(int code){
		this.code = code;
	}

	public int getCode(){
		return this.code;
	}
}
