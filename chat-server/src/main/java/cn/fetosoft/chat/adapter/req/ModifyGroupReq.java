package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/30 16:29
 */
@Setter
@Getter
public class ModifyGroupReq extends BaseRequest {

	/**
	 * 群ID
	 */
	private String groupId;

	/**
	 * 群名称
	 */
	private String name;

	/**
	 * 群公告
	 */
	private String topic;

	/**
	 * 群头像
	 */
	private String avatar;
}
