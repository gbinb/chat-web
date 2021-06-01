package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 16:55
 */
@Setter
@Getter
public class GetAppUpgradeRes extends BaseResponse {

	/**
	 * 1-强制更新
	 */
	private int forceUpdate = 0;

	private String version;

	private String apkUrl;

	private String note;
}
