package cn.fetosoft.chat.adapter.res;

import cn.fetosoft.chat.core.adapter.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guobingbing
 * @create 2020/12/24 11:53
 */
@Setter
@Getter
public class GetMyGroupRes extends BaseResponse {

	private List<MyGroup> list = new ArrayList<>(16);

	public void addGroup(MyGroup group){
		this.list.add(group);
	}

	@Setter
	@Getter
	public static class MyGroup{

		/**
		 * 群ID
		 */
		private String groupId;

		/**
		 * 群主ID
		 */
		private String ownerId;

		/**
		 * 群名称
		 */
		private String name;

		/**
		 * 公告
		 */
		private String topic;

		/**
		 * 群头像
		 */
		private String avatar;

		/**
		 * 成员人数
		 */
		private Integer total;

		/**
		 * 创建时间
		 */
		private long createTime;
	}
}
