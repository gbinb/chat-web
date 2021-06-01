package cn.fetosoft.chat.core.data.entity;

import lombok.Data;
import java.util.Date;

/**
 * @author guobingbing
 * @create 2020/12/24 13:56
 */
@Data
public class MyGroup extends GroupMember {

	private String groupUid;

	private String groupName;

	private String groupTopic;

	private int groupMembers;

	private String groupAvatar;

	private Date groupTime;
}
