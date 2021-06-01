package cn.fetosoft.chat.core.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserKey {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}