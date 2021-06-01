package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetApplyFriendReq;
import cn.fetosoft.chat.adapter.res.GetApplyFriendRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.ApplyFriend;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.form.ApplyFriendForm;
import cn.fetosoft.chat.core.data.service.ApplyFriendService;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.enums.Relationship;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * 获取新好友信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/18 15:18
 */
@Component(ChatServiceName.GET_APPLY_FRIEND)
public class GetApplyFriendImpl extends AbstractChatHandle<GetApplyFriendReq, GetApplyFriendRes> {

	@Autowired
	private ApplyFriendService applyFriendService;
	@Autowired
	private FriendService friendService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetApplyFriendReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetApplyFriendReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetApplyFriendRes> doHandle(GetApplyFriendReq req) {
		RespResult<GetApplyFriendRes> result = RespResult.createSuccess();
		GetApplyFriendRes res = new GetApplyFriendRes();
		result.setData(res);

		ApplyFriendForm form = new ApplyFriendForm();
		form.setTargetUserId(req.getUserId());
		form.setDescField("create_time");
		form.setStatus(req.getStatus());
		form.setRows(0);
		List<ApplyFriend> list = applyFriendService.selectListByForm(form);
		if(!CollectionUtils.isEmpty(list)){
			for(ApplyFriend f : list){
				GetApplyFriendRes.Friend friend = new GetApplyFriendRes.Friend();
				friend.setApplyId(f.getApplyId());
				ShareUser shareUser = new ShareUser(f.getApplyUserId(), f.getApplyNickName());
				shareUser.setAvatar(f.getApplyAvatar());
				shareUser.setCountry(f.getApplyCountry());
				Friend friendInfo = friendService.getFriend(req.getUserId(), f.getApplyUserId());
				if(friendInfo!=null){
					shareUser.setRelationship(Relationship.Friend.getCode());
				}else{
					shareUser.setRelationship(Relationship.Stranger.getCode());
				}
				friend.setUser(shareUser);
				friend.setStatus(f.getStatus());
				friend.setLeaveMsg(f.getLeaveMsg());
				friend.setApplyTime(f.getCreateTime().getTime());
				res.addFriend(friend);
			}
		}
		return result;
	}
}
