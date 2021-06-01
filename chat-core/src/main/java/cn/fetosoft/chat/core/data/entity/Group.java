package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Group {
    /**
     * 自动ID
     */
    private Long id;

    /**
     * 群ID
     */
    private String groupId;

    /**
     * 群主ID
     */
    private String ownerId;

    /**
     * 群名称
     */
    private String name;

    /**
     * 公告
     */
    private String topic;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 成员人数
     */
    private Integer members;

    /**
     * 创建时间
     */
    private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;
}