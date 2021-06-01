package cn.fetosoft.chat.adapter.req.appupgrade;

import cn.fetosoft.chat.adapter.req.chat.PageRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 9:41
 */
@Setter
@Getter
public class QueryAppUpgradeReq extends PageRequest {

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 版本类型
	 */
	private String versionType;
}
