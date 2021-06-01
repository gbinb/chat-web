package cn.fetosoft.chat.adapter.res;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/15 10:18
 */
@Setter
@Getter
public class QuitGroupRes extends GroupProfile {

	public QuitGroupRes(String groupId) {
		super(groupId);
	}
}
