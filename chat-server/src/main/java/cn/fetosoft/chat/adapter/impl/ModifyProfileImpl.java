package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.ModifyProfileReq;
import cn.fetosoft.chat.adapter.res.ModifyProfileRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.exceptions.ParamException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 修改个人信息
 * @author guobingbing
 * @create 2020/12/30 9:28
 */
@Component(ChatServiceName.MODIFY_PROFILE)
public class ModifyProfileImpl extends AbstractChatHandle<ModifyProfileReq, ModifyProfileRes> {

	private static final Logger logger = LoggerFactory.getLogger(ModifyProfileImpl.class);
	@Autowired
	private UserService userService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected ModifyProfileReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, ModifyProfileReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<ModifyProfileRes> doHandle(ModifyProfileReq req) {
		RespResult<ModifyProfileRes> result = RespResult.create(RespCode.BUSI_ERROR);
		ModifyProfileRes res = new ModifyProfileRes();
		result.setData(res);

		User user = userService.selectByUserId(req.getUserId());
		if(user!=null) {
			User modifyUser = new User();
			modifyUser.setId(user.getId());
			modifyUser.setUserId(user.getUserId());
			modifyUser.setNickName(req.getNickName());
			modifyUser.setAvatar(req.getAvatar());
			modifyUser.setCountry(req.getCountry());
			modifyUser.setSignature(req.getSignature());
			modifyUser.setGender(req.getGender());
			modifyUser.setModifyTime(new Date());
			try{
				int flag = userService.modifyUser(modifyUser);
				if(flag>0){
					user = userService.selectByUserId(req.getUserId());
					res.setUserId(user.getUserId());
					res.setNickName(user.getNickName());
					res.setAvatar(user.getAvatar());
					res.setSignature(user.getSignature());
					res.setCountry(user.getCountry());
					res.setMobile(user.getMobile());
					res.setGender(user.getGender());
					result.setStatus(RespCode.SUCCESS);
				}else{
					result.setMessage("更新失败");
				}
			}catch(ParamException e){
				result.setMessage(e.getMessage());
				logger.error("modifyProfile", e);
			}
		}else{
			result.setMessage("用户不存在");
		}
		return result;
	}
}
