package cn.fetosoft.chat.core.sms;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/18 9:08
 */
@Setter
@Getter
public class SmsResult {

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 错误码
	 */
	private String errorCode;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	public SmsResult(boolean success){
		this.success = success;
	}
}
