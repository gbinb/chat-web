package cn.fetosoft.chat.adapter.impl.common;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.res.common.ObtainCaptchaRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseRequest;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.utils.ImageCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 获取验证码
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/13 9:33
 */
@Component(AdapterServiceName.OBTAIN_CAPCHA)
public class ObtainCaptchaImpl extends AbstractChatHandle<BaseRequest, ObtainCaptchaRes> implements UnNeedVerifyToken {

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
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<ObtainCaptchaRes> doHandle(BaseRequest req) {
		RespResult<ObtainCaptchaRes> result = RespResult.createSuccess();
		ObtainCaptchaRes res = new ObtainCaptchaRes();
		result.setData(res);

		String[] imageArr = ImageCodeUtil.createImage(100, 36);
		HttpSession session = req.getRequest().getSession();
		session.setAttribute("verifyCode", imageArr[0]);
		res.setCaptcha(imageArr[1]);
		return result;
	}
}
