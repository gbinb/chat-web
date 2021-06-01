package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.UpdateKeyReq;
import cn.fetosoft.chat.adapter.res.UpdateKeyRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.service.UserKeyService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.encrypt.RSAUtils;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 更新AesKey
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/23 13:28
 */
@Component(ChatServiceName.UPDATE_KEY)
public class UpdateKeyImpl extends AbstractChatHandle<UpdateKeyReq, UpdateKeyRes> {

	private static final Logger logger = LoggerFactory.getLogger(UpdateKeyImpl.class);
	@Autowired
	private UserKeyService userKeyService;
	@Autowired
	private UserService userService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected UpdateKeyReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, UpdateKeyReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<UpdateKeyRes> doHandle(UpdateKeyReq req) {
		RespResult<UpdateKeyRes> result = RespResult.create(RespCode.HANDLE_ERROR);
		UpdateKeyRes res = new UpdateKeyRes();
		result.setData(res);

		User user = userService.selectByUserId(req.getUserId());
		if(user!=null){
			UserKey userKey = userKeyService.selectByUserId(req.getUserId());
			String privateKey = userKey.getPrivateKey();
			try {
				String aesKey = RSAUtils.decrypt(privateKey, req.getEncryptData());
				User updateUser = new User();
				updateUser.setId(user.getId());
				updateUser.setAesKey(aesKey);
				updateUser.setModifyTime(new Date());
				userService.updateByPrimaryKeySelective(updateUser);
				result.setStatus(RespCode.SUCCESS);
			} catch (Exception e) {
				result.setMessage("解密失败");
				logger.error("updateKey", e);
			}
		}else{
			result.setMessage("验证用户失败");
		}
		return result;
	}
}
