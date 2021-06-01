package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.form.UserKeyForm;
import cn.fetosoft.chat.core.data.mapper.UserKeyMapper;
import cn.fetosoft.chat.core.data.service.UserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户密钥服务
 * @author guobingbing
 * @create 2020/12/16 14:15
 */
@Service
public class UserKeyServiceImpl extends AbstractDataService<Long, UserKey, UserKeyForm> implements UserKeyService {

	@Autowired
	private UserKeyMapper userKeyMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, UserKey, UserKeyForm> getBaseMapper() {
		return userKeyMapper;
	}

	/**
	 * 根据userId查询
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserKey selectByUserId(String userId) {
		return userKeyMapper.selectByUserId(userId);
	}
}
