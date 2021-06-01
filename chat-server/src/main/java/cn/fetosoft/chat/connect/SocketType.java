package cn.fetosoft.chat.connect;

/**
 * Socket类型
 * @author guobingbing
 * @create 2020/12/14 19:57
 */
public enum SocketType {

	/**
	 * scoket
	 */
	Socket("socket"),

	/**
	 * websocket
	 */
	WebSocket("websocket");

	private String code;

	SocketType(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}
}
