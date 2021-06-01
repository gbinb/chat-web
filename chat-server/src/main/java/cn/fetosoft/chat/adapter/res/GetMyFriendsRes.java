package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/16 20:35
 */
@Setter
@Getter
public class GetMyFriendsRes extends BaseResponse {

	private List<MyFriend> list = new ArrayList<>(16);

	public void addFriend(MyFriend friend){
		this.list.add(friend);
	}

	@Setter
	@Getter
	public static class MyFriend{

		/**
		 * 基本用户信息
		 */
		private ShareUser user;

		/**
		 * 备注名
		 */
		private String alias;

		/**
		 * 标签
		 */
		private String tag;

		/**
		 * 备注信息
		 */
		private String note;
	}
}
