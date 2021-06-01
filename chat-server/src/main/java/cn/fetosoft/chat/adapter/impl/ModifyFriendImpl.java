package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.ModifyFriendReq;
import cn.fetosoft.chat.adapter.res.ModifyFriendRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.FriendForm;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * 修改好友信息
 * @author guobingbing
 * @wechat t_gbinb
 * @date 2020/12/30 15:22
 * @version 1.0
 */
@Component(ChatServiceName.MODIFY_FRIEND)
public class ModifyFriendImpl extends AbstractChatHandle<ModifyFriendReq, ModifyFriendRes> {

	@Autowired
	private FriendService friendService;
	@Autowired
	private UserService userService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected ModifyFriendReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, ModifyFriendReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<ModifyFriendRes> doHandle(ModifyFriendReq req) {
		RespResult<ModifyFriendRes> result = RespResult.create(RespCode.BUSI_ERROR);
		FriendForm form = new FriendForm();
		form.setUserId(req.getUserId());
		form.setFriendId(req.getFriendId());
		List<Friend> friendList = friendService.selectListByForm(form);
		if(!CollectionUtils.isEmpty(friendList)){
			Friend friend = friendList.get(0);
			friend.setAlias(req.getAlias());
			friend.setTag(req.getTag());
			friend.setNote(req.getNote());
			int flag = friendService.modifyFriend(friend);
			if(flag>0){
				ModifyFriendRes res = new ModifyFriendRes();
				res.setAlias(friend.getAlias());
				res.setTag(friend.getTag());
				res.setNote(friend.getNote());

				User user = userService.selectByUserId(friend.getFriendId());
				ShareUser shareUser = new ShareUser(user.getUserId(), user.getNickName());
				shareUser.setAvatar(user.getAvatar());
				shareUser.setCountry(user.getCountry());
				shareUser.setMobile(user.getMobile());
				shareUser.setSignature(user.getSignature());
				res.setUser(shareUser);
				result.setData(res);
				result.setStatus(RespCode.SUCCESS);
			}else{
				result.setMessage("修改失败");
			}
		}else{
			result.setMessage("好友不存在");
		}
		return result;
	}
}
