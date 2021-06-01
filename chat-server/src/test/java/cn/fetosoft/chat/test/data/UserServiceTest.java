package cn.fetosoft.chat.test.data;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.entity.UserKey;
import cn.fetosoft.chat.core.data.service.UserKeyService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.encrypt.HmacSHA1Signer;
import cn.fetosoft.chat.core.encrypt.RSAUtils;
import cn.fetosoft.chat.core.utils.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * 用户数据
 * @author guobingbing
 * @create 2020/12/15 16:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "cn.fetosoft.chat.core.data.mapper")
public class UserServiceTest {

	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private UserService userService;
	@Autowired
	private UserKeyService userKeyService;

	@Test
	public void insert(){
		User user = new User();
		user.setUserId("U5214658001");
		user.setUserName("gbinb");
		user.setNickName("米酒");
		user.setMobile("18600567899");
		user.setAesKey(RandomUtil.getRandomKey(32));
		user.setPassWord(HmacSHA1Signer.signString("123456", systemConfig.getHmacKey()));
		user.setStatus("1");
		user.setCreateTime(new Date());
		int flag = userService.insert(user);

		UserKey userKey = new UserKey();
		userKey.setUserId(user.getUserId());
		try {
			RSAUtils.RsaKey rsaKey = RSAUtils.genKeyPair();
			userKey.setPrivateKey(rsaKey.getPrivateKey());
			userKey.setPublicKey(rsaKey.getPublicKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		userKey.setCreateTime(new Date());
		flag = userKeyService.insert(userKey);
		System.out.println(flag);
	}

	@Test
	public void getUser(){
		User user = userService.selectByMobile("18600567899");
		System.out.println(user.getNickName());
	}
}
