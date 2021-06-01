package cn.fetosoft.chat.core.enums;

/**
 * @author guobingbing
 * @create 2020/12/18 10:42
 */
public enum ApplyStatus {

	/**
	 * 待处理
	 */
	Pending("0", "待处理"),

	Agree("1", "通过"),

	Refuse("2", "拒绝"),

	Ignore("3", "忽略");

	private String code;
	private String text;

	ApplyStatus(String code, String text){
		this.code = code;
		this.text = text;
	}

	public String getCode(){
		return this.code;
	}

	public String getText(){
		return this.text;
	}

	public static ApplyStatus getStatus(String code){
		ApplyStatus[] items = ApplyStatus.values();
		for(ApplyStatus s : items){
			if(s.getCode().equals(code)){
				return s;
			}
		}
		return null;
	}
}
