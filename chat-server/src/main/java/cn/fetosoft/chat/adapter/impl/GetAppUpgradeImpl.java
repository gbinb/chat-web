package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetAppUpgradeReq;
import cn.fetosoft.chat.adapter.res.GetAppUpgradeRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.data.entity.AppUpgrade;
import cn.fetosoft.chat.core.data.form.AppUpgradeForm;
import cn.fetosoft.chat.core.data.service.AppUpgradeService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.enums.YesOrNo;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 获取APP升级信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 16:54
 */
@Component(ChatServiceName.GET_APP_UPGRADE)
public class GetAppUpgradeImpl extends AbstractChatHandle<GetAppUpgradeReq, GetAppUpgradeRes> implements UnNeedVerifyToken {

	@Autowired
	private AppUpgradeService appUpgradeService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetAppUpgradeReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetAppUpgradeReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetAppUpgradeRes> doHandle(GetAppUpgradeReq req) {
		RespResult<GetAppUpgradeRes> result = RespResult.create(RespCode.BUSI_ERROR);
		GetAppUpgradeRes res = new GetAppUpgradeRes();
		result.setData(res);

		AppUpgrade appUpgrade = appUpgradeService.getAppUpgrade(req.getVersion(), req.getVersionType());
		if(appUpgrade==null){
			result.setMessage("未查到版本信息");
			return result;
		}

		AppUpgradeForm form = new AppUpgradeForm();
		form.setId(appUpgrade.getId());
		form.setVersionType(req.getVersionType());
		form.setAscField("id");
		List<AppUpgrade> list = appUpgradeService.selectListByForm(form);
		if(CollectionUtils.isEmpty(list)){
			result.setMessage("未查到版本信息");
			return result;
		}

		AppUpgrade lastVersion = null;
		for(AppUpgrade upgrade : list){
			if(YesOrNo.Yes.getCode().equals(upgrade.getForceUpgrade())){
				lastVersion = upgrade;
				res.setForceUpdate(1);
				break;
			}
		}
		if(lastVersion==null){
			lastVersion = list.get(list.size()-1);
			res.setForceUpdate(0);
		}
		res.setVersion(lastVersion.getVersion());
		res.setApkUrl(lastVersion.getApkUrl());
		res.setNote(lastVersion.getNote());
		result.setStatus(RespCode.SUCCESS);
		return result;
	}
}
