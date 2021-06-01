package cn.fetosoft.chat.adapter.impl.common;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.common.VerifyTokenReq;
import cn.fetosoft.chat.adapter.res.common.VerifyTokenRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.data.entity.system.Admin;
import cn.fetosoft.chat.data.service.AdminService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证token
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/13 13:56
 */
@Component(AdapterServiceName.VERIFY_TOKEN)
public class VerifyTokenImpl extends AbstractChatHandle<VerifyTokenReq, VerifyTokenRes> {

	@Autowired
	private AdminService adminService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected VerifyTokenReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, VerifyTokenReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<VerifyTokenRes> doHandle(VerifyTokenReq req) {
		RespResult<VerifyTokenRes> result = RespResult.createSuccess();
		String adminId = req.getAdminId();
		Admin admin = adminService.selectByAdminId(adminId);
		if(admin!=null){
			VerifyTokenRes res = new VerifyTokenRes();
			res.setName(admin.getName());
			res.setUsername(admin.getUsername());
			result.setData(res);
		}else{
			result.setStatus(RespCode.NEED_LOGIN);
		}
		return result;
	}
}
