package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取我的好友
 * @author guobingbing
 * @create 2020/12/16 20:14
 */
@Setter
@Getter
public class GetMyFriendsReq extends BaseRequest {

	/**
	 * 搜索关键字
	 */
	private String searchKey;

}
