package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.VerifyMobileReq;
import cn.fetosoft.chat.adapter.res.VerifyMobileRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import cn.fetosoft.chat.core.utils.UUidUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证手机号
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 15:02
 */
@Component(ChatServiceName.VERIFY_MOBILE)
public class VerifyMobileImpl extends AbstractChatHandle<VerifyMobileReq, VerifyMobileRes> implements UnNeedVerifyToken {

	@Autowired
	private RedissonTemplate redissonTemplate;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected VerifyMobileReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, VerifyMobileReq.class);
	}

	/**
	 * 详细业务处理
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<VerifyMobileRes> doHandle(VerifyMobileReq req) {
		RespResult<VerifyMobileRes> result = RespResult.create(RespCode.BUSI_ERROR);
		VerifyMobileRes res = new VerifyMobileRes();
		result.setData(res);

		String code = redissonTemplate.getString(req.getMobile());
		if(StringUtils.isNotBlank(code) && req.getSmsCode().equals(code)){
			String key = UUidUtil.buildUUID();
			redissonTemplate.setString(key, req.getMobile(), 15*60);
			res.setAuthCode(key);
			result.setStatus(RespCode.SUCCESS);
		}else{
			result.setMessage("验证码不正确或已过期");
		}
		redissonTemplate.deleteString(req.getMobile());
		return result;
	}
}
