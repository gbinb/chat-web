package cn.fetosoft.chat.core.sms;

/**
 * 短信服务
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/18 9:07
 */
public interface SmsService {

	/**
	 * 发送短信
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2021/1/18 9:09
	 * @param content
	 * @return com.zlhn.framework.sms.SmsResult
	 * @version 1.0
	 */
	SmsResult sendMsg(SmsContent content);
}
