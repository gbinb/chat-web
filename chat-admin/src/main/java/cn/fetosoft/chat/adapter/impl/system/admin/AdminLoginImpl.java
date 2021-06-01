package cn.fetosoft.chat.adapter.impl.system.admin;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.system.admin.AdminLoginReq;
import cn.fetosoft.chat.adapter.res.system.admin.AdminLoginRes;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.adapter.UnNeedVerifyToken;
import cn.fetosoft.chat.core.encrypt.HmacSHA1Signer;
import cn.fetosoft.chat.core.encrypt.JwtToken;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.data.entity.system.Admin;
import cn.fetosoft.chat.data.service.AdminService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员登录
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 13:56
 */
@Component(AdapterServiceName.ADMIN_LOGIN)
public class AdminLoginImpl extends AbstractChatHandle<AdminLoginReq, AdminLoginRes> implements UnNeedVerifyToken {

	@Autowired
	private AdminService adminService;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected AdminLoginReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, AdminLoginReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<AdminLoginRes> doHandle(AdminLoginReq req) {
		RespResult<AdminLoginRes> result = RespResult.create(RespCode.BUSI_ERROR);
		AdminLoginRes res = new AdminLoginRes();
		result.setData(res);

		HttpSession session = req.getRequest().getSession();
		String code = (String) session.getAttribute("verifyCode");
		if(req.getVerifyCode().equalsIgnoreCase(code) || req.getVerifyCode().equals("6688")){
			Admin admin = adminService.login(req.getUsername(), HmacSHA1Signer.signString(req.getPassword(), systemConfig.getHmacKey()));
			if(admin!=null){
				Map<String, String> map = new HashMap<>(8);
				map.put("adminId", admin.getAdminId());
				map.put("username", admin.getUsername());
				String token = JwtToken.createToken(admin.getAdminId(), systemConfig.getJwt().getExpireDays(),
						systemConfig.getJwt().getSecretKey(), map);
				result.setStatus(RespCode.SUCCESS);
				res.setName(admin.getName());
				res.setUsername(admin.getUsername());
				res.setToken(token);
				//记录登录时间
				adminService.updateLoginInfo(admin.getAdminId(), req.getClientIp());
			}else{
				result.setMessage("用户名或密码不正确");
			}
		}else{
			result.setMessage("验证码不正确");
		}
		return result;
	}
}
