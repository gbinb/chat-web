package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.*;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;
import cn.fetosoft.chat.core.data.mapper.GroupMapper;
import cn.fetosoft.chat.core.data.mapper.GroupMemberMapper;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群成员信息
 * @author guobingbing
 * @create 2020/12/24 14:49
 */
@Service
public class GroupMemberServiceImpl extends AbstractDataService<Long, GroupMember, GroupMemberForm> implements GroupMemberService {

	@Autowired
	private GroupMemberMapper groupMemberMapper;
	@Autowired
	private GroupMapper groupMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, GroupMember, GroupMemberForm> getBaseMapper() {
		return groupMemberMapper;
	}

	/**
	 * 查询我的群信息
	 *
	 * @param form
	 * @return
	 */
	@Override
	public List<MyGroup> selectMyGroupList(GroupMemberForm form) {
		return groupMemberMapper.selectMyGroupList(form);
	}

	/**
	 * 成员退出群
	 *
	 * @param groupId 群ID
	 * @param members  用户ID
	 * @return
	 */
	@Override
	public int deleteByGroupMembers(String groupId, List<String> members) {
		Map<String, Object> map = new HashMap<>(8);
		map.put("groupId", groupId);
		map.put("list", members);
		int flag = groupMemberMapper.batchDeleteMembers(map);
		if(flag>0){
			GroupMemberForm form = new GroupMemberForm();
			form.setGroupId(groupId);
			int total = groupMemberMapper.selectCountByForm(form);
			Group group = new Group();
			group.setGroupId(groupId);
			group.setMembers(total);
			group.setModifyTime(new Date());
			groupMapper.updateByGroupId(group);
		}
		return flag;
	}

	/**
	 * 查询群的所有的用户ID
	 *
	 * @param ownerId 群主ID
	 * @param groupId   群ID
	 * @return
	 */
	@Override
	public List<String> selectMembersUid(String ownerId, String groupId) {
		GroupMemberForm form = new GroupMemberForm();
		form.setOwnerId(ownerId);
		form.setGroupId(groupId);
		return groupMemberMapper.selectMembersUid(form);
	}

	/**
	 * 获取群中好友的信息
	 *
	 * @param groupId
	 * @return
	 */
	@Override
	public List<User> getGroupMembers(String groupId) {
		GroupMemberForm form = new GroupMemberForm();
		form.setGroupId(groupId);
		return groupMemberMapper.getGroupMembers(form);
	}
}
