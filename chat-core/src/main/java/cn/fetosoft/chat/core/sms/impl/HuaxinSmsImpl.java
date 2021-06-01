package cn.fetosoft.chat.core.sms.impl;

import cn.fetosoft.chat.core.sms.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 华信短信服务
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/19 14:00
 */
public class HuaxinSmsImpl implements SmsService {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(HuaxinSmsImpl.class);
	private SmsConfig smsConfig;

	public HuaxinSmsImpl(SmsConfig smsConfig){
		this.smsConfig = smsConfig;
	}

	/**
	 * 发送短信
	 *
	 * @param content
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2021/1/18 9:09
	 * @version 1.0
	 */
	@Override
	public SmsResult sendMsg(SmsContent content) {
		SmsResult result = new SmsResult(false);
		try{
			String template = content.getTemplate();
			template = String.format(template, content.getParams().toArray());
			String sm = URLEncoder.encode(template, "utf8");
			Map<String, Object> paramMap = new HashMap<>(16);
			paramMap.put("action", "send");
			paramMap.put("userid", smsConfig.getKey());
			paramMap.put("account", smsConfig.getAccount());
			paramMap.put("password", smsConfig.getSecret());

			//单一内容时群发  将手机号用;隔开
			paramMap.put("mobile", content.getMobiles()[0]);
			paramMap.put("content", sm);
			paramMap.put("sendTime", "");

			String resp = SmsSendClient.postData(smsConfig.getGatewayUrl(), paramMap);
			JSONObject json = JSON.parseObject(resp);
			logger.info(resp);
			if("success".equalsIgnoreCase(json.getString("returnstatus"))) {
				result.setSuccess(true);
			}
			result.setErrorMsg(json.getString("message"));
		}catch(Exception e){
			result.setErrorMsg(e.getMessage());
			logger.error("sendMsg", e);
		}
		return result;
	}
}
