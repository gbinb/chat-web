package cn.fetosoft.chat.core.data.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {

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
     * 密码
     */
    private String passWord;

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

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

	/**
	 * 最近登录IP
	 */
	private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
}