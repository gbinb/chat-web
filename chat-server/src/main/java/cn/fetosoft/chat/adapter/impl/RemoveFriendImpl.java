package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.RemoveFriendReq;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.form.FriendForm;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * 删除好友
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/31 11:02
 */
@Component(ChatServiceName.REMOVE_FRIEND)
public class RemoveFriendImpl extends AbstractChatHandle<RemoveFriendReq, BaseResponse> {

	@Autowired
	private FriendService friendService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected RemoveFriendReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, RemoveFriendReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<BaseResponse> doHandle(RemoveFriendReq req) {
		RespResult<BaseResponse> result = RespResult.create(RespCode.HANDLE_ERROR);
		FriendForm form = new FriendForm();
		form.setUserId(req.getUserId());
		form.setFriendId(req.getFriendId());
		List<Friend> friendList = friendService.selectListByForm(form);
		if(!CollectionUtils.isEmpty(friendList)){
			int flag = friendService.removeFriend(friendList.get(0));
			if(flag>0){
				result.setStatus(RespCode.SUCCESS).setMessage("删除成功");
				//将本人从对方好友中也删除
				FriendForm otherForm = new FriendForm();
				otherForm.setUserId(req.getFriendId());
				otherForm.setFriendId(req.getUserId());
				List<Friend> otherList = friendService.selectListByForm(otherForm);
				if(!CollectionUtils.isEmpty(otherList)){
					friendService.removeFriend(otherList.get(0));
				}
			}else{
				result.setMessage("好友不存在或已删除");
			}
		}else{
			result.setMessage("好友不存在或已删除");
		}
		return result;
	}
}
