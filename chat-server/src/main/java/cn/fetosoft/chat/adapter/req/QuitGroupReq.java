package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/31 13:52
 */
@Setter
@Getter
public class QuitGroupReq extends BaseRequest {

	/**
	 * 群ID
	 */
	@NotBlank(message = "群ID不能为空")
	private String groupId;

	/**
	 * 群成员ID，多个请用","隔开
	 */
	private String members;
}
