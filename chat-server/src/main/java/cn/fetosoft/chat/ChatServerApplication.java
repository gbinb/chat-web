package cn.fetosoft.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 启动类
 * @author guobingbing
 * @create 2020/12/11 9:54
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan(basePackages = "cn.fetosoft.chat.core.data.mapper")
public class ChatServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatServerApplication.class, args);
		System.out.println("The server is start!!!");
	}
}
