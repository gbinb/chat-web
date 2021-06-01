package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/15 16:17
 */
@Setter
@Getter
public class ModifyFriendRes extends BaseResponse {

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

	/**
	 * 好友用户信息
	 */
	private ShareUser user;
}
