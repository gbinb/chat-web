package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.entity.GroupOwner;
import cn.fetosoft.chat.core.data.form.GroupForm;
import cn.fetosoft.chat.core.data.vo.GroupVO;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/23 18:45
 */
public interface GroupService extends BaseDataService<Long, Group, GroupForm> {

	/**
	 * 通过群ID查询
	 * @param groupId
	 * @return
	 */
	Group selectByGroupId(String groupId);

	/**
	 * 通过群ID删除
	 * @param groupId
	 * @return
	 */
	int deleteByGroupId(String groupId);

	/**
	 * 创建群
	 * @param group 群信息
	 * @param members
	 * @return
	 */
	int createGroup(Group group, List<String> members);

	/**
	 * 加入群成员
	 * @param group
	 * @param members
	 * @return
	 */
	int joinGroup(Group group, List<String> members);

	/**
	 * 解散群组
	 * @param group
	 * @return
	 */
	int dismissGroup(Group group);

	/**
	 * 查询列表 join user
	 * @param form
	 * @return
	 */
	List<GroupOwner> selectListWithOwner(GroupForm form);

	/**
	 * 查询列表 join user
	 * @param form
	 * @return
	 */
	List<GroupVO> selectListVoWithOwner(GroupForm form);

	/**
	 * 查询记录数 join user
	 * @param form
	 * @return
	 */
	int selectCountWithOwner(GroupForm form);
}
