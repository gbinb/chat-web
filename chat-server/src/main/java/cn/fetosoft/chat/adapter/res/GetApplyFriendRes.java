package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/18 15:39
 */
@Setter
@Getter
public class GetApplyFriendRes extends BaseResponse {

	private List<Friend> list = new ArrayList<>(16);

	public void addFriend(Friend friend){
		this.list.add(friend);
	}

	@Setter
	@Getter
	public static class Friend{

		private String applyId;

		private String leaveMsg;

		private long applyTime;

		private String status;

		private ShareUser user;
	}
}
