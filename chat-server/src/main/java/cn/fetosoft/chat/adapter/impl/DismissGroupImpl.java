package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.DismissGroupReq;
import cn.fetosoft.chat.config.Constant;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.redis.RedissonTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 解散群
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/14 16:05
 */
@Component(ChatServiceName.DISMISS_GROUP)
public class DismissGroupImpl extends AbstractChatHandle<DismissGroupReq, BaseResponse> {

	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupMemberService groupMemberService;
	@Autowired
	private RedissonTemplate redissonTemplate;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected DismissGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, DismissGroupReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<BaseResponse> doHandle(DismissGroupReq req) {
		RespResult<BaseResponse> result = RespResult.create(RespCode.BUSI_ERROR);
		Group group = groupService.selectByGroupId(req.getGroupId());
		if(group!=null) {
			if(group.getOwnerId().equals(req.getUserId())){
				List<String> members = groupMemberService.selectMembersUid(group.getOwnerId(), group.getGroupId());
				int flag = groupService.dismissGroup(group);
				if(flag>0){
					redissonTemplate.setList(Constant.REDIS_GROUP_DISMISS + group.getGroupId(), members, 10);
					result.setStatus(RespCode.SUCCESS);
					result.setMessage("解散成功");
				}
			}else{
				result.setMessage("非群主无权限解除群");
			}
		}else{
			result.setMessage("群不存在或已解散");
		}
		return result;
	}
}
