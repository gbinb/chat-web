package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.entity.GroupOwner;
import cn.fetosoft.chat.core.data.form.GroupForm;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/23 10:03
 */
public interface GroupMapper extends BaseMapper<Long, Group, GroupForm> {

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
	 * 根据群ID更新
	 * @param group
	 * @return
	 */
	int updateByGroupId(Group group);

	/**
	 * 查询列表 join user
	 * @param form
	 * @return
	 */
	List<GroupOwner> selectListWithOwner(GroupForm form);

	/**
	 * 查询记录数 join user
	 * @param form
	 * @return
	 */
	int selectCountWithOwner(GroupForm form);
}
