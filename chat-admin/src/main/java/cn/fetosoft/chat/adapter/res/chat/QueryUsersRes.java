package cn.fetosoft.chat.adapter.res.chat;

import cn.fetosoft.chat.core.data.vo.UserVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:08
 */
@Setter
@Getter
public class QueryUsersRes extends PageResponse {

	private List<UserVO> list;
}
