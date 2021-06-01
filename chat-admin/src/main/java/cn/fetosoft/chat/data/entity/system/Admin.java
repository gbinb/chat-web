package cn.fetosoft.chat.data.entity.system;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Admin {
    /**
     * 自增ID
     */
    private Integer id;

	/**
	 * 管理员ID
	 */
	private String adminId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 锁定状态：0-正常，1-锁定
     */
    private String locked;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String note;

    /**
     * 修改时间
     */
    private Date modifyTime;

    private String loginIp;

    /**
     * 最近登录时间
     */
    private Date loginTime;
}