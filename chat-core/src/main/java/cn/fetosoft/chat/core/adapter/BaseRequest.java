package cn.fetosoft.chat.core.adapter;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author guobingbing
 * @create 2020/12/11 11:18
 */
@Setter
@Getter
public class BaseRequest {

	private String userId;

	private String clientIp;

	@JSONField(serialize = false)
	private HttpServletRequest request;
}
