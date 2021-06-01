package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.JoinGroupReq;
import cn.fetosoft.chat.adapter.res.JoinGroupRes;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 加入群聊
 * @author guobingbing
 * @create 2020/12/26 17:22
 */
@Component(ChatServiceName.JOIN_GROUP)
public class JoinGroupImpl extends AbstractGroupHandler<JoinGroupReq, JoinGroupRes> {

	@Override
	protected JoinGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, JoinGroupReq.class);
	}

	/**
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<JoinGroupRes> doHandle(JoinGroupReq req) {
		RespResult<JoinGroupRes> result = RespResult.create(RespCode.BUSI_ERROR);
		JoinGroupRes res = new JoinGroupRes(req.getGroupId());
		result.setData(res);

		Group group = groupService.selectByGroupId(req.getGroupId());
		if(group==null){
			result.setMessage("未找到群");
			return result;
		}
		if(StringUtils.isBlank(req.getMembers())){
			result.setMessage("请选择群成员");
			return result;
		}
		String[] newMembers = req.getMembers().split(",");
		if((group.getMembers()+newMembers.length)>100){
			result.setMessage("群成员限制100人以内");
			return result;
		}
		int flag = groupService.joinGroup(group, Arrays.asList(newMembers));
		if(flag>0){
			this.initGroupProfile(res);
			res.setList(this.getMemberList(req.getUserId(), group.getGroupId()));
			result.setStatus(RespCode.SUCCESS);
			result.setMessage("您已经是群成员了");
		}else{
			result.setMessage("加群失败");
		}
		return result;
	}
}
