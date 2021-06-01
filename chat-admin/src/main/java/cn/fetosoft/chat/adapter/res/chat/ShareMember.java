package cn.fetosoft.chat.adapter.res.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/15 10:03
 */
@Setter
@Getter
public class ShareMember {

	private ShareUser user;

	private String alias;

	private String tag;

	private String note;
}
