package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @create 2020/12/17 16:44
 */
@Setter
@Getter
public class ApplyAddFriendReq extends BaseRequest {

	/**
	 * 目标用户ID
	 */
	@NotBlank(message = "目标用户ID不能为空")
	@Length(min = 5, max = 30, message = "目标用户ID格式不正确")
	private String targetUserId;

	/**
	 * 留言
	 */
	private String leaveMsg;
}
