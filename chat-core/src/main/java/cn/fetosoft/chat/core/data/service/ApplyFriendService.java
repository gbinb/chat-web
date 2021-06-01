package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.ApplyFriend;
import cn.fetosoft.chat.core.data.form.ApplyFriendForm;
import cn.fetosoft.chat.core.enums.ApplyStatus;

/**
 * 申请加好友
 * @author guobingbing
 * @create 2020/12/17 16:16
 */
public interface ApplyFriendService extends BaseDataService<Long, ApplyFriend, ApplyFriendForm> {

	/**
	 * 申请加好友
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2021/1/4 17:43
	 * @param applyUid
	 * @param targetUid
	 * @param leaveMsg
	 * @return cn.fetosoft.chat.core.data.service.Result
	 * @version 1.0
	 */
	int applyFriend(String applyUid, String targetUid, String leaveMsg);

	/**
	 * 处理新增好友
	 * @param applyFriend
	 * @param status
	 * @return
	 */
	int friendHandler(ApplyFriend applyFriend, ApplyStatus status);

	/**
	 * 通过applyId查询
	 * @param applyId
	 * @return
	 */
	ApplyFriend selectByApplyId(String applyId);
}
