package cn.fetosoft.chat.connect.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 接收消息内容
 * @author guobingbing
 * @create 2020/12/22 9:44
 */
@Setter
@Getter
public class ReceiveMessage<T extends BaseMessage> implements Serializable {

	/**
	 * 消息场景: 0-心跳 1-单人聊天 2-群聊 3-广播 4-加好友通知 5-加友处理结果 6-创建群通知 7-退群通知, 8-呼叫
	 */
	private int mode;

	/**
	 * 接收时间戳
	 */
	private long rt;

	/**
	 * 计数器
	 */
	private long counter;

	/**
	 * 消息内容
	 */
	private T chatMessage;

	/**
	 * 转换成json字符串
	 * @return
	 */
	public String toJsonString(){
		return JSON.toJSONString(this);
	}

	/**
	 * 将json转换为消息对象
	 * @param message
	 * @return
	 */
	public static ReceiveMessage<BaseMessage> toRecMessage(String message){
		ReceiveMessage<BaseMessage> receiveMessage = new ReceiveMessage<>();
		JSONObject json = JSON.parseObject(message);
		int mode = json.getIntValue("mode");
		receiveMessage.setMode(mode);
		receiveMessage.setRt(json.getLongValue("rt"));
		receiveMessage.setCounter(json.getLongValue("counter"));
		JSONObject jsonMessage = json.getJSONObject("chatMessage");
		if(ChatMode.Single.getCode()==mode || ChatMode.Many.getCode()==mode){
			ChatMessage chatMessage = JSON.toJavaObject(jsonMessage, ChatMessage.class);
			receiveMessage.setChatMessage(chatMessage);
		}else{
			NoticeMessage noticeMessage = JSON.toJavaObject(jsonMessage, NoticeMessage.class);
			receiveMessage.setChatMessage(noticeMessage);
		}
		return receiveMessage;
	}
}
