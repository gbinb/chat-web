package cn.fetosoft.chat.adapter.res.chat;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/28 11:03
 */
@Setter
@Getter
public class QueryGroupMemberRes extends BaseResponse {

	/**
	 * 群成员信息
	 */
	private List<ShareMember> list = new ArrayList<>();

	public void addMemebr(ShareMember member){
		this.list.add(member);
	}
}
