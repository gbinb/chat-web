package cn.fetosoft.chat.core.data.service;

import lombok.Getter;
import lombok.Setter;

/**
 * 处理结果
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/4 17:41
 */
@Setter
@Getter
public final class Result<T> {

	public static final int SUCCESS = 1;
	public static final int ERROR = 500;

	/**
	 * 是否成功
	 */
	private int status;

	/**
	 * 错误消息
	 */
	private String message;

	/**
	 * 其它数据
	 */
	private T data;

	private Result(int status, String message){
		this.status = status;
		this.message = message;
	}

	public static Result createSuccess(){
		return new Result(SUCCESS, null);
	}

	public static Result createError(String message){
		return new Result(ERROR, message);
	}

	public Result setStatus(int status){
		this.status = status;
		return this;
	}

	public Result setMessage(String message){
		this.message = message;
		return this;
	}

	/**
	 * 判断是否成功
	 * @return
	 */
	public boolean isSuccess(){
		return this.status==SUCCESS;
	}
}
