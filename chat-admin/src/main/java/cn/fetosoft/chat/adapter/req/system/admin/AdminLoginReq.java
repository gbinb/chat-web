package cn.fetosoft.chat.adapter.req.system.admin;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 14:02
 */
@Setter
@Getter
public class AdminLoginReq extends BaseRequest {

	@NotBlank(message = "用户名不能为空")
	@Length(min = 4, max = 20, message = "用户名长度在4~20位且由字母数字下划线组成")
	private String username;

	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度不足")
	private String password;

	@NotBlank(message = "验证码不能为空")
	@Length(min = 4, max = 4, message = "验证码格式不正确")
	private String verifyCode;
}
