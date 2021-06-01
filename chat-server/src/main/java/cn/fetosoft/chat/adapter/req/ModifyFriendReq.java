package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改好友信息
 * @author guobingbing
 * @wechat t_gbinb
 * @create 2020/12/30 15:30
 */
@Setter
@Getter
public class ModifyFriendReq extends BaseRequest {

	/**
	 * 好友ID
	 */
	private String friendId;

	/**
	 * 备注名
	 */
	private String alias;

	/**
	 * 标签
	 */
	private String tag;

	/**
	 * 备注信息
	 */
	private String note;
}
