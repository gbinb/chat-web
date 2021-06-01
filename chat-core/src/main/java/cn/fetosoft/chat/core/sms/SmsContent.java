package cn.fetosoft.chat.core.sms;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信内容
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/18 9:06
 */
@Setter
@Getter
public class SmsContent {

	/**
	 * 手机号
	 */
	private String[] mobiles;

	/**
	 * 短信模板内容
	 */
	private String template;

	/**
	 * 参数列表
	 */
	private List<String> params = new ArrayList<>();

	/**
	 * 新增参数
	 * @param param
	 */
	public void addParam(String param){
		this.params.add(param);
	}
}
