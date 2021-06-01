package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.form.UserKeyForm;

/**
 * @author guobingbing
 * @create 2020/12/16 14:13
 */
public interface UserKeyService extends BaseDataService<Long, UserKey, UserKeyForm> {

	/**
	 * 根据userId查询
	 * @param userId
	 * @return
	 */
	UserKey selectByUserId(String userId);
}
