package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/16 20:53
 */
@Setter
@Getter
public class FriendForm extends BaseForm {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 好友ID
	 */
	private String friendId;

	/**
	 * 关键字
	 */
	private String searchKey;

	/**
	 * 黑名单：0-否 1-是
	 */
	private String black;
}
