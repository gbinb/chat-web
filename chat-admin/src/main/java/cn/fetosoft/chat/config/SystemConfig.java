package cn.fetosoft.chat.config;

import cn.fetosoft.chat.core.minio.MinioHelper;
import cn.fetosoft.chat.core.redis.RedissonBuilder;
import cn.fetosoft.chat.core.redis.RedissonConfig;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 10:17
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

	/**
	 * 过期时间
	 */
	private int chatExpired;

	/**
	 * HmacSHA加密
	 */
	private String hmacKey;

	/**
	 * Aes加密Key
	 */
	private String aesKey;

	/**
	 * 文件存放路径
	 */
	private String saveFilePath;

	/**
	 * 文件访问的域名
	 */
	private String fileDomain;

	/**
	 * Jwt配置
	 */
	private Jwt jwt;

	/**
	 * 文件存储库配置
	 */
	private Minio minio;

	@Setter
	@Getter
	public static class Jwt{

		private String secretKey;

		private int expireDays;
	}

	@Setter
	@Getter
	public static class Minio {

		private String endPoint;

		private String accessKey;

		private String secretKey;

		private String minioBucket;
	}

	/**
	 * 配置Redisson
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "redisson")
	public RedissonConfig createRedissonConfig(){
		return new RedissonConfig();
	}

	/**
	 * RedissonClient
	 * @param redissonConfig
	 * @return
	 */
	@Bean
	@ConditionalOnBean({RedissonConfig.class})
	public RedissonClient createRedissionClient(RedissonConfig redissonConfig){
		return new RedissonBuilder(redissonConfig).build();
	}

	/**
	 * 创建RedissonClient
	 * @param redissonClient
	 * @return
	 */
	@Bean
	@ConditionalOnBean({RedissonClient.class})
	public RedissonTemplate createRedissonTemplate(RedissonClient redissonClient){
		return new RedissonTemplate(redissonClient);
	}

	/**
	 * MinIO工具类
	 * @return
	 * @throws Exception
	 */
	@Bean
	public MinioHelper createMinioHelper() throws Exception{
		MinioClient client = new MinioClient(this.minio.getEndPoint(), this.minio.getAccessKey(), this.minio.getSecretKey());
		return new MinioHelper(client);
	}
}
