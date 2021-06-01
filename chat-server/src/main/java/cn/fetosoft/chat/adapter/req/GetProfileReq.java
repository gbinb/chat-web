package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/29 20:03
 */
@Setter
@Getter
public class GetProfileReq extends BaseRequest {

	/**
	 * 目标用户ID
	 */
	private String targetUserId;
}
