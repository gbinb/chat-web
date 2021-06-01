package cn.fetosoft.chat.core.enums;

/**
 * 通用状态
 * @author guobingbing
 * @create 2020/12/18 10:33
 */
public enum CommonStatus {

	/**
	 * 待处理
	 */
	Pending("0", "待处理"),

	Processing("1", "处理中"),

	SUCCESS("2", "成功"),

	Failure("3", "失败");

	private String code;
	private String text;

	CommonStatus(String code, String text){
		this.code = code;
		this.text = text;
	}

	public String getCode(){
		return this.code;
	}

	public String getText(){
		return this.text;
	}
}
