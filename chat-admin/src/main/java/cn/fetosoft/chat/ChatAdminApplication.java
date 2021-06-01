package cn.fetosoft.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 启动类
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 10:15
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan(basePackages = {"cn.fetosoft.chat.core.data.mapper","cn.fetosoft.chat.data.mapper"})
public class ChatAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatAdminApplication.class, args);
		System.out.println("The server is start!!!");
	}
}
