package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/16 19:22
 */
@Setter
@Getter
public class SearchUserRes extends BaseResponse {

	private List<ShareUser> list = new ArrayList<>();

	/**
	 * 添加朋友
	 * @param user
	 */
	public void addUser(ShareUser user){
		this.list.add(user);
	}
}
