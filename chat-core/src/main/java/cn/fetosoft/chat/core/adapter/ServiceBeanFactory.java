package cn.fetosoft.chat.core.adapter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 服务bean工厂
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/9/4 14:18
 */
@Component
public class ServiceBeanFactory implements ApplicationContextAware {

	/**
	 * Spring上下文
	 */
	private static ApplicationContext applicationCtx;

	/**
	 * 根据名称从容器中获取Bean
	 * @param name
	 * @return
	 */
	public static ChatService getBean(String name){
		return (ChatService)applicationCtx.getBean(name);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		applicationCtx = applicationContext;
	}
}
