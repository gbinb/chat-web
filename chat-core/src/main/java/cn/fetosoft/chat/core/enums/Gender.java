package cn.fetosoft.chat.core.enums;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/15 14:15
 */
public enum Gender {

	/**
	 * 男
	 */
	Man("M"),

	/**
	 * 女
	 */
	Woman("W");

	private String code;

	Gender(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}
}
