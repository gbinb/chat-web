package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.UserForm;
import cn.fetosoft.chat.core.data.vo.UserVO;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/15 15:42
 */
public interface UserService extends BaseDataService<Long, User, UserForm> {

	/**
	 * 创建User
	 * @param user
	 * @return
	 */
	int createUserAndKey(User user) throws Exception;

	/**
	 * 通过userId查询
	 * @param userId
	 * @return
	 */
	User selectByUserId(String userId);

	/**
	 * 通过手机号查询
	 * @param mobile
	 * @return
	 */
	User selectByMobile(String mobile);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	int modifyUser(User user);

	/**
	 * 查询用户信息
	 * @param form
	 * @return
	 */
	List<UserVO> selectListUserVO(UserForm form);
}
