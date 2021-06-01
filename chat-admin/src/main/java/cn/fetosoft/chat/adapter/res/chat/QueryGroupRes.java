package cn.fetosoft.chat.adapter.res.chat;

import cn.fetosoft.chat.core.data.vo.GroupVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 20:18
 */
@Setter
@Getter
public class QueryGroupRes extends PageResponse {

	private List<GroupVO> list;
}
