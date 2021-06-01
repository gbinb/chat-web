package cn.fetosoft.chat.config;

import cn.fetosoft.chat.core.minio.MinioHelper;
import cn.fetosoft.chat.core.redis.RedissonBuilder;
import cn.fetosoft.chat.core.redis.RedissonConfig;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import cn.fetosoft.chat.core.sms.SmsConfig;
import cn.fetosoft.chat.core.sms.SmsService;
import cn.fetosoft.chat.core.sms.impl.HuaweiCloudSmsImpl;
import cn.fetosoft.chat.core.sms.impl.HuaxinSmsImpl;
import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 * @author guobingbing
 * @create 2020/11/28 15:53
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

	/**
	 * 聊天端口号
	 */
	private int chatPort;

	/**
	 * 过期时间
	 */
	private int chatExpired;

	/**
	 * socket类型
	 */
	private String socketType;

	/**
	 * HmacSHA加密
	 */
	private String hmacKey;

	/**
	 * Aes加密Key
	 */
	private String aesKey;

	/**
	 * 默认图像
	 */
	private String defaultAvatar;

	/**
	 * 默认群图像
	 */
	private String defaultGroupAvatar;

	/**
	 * 文件存放路径
	 */
	private String saveFilePath;

	/**
	 * 文件访问的域名
	 */
	private String fileDomain;

	/**
	 * 非法文件格式
	 */
	private String illegalFile;

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
	 * 短信配置
	 */
	private SmsConfig sms;

	/**
	 * 媒体信息配置
	 */
	private Media media;

	/**
	 * 多媒体通道配置
	 */
	@Setter
	@Getter
	public static class Media{
		private String appId;
		private String appSecret;
		private long expiredTime;
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

	/**
	 * 短信服务
	 * @return
	 */
	@Bean
	public SmsService smsService(){
		if("huawei".equalsIgnoreCase(this.sms.getMerchant())){
			return new HuaweiCloudSmsImpl(this.sms);
		}else{
			return new HuaxinSmsImpl(this.sms);
		}
	}
}
