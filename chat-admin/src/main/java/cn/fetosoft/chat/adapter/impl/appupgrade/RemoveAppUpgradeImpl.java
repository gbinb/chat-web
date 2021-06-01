package cn.fetosoft.chat.adapter.impl.appupgrade;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.appupgrade.RemoveAppUpgradeReq;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.service.AppUpgradeService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除APP版本信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 15:33
 */
@Component(AdapterServiceName.REMOVE_APP_UPGRADE)
public class RemoveAppUpgradeImpl extends AbstractChatHandle<RemoveAppUpgradeReq, BaseResponse> {

	@Autowired
	private AppUpgradeService appUpgradeService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected RemoveAppUpgradeReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, RemoveAppUpgradeReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<BaseResponse> doHandle(RemoveAppUpgradeReq req) {
		RespResult<BaseResponse> result = RespResult.createError();
		if(req.getIds()!=null && req.getIds().length>0){
			int flag = 0;
			for(String id : req.getIds()){
				flag += appUpgradeService.deleteByPrimaryKey(Integer.valueOf(id));
			}
			if(flag>0){
				result.setStatus(RespCode.SUCCESS);
			}else{
				result.setMessage("删除失败！");
			}
		}
		return result;
	}
}
