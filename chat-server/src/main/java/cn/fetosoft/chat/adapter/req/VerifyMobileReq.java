package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 15:03
 */
@Setter
@Getter
public class VerifyMobileReq extends BaseRequest {

	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
	private String mobile;

	@NotBlank(message = "验证码不能为空")
	private String smsCode;
}
