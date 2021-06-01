package cn.fetosoft.chat.core.controller;

import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.encrypt.JwtToken;
import cn.fetosoft.chat.core.enums.RespCode;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础Controller
 * @author guobingbing
 * @create 2020/11/28 10:25
 */
public abstract class BaseController {

	/**
	 * 验证token
	 * @param token
	 * @return
	 */
	protected RespResult<Map<String, String>> verifyToken(String token, String secret){
		RespResult<Map<String, String>> result = RespResult.createError();
		if(StringUtils.isBlank(token)){
			result.setStatus(RespCode.NEED_LOGIN);
			return result;
		}
		JwtToken.VerifyResult verifyResult = JwtToken.verifyToekn(token, secret);
		if(verifyResult.isSuccess()){
			result = RespResult.createSuccess();
			result.setData(verifyResult.getDataMap());
			return result;
		}else{
			result.setMessage(verifyResult.getErrorMsg());
		}
		return result;
	}
}
