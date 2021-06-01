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

/**
 * 更新信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 16:39
 */
@Component(AdapterServiceName.UPDATE_APP_UPGRADE)
public class UpdateAppUpgradeImpl extends AbstractAppUpgrade {

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
		if(appUpgrade.getId()==null || appUpgrade.getId()<=0){
			result.setMessage("主键不能为空！");
			return result;
		}
		int flag = appUpgradeService.updateByPrimaryKeySelective(appUpgrade);
		if(flag>0){
			result.setStatus(RespCode.SUCCESS);
		}else{
			result.setMessage("更新失败！");
		}
		return result;
	}
}
