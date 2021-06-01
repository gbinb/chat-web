package cn.fetosoft.chat.adapter.req.appupgrade;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 9:50
 */
@Setter
@Getter
public class SaveAppUpgradeReq extends BaseRequest {

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

	/**
	 * 是否跳过版本：0-否 1-是
	 */
	private String isSkip;

}
