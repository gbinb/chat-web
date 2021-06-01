package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.GroupMember;
import cn.fetosoft.chat.core.data.entity.MyGroup;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/24 14:48
 */
public interface GroupMemberService extends BaseDataService<Long, GroupMember, GroupMemberForm> {

	/**
	 * 查询我的群信息
	 * @param form
	 * @return
	 */
	List<MyGroup> selectMyGroupList(GroupMemberForm form);

	/**
	 * 成员退出群
	 * @param groupId 群ID
	 * @param members 群成员
	 * @return
	 */
	int deleteByGroupMembers(String groupId, List<String> members);


	/**
	 * 查询群的所有的用户ID
	 * @param ownerId 群主ID
	 * @param groupId 群ID
	 * @return
	 */
	List<String> selectMembersUid(String ownerId, String groupId);

	/**
	 * 获取群中好友的信息
	 * @param groupId
	 * @return
	 */
	List<User> getGroupMembers(String groupId);
}
