package cn.fetosoft.chat.core.component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 单机版的缓存容器
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 19:45
 */
public final class CacheFactory {

	private static final Cache<String, String> CACHE = CacheBuilder.newBuilder().initialCapacity(16)
			.concurrencyLevel(Runtime.getRuntime().availableProcessors()<<2).expireAfterWrite(5, TimeUnit.MINUTES).build();

	/**
	 * 存入缓存
	 * @param key
	 */
	public static void put(String key, String value){
		CACHE.put(key, value);
	}

	/**
	 * 取数据
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return CACHE.getIfPresent(key);
	}

	/**
	 * 删除缓存
	 * @param key
	 */
	public static void remove(String key){
		CACHE.invalidate(key);
	}
}
