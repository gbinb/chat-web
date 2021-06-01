package cn.fetosoft.chat.core.enums;

/**
 * 用户状态
 * @author guobingbing
 * @create 2020/12/16 15:53
 */
public enum UserStatus {

	/**
	 * 正常
	 */
	Normal("1");

	private String code;

	UserStatus(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}
}
