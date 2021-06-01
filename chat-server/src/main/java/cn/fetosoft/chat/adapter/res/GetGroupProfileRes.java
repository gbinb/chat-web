package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/9 11:29
 */
@Setter
@Getter
public class GetGroupProfileRes extends BaseResponse {

	private String groupId;

	private String ownerId;

	private String name;

	private String avatar;

	private String topic;

	private int total;

	private ShareUser owner;

	private List<ShareMember> list;
}
