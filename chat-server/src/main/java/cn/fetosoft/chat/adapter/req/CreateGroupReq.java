package cn.fetosoft.chat.adapter.req;

import cn.fetosoft.chat.core.adapter.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/23 19:17
 */
@Setter
@Getter
public class CreateGroupReq extends BaseRequest {

	/**
	 * 群成员信息
	 */
	private String members;
}
