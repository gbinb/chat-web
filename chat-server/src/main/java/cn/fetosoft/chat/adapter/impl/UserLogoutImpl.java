package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseRequest;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 用户登出
 * @author guobingbing
 * @create 2020/12/30 10:16
 */
@Component(ChatServiceName.USER_LOGOUT)
public class UserLogoutImpl extends AbstractChatHandle<BaseRequest, BaseResponse> {

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected BaseRequest convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, BaseRequest.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param baseRequest
	 * @return
	 */
	@Override
	protected RespResult<BaseResponse> doHandle(BaseRequest baseRequest) {
		RespResult<BaseResponse> result = RespResult.createSuccess();
		//TODO 做一些状态变更之类的业务处理。
		return result;
	}
}
