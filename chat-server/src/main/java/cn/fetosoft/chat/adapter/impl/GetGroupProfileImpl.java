package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.GetGroupProfileReq;
import cn.fetosoft.chat.adapter.res.GetGroupProfileRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.GroupMemberForm;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取群信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/9 11:14
 */
@Component(ChatServiceName.GET_GROUP_PROFILE)
public class GetGroupProfileImpl extends AbstractGroupHandler<GetGroupProfileReq, GetGroupProfileRes> {

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected GetGroupProfileReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, GetGroupProfileReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<GetGroupProfileRes> doHandle(GetGroupProfileReq req) {
		RespResult<GetGroupProfileRes> result = RespResult.create(RespCode.BUSI_ERROR);
		GetGroupProfileRes res = new GetGroupProfileRes();
		result.setData(res);

		GroupMemberForm memberForm = new GroupMemberForm();
		memberForm.setGroupId(req.getGroupId());
		memberForm.setUserId(req.getUserId());
		int total = memberService.selectCountByForm(memberForm);
		if(total<=0){
			result.setMessage("不属于此群的成员");
			return result;
		}

		Group group = groupService.selectByGroupId(req.getGroupId());
		if(group!=null){
			res.setGroupId(group.getGroupId());
			res.setOwnerId(group.getOwnerId());
			res.setName(group.getName());
			res.setAvatar(group.getAvatar());
			res.setTopic(group.getTopic());
			res.setTotal(group.getMembers());
			if(req.getType()==2){
				User user = userService.selectByUserId(group.getOwnerId());
				if(user!=null) {
					ShareUser owner = new ShareUser(user.getUserId(), user.getNickName());
					owner.setAvatar(user.getAvatar());
					owner.setCountry(user.getCountry());
					res.setOwner(owner);
				}
				res.setList(this.getMemberList(req.getUserId(), group.getGroupId()));
			}
			result.setStatus(RespCode.SUCCESS);
		}else{
			result.setMessage("群信息不存在");
		}
		return result;
	}
}
