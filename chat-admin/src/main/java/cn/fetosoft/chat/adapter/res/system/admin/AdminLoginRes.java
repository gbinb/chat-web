package cn.fetosoft.chat.adapter.res.system.admin;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 14:03
 */
@Setter
@Getter
public class AdminLoginRes extends BaseResponse {

	/**
	 * token
	 */
	private String token;

	private String username;

	private String name;

	private String avatar;
}
