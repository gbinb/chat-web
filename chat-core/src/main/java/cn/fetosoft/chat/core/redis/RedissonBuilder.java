package cn.fetosoft.chat.core.redis;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.BaseConfig;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * 创建Redisson
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/11/28 15:15
 */
public class RedissonBuilder {

	private static final String MODE_SINGLETON = "singleton";
	private static final String MODE_CLUSTER = "cluster";
	private RedissonConfig redissonConfig;

	public RedissonBuilder(RedissonConfig redissonConfig){
		this.redissonConfig = redissonConfig;
	}

	/**
	 * 创建RedissonClient
	 * @return
	 */
	public RedissonClient build(){
		int length = redissonConfig.getNodes().size();
		String[] nodes = new String[length];
		Config config = new Config();

		BaseConfig baseConfig = null;
		if(MODE_SINGLETON.equalsIgnoreCase(redissonConfig.getMode())){
			baseConfig = config.setCodec(new StringCodec()).useSingleServer().setAddress(redissonConfig.getNodes().get(0));
		}else {
			baseConfig = config.setCodec(new StringCodec()).useClusterServers()
					.addNodeAddress(redissonConfig.getNodes().toArray(nodes));
		}
		//设置密码
		if(StringUtils.isNotBlank(redissonConfig.getPassword())) {
			baseConfig.setPassword(redissonConfig.getPassword());
		}
		return Redisson.create(config);
	}
}
