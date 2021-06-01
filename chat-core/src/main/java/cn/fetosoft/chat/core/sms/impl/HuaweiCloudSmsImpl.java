package cn.fetosoft.chat.core.sms.impl;

import cn.fetosoft.chat.core.sms.SmsConfig;
import cn.fetosoft.chat.core.sms.SmsContent;
import cn.fetosoft.chat.core.sms.SmsResult;
import cn.fetosoft.chat.core.sms.SmsService;
import cn.fetosoft.chat.core.utils.IdGenerateUtils;
import cn.fetosoft.commons.utils.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 华为云短信
 * @author guobingbing
 * @create 2020-9-22 21:44
 */
public class HuaweiCloudSmsImpl implements SmsService {

	private static final Logger logger = LoggerFactory.getLogger(HuaweiCloudSmsImpl.class);
	private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
	private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

	private SmsConfig config;

	public HuaweiCloudSmsImpl(SmsConfig config){
		this.config = config;
	}

	@Override
	public SmsResult sendMsg(SmsContent content) {
		SmsResult result = new SmsResult(false);
		String wsseHeader = buildWsseHeader();
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0; i<content.getParams().size(); i++){
			sb.append("\"").append(content.getParams().get(i)).append("\"");
			if(i<(content.getParams().size()-1)){
				sb.append(",");
			}
		}
		sb.append("]");
		Map<String, String> header = new HashMap<>();
		header.put("X-WSSE", wsseHeader);
		header.put(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE);
		header.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

		Map<String, String> body = new HashMap<>();
		//body.put("from", this.sender);
		body.put("to", "+86" + content.getMobiles()[0]);
		body.put("templateId", content.getTemplate());
		body.put("templateParas", sb.toString());
		try{
			String res = HttpClientUtil.postFormData(this.config.getGatewayUrl(), body, header, null);
			JSONObject json = JSONObject.parseObject(res);
			String code = json.getString("code");
			String message = json.getString("description");
			logger.info("code:{} => message:{}", code, message);
			if("000000".equals(code)){
				result.setSuccess(true);
			}else{
				result.setErrorMsg(message);
			}
		}catch(Exception e){
			logger.error("sendSms", e);
		}
		return result;
	}

	private String buildWsseHeader() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String time = sdf.format(new Date());
		String nonce = IdGenerateUtils.buildUUid();
		byte[] passwordDigest = DigestUtils.sha256(nonce + time + this.config.getSecret());
		String hexDigest = Hex.encodeHexString(passwordDigest);
		String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes());
		return String.format(WSSE_HEADER_FORMAT, this.config.getKey(), passwordDigestBase64Str, nonce, time);
	}
}
