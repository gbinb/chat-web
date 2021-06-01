package cn.fetosoft.chat.core.redis;

import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redisson模板工具类
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/11/28 16:17
 */
public class RedissonTemplate {

	private RedissonClient redissonClient;

	public RedissonTemplate(RedissonClient redissonClient){
		this.redissonClient = redissonClient;
	}

	/**
	 * 获取String值
	 * @param key
	 * @return
	 */
	public String getString(String key){
		String value = null;
		RBucket<String> bucket = this.redissonClient.getBucket(key);
		if(bucket!=null){
			value = bucket.get();
		}
		return value;
	}

	/**
	 * 设置一个Key
	 * @param key
	 * @param value
	 * @param expiredTime
	 */
	public void setString(String key, String value, long expiredTime){
		RBucket<String> bucket = this.redissonClient.getBucket(key);
		bucket.set(value, expiredTime, TimeUnit.SECONDS);
	}

	/**
	 * 删除字符串缓存
	 * @param key
	 * @return
	 */
	public boolean deleteString(String key){
		RBucket<String> bucket = this.redissonClient.getBucket(key);
		if(bucket.isExists()){
			bucket.delete();
		}
		return true;
	}

	/**
	 * 获取List
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> RList<T> getList(String key){
		return this.redissonClient.getList(key);
	}

	/**
	 * 存放集合
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> boolean putList(String key, List<T> list){
		RList<T> rList = this.redissonClient.getList(key);
		return rList.addAll(list);
	}

	/**
	 * 存放集合
	 * @param key
	 * @param list
	 * @param expireTime 过期时间，分钟
	 * @return
	 */
	public <T> boolean setList(String key, List<T> list, long expireTime){
		RList<T> rList = this.redissonClient.getList(key);
		rList.expire(expireTime, TimeUnit.MINUTES);
		if(rList.isExists() && !rList.isEmpty()){
			rList.clear();
		}
		return rList.addAll(list);
	}

	/**
	 * 删除集合
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> boolean deleteList(String key){
		RList<T> rList = this.redissonClient.getList(key);
		return rList.delete();
	}
}
