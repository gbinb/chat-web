package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 登录
 * @author guobingbing
 * @create 2020/12/11 10:58
 */
@Setter
@Getter
public class UserLoginReq extends BaseRequest {

	/**
	 * 1-验证码登录，2-密码登录
	 */
	private int type = 1;

	/**
	 * 用户名
	 */
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
	private String mobile;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 短信验证码
	 */
	private String smsCode;
}
