package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Friend {

    /**
     * 自动ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 好友ID
     */
    private String friendId;

    /**
     * 好友的昵称
     */
    private String friendName;

    /**
     * 好友手机号
     */
    private String friendMobile;

	/**
	 * 头像
	 */
	private String friendAvatar;

	/**
	 * 国家
	 */
	private String friendCountry;

	/**
	 * 备注名
	 */
	private String alias;

	/**
	 * 标签
	 */
	private String tag;

	/**
	 * 备注信息
	 */
	private String note;

    /**
     * 黑名单：0-否 1-是
     */
    private String black;

    /**
     * 创建时间
     */
    private Date createTime;
}