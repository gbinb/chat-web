package cn.fetosoft.chat.test.data;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.data.service.SequenceService;
import cn.fetosoft.chat.core.encrypt.HmacSHA1Signer;
import cn.fetosoft.chat.core.enums.YesOrNo;
import cn.fetosoft.chat.data.entity.system.Admin;
import cn.fetosoft.chat.data.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 18:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = {"cn.fetosoft.chat.core.data.mapper","cn.fetosoft.chat.data.mapper"})
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;
	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private SystemConfig systemConfig;

	@Test
	public void insert(){
		Admin admin = new Admin();
		admin.setAdminId(sequenceService.getAdminId());
		admin.setUsername("root");
		admin.setPassword(HmacSHA1Signer.signString("123456", systemConfig.getHmacKey()));
		admin.setName("超级管理员");
		admin.setLocked(YesOrNo.No.getCode());
		admin.setCreateTime(new Date());
		int flag = adminService.insert(admin);
		System.out.println(flag);
	}
}
