package cn.fetosoft.chat.core.data.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/21 9:02
 */
@Setter
@Getter
public class GroupOwner extends Group {

	private String ownerNickName;

	private String ownerAvatar;

	private String ownerMobile;
}
