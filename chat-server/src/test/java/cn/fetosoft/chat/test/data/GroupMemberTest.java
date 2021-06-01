package cn.fetosoft.chat.test.data;

import cn.fetosoft.chat.core.data.service.GroupMemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 15:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "cn.fetosoft.chat.core.data.mapper")
public class GroupMemberTest {

	@Autowired
	private GroupMemberService groupMemberService;

	@Test
	public void removeMember(){
		List<String> members = new ArrayList<>();
		members.add("U201216000002");
		members.add("U201226S51OC000005");
		members.add("U201228EO1GO000006");
		int flag = groupMemberService.deleteByGroupMembers("G210114XVJCC000085", members);
		System.out.println(flag);
	}
}
