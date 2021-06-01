package cn.fetosoft.chat.core.data.form;

import cn.fetosoft.chat.core.data.base.BaseForm;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/11 15:25
 */
@Setter
@Getter
public class MsgFileForm extends BaseForm {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 文件ID
	 */
	private String fileId;

	/**
	 * md5值
	 */
	private String md5;

	/**
	 * 是否已读：0-未读 1-已读
	 */
	private String isRead;

	/**
	 * 销毁时间
	 */
	private String destroyTime;
}
