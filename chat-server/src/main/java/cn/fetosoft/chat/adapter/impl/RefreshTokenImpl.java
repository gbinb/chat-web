package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.res.UserLoginRes;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseRequest;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.service.UserKeyService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.encrypt.JwtToken;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证token有效性
 * @author guobingbing
 * @create 2020-12-27 09:43
 */
@Component(ChatServiceName.REFRESH_TOKEN)
public class RefreshTokenImpl extends AbstractChatHandle<BaseRequest, UserLoginRes> {

	@Autowired
	private UserService userService;
	@Autowired
	private UserKeyService userKeyService;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected BaseRequest convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, BaseRequest.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<UserLoginRes> doHandle(BaseRequest req) {
		RespResult<UserLoginRes> result = RespResult.createSuccess();
		String userId = req.getUserId();
		User user = userService.selectByUserId(userId);
		if(user!=null){
			UserLoginRes res = new UserLoginRes();
			Map<String, String> map = new HashMap<>(8);
			map.put("userId", user.getUserId());
			map.put("mobile", user.getMobile());
			String token = JwtToken.createToken(user.getUserId(), systemConfig.getJwt().getExpireDays(),
					systemConfig.getJwt().getSecretKey(), map);
			res.setUserId(user.getUserId());
			res.setRegisterTime(user.getCreateTime().getTime());
			res.setMobile(user.getMobile());
			res.setNickName(user.getNickName());
			res.setAvatar(user.getAvatar());
			res.setGender(user.getGender());
			res.setCountry(user.getCountry());
			res.setSignature(user.getSignature());
			res.setToken(token);
			UserKey userKey = userKeyService.selectByUserId(user.getUserId());
			res.setPublicKey(userKey.getPublicKey());
			result.setData(res);
		}else{
			result.setStatus(RespCode.NEED_LOGIN);
		}
		return result;
	}
}
