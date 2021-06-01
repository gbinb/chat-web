package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.entity.GroupMember;
import cn.fetosoft.chat.core.data.entity.GroupOwner;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.GroupForm;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;
import cn.fetosoft.chat.core.data.mapper.GroupMapper;
import cn.fetosoft.chat.core.data.mapper.GroupMemberMapper;
import cn.fetosoft.chat.core.data.mapper.UserMapper;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.data.service.SequenceService;
import cn.fetosoft.chat.core.data.vo.GroupVO;
import cn.fetosoft.chat.core.enums.DateFormatEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 群服务
 * @author guobingbing
 * @create 2020/12/23 18:47
 */
@Service
public class GroupServiceImpl extends AbstractDataService<Long, Group, GroupForm> implements GroupService {

	@Autowired
	private GroupMapper groupMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private GroupMemberMapper groupMemberMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, Group, GroupForm> getBaseMapper() {
		return groupMapper;
	}

	/**
	 * 通过群ID查询
	 *
	 * @param groupId
	 * @return
	 */
	@Override
	public Group selectByGroupId(String groupId) {
		return groupMapper.selectByGroupId(groupId);
	}

	/**
	 * 通过群ID删除
	 *
	 * @param groupId
	 * @return
	 */
	@Override
	public int deleteByGroupId(String groupId) {
		return groupMapper.deleteByGroupId(groupId);
	}

	/**
	 * 创建群
	 *
	 * @param members
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int createGroup(Group group, List<String> members) {
		int flag = 0;
		List<User> userList = userMapper.selectListByUids(members);
		if(userList!=null && userList.size()>2){
			if(StringUtils.isBlank(group.getGroupId())) {
				group.setGroupId(sequenceService.genGroupId());
			}
			group.setMembers(userList.size());
			group.setName(userList.get(0).getNickName() + "、" + userList.get(1).getNickName() + "、" + userList.get(2).getNickName());
			if(userList.size()>4){
				group.setName(group.getName() + "...");
			}
			group.setCreateTime(new Date());
			flag = groupMapper.insert(group);
			if(flag>0){
				List<GroupMember> memberList = new ArrayList<>();
				for(User user : userList){
					memberList.add(this.buildMember(group, user.getUserId()));
				}
				flag += groupMemberMapper.batchInsert(memberList);
			}
		}
		return flag;
	}

	/**
	 * 创建群成员信息
	 * @param group
	 * @param userId
	 * @return
	 */
	private GroupMember buildMember(Group group, String userId){
		GroupMember m = new GroupMember();
		m.setOwnerId(group.getOwnerId());
		m.setGroupId(group.getGroupId());
		m.setUserId(userId);
		m.setCreateTime(new Date());
		return m;
	}

	/**
	 * 加入群成员
	 *
	 * @param group
	 * @param members
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int joinGroup(Group group, List<String> members) {
		GroupMemberForm form = new GroupMemberForm();
		form.setOwnerId(group.getOwnerId());
		form.setGroupId(group.getGroupId());
		List<String> haveMembers = groupMemberMapper.selectMembersUid(form);
		List<String> newMembers = new ArrayList<>();
		for (String m : members){
			if(!haveMembers.contains(m)){
				newMembers.add(m);
			}
		}
		List<User> userList = userMapper.selectListByUids(newMembers);
		List<GroupMember> memberList = new ArrayList<>();
		for(User user : userList){
			memberList.add(this.buildMember(group, user.getUserId()));
		}
		int flag = groupMemberMapper.batchInsert(memberList);
		if(flag>0){
			group.setMembers(group.getMembers()+userList.size());
			flag = groupMapper.updateByPrimaryKeySelective(group);
		}
		return flag;
	}

	/**
	 * 解散群组
	 *
	 * @param group
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int dismissGroup(Group group) {
		int flag = groupMapper.deleteByGroupId(group.getGroupId());
		if(flag>0) {
			groupMemberMapper.deleteByGroupId(group.getGroupId());
		}
		return flag;
	}

	/**
	 * 查询列表 join user
	 *
	 * @param form
	 * @return
	 */
	@Override
	public List<GroupOwner> selectListWithOwner(GroupForm form) {
		return groupMapper.selectListWithOwner(form);
	}

	/**
	 * 查询列表 join user
	 *
	 * @param form
	 * @return
	 */
	@Override
	public List<GroupVO> selectListVoWithOwner(GroupForm form) {
		List<GroupVO> voList = new ArrayList<>();
		List<GroupOwner> ownerList = groupMapper.selectListWithOwner(form);
		if(!CollectionUtils.isEmpty(ownerList)){
			for(GroupOwner g : ownerList){
				GroupVO vo = new GroupVO();
				BeanUtils.copyProperties(g, vo);
				if(g.getCreateTime()!=null){
					vo.setCreateTime(DateFormatEnum.YMD_HMS.dateToString(g.getCreateTime()));
				}
				if(g.getModifyTime()!=null){
					vo.setModifyTime(DateFormatEnum.YMD_HMS.dateToString(g.getModifyTime()));
				}
				voList.add(vo);
			}
		}
		return voList;
	}

	/**
	 * 查询记录数 join user
	 *
	 * @param form
	 * @return
	 */
	@Override
	public int selectCountWithOwner(GroupForm form) {
		return groupMapper.selectCountWithOwner(form);
	}
}
