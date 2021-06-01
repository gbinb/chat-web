package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.QuitGroupReq;
import cn.fetosoft.chat.adapter.res.QuitGroupRes;
import cn.fetosoft.chat.config.Constant;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 退群或被群主移出群组
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/31 13:50
 */
@Component(ChatServiceName.QUIT_GROUP)
public class QuitGroupImpl extends AbstractGroupHandler<QuitGroupReq, QuitGroupRes> {

	@Autowired
	private GroupService groupService;
	@Autowired
	private RedissonTemplate redissonTemplate;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected QuitGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, QuitGroupReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<QuitGroupRes> doHandle(QuitGroupReq req) {
		RespResult<QuitGroupRes> result = RespResult.create(RespCode.BUSI_ERROR);
		QuitGroupRes res = new QuitGroupRes(req.getGroupId());
		result.setData(res);

		Group group = groupService.selectByGroupId(req.getGroupId());
		if(group!=null){
			int flag = 0;
			//判断是否为群主
			if(group.getOwnerId().equals(req.getUserId())){
				//将成员移出群
				if(StringUtils.isNotBlank(req.getMembers())){
					String[] memberArr = req.getMembers().split(",");
					List<String> memberList = Arrays.asList(memberArr);
					if(memberList.contains(group.getOwnerId())){
						result.setMessage("群主不能退群");
						return result;
					}
					flag = memberService.deleteByGroupMembers(group.getGroupId(), memberList);
					redissonTemplate.setList(Constant.REDIS_GROUP_REMOVE + group.getGroupId(), memberList, 10);

					this.initGroupProfile(res);
					res.setList(this.getMemberList(req.getUserId(), group.getGroupId()));
				}else{
					result.setMessage("群成员ID不能为空");
				}
			}else{
				//用户退群
				GroupMemberForm form = new GroupMemberForm();
				form.setGroupId(req.getGroupId());
				form.setUserId(req.getUserId());
				int total = memberService.selectCountByForm(form);
				if(total==1){
					List<String> members = new ArrayList<>(8);
					members.add(req.getUserId());
					flag = memberService.deleteByGroupMembers(group.getGroupId(), members);
				}else{
					result.setMessage("群不存或已退退");
				}
			}
			if(flag>0){
				result.setStatus(RespCode.SUCCESS).setMessage("成功退群");
			}
		}else{
			result.setMessage("群不存或已退退");
		}
		return result;
	}
}
