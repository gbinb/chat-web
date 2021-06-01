package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetProfileReq;
import cn.fetosoft.chat.adapter.res.GetProfileRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.data.service.UserKeyService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.Relationship;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取用户信息
 * @author guobingbing
 * @create 2020/12/29 14:01
 */
@Component(ChatServiceName.GET_PROFILE)
public class GetProfileImpl extends AbstractChatHandle<GetProfileReq, GetProfileRes> {

	@Autowired
	private UserService userService;
	@Autowired
	private UserKeyService userKeyService;
	@Autowired
	private FriendService friendService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetProfileReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetProfileReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetProfileRes> doHandle(GetProfileReq req) {
		RespResult<GetProfileRes> result = RespResult.createSuccess();
		User user = null;
		Friend friend = null;
		GetProfileRes res = new GetProfileRes();
		if(StringUtils.isBlank(req.getTargetUserId())) {
			user = userService.selectByUserId(req.getUserId());
			UserKey userKey = userKeyService.selectByUserId(user.getUserId());
			res.setPublicKey(userKey.getPublicKey());
			res.setRelationship(Relationship.Myself.getCode());
		}else{
			user = userService.selectByUserId(req.getTargetUserId());
			friend = friendService.getFriend(req.getUserId(), req.getTargetUserId());
			if(friend!=null){
				res.setRelationship(Relationship.Friend.getCode());
			}else{
				res.setRelationship(Relationship.Stranger.getCode());
			}
		}
		if(user!=null){
			res.setUserId(user.getUserId());
			res.setAvatar(user.getAvatar());
			res.setNickName(user.getNickName());
			res.setCountry(user.getCountry());
			res.setMobile(user.getMobile());
			res.setGender(user.getGender());
			res.setSignature(user.getSignature());
			res.setRegisterTime(user.getCreateTime().getTime());
			result.setData(res);
		}else{
			result.setStatus(RespCode.HANDLE_ERROR).setMessage("用户不存在");
		}
		return result;
	}
}
