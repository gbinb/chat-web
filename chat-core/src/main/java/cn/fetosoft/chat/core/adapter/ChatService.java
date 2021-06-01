package cn.fetosoft.chat.core.adapter;

/**
 * 业务服务
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/11 11:11
 */
public interface ChatService {

	/**
	 * 处理聊天信息
	 * @param message
	 * @return
	 */
	String handler(RequestMessage message);
}
