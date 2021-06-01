package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetFriendProfileReq;
import cn.fetosoft.chat.adapter.res.GetFriendProfileRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.Relationship;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.utils.StringProccUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询好友的详情
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/5 19:32
 */
@Component(ChatServiceName.GET_FRIEND_PROFILE)
public class GetFriendProfileImpl extends AbstractChatHandle<GetFriendProfileReq, GetFriendProfileRes> {

	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetFriendProfileReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetFriendProfileReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetFriendProfileRes> doHandle(GetFriendProfileReq req) {
		RespResult<GetFriendProfileRes> result = RespResult.createSuccess();
		GetFriendProfileRes res = new GetFriendProfileRes();
		result.setData(res);

		User user = userService.selectByUserId(req.getFriendId());
		if(user!=null){
			ShareUser shareUser = new ShareUser(user.getUserId(), user.getNickName());
			shareUser.setCountry(user.getCountry());
			shareUser.setAvatar(user.getAvatar());
			shareUser.setSignature(user.getSignature());

			if(req.getUserId().equals(req.getFriendId())){
				shareUser.setRelationship(Relationship.Myself.getCode());
			}else {
				Friend friend = friendService.getFriend(req.getUserId(), req.getFriendId());
				if (friend != null) {
					shareUser.setMobile(StringProccUtil.proceeMobile(user.getMobile()));
					shareUser.setRelationship(Relationship.Friend.getCode());
					res.setAlias(friend.getAlias());
					res.setTag(friend.getTag());
					res.setNote(friend.getNote());
				} else {
					shareUser.setRelationship(Relationship.Stranger.getCode());
				}
			}
			res.setUser(shareUser);
		}else{
			result.setStatus(RespCode.BUSI_ERROR).setMessage("未查询到好友信息");
		}
		return result;
	}
}
