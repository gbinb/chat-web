package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.form.UserKeyForm;

/**
 * 用户密钥
 * @author guobingbing
 * @create 2020/12/16 14:07
 */
public interface UserKeyMapper extends BaseMapper<Long, UserKey, UserKeyForm> {

	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	UserKey selectByUserId(String userId);
}
