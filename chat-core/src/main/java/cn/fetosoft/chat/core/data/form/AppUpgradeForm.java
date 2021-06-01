package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 10:01
 */
@Setter
@Getter
public class AppUpgradeForm extends BaseForm {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 版本类型：test-测试，release-发行版
	 */
	private String versionType;
}
