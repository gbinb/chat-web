package cn.fetosoft.chat.data.mapper.system;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.data.entity.system.Admin;
import cn.fetosoft.chat.data.form.system.AdminForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 17:06
 */
@Mapper
public interface AdminMapper extends BaseMapper<Integer, Admin, AdminForm> {

	/**
	 * 查询管理员
	 * @param adminId
	 * @return
	 */
	Admin selectByAdminId(String adminId);

	/**
	 * 更新登录信息
	 * @param admin
	 * @return
	 */
	int updateLoginByAdminId(Admin admin);
}
