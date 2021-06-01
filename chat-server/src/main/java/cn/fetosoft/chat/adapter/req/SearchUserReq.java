package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @create 2020/12/16 19:22
 */
@Setter
@Getter
public class SearchUserReq extends BaseRequest {

	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	private String mobile;
}
