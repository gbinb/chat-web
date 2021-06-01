package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/15 14:53
 */
@Setter
@Getter
public class UserForm extends BaseForm {

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 密码
	 */
	private String passWord;

	/**
	 * 状态：1-正常
	 */
	private String status;
}
