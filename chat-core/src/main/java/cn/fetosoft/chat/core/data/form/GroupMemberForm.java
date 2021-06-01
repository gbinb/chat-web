package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/23 20:49
 */
@Setter
@Getter
public class GroupMemberForm extends BaseForm {

	/**
	 * 群主ID
	 */
	private String ownerId;

	/**
	 * 成员用户ID
	 */
	private String userId;

	/**
	 * 群ID
	 */
	private String groupId;
}
