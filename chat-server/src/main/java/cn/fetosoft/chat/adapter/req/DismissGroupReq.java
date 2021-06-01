package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 16:06
 */
@Setter
@Getter
public class DismissGroupReq extends BaseRequest {

	/**
	 * 群ID
	 */
	private String groupId;
}
