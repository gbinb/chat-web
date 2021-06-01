package cn.fetosoft.chat.core.exceptions;

import cn.fetosoft.chat.core.adapter.HandleStatus;

/**
 * 参数异常
 * @author guobingbing
 * @create 2020/12/8 10:02
 */
public class ParamException extends RuntimeException {

	private String code;

	public ParamException(String message){
		this(null, message);
	}

	public ParamException(HandleStatus handleStatus) {
		super(handleStatus.getMessage());
		this.code = handleStatus.getCode();
	}

	public ParamException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
