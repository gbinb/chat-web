package cn.fetosoft.chat.adapter.impl.chat;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.chat.QueryGroupReq;
import cn.fetosoft.chat.adapter.res.chat.QueryGroupRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.form.GroupForm;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.data.vo.GroupVO;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.utils.StringProccUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询群信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 20:16
 */
@Component(AdapterServiceName.QUERY_GROUP)
public class QueryGroupImpl extends AbstractChatHandle<QueryGroupReq, QueryGroupRes> {

	@Autowired
	private GroupService groupService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected QueryGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSONObject.toJavaObject(jsonObject, QueryGroupReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<QueryGroupRes> doHandle(QueryGroupReq req) {
		RespResult<QueryGroupRes> result = RespResult.createSuccess();
		QueryGroupRes res = new QueryGroupRes();
		result.setData(res);

		GroupForm form = new GroupForm();
		form.setDescField("id");
		form.setGroupId(req.getGroupId());
		form.setMobile(StringProccUtil.proceeMobile(req.getMobile()));
		form.setOwnerId(req.getOwnerId());
		form.setMembers(req.getMembers());
		form.setPage(req.getPage());
		form.setRows(req.getRows());
		int total = groupService.selectCountWithOwner(form);
		if(total>0){
			res.setRecordTotal(total, req.getRows());
			List<GroupVO> voList = groupService.selectListVoWithOwner(form);
			res.setList(voList);
		}
		return result;
	}
}
