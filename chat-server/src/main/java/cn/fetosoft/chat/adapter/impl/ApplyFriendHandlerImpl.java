package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.ApplyFriendHandlerReq;
import cn.fetosoft.chat.adapter.res.ApplyFriendHandlerRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.ApplyFriend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.service.ApplyFriendService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.ApplyStatus;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新朋友的处理
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/18 17:03
 */
@Component(ChatServiceName.APPLY_FRIEND_HANDLER)
public class ApplyFriendHandlerImpl extends AbstractChatHandle<ApplyFriendHandlerReq, ApplyFriendHandlerRes> {

	@Autowired
	private ApplyFriendService applyFriendService;
	@Autowired
	private UserService userService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected ApplyFriendHandlerReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, ApplyFriendHandlerReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<ApplyFriendHandlerRes> doHandle(ApplyFriendHandlerReq req) {
		RespResult<ApplyFriendHandlerRes> result = RespResult.createSuccess();
		ApplyFriendHandlerRes res = new ApplyFriendHandlerRes();
		result.setData(res);

		ApplyFriend friend = applyFriendService.selectByApplyId(req.getApplyId());
		if(friend!=null && friend.getTargetUserId().equals(req.getUserId())){
			if(ApplyStatus.Agree.getCode().equals(friend.getStatus())){
				result.setStatus(RespCode.BUSI_ERROR.setMessage("好友已通过"));
				this.getApplyUser(res, friend.getApplyUserId());
				return result;
			}else if(ApplyStatus.Refuse.getCode().equals(friend.getStatus())){
				result.setStatus(RespCode.BUSI_ERROR.setMessage("对方已拒绝"));
				return result;
			}else if(ApplyStatus.Ignore.getCode().equals(friend.getStatus())){
				result.setStatus(RespCode.BUSI_ERROR.setMessage("对方已忽略"));
				return result;
			}
			int flag = applyFriendService.friendHandler(friend, ApplyStatus.getStatus(req.getStatus()));
			if(flag>0){
				if(ApplyStatus.Agree.getCode().equals(friend.getStatus())){
					this.getApplyUser(res, friend.getApplyUserId());
				}
				result.setMessage("处理成功");
			}else{
				result.setStatus(RespCode.BUSI_ERROR.setMessage("添加好友失败"));
			}
		}else{
			result.setStatus(RespCode.BUSI_ERROR.setMessage("好友不存在"));
		}
		return result;
	}

	/**
	 * 设置申请人信息
	 * @param res
	 * @param applyUserId
	 */
	private void getApplyUser(ApplyFriendHandlerRes res, String applyUserId){
		User user = userService.selectByUserId(applyUserId);
		res.setUserId(user.getUserId());
		res.setNickName(user.getNickName());
		res.setAvatar(user.getAvatar());
		res.setCountry(user.getCountry());
		res.setMobile(user.getMobile());
	}
}
