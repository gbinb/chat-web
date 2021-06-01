package cn.fetosoft.chat.core.exceptions;

import cn.fetosoft.chat.core.adapter.HandleStatus;

/**
 * 参数转换异常
 * @author guobingbing
 * @create 2020/11/27 19:52
 */
public class ConversionException extends RuntimeException {

	private String code;

	public ConversionException(HandleStatus handleStatus) {
		super(handleStatus.getMessage());
		this.code = handleStatus.getCode();
	}

	public ConversionException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
