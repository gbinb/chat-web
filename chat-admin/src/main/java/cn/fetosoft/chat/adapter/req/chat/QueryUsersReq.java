package cn.fetosoft.chat.adapter.req.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:02
 */
@Setter
@Getter
public class QueryUsersReq extends PageRequest {

	private String userId;

	private String mobile;
}
