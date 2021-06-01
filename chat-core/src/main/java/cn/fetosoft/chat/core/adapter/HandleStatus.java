package cn.fetosoft.chat.core.adapter;

/**
 * 响应状态
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/11/27 17:47
 */
public interface HandleStatus {

	/**
	 * 响应状态码
	 * @return
	 */
	String getCode();

	/**
	 * 响应状态信息
	 * @return
	 */
	String getMessage();
}
