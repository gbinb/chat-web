package cn.fetosoft.chat.core.adapter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求的消息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/11 10:28
 */
@Setter
@Getter
public class RequestMessage {

	/**
	 * 动作
	 */
	private String action;

	/**
	 * 会话token
	 */
	private String token;

	/**
	 * 加密类型
	 */
	private int encodeType;

	/**
	 * 签名值
	 */
	private String sign;

	/**
	 * 客户IP
	 */
	private String clientIp;

	/**
	 * 参数数据
	 */
	private JSONObject data;

	@JSONField(serialize = false)
	private HttpServletRequest request;
}
