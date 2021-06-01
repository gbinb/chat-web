package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.GroupMember;
import cn.fetosoft.chat.core.data.entity.MyGroup;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;

import java.util.List;
import java.util.Map;

/**
 * @author guobingbing
 * @create 2020/12/24 9:08
 */
public interface GroupMemberMapper extends BaseMapper<Long, GroupMember, GroupMemberForm> {

	/**
	 * 通过群号删除
	 * @param groupId
	 * @return
	 */
	int deleteByGroupId(String groupId);

	/**
	 * 以群号及用户ID为条件删除
	 * @param member
	 * @return
	 */
	int deleteByGroupIdAndUserId(GroupMember member);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int batchInsert(List<GroupMember> list);

	/**
	 * 查询我的群信息
	 * @param form
	 * @return
	 */
	List<MyGroup> selectMyGroupList(GroupMemberForm form);

	/**
	 * 查询群的所有的用户ID
	 * @param form
	 * @return
	 */
	List<String> selectMembersUid(GroupMemberForm form);

	/**
	 * 批量删除群中的成员
	 * @param map
	 * @return
	 */
	int batchDeleteMembers(Map<String, Object> map);

	/**
	 * 获取群中好友的信息
	 * @param form
	 * @return
	 */
	List<User> getGroupMembers(GroupMemberForm form);
}
