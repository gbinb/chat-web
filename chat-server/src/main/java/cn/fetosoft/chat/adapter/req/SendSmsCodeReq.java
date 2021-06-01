package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author guobingbing
 * @create 2020/12/28 14:13
 */
@Setter
@Getter
public class SendSmsCodeReq extends BaseRequest {

	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
	private String mobile;

	private String imageCode;
}
