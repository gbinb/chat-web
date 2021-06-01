package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @create 2020/12/18 17:04
 */
@Setter
@Getter
public class ApplyFriendHandlerReq extends BaseRequest {

	/**
	 * 申请ID
	 */
	@NotBlank(message = "申请ID不能为空")
	private String applyId;

	/**
	 * 是否通过
	 */
	private String status;
}
