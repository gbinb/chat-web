package cn.fetosoft.chat.connect.events;

/**
 * 用户上线事件
 * @author guobingbing
 * @wechat t_gbinb
 * @create 2020/12/25 9:51
 */
public class UserOnlineEvent {

	private String userId;

	/**
	 *
	 */
	public UserOnlineEvent(String userId) {
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}
}
