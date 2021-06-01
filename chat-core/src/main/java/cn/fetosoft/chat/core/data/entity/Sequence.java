package cn.fetosoft.chat.core.data.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Sequence {

    /**
     * 自增ID
     */
    private Long id;

	/**
	 * 类别
	 */
	private String category;

    /**
     * 创建时间
     */
    private Date createTime;
}