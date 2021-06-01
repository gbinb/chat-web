package cn.fetosoft.chat.adapter.impl.chat;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.chat.QueryMsgFileReq;
import cn.fetosoft.chat.adapter.res.chat.QueryMsgFileRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.service.MsgFileService;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询消息文件
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/29 14:36
 */
@Component(AdapterServiceName.QUERY_MSG_FILE)
public class QueryMsgFileImpl extends AbstractChatHandle<QueryMsgFileReq, QueryMsgFileRes> {

	@Autowired
	private MsgFileService msgFileService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected QueryMsgFileReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSONObject.toJavaObject(jsonObject, QueryMsgFileReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<QueryMsgFileRes> doHandle(QueryMsgFileReq req) {
		RespResult<QueryMsgFileRes> result = RespResult.createSuccess();

		return result;
	}
}