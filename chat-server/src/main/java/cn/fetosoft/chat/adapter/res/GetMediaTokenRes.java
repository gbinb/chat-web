package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/3/1 16:50
 */
@Setter
@Getter
public class GetMediaTokenRes extends BaseResponse {

	/**
	 * 认证Token
	 */
	private String token;

	/**
	 * 频道ID
	 */
	private String channelId;

}
