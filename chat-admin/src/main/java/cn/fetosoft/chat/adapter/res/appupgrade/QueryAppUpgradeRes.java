package cn.fetosoft.chat.adapter.res.appupgrade;

import cn.fetosoft.chat.adapter.res.chat.PageResponse;
import cn.fetosoft.chat.core.data.vo.AppUpgradeVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 9:42
 */
@Setter
@Getter
public class QueryAppUpgradeRes extends PageResponse {

	private List<AppUpgradeVO> list;
}
