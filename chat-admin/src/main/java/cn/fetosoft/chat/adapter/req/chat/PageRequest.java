package cn.fetosoft.chat.adapter.req.chat;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 19:03
 */
@Setter
@Getter
public class PageRequest extends BaseRequest {

	/**
	 * 超始页
	 */
	private int page = 1;

	/**
	 * 每页记录数，默认0为查询全部
	 */
	private int rows = 10;
}
