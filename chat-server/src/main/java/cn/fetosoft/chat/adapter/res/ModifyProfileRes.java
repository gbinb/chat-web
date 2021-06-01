package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/13 15:12
 */
@Setter
@Getter
public class ModifyProfileRes extends BaseResponse {

	private String userId;

	private String nickName;

	private String mobile;

	private String avatar;

	private String country;

	/**
	 * 性别
	 */
	private String gender;

	private String signature;
}
