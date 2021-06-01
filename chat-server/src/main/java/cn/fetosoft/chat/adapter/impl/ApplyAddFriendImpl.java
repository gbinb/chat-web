package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.ApplyAddFriendReq;
import cn.fetosoft.chat.adapter.res.ApplyAddFriendRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.FriendForm;
import cn.fetosoft.chat.core.data.service.ApplyFriendService;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 申请加好友
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/17 16:43
 */
@Component(ChatServiceName.APPLY_ADD_FRIEND)
public class ApplyAddFriendImpl extends AbstractChatHandle<ApplyAddFriendReq, ApplyAddFriendRes> {

	@Autowired
	private ApplyFriendService applyFriendService;
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
	protected ApplyAddFriendReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, ApplyAddFriendReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<ApplyAddFriendRes> doHandle(ApplyAddFriendReq req) {
		RespResult<ApplyAddFriendRes> result = RespResult.create(RespCode.BUSI_ERROR);
		User user = userService.selectByUserId(req.getTargetUserId());
		if(user!=null) {
			if (user.getUserId().equals(req.getUserId())) {
				return result.setMessage("不能加自己为好友");
			}
			FriendForm friendForm = new FriendForm();
			friendForm.setUserId(req.getUserId());
			friendForm.setFriendId(req.getTargetUserId());
			int count = friendService.selectCountByForm(friendForm);
			if (count > 0) {
				return result.setMessage("已经是好友了");
			}
			int flag = applyFriendService.applyFriend(req.getUserId(), req.getTargetUserId(), req.getLeaveMsg());
			if(flag>0){
				result.setStatus(RespCode.SUCCESS);
			}else{
				result.setMessage("申请好友失败");
			}
		}else{
			result.setMessage("好友不存在");
		}
		return result;
	}

}
