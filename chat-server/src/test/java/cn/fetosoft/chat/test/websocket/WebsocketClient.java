package cn.fetosoft.chat.test.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/30 17:58
 */
public class WebsocketClient extends WebSocketClient {

	public WebsocketClient(String url){
		super(URI.create(url));
	}

	@Override
	public void onOpen(ServerHandshake serverHandshake) {

	}

	@Override
	public void onMessage(String s) {
		System.out.println(s);
	}

	@Override
	public void onClose(int i, String s, boolean b) {
		System.out.println(s);
	}

	@Override
	public void onError(Exception e) {
		e.printStackTrace();
	}

	public static void main(String[] args) {
		try{
			WebsocketClient client = new WebsocketClient("ws://172.18.10.211:5002/chat/asdfasdfasf");
			client.connect();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
