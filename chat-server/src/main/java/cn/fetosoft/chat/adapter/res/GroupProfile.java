package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/15 13:26
 */
@Setter
@Getter
public class GroupProfile extends BaseResponse {

	private String groupId;

	private String ownerId;

	private String name;

	private String avatar;

	private String topic;

	private int total;

	private ShareUser owner;

	private List<ShareMember> list;

	public GroupProfile(String groupId){
		this.groupId = groupId;
	}
}
