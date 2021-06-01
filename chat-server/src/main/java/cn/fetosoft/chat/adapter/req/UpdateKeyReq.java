package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/23 13:28
 */
@Setter
@Getter
public class UpdateKeyReq extends BaseRequest {

	/**
	 * 加密的数据
	 */
	private String encryptData;
}
