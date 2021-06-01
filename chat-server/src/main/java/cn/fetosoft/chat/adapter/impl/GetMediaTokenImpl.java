package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetMediaTokenReq;
import cn.fetosoft.chat.adapter.res.GetMediaTokenRes;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.utils.UUidUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.agora.media.RtcTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取多媒体语音或视频TOKEN
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/3/1 16:47
 */
@Component(ChatServiceName.GET_MEDIA_TOKEN)
public class GetMediaTokenImpl extends AbstractChatHandle<GetMediaTokenReq, GetMediaTokenRes> {

	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetMediaTokenReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetMediaTokenReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetMediaTokenRes> doHandle(GetMediaTokenReq req) {
		RespResult<GetMediaTokenRes> result = RespResult.createSuccess();
		GetMediaTokenRes res = new GetMediaTokenRes();
		result.setData(res);

		SystemConfig.Media media = systemConfig.getMedia();
		RtcTokenBuilder tokenBuilder = new RtcTokenBuilder();
		int timestamp = (int)(System.currentTimeMillis() / 1000 + media.getExpiredTime());
		String channelName = UUidUtil.buildUUID();
		String token = tokenBuilder.buildTokenWithUserAccount(media.getAppId(), media.getAppSecret(),
				channelName, req.getUserId(), RtcTokenBuilder.Role.Role_Publisher, timestamp);
		res.setChannelId(channelName);
		res.setToken(token);
		return result;
	}
}
