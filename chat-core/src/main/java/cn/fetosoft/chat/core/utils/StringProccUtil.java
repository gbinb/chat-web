package cn.fetosoft.chat.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串处理工具
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/3/16 14:50
 */
public final class StringProccUtil {

	/**
	 * 手机号脱敏
	 * @param mobile
	 * @return
	 */
	public static String proceeMobile(String mobile){
		if(StringUtils.isNotBlank(mobile)){
			mobile = StringUtils.left(mobile, 3) + "****" + StringUtils.right(mobile, 4);
		}
		return mobile;
	}
}
