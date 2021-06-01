package cn.fetosoft.chat.adapter.res.common;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/13 9:35
 */
@Setter
@Getter
public class ObtainCaptchaRes extends BaseResponse {

	private String captcha;
}
