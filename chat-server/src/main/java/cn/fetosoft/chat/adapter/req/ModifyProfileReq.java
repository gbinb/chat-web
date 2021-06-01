package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/30 10:52
 */
@Setter
@Getter
public class ModifyProfileReq extends BaseRequest {

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像url
	 */
	private String avatar;

	/**
	 * 国家地区
	 */
	private String country;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 个性签名
	 */
	private String signature;
}
