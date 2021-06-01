package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ApplyFriend {

    /**
     * 自动编号
     */
    private Long id;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 申请人ID
     */
    private String applyUserId;

	/**
	 * 申请人昵称
	 */
	private String applyNickName;

	/**
	 * 申请人图片
	 */
    private String applyAvatar;

	/**
	 * 申请人国家
	 */
	private String applyCountry;

	/**
	 * 目标用户ID
	 */
	private String targetUserId;

	/**
	 * 留言
	 */
	private String leaveMsg;

    /**
     * 是否通过：0-未处理, 1-通过，2-不通过, 3-忽略
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}