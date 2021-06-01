package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.ApplyFriend;
import cn.fetosoft.chat.core.data.form.ApplyFriendForm;

/**
 * @author guobingbing
 * @create 2020/12/17 16:15
 */
public interface ApplyFriendMapper extends BaseMapper<Long, ApplyFriend, ApplyFriendForm> {

	/**
	 * 通过applyId查询
	 * @param applyId
	 * @return
	 */
	ApplyFriend selectByApplyId(String applyId);
}
