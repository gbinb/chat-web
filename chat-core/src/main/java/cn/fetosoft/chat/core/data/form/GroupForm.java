package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/23 9:52
 */
@Setter
@Getter
public class GroupForm extends BaseForm {

	/**
	 * 群ID
	 */
	private String groupId;

	/**
	 * 群主ID
	 */
	private String ownerId;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 成员数
	 */
	private Integer members;
}
