package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/23 19:17
 */
@Setter
@Getter
public class CreateGroupRes extends BaseResponse {

	private String groupId;

	private String ownerId;

	private String name;

	private String avatar;

	private String topic;

	private int total;

	private ShareUser owner;

	private List<ShareMember> list;
}
