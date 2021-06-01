package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetMyGroupReq;
import cn.fetosoft.chat.adapter.res.GetMyGroupRes;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.MyGroup;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 获取群信息
 * @author guobingbing
 * @create 2020/12/24 11:52
 */
@Component(ChatServiceName.GET_MY_GROUP)
public class GetMyGroupImpl extends AbstractChatHandle<GetMyGroupReq, GetMyGroupRes> {

	@Autowired
	private GroupMemberService groupMemberService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetMyGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetMyGroupReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetMyGroupRes> doHandle(GetMyGroupReq req) {
		RespResult<GetMyGroupRes> result = RespResult.createSuccess();
		GetMyGroupRes res = new GetMyGroupRes();
		result.setData(res);

		GroupMemberForm form = new GroupMemberForm();
		form.setRows(0);
		form.setUserId(req.getUserId());
		List<MyGroup> groups = groupMemberService.selectMyGroupList(form);
		if(!CollectionUtils.isEmpty(groups)){
			for(MyGroup g : groups){
				GetMyGroupRes.MyGroup myGroup = new GetMyGroupRes.MyGroup();
				myGroup.setGroupId(g.getGroupId());
				myGroup.setName(g.getGroupName());
				myGroup.setTopic(g.getGroupTopic());
				myGroup.setAvatar(g.getGroupAvatar());
				myGroup.setTotal(g.getGroupMembers());
				myGroup.setCreateTime(g.getGroupTime().getTime());
				myGroup.setOwnerId(g.getOwnerId());
				res.addGroup(myGroup);
			}
		}
		return result;
	}
}
