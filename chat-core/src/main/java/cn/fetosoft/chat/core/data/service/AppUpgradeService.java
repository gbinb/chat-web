package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.AppUpgrade;
import cn.fetosoft.chat.core.data.form.AppUpgradeForm;
import cn.fetosoft.chat.core.data.vo.AppUpgradeVO;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 10:12
 */
public interface AppUpgradeService extends BaseDataService<Integer, AppUpgrade, AppUpgradeForm> {

	/**
	 * 查找升级信息
	 * @param version
	 * @param versionType
	 * @return
	 */
	AppUpgrade getAppUpgrade(String version, String versionType);

	/**
	 * 查询列表
	 * @param form
	 * @return
	 */
	List<AppUpgradeVO> selectListVo(AppUpgradeForm form);
}
