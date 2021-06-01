package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/11 11:38
 */
@Setter
@Getter
public class UserLoginRes extends BaseResponse {

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 认证token
	 */
	private String token;

	/**
	 * 头像
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

	/**
	 * 公钥
	 */
	private String publicKey;

	/**
	 * 注册时间戳
	 */
	private long registerTime;
}
