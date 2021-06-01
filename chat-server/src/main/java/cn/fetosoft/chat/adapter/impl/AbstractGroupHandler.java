package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.res.GroupProfile;
import cn.fetosoft.chat.adapter.res.ShareMember;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseRequest;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.FriendForm;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群相关信息处理
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/15 10:01
 */
public abstract class AbstractGroupHandler<T extends BaseRequest, R extends BaseResponse> extends AbstractChatHandle<T, R> {

	@Autowired
	protected FriendService friendService;
	@Autowired
	protected GroupMemberService memberService;
	@Autowired
	protected GroupService groupService;
	@Autowired
	protected UserService userService;

	/**
	 * 初始化参数
	 * @param profile
	 */
	protected void initGroupProfile(GroupProfile profile){
		Group group = groupService.selectByGroupId(profile.getGroupId());
		profile.setAvatar(group.getAvatar());
		profile.setName(group.getName());
		profile.setOwnerId(group.getOwnerId());
		profile.setTopic(group.getTopic());
		profile.setTotal(group.getMembers());

		User user = userService.selectByUserId(group.getOwnerId());
		ShareUser owner = new ShareUser();
		owner.setUserId(user.getUserId());
		owner.setNickName(user.getNickName());
		owner.setAvatar(user.getAvatar());
		owner.setCountry(user.getCountry());

		profile.setOwner(owner);
	}

	/**
	 * 获取群成员信息
	 * @param userId
	 * @param groupId
	 * @return
	 */
	protected List<ShareMember> getMemberList(String userId, String groupId){
		List<ShareMember> shareList = new ArrayList<>();
		//查询我的好友
		FriendForm friendForm = new FriendForm();
		friendForm.setUserId(userId);
		List<Friend> friendList = friendService.selectListByForm(friendForm);
		int mapSize = CollectionUtils.isEmpty(friendList)?16:friendList.size()*2;
		Map<String, Friend> map = new HashMap<>(mapSize);
		for(Friend f : friendList){
			map.put(f.getFriendId(), f);
		}

		List<User> memberList = memberService.getGroupMembers(groupId);
		if(!CollectionUtils.isEmpty(memberList)){
			for(User u : memberList){
				ShareMember member = new ShareMember();
				ShareUser su = new ShareUser(u.getUserId(), u.getNickName());
				su.setAvatar(u.getAvatar());
				su.setCountry(u.getCountry());

				Friend friend = map.get(u.getUserId());
				if(friend!=null) {
					member.setAlias(friend.getAlias());
					member.setNote(friend.getNote());
					member.setTag(friend.getTag());
					su.setRelationship(Relationship.Friend.getCode());
				}else{
					if(userId.equals(u.getUserId())){
						su.setRelationship(Relationship.Myself.getCode());
					}else{
						su.setRelationship(Relationship.Stranger.getCode());
					}
				}
				member.setUser(su);
				shareList.add(member);
			}
		}
		return shareList;
	}
}
