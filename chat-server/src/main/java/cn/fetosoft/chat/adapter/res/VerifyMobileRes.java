package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/20 15:05
 */
@Setter
@Getter
public class VerifyMobileRes extends BaseResponse {

	/**
	 * 认证码
	 */
	private String authCode;
}
