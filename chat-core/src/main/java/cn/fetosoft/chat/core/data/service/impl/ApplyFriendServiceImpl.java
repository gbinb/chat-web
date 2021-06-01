package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.ApplyFriend;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.ApplyFriendForm;
import cn.fetosoft.chat.core.data.mapper.ApplyFriendMapper;
import cn.fetosoft.chat.core.data.mapper.UserMapper;
import cn.fetosoft.chat.core.data.service.ApplyFriendService;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.enums.ApplyStatus;
import cn.fetosoft.chat.core.enums.YesOrNo;
import cn.fetosoft.chat.core.utils.IdGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 申请加好友
 * @author guobingbing
 * @create 2020/12/17 16:28
 */
@Service
public class ApplyFriendServiceImpl extends AbstractDataService<Long, ApplyFriend, ApplyFriendForm> implements ApplyFriendService {

	@Autowired
	private ApplyFriendMapper applyFriendMapper;
	@Autowired
	private FriendService friendService;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, ApplyFriend, ApplyFriendForm> getBaseMapper() {
		return applyFriendMapper;
	}

	/**
	 * 申请加好友
	 *
	 * @param applyUid
	 * @param targetUid
	 * @param leaveMsg
	 * @return cn.fetosoft.chat.core.data.service.Result
	 * @author guobingbing
	 * @wechat t_gbinb
	 * @date 2021/1/4 17:43
	 * @version 1.0
	 */
	@Override
	public int applyFriend(String applyUid, String targetUid, String leaveMsg) {
		ApplyFriendForm applyFriendForm = new ApplyFriendForm();
		applyFriendForm.setApplyUserId(applyUid);
		applyFriendForm.setTargetUserId(targetUid);
		List<ApplyFriend> applyFriends = applyFriendMapper.selectListByForm(applyFriendForm);
		User mySelf = userMapper.selectByUserId(applyUid);
		int flag = 0;
		if(!CollectionUtils.isEmpty(applyFriends)){
			ApplyFriend applyFriend = applyFriends.get(0);
			applyFriend.setLeaveMsg(leaveMsg);
			this.setValue(applyFriend, mySelf);
			flag = applyFriendMapper.updateByPrimaryKeySelective(applyFriend);
		}else {
			ApplyFriend friend = new ApplyFriend();
			friend.setApplyId(IdGenerateUtils.buildUUid());
			friend.setApplyUserId(applyUid);
			friend.setLeaveMsg(leaveMsg);
			friend.setTargetUserId(targetUid);
			this.setValue(friend, mySelf);
			flag = applyFriendMapper.insert(friend);
		}
		return flag;
	}

	/**
	 * 设置值
	 * @param friend
	 * @param user
	 */
	private void setValue(ApplyFriend friend, User user){
		friend.setApplyAvatar(user.getAvatar());
		friend.setApplyNickName(user.getNickName());
		friend.setApplyCountry(user.getCountry());
		friend.setStatus(ApplyStatus.Pending.getCode());
		friend.setCreateTime(new Date());
	}

	/**
	 * 处理新增好友
	 *
	 * @param applyFriend
	 * @param status
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int friendHandler(ApplyFriend applyFriend, ApplyStatus status) {
		applyFriend.setStatus(status.getCode());
		applyFriend.setModifyTime(new Date());
		int flag = applyFriendMapper.updateByPrimaryKeySelective(applyFriend);
		if(flag>0 && ApplyStatus.Agree == status) {
			User user = userMapper.selectByUserId(applyFriend.getApplyUserId());
			if(!friendService.isFriend(applyFriend.getTargetUserId(), applyFriend.getApplyUserId())) {
				//将申请人添加到对方的朋友列表中
				Friend myFriend = new Friend();
				myFriend.setUserId(applyFriend.getTargetUserId());
				myFriend.setFriendId(applyFriend.getApplyUserId());
				myFriend.setFriendName(applyFriend.getApplyNickName());
				myFriend.setFriendMobile(user.getMobile());
				myFriend.setFriendAvatar(applyFriend.getApplyAvatar());
				myFriend.setFriendCountry(applyFriend.getApplyCountry());
				myFriend.setBlack(YesOrNo.No.getCode());
				myFriend.setCreateTime(new Date());
				friendService.insert(myFriend);
			}

			//申请人将好友加到朋友列表中
			if(!friendService.isFriend(applyFriend.getApplyUserId(), applyFriend.getTargetUserId())) {
				user = userMapper.selectByUserId(applyFriend.getTargetUserId());
				Friend hisFriend = new Friend();
				hisFriend.setUserId(applyFriend.getApplyUserId());
				hisFriend.setFriendId(user.getUserId());
				hisFriend.setFriendName(user.getNickName());
				hisFriend.setFriendMobile(user.getMobile());
				hisFriend.setFriendAvatar(user.getAvatar());
				hisFriend.setFriendCountry(user.getCountry());
				hisFriend.setBlack(YesOrNo.No.getCode());
				hisFriend.setCreateTime(new Date());
				friendService.insert(hisFriend);
			}
		}
		return flag;
	}

	/**
	 * 通过applyId查询
	 *
	 * @param applyId
	 * @return
	 */
	@Override
	public ApplyFriend selectByApplyId(String applyId) {
		return applyFriendMapper.selectByApplyId(applyId);
	}
}
