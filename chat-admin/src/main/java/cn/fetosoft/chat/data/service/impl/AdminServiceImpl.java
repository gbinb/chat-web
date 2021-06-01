package cn.fetosoft.chat.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.data.entity.system.Admin;
import cn.fetosoft.chat.data.form.system.AdminForm;
import cn.fetosoft.chat.data.mapper.system.AdminMapper;
import cn.fetosoft.chat.data.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 管理员
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 18:46
 */
@Service
public class AdminServiceImpl extends AbstractDataService<Integer, Admin, AdminForm> implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Integer, Admin, AdminForm> getBaseMapper() {
		return adminMapper;
	}

	/**
	 * 登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public Admin login(String username, String password) {
		Admin admin = null;
		AdminForm form = new AdminForm();
		form.setUsername(username);
		form.setPassword(password);
		List<Admin> list = adminMapper.selectListByForm(form);
		if(!CollectionUtils.isEmpty(list)){
			admin = list.get(0);
		}
		return admin;
	}

	/**
	 * 更新登录信息
	 *
	 * @param adminId
	 * @param ip
	 * @return
	 */
	@Override
	public int updateLoginInfo(String adminId, String ip) {
		Admin admin = new Admin();
		admin.setAdminId(adminId);
		admin.setLoginIp(ip);
		admin.setLoginTime(new Date());
		return adminMapper.updateLoginByAdminId(admin);
	}

	/**
	 * 查询管理员
	 *
	 * @param adminId
	 * @return
	 */
	@Override
	public Admin selectByAdminId(String adminId) {
		return adminMapper.selectByAdminId(adminId);
	}
}
