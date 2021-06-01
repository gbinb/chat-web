package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.form.UserForm;
import cn.fetosoft.chat.core.data.mapper.FriendMapper;
import cn.fetosoft.chat.core.data.mapper.UserKeyMapper;
import cn.fetosoft.chat.core.data.mapper.UserMapper;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.data.vo.UserVO;
import cn.fetosoft.chat.core.encrypt.RSAUtils;
import cn.fetosoft.chat.core.enums.DateFormatEnum;
import cn.fetosoft.chat.core.enums.Gender;
import cn.fetosoft.chat.core.exceptions.ParamException;
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
 * 用户服务
 * @author guobingbing
 * @create 2020/12/15 15:43
 */
@Service
public class UserServiceImpl extends AbstractDataService<Long, User, UserForm> implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserKeyMapper userKeyMapper;
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
	protected BaseMapper<Long, User, UserForm> getBaseMapper() {
		return userMapper;
	}

	/**
	 * 创建User
	 *
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int createUserAndKey(User user) throws Exception {
		int flag = userMapper.insert(user);
		if(flag>0){
			UserKey userKey = new UserKey();
			userKey.setUserId(user.getUserId());
			RSAUtils.RsaKey rsaKey = RSAUtils.genKeyPair();
			userKey.setPrivateKey(rsaKey.getPrivateKey());
			userKey.setPublicKey(rsaKey.getPublicKey());
			userKey.setCreateTime(new Date());
			userKeyMapper.insert(userKey);
		}
		return flag;
	}

	/**
	 * 通过userId查询
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public User selectByUserId(String userId) {
		return this.userMapper.selectByUserId(userId);
	}

	/**
	 * 通过手机号查询
	 *
	 * @param mobile
	 * @return
	 */
	@Override
	public User selectByMobile(String mobile) {
		return this.userMapper.selectByMobile(mobile);
	}

	/**
	 * 更新用户信息
	 * 不用事务的目的在于可接受用户信息变更，但在好友中的信息暂时没有更新，可以事后补偿更新
	 * 需要实现补偿方案。
	 * @param user
	 * @return
	 */
	@Override
	public int modifyUser(User user) {
		if(StringUtils.isBlank(user.getUserId())){
			throw new ParamException("用户UserID为空");
		}
		int flag = userMapper.updateByPrimaryKeySelective(user);
		if(flag>0){
			//更新在好友中的信息
			Friend friend = new Friend();
			friend.setFriendId(user.getUserId());
			friend.setFriendName(user.getNickName());
			friend.setFriendMobile(user.getMobile());
			friend.setFriendCountry(user.getCountry());
			friend.setFriendAvatar(user.getAvatar());
			friendMapper.updateFriendBySelf(friend);
		}
		return flag;
	}

	/**
	 * 查询用户信息
	 *
	 * @param form
	 * @return
	 */
	@Override
	public List<UserVO> selectListUserVO(UserForm form) {
		List<UserVO> voList = new ArrayList<>();
		List<User> userList = userMapper.selectListByForm(form);
		if(!CollectionUtils.isEmpty(userList)){
			for(User u : userList){
				UserVO vo = new UserVO();
				BeanUtils.copyProperties(u, vo);
				if(StringUtils.isNotBlank(u.getGender())){
					if(u.getGender().equalsIgnoreCase(Gender.Man.getCode())){
						vo.setGenderText("男");
					}else{
						vo.setGenderText("女");
					}
				}
				if(u.getCreateTime()!=null){
					vo.setCreateTime(DateFormatEnum.YMD_HMS.dateToString(u.getCreateTime()));
				}
				if(u.getModifyTime()!=null){
					vo.setModifyTime(DateFormatEnum.YMD_HMS.dateToString(u.getModifyTime()));
				}
				if(u.getLastLoginTime()!=null){
					vo.setLastLoginTime(DateFormatEnum.YMD_HMS.dateToString(u.getLastLoginTime()));
				}
				voList.add(vo);
			}
		}
		return voList;
	}
}
