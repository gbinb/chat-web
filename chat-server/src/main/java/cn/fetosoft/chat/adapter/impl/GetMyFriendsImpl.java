package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetMyFriendsReq;
import cn.fetosoft.chat.adapter.res.GetMyFriendsRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.form.FriendForm;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.utils.StringProccUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * 获取我的好友
 * @author guobingbing
 * @create 2020/12/16 20:14
 */
@Component(ChatServiceName.GET_MY_FRIENDS)
public class GetMyFriendsImpl extends AbstractChatHandle<GetMyFriendsReq, GetMyFriendsRes> {

	@Autowired
	private FriendService friendService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetMyFriendsReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetMyFriendsReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetMyFriendsRes> doHandle(GetMyFriendsReq req) {
		RespResult<GetMyFriendsRes> result = RespResult.createSuccess();
		GetMyFriendsRes res = new GetMyFriendsRes();
		result.setData(res);

		FriendForm form = new FriendForm();
		form.setRows(0);
		form.setUserId(req.getUserId());
		form.setSearchKey(req.getSearchKey());
		List<Friend> list = friendService.selectListByForm(form);
		if(!CollectionUtils.isEmpty(list)){
			for(Friend f : list){
				GetMyFriendsRes.MyFriend myFriend = new GetMyFriendsRes.MyFriend();
				myFriend.setAlias(f.getAlias());
				myFriend.setTag(f.getTag());
				myFriend.setNote(f.getNote());
				ShareUser user = new ShareUser();
				user.setUserId(f.getFriendId());
				user.setNickName(f.getFriendName());
				user.setMobile(StringProccUtil.proceeMobile(f.getFriendMobile()));
				user.setAvatar(f.getFriendAvatar());
				user.setCountry(f.getFriendCountry());
				myFriend.setUser(user);
				res.addFriend(myFriend);
			}
		}
		return result;
	}
}
