package cn.fetosoft.chat.queue;

import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.connect.chat.BaseMessage;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 消息队列处理
 * @author guobingbing
 * @wechat
 * @create 2020/12/25 11:51
 */
@Component
public class MessageQueue<T extends BaseMessage> {

	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 向队列中增加元素
	 * @param receiveMessage
	 * @return
	 */
	public void offer(ReceiveMessage<T> receiveMessage){
		T chatMessage = receiveMessage.getChatMessage();
		if(chatMessage!=null && StringUtils.isNotBlank(chatMessage.getRid())){
			this.offerAsync(chatMessage.getRid(), receiveMessage);
		}
	}

	/**
	 * 向队列中增加元素
	 * @param key
	 * @param receiveMessage
	 */
	public void offer(String key, ReceiveMessage<T> receiveMessage){
		T chatMessage = receiveMessage.getChatMessage();
		if(chatMessage!=null && StringUtils.isNotBlank(key)){
			this.offerAsync(key, receiveMessage);
		}
	}

	/**
	 * 异步向队列中增加元素
	 * @param key
	 * @param receiveMessage
	 */
	private void offerAsync(String key, ReceiveMessage<T> receiveMessage){
		RQueue<String> queue = redissonClient.getQueue(key);
		if(queue.isEmpty()){
			queue.expire(systemConfig.getChatExpired(), TimeUnit.HOURS);
		}
		queue.offerAsync(receiveMessage.toJsonString());
	}

	/**
	 * 从队列中获取消息
	 * @param userId
	 * @return
	 */
	public List<String> pollForString(String userId){
		RQueue<String> queue = redissonClient.getQueue(userId);
		if(queue.size()>0){
			return queue.poll(queue.size());
		}else{
			return Collections.emptyList();
		}
	}

	/**
	 * 从队列中获取消息
	 * @param userId
	 * @return
	 */
	public List<ReceiveMessage> pollForRecMessage(String userId){
		RQueue<String> queue = redissonClient.getQueue(userId);
		if(queue!=null && queue.size()>0){
			List<ReceiveMessage> recList = new ArrayList<>();
			List<String> list = queue.poll(queue.size());
			for(String json : list){
				recList.add(ReceiveMessage.toRecMessage(json));
			}
			return recList;
		}else{
			return Collections.emptyList();
		}
	}
}
