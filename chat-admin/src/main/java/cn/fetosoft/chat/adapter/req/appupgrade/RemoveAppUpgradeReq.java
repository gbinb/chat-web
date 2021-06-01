package cn.fetosoft.chat.adapter.req.appupgrade;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 15:33
 */
@Setter
@Getter
public class RemoveAppUpgradeReq extends BaseRequest {

	/**
	 * 删除的ID集合
	 */
	private String[] ids;
}
