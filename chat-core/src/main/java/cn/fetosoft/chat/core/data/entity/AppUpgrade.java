package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AppUpgrade implements Comparable<AppUpgrade> {

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

    /**
     * 创建时间
     */
    private Date createTime;

	/**
	 *
	 */
	@Override
	public int compareTo(AppUpgrade o) {
		if(this.id>o.getId()){
			return -1;
		}else if(this.id<o.getId()){
			return 1;
		}else {
			return 0;
		}
	}
}