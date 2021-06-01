package cn.fetosoft.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 * @author guobingbing
 * @create 2020-03-07 20:19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//默认静态资源处理
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/**").addResourceLocations("/");
	}

	/**
	 * 解决跨域问题
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}
