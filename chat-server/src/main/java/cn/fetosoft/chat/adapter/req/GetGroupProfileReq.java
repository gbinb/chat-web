package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/9 11:28
 */
@Setter
@Getter
public class GetGroupProfileReq extends BaseRequest {

	@NotBlank(message = "群ID不能为空")
	private String groupId;

	/**
	 * 1-基本信息 2-详细信息（群主及群成员信息）
	 */
	@Max(value = 2, message = "类型格式不正确")
	@Min(value = 1, message = "类型格式不正确")
	private int type = 1;

}
