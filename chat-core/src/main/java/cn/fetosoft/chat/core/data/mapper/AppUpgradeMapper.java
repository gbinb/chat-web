package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.AppUpgrade;
import cn.fetosoft.chat.core.data.form.AppUpgradeForm;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 10:11
 */
public interface AppUpgradeMapper extends BaseMapper<Integer, AppUpgrade, AppUpgradeForm> {

	/**
	 *
	 * @param form
	 * @return
	 */
	AppUpgrade selectByVersionAndType(AppUpgradeForm form);
}
