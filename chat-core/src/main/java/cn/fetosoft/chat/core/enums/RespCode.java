package cn.fetosoft.chat.core.enums;


import cn.fetosoft.chat.core.adapter.HandleStatus;

/**
 * @author guobingbing
 * @create 2020/9/4 14:53
 */
public enum RespCode implements HandleStatus {

	/**
	 * 成功
	 */
	SUCCESS("200", "操作成功"),
	/**
	 * 用于业务处理失败的码，如登录密码不正确等，前端可以直接显示错误信息
	 */
	BUSI_ERROR("3000", "业务失败"),
	HANDLE_ERROR("5000","服务异常"),
	ENCRYPT_ERROR("5001", "数据加密异常"),
	PARAMS_ERROR("5002", "参数错误"),
	TOKEN_INVALID("5004", "TOKEN无效"),
	NEED_LOGIN("5003", "请先登录");

	private String code;
	private String message;

	RespCode(String status, String message){
		this.code = status;
		this.message = message;
	}

	public RespCode setMessage(String message){
		this.message = message;
		return this;
	}

	/**
	 * 响应状态码
	 *
	 * @return
	 */
	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMessage(){
		return this.message;
	}
}
