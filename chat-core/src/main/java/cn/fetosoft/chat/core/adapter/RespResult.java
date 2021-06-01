package cn.fetosoft.chat.core.adapter;

import cn.fetosoft.chat.core.enums.RespCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/11/27 17:29
 */
public class RespResult<T> {

	private String code;
	private String message;
	private int decodeType;
	private String sign;
	private T data;

	private RespResult(HandleStatus handleStatus){
		this.code = handleStatus.getCode();
		this.message = handleStatus.getMessage();
	}

	/**
	 * 创建响应信息
	 * @param handleStatus
	 * @param <T>
	 * @return
	 */
	public static <T> RespResult<T> create(HandleStatus handleStatus){
		return new RespResult<>(handleStatus);
	}

	/**
	 * 处理成功
	 * @param <T>
	 * @return
	 */
	public static <T> RespResult<T> createSuccess(){
		return new RespResult<>(RespCode.SUCCESS);
	}

	/**
	 * 处理异常
	 * @param <T>
	 * @return
	 */
	public static <T> RespResult<T> createError(){
		return new RespResult<>(RespCode.HANDLE_ERROR);
	}

	public RespResult setStatus(HandleStatus handleStatus){
		this.code = handleStatus.getCode();
		this.message = handleStatus.getMessage();
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public RespResult<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public int getDecodeType() {
		return decodeType;
	}

	public void setDecodeType(int decodeType) {
		this.decodeType = decodeType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 转换成JSON字符串
	 * @return
	 */
	@Override
	public String toString(){
		return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
	}
}
