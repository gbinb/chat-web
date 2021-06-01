package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.SendSmsCodeReq;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import cn.fetosoft.chat.core.sms.SmsContent;
import cn.fetosoft.chat.core.sms.SmsResult;
import cn.fetosoft.chat.core.sms.SmsService;
import cn.fetosoft.chat.core.utils.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 发送短信验证码
 * @author guobingbing
 * @create 2020/12/28 14:12
 */
@Component(ChatServiceName.SEND_SMS_CODE)
public class SendSmsCodeImpl extends AbstractChatHandle<SendSmsCodeReq, BaseResponse> implements UnNeedVerifyToken {

	private static final Logger logger = LoggerFactory.getLogger(SendSmsCodeImpl.class);
	@Autowired
	private RedissonTemplate redissonTemplate;
	@Autowired
	private SystemConfig config;
	@Autowired
	private SmsService smsService;
	private Set<String> mobileSet = new HashSet<>(16);

	public SendSmsCodeImpl(){
		mobileSet.add("18699999999");
		mobileSet.add("15311111111");
		mobileSet.add("18600567899");
		mobileSet.add("15696801082");
		mobileSet.add("13313093253");
		mobileSet.add("18300702318");
	}

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected SendSmsCodeReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, SendSmsCodeReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<BaseResponse> doHandle(SendSmsCodeReq req) {
		RespResult<BaseResponse> result = RespResult.create(RespCode.BUSI_ERROR);

		//测试手机号
		if(this.sendTestMobile(req.getMobile())){
			result.setStatus(RespCode.SUCCESS);
			return result;
		}

		String limit = redissonTemplate.getString(req.getClientIp());
		int count = 0;
		if(StringUtils.isNotBlank(limit)){
			count = Integer.parseInt(limit);
		}
		if(count>config.getSms().getRateLimit()){
			result.setMessage("发送频率超限，请30分钟后再试");
			return result;
		}
		String code = RandomUtil.getLenRandom(6);
		SmsContent smsContent = new SmsContent();
		smsContent.setTemplate(config.getSms().getTemplate());
		smsContent.setMobiles(new String[]{req.getMobile()});
		smsContent.addParam(code);
		SmsResult smsResult = smsService.sendMsg(smsContent);
		if(smsResult.isSuccess()){
			redissonTemplate.setString(req.getMobile(), code, 60*5);
			result.setStatus(RespCode.SUCCESS);
			logger.info("{}=========>{}", req.getMobile(), code);
		}else{
			result.setMessage("短信发送失败");
		}
		count++;
		redissonTemplate.setString(req.getClientIp(), String.valueOf(count), 60*30);
		return result;
	}

	/**
	 * 测试手机号
	 * @param mobile
	 * @return
	 */
	private boolean sendTestMobile(String mobile){
		if(mobileSet.contains(mobile)){
			redissonTemplate.setString(mobile, "666666", 60*5);
			return true;
		}else{
			return false;
		}
	}
}
