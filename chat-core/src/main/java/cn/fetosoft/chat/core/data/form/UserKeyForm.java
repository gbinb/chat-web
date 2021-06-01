package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/16 10:49
 */
@Setter
@Getter
public class UserKeyForm extends BaseForm {

	/**
	 * 用户编号
	 */
	private String userId;
}
