package cn.fetosoft.chat.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.data.entity.system.Admin;
import cn.fetosoft.chat.data.form.system.AdminForm;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 18:06
 */
public interface AdminService extends BaseDataService<Integer, Admin, AdminForm> {

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	Admin login(String username, String password);

	/**
	 * 更新登录信息
	 * @param adminId
	 * @param ip
	 * @return
	 */
	int updateLoginInfo(String adminId, String ip);

	/**
	 * 查询管理员
	 * @param adminId
	 * @return
	 */
	Admin selectByAdminId(String adminId);
}
