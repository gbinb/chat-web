package cn.fetosoft.chat.test.redis;

import cn.fetosoft.chat.connect.chat.ChatContent;
import cn.fetosoft.chat.connect.chat.ChatMessage;
import cn.fetosoft.chat.connect.chat.ChatMode;
import cn.fetosoft.chat.connect.chat.ReceiveMessage;
import cn.fetosoft.chat.core.utils.UUidUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author guobingbing
 * @create 2020/12/25 14:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "cn.fetosoft.chat.core.data.mapper")
public class RedisTest {

	@Autowired
	private RedissonClient redissonClient;

	@Test
	public void offer() throws Exception{
		RQueue<String> queue = redissonClient.getQueue("U201216000002");
		ReceiveMessage message = new ReceiveMessage();
		message.setMode(ChatMode.Single.getCode());
		message.setRt(System.currentTimeMillis());

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMid(UUidUtil.buildUUID());
		chatMessage.setType(10);

		ChatContent content = new ChatContent();
		content.setText("收到历史消息！");
		chatMessage.setContent(content);
		message.setChatMessage(chatMessage);
		queue.expire(1, TimeUnit.MINUTES);
		System.out.println("offer >> " + queue.offer(JSON.toJSONString(message)));

		TimeUnit.SECONDS.sleep(40);

		chatMessage.setMid(UUidUtil.buildUUID());
		queue.expire(1, TimeUnit.MINUTES);
		System.out.println("offer >> " + queue.offer(JSON.toJSONString(message)));

		TimeUnit.SECONDS.sleep(30);
		this.poll();
	}


	public void poll(){
		RQueue<String> queue = redissonClient.getQueue("U201216000002");
		System.out.println(queue.size());
		List<String> list = queue.poll(queue.size());
		for(int i=0; i<list.size(); i++){
			ReceiveMessage m = JSON.toJavaObject(JSON.parseObject(list.get(i)), ReceiveMessage.class);
			//System.out.println(m.getChatMessage().getMid() + " => " + m.getChatMessage().getContent().getText());
		}
	}

	@Test
	public void check(){
		RQueue<String> queue = redissonClient.getQueue("U2012160000021");
		System.out.println(queue.isExists());
	}
}
