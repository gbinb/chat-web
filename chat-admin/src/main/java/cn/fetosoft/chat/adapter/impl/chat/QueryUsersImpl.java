package cn.fetosoft.chat.adapter.impl.chat;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.chat.QueryUsersReq;
import cn.fetosoft.chat.adapter.res.chat.QueryUsersRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.form.UserForm;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.data.vo.UserVO;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:02
 */
@Component(AdapterServiceName.QUERY_USERS)
public class QueryUsersImpl extends AbstractChatHandle<QueryUsersReq, QueryUsersRes> {

	@Autowired
	private UserService userService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected QueryUsersReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, QueryUsersReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<QueryUsersRes> doHandle(QueryUsersReq req) {
		RespResult<QueryUsersRes> result = RespResult.createSuccess();
		QueryUsersRes res = new QueryUsersRes();
		result.setData(res);

		UserForm form = new UserForm();
		BeanUtils.copyProperties(req, form);
		int total = userService.selectCountByForm(form);
		if(total>0) {
			res.setRecordTotal(total, req.getRows());
			List<UserVO> voList = userService.selectListUserVO(form);
			res.setList(voList);
		}
		return result;
	}
}
