package cn.fetosoft.chat.core.data.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/23 9:38
 */
@Setter
@Getter
public class GroupVO {

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
	private String createTime;

	/**
	 * 修改时间
	 */
	private String modifyTime;

	/**
	 * 群主昵称
	 */
	private String ownerNickName;

	/**
	 * 群主头像
	 */
	private String ownerAvatar;

	/**
	 * 群主手机
	 */
	private String ownerMobile;
}
