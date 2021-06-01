package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MsgFile {
    /**
     * 自动ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件格式
     */
    private String fileFormat;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     * 文件Url
     */
    private String fileUrl;

    /**
     * 存储相对位置
     */
    private String relativePath;

    /**
     * md5值
     */
    private String md5;

    /**
     * 存活时长，单位秒
     */
    private Integer alive;

    /**
     * 销毁时间
     */
    private Date destroyTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否已读：0-未读 1-已读
     */
    private String isRead;

    /**
     * 读取时间
     */
    private Date readTime;
}