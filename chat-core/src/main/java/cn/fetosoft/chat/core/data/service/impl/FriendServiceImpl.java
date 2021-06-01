package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.form.FriendForm;
import cn.fetosoft.chat.core.data.mapper.FriendMapper;
import cn.fetosoft.chat.core.data.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/17 9:37
 */
@Service
public class FriendServiceImpl extends AbstractDataService<Long, Friend, FriendForm> implements FriendService {

	@Autowired
	private FriendMapper friendMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, Friend, FriendForm> getBaseMapper() {
		return friendMapper;
	}

	/**
	 * 修改好友信息
	 *
	 * @param friend
	 * @return int
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2020/12/31 13:35
	 * @version 1.0
	 */
	@Override
	public int modifyFriend(Friend friend) {
		return friendMapper.updateByPrimaryKeySelective(friend);
	}

	/**
	 * 删除好友
	 *
	 * @param friend
	 * @return int
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2020/12/31 11:40
	 * @version 1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int removeFriend(Friend friend) {
		return friendMapper.deleteByPrimaryKey(friend.getId());
	}

	/**
	 * 判断是否为好友
	 *
	 * @param userId
	 * @param friendId
	 * @return boolean
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2021/1/4 15:03
	 * @version 1.0
	 */
	@Override
	public boolean isFriend(String userId, String friendId) {
		FriendForm form = new FriendForm();
		form.setUserId(userId);
		form.setFriendId(friendId);
		int count = friendMapper.selectCountByForm(form);
		return count>0;
	}

	/**
	 * 查询好友信息
	 *
	 * @param userId
	 * @param friendId
	 * @return
	 */
	@Override
	public Friend getFriend(String userId, String friendId) {
		Friend friend = null;
		FriendForm form = new FriendForm();
		form.setUserId(userId);
		form.setFriendId(friendId);
		List<Friend> friends = friendMapper.selectListByForm(form);
		if(!CollectionUtils.isEmpty(friends)){
			friend = friends.get(0);
		}
		return friend;
	}
}
