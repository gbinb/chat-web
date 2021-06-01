package cn.fetosoft.chat.adapter.impl.appupgrade;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.appupgrade.SaveAppUpgradeReq;
import cn.fetosoft.chat.adapter.res.appupgrade.SaveAppUpgradeRes;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.AppUpgrade;
import cn.fetosoft.chat.core.data.service.AppUpgradeService;
import cn.fetosoft.chat.core.enums.RespCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 创建APP升级信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 9:50
 */
@Component(AdapterServiceName.CREATE_APP_UPGRADE)
public class CreateAppUpgradeImpl extends AbstractAppUpgrade {

	@Autowired
	private AppUpgradeService appUpgradeService;

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<SaveAppUpgradeRes> doHandle(SaveAppUpgradeReq req) {
		RespResult<SaveAppUpgradeRes> result = RespResult.createError();
		AppUpgrade appUpgrade = this.convertAppUpgrade(req);
		appUpgrade.setCreateTime(new Date());
		int flag = appUpgradeService.insert(appUpgrade);
		if(flag>0){
			result.setStatus(RespCode.SUCCESS);
		}
		return result;
	}
}
