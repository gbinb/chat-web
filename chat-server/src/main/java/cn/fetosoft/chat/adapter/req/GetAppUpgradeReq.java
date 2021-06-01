package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 16:54
 */
@Setter
@Getter
public class GetAppUpgradeReq extends BaseRequest {

	/**
	 * 版本号
	 */
	@NotBlank(message = "版本不能为空")
	private String version;

	/**
	 * 版本类型
	 */
	@NotBlank(message = "版本类型不能为空")
	private String versionType;
}
