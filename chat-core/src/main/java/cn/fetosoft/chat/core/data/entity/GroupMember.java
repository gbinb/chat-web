package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GroupMember {

    /**
     * 自动ID
     */
    private Long id;

	/**
	 * 群主ID
	 */
	private String ownerId;

    /**
     * 群ID
     */
    private String groupId;

	/**
	 * 成员用户ID
	 */
	private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
}