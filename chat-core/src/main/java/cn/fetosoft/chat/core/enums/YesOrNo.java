package cn.fetosoft.chat.core.enums;

/**
 * 是或否
 * @author guobingbing
 * @create 2020/12/18 17:33
 */
public enum YesOrNo {

	/**
	 * 是
	 */
	Yes("1"),

	/**
	 * 否
	 */
	No("0");

	private String code;

	YesOrNo(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}
}
