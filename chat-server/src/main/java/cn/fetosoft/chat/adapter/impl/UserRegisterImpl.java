package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.UserRegisterReq;
import cn.fetosoft.chat.adapter.res.UserRegisterRes;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.service.SequenceService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.encrypt.HmacSHA1Signer;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.enums.UserStatus;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 用户注册
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/15 19:39
 */
@Component(ChatServiceName.USER_REGISTER)
public class UserRegisterImpl extends AbstractChatHandle<UserRegisterReq, UserRegisterRes> implements UnNeedVerifyToken {

	private static final Logger logger = LoggerFactory.getLogger(UserRegisterImpl.class);
	@Autowired
	private UserService userService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private RedissonTemplate redissonTemplate;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected UserRegisterReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, UserRegisterReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<UserRegisterRes> doHandle(UserRegisterReq req) {
		RespResult<UserRegisterRes> result = RespResult.create(RespCode.BUSI_ERROR);

		String mobile = redissonTemplate.getString(req.getAuthCode());
		if(StringUtils.isBlank(mobile)){
			result.setMessage("注册信息已过期请重新获取验证码");
			return result;
		}
		User user = userService.selectByMobile(mobile);
		if(user!=null){
			result.setMessage("手机号已经被注册");
			return result;
		}
		user = new User();
		user.setUserId(sequenceService.genUserId());
		user.setUserName(mobile);
		user.setNickName(req.getNickName());
		user.setMobile(mobile);
		user.setPassWord(HmacSHA1Signer.signString(req.getPassword(), systemConfig.getHmacKey()));
		user.setStatus(UserStatus.Normal.getCode());
		user.setAvatar(systemConfig.getDefaultAvatar());
		user.setCreateTime(new Date());
		try {
			int flag = userService.createUserAndKey(user);
			if(flag>0){
				result.setStatus(RespCode.SUCCESS);
				result.setMessage("注册成功");
			}
		} catch (Exception e) {
			result.setMessage("注册失败");
			logger.error("userRegister", e);
		}
		return result;
	}
}
