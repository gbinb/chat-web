package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.UserForm;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/15 15:39
 */
public interface UserMapper extends BaseMapper<Long, User, UserForm> {

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
	 * 通过userID批量查询用户
	 * @param userIds
	 * @return
	 */
	List<User> selectListByUids(List<String> userIds);
}
