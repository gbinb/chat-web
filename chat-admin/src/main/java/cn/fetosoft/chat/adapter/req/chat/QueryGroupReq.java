package cn.fetosoft.chat.adapter.req.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 20:16
 */
@Setter
@Getter
public class QueryGroupReq extends PageRequest {

	private String groupId;

	private String ownerId;

	private String mobile;

	private int members;
}
