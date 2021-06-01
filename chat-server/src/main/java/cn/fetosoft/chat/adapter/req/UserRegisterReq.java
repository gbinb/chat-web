package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

/**
 * 用户注册
 * @author guobingbing
 * @create 2020/12/15 19:42
 */
@Setter
@Getter
public class UserRegisterReq extends BaseRequest {

	/**
	 * 认证码
	 */
	@NotBlank(message = "认证码不能为空")
	private String authCode;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	@Length(min = 6, max = 20, message = "密码长度为6~20个字符")
	private String password;

	/**
	 * 昵称
	 */
	@NotBlank(message = "昵称不能为空")
	@Length(min = 1, max = 20, message = "昵称长度为1~20个字符")
	private String nickName;
}
