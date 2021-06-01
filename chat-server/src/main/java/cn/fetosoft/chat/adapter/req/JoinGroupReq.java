package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/26 17:23
 */
@Setter
@Getter
public class JoinGroupReq extends BaseRequest {

	/**
	 * 群ID
	 */
	private String groupId;

	/**
	 * 成员
	 */
	private String members;
}
