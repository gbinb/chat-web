package cn.fetosoft.chat.adapter.res;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/26 17:24
 */
@Setter
@Getter
public class JoinGroupRes extends GroupProfile {

	public JoinGroupRes(String groupId) {
		super(groupId);
	}
}
