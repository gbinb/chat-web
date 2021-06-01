package cn.fetosoft.chat.adapter.impl.appupgrade;

import cn.fetosoft.chat.adapter.req.appupgrade.SaveAppUpgradeReq;
import cn.fetosoft.chat.adapter.res.appupgrade.SaveAppUpgradeRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.data.entity.AppUpgrade;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 9:53
 */
public abstract class AbstractAppUpgrade extends AbstractChatHandle<SaveAppUpgradeReq, SaveAppUpgradeRes> {

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected SaveAppUpgradeReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, SaveAppUpgradeReq.class);
	}

	/**
	 *
	 * @param req
	 * @return
	 */
	protected AppUpgrade convertAppUpgrade(SaveAppUpgradeReq req){
		AppUpgrade appUpgrade = new AppUpgrade();
		BeanUtils.copyProperties(req, appUpgrade);
		return appUpgrade;
	}
}
