package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/29 14:02
 */
@Setter
@Getter
public class GetProfileRes extends BaseResponse {

	private String userId;

	private String nickName;

	private String mobile;

	private String avatar;

	private String country;

	private long registerTime;

	private String publicKey;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 个性签名
	 */
	private String signature;

	/**
	 * 是否好友
	 */
	private int relationship;
}
