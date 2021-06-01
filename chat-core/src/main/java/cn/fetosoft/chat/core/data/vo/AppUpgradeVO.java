package cn.fetosoft.chat.core.data.vo;

import cn.fetosoft.chat.core.enums.YesOrNo;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 10:15
 */
@Setter
@Getter
public class AppUpgradeVO {

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

	/**
	 * apk下载地址
	 */
	private String apkUrl;

	/**
	 * 版本说明
	 */
	private String note;

	/**
	 * 强制升级：0-否 1-是
	 */
	private String forceUpgrade;

	private String forceUpgradeText;

	/**
	 * 是否跳过版本：0-否 1-是
	 */
	private String isSkip;

	private String isSkipText;

	/**
	 * 创建时间
	 */
	private String createTime;
}
