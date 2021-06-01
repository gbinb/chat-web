package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.form.FriendForm;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/17 9:35
 */
public interface FriendMapper extends BaseMapper<Long, Friend, FriendForm> {

	/**
	 * 仅用于用户自己更新信息时使用
	 * 好友信息更新
	 * @param friend
	 * @return
	 */
	int updateFriendBySelf(Friend friend);

	/**
	 * 查询好友ID批量查询
	 * @param friendIds
	 * @return
	 */
	List<Friend> selectListByFids(List<String> friendIds);
}
