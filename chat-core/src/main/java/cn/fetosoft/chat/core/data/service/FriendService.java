package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.form.FriendForm;

/**
 * 好友
 * @author guobingbing
 * @wechat t_gbinb
 * @create 2020/12/17 9:36
 */
public interface FriendService extends BaseDataService<Long, Friend, FriendForm> {

	/**
	 * 修改好友信息
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2020/12/31 13:35
	 * @param friend
	 * @return int
	 * @version 1.0
	 */
	int modifyFriend(Friend friend);

	/**
	 * 删除好友
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2020/12/31 11:40
	 * @param friend
	 * @return int
	 * @version 1.0
	 */
	int removeFriend(Friend friend);

	/**
	 * 判断是否为好友
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2021/1/4 15:03
	 * @param userId
	 * @param friendId
	 * @return boolean
	 * @version 1.0
	 */
	boolean isFriend(String userId, String friendId);

	/**
	 * 查询好友信息
	 * @param userId
	 * @param friendId
	 * @return
	 */
	Friend getFriend(String userId, String friendId);
}
