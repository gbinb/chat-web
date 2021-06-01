package cn.fetosoft.chat.adapter.req.common;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/13 13:58
 */
@Setter
@Getter
public class VerifyTokenReq extends BaseRequest {

	private String adminId;
}
