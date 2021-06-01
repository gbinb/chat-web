package cn.fetosoft.chat.data.form.system;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 17:01
 */
@Setter
@Getter
public class AdminForm extends BaseForm {

	/**
	 * 管理员ID
	 */
	private String adminId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 锁定状态：0-正常，1-锁定
	 */
	private String locked;
}
