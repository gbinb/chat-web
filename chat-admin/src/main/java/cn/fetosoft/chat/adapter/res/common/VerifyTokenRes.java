package cn.fetosoft.chat.adapter.res.common;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/13 15:58
 */
@Setter
@Getter
public class VerifyTokenRes extends BaseResponse {

	private String username;

	private String name;

	private String avatar;
}
