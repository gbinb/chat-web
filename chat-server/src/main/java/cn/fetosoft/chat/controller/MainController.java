package cn.fetosoft.chat.controller;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.*;
import cn.fetosoft.chat.core.controller.BaseController;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.utils.NetUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 请求入口
 * @author guobingbing
 * @create 2020/11/27 20:54
 */
@Controller
@RequestMapping("/chat/service")
public class MainController extends BaseController {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 处理请求
	 * @author guobingbing
	 * @date 2020/11/26 9:40
	 * @param request
	 * @return java.lang.String
	 */
	@PostMapping("/v1")
	@ResponseBody
	public String doRequest(HttpServletRequest request){
		String result = "";
		try{
			// 读取请求内容
			String reqBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
			logger.info("业务端请求=Input Params>>>>>>>>>>>{}", reqBody);
			if(StringUtils.isNotBlank(reqBody)){
				JSONObject jsonObject = JSON.parseObject(reqBody);
				String action = jsonObject.getString("action");
				if(StringUtils.isBlank(action)){
					return RespResult.create(RespCode.PARAMS_ERROR).setMessage("接口名称不能为空").toString();
				}
				ChatService chatService = ServiceBeanFactory.getBean(action);
				if(chatService !=null){
					boolean isPass = false;
					String token = jsonObject.getString("token");
					JSONObject body = jsonObject.getJSONObject("data");
					if(body==null){
						return RespResult.create(RespCode.PARAMS_ERROR).setMessage("参数为空").toString();
					}
					if(chatService instanceof UnNeedVerifyToken){
						isPass = true;
					}else{
						if(StringUtils.isBlank(token)){
							return RespResult.create(RespCode.PARAMS_ERROR).setMessage("Token为空").toString();
						}
						RespResult<Map<String, String>> verifyResult = this.verifyToken(token, systemConfig.getJwt().getSecretKey());
						if(RespCode.SUCCESS.getCode().equals(verifyResult.getCode())){
							isPass = true;
							body.put("userId", verifyResult.getData().get("userId"));
							if(!body.containsKey("mobile")){
								body.put("mobile", verifyResult.getData().get("mobile"));
							}
						}
					}
					if(isPass){
						RequestMessage reqMessage = new RequestMessage();
						reqMessage.setAction(action);
						reqMessage.setClientIp(NetUtil.getRemoteIp(request));
						reqMessage.setToken(token);
						reqMessage.setData(body);
						result = chatService.handler(reqMessage);
					}else{
						result = RespResult.create(RespCode.NEED_LOGIN).toString();
					}
				} else{
					result = RespResult.create(RespCode.PARAMS_ERROR).setMessage("接口名称错误").toString();
				}
			}else{
				result = RespResult.create(RespCode.PARAMS_ERROR).setMessage("参数内容不能为空").toString();
			}
		}catch(Exception e){
			logger.error("MainController", e);
			result = RespResult.create(RespCode.HANDLE_ERROR).toString();
		}finally {
			logger.info("业务端输出=Output Params>>>>>>>>>>>{}", result);
		}
		return result;
	}

}
