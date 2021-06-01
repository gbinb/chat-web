package cn.fetosoft.chat.core.data.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:23
 */
@Setter
@Getter
public class UserVO {

	/**
	 * 自动ID
	 */
	private Long id;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 图像
	 */
	private String avatar;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 性别
	 */
	private String gender;

	private String genderText;

	/**
	 * 个性签名
	 */
	private String signature;

	/**
	 * aesKey
	 */
	private String aesKey;

	/**
	 * 状态：1-正常
	 */
	private String status;

	private String statusText;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String modifyTime;

	/**
	 * 最近登录IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登录时间
	 */
	private String lastLoginTime;
}
