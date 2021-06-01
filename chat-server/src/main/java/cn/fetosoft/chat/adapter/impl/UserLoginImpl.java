package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.req.UserLoginReq;
import cn.fetosoft.chat.adapter.res.UserLoginRes;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.form.UserForm;
import cn.fetosoft.chat.core.data.service.UserKeyService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.encrypt.HmacSHA1Signer;
import cn.fetosoft.chat.core.encrypt.JwtToken;
import cn.fetosoft.chat.core.enums.LoginType;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登录
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/11 11:04
 */
@Component(ChatServiceName.USER_LOGIN)
public class UserLoginImpl extends AbstractChatHandle<UserLoginReq, UserLoginRes> implements UnNeedVerifyToken {

	@Autowired
	private UserService userService;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private UserKeyService userKeyService;
	@Autowired
	private RedissonTemplate redissonTemplate;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected UserLoginReq convertParam(JSONObject jsonObject) throws ConversionException {
		UserLoginReq req = JSON.toJavaObject(jsonObject, UserLoginReq.class);
		if(LoginType.Verify.getCode()==req.getType()){
			if(StringUtils.isBlank(req.getSmsCode()) || req.getSmsCode().length()!=6){
				throw new ConversionException(RespCode.PARAMS_ERROR.setMessage("短信码格式不正确"));
			}
		}else if(LoginType.Password.getCode()==req.getType()){
			if(StringUtils.isBlank(req.getPassword()) || req.getPassword().length()<6){
				throw new ConversionException(RespCode.PARAMS_ERROR.setMessage("密码格式不正确"));
			}
		}
		return req;
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<UserLoginRes> doHandle(UserLoginReq req) {
		RespResult<UserLoginRes> result = RespResult.create(RespCode.BUSI_ERROR);
		User user = null;
		if(LoginType.Verify.getCode()==req.getType()){
			String smsCode = redissonTemplate.getString(req.getMobile());
			if(req.getSmsCode().equals(smsCode)){
				user = userService.selectByMobile(req.getMobile());
				redissonTemplate.deleteString(req.getMobile());
			}else{
				result.setMessage("验证码不正确");
				return result;
			}
		}else if(LoginType.Password.getCode()==req.getType()){
			UserForm form = new UserForm();
			form.setMobile(req.getMobile());
			form.setPassWord(HmacSHA1Signer.signString(req.getPassword(), systemConfig.getHmacKey()));
			List<User> list = userService.selectListByForm(form);
			if(!CollectionUtils.isEmpty(list) || list.size()>1){
				user = list.get(0);
			}else{
				result.setStatus(RespCode.BUSI_ERROR).setMessage("用户名或密码不正确");
				return result;
			}
		}
		if(user!=null){
			Map<String, String> map = new HashMap<>(8);
			map.put("userId", user.getUserId());
			map.put("mobile", user.getMobile());
			String token = JwtToken.createToken(user.getUserId(), systemConfig.getJwt().getExpireDays(),
					systemConfig.getJwt().getSecretKey(), map);
			UserKey userKey = userKeyService.selectByUserId(user.getUserId());
			UserLoginRes res = new UserLoginRes();
			res.setUserId(user.getUserId());
			res.setMobile(user.getMobile());
			res.setNickName(user.getNickName());
			res.setAvatar(user.getAvatar());
			res.setCountry(user.getCountry());
			res.setGender(user.getGender());
			res.setSignature(user.getSignature());
			res.setToken(token);
			res.setPublicKey(userKey.getPublicKey());
			res.setRegisterTime(user.getCreateTime().getTime());
			result.setStatus(RespCode.SUCCESS);
			result.setData(res);
		}else{
			result.setMessage("登录失败，请检查输入是否正确");
		}
		return result;
	}
}
