package cn.fetosoft.chat.adapter.req.chat;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/28 11:03
 */
@Setter
@Getter
public class QueryGroupMemberReq extends BaseRequest {

	/**
	 * 群编号
	 */
	private String groupId;
}
