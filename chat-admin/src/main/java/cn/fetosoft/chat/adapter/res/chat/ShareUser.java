package cn.fetosoft.chat.adapter.res.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * 共用的基础用户信息
 * @author guobingbing
 * @create 2020/12/29 20:29
 */
@Setter
@Getter
public class ShareUser {

	private String userId;

	private String nickName;

	private String mobile;

	private String avatar;

	private String country;

	private String signature;

	private int relationship;

	public ShareUser(){}

	public ShareUser(String userId, String nickName){
		this.userId = userId;
		this.nickName = nickName;
	}
}
