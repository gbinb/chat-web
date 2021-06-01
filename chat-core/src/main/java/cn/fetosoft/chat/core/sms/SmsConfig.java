package cn.fetosoft.chat.core.sms;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/18 10:26
 */
@Setter
@Getter
public class SmsConfig {

	/**
	 * 服务商
	 */
	private String merchant;

	/**
	 * 用户名
	 */
	private String key;

	/**
	 * 密码
	 */
	private String secret;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 短信网关
	 */
	private String gatewayUrl;

	/**
	 * 短信码模板号
	 */
	private String template;

	/**
	 * IP发送频率限制
	 */
	private int rateLimit = 5;
}
