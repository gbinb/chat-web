package cn.fetosoft.chat.core.utils;

import java.util.UUID;

/**
 * UUID
 * @author guobingbing
 * @create 2020/12/15 17:47
 */
public class UUidUtil {

	public static String buildUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
