package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/17 15:47
 */
@Setter
@Getter
public class ApplyFriendForm extends BaseForm {

	/**
	 * 申请编号
	 */
	private String applyId;

	/**
	 * 申请人ID
	 */
	private String applyUserId;

	/**
	 * 目标用户ID
	 */
	private String targetUserId;

	/**
	 * 是否通过：0-未处理, 1-通过，2-不通过, 3-忽略
	 */
	private String status;
}