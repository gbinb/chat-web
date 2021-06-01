package cn.fetosoft.chat.core.redis;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Redisson基本配置
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/11/30 11:01
 */
@Setter
@Getter
public class RedissonConfig {

	private String password;

	private String mode;

	private int scanInterval;

	private List<String> nodes;
}
