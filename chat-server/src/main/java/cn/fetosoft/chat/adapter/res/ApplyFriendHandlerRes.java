package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guobingbing
 * @create 2020/12/18 17:04
 */
@Setter
@Getter
public class ApplyFriendHandlerRes extends BaseResponse {

	private String userId;

	private String nickName;

	private String avatar;

	private String country;

	private String mobile;
}
