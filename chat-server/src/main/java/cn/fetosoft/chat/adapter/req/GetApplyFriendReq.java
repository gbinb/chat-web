package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/18 15:39
 */
@Setter
@Getter
public class GetApplyFriendReq extends BaseRequest {

	/**
	 * 是否能过，0-未处理 1-通过 2-拒绝 3-忽略
	 */
	private String status;
}
