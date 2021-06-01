package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/31 11:06
 */
@Setter
@Getter
public class RemoveFriendReq extends BaseRequest {

	/**
	 * 好友的用户ID
	 */
	private String friendId;
}
