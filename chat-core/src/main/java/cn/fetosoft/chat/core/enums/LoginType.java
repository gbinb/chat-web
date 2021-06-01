package cn.fetosoft.chat.core.enums;

/**
 * 登录方式
 * @author guobingbing
 * @create 2020/12/28 16:23
 */
public enum LoginType {

	/**
	 * 短信验证码
	 */
	Verify(1),

	Password(2);

	private int code;

	LoginType(int code){
		this.code = code;
	}

	public int getCode(){
		return this.code;
	}
}
