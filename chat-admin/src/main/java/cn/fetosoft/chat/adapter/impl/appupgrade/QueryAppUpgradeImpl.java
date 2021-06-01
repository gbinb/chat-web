package cn.fetosoft.chat.adapter.impl.appupgrade;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.appupgrade.QueryAppUpgradeReq;
import cn.fetosoft.chat.adapter.res.appupgrade.QueryAppUpgradeRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.form.AppUpgradeForm;
import cn.fetosoft.chat.core.data.service.AppUpgradeService;
import cn.fetosoft.chat.core.data.vo.AppUpgradeVO;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询APP升级信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 9:40
 */
@Component(AdapterServiceName.QUERY_APP_UPGRADE)
public class QueryAppUpgradeImpl extends AbstractChatHandle<QueryAppUpgradeReq, QueryAppUpgradeRes> {

	@Autowired
	private AppUpgradeService appUpgradeService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected QueryAppUpgradeReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, QueryAppUpgradeReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<QueryAppUpgradeRes> doHandle(QueryAppUpgradeReq req) {
		RespResult<QueryAppUpgradeRes> result = RespResult.createSuccess();
		AppUpgradeForm form = new AppUpgradeForm();
		BeanUtils.copyProperties(req, form);
		form.setDescField("id");

		QueryAppUpgradeRes res = new QueryAppUpgradeRes();
		result.setData(res);
		int recordTotal = appUpgradeService.selectCountByForm(form);
		if(recordTotal>0){
			res.setRecordTotal(recordTotal, req.getRows());
			List<AppUpgradeVO> voList = appUpgradeService.selectListVo(form);
			res.setList(voList);
		}
		return result;
	}
}
