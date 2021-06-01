package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.CreateGroupReq;
import cn.fetosoft.chat.adapter.res.CreateGroupRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.config.SystemConfig;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建聊天群
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/23 18:59
 */
@Component(ChatServiceName.CREATE_GROUP)
public class CreateGroupImpl extends AbstractGroupHandler<CreateGroupReq, CreateGroupRes> {

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected CreateGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		CreateGroupReq req = JSON.toJavaObject(jsonObject, CreateGroupReq.class);
		if(StringUtils.isBlank(req.getMembers())){
			throw new ConversionException(RespCode.PARAMS_ERROR.setMessage("群成员不能为空"));
		}
		return req;
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<CreateGroupRes> doHandle(CreateGroupReq req) {
		RespResult<CreateGroupRes> result = RespResult.create(RespCode.BUSI_ERROR);
		String[] memberArr = req.getMembers().split(",");
		if(memberArr.length<2){
			result.setMessage("群成员不能少于3位");
			return result;
		}
		User user = userService.selectByUserId(req.getUserId());
		if(user!=null){
			Group group = new Group();
			group.setOwnerId(user.getUserId());
			group.setAvatar(systemConfig.getDefaultGroupAvatar());
			List<String> memberList = new ArrayList<>();
			memberList.addAll(Arrays.asList(memberArr));
			if(!req.getMembers().contains(req.getUserId())){
				memberList.add(req.getUserId());
			}
			int flag = groupService.createGroup(group, memberList);
			if(flag>0){
				CreateGroupRes res = new CreateGroupRes();
				res.setGroupId(group.getGroupId());
				res.setOwnerId(group.getOwnerId());
				res.setName(group.getName());
				res.setAvatar(group.getAvatar());
				res.setTotal(group.getMembers());
				User owner = userService.selectByUserId(group.getOwnerId());
				if(owner!=null) {
					ShareUser shareUser = new ShareUser(owner.getUserId(), owner.getNickName());
					shareUser.setAvatar(owner.getAvatar());
					shareUser.setCountry(owner.getCountry());
					res.setOwner(shareUser);
				}
				res.setList(this.getMemberList(req.getUserId(), group.getGroupId()));

				result.setData(res);
				result.setStatus(RespCode.SUCCESS.setMessage("创建成功"));
			}else{
				result.setMessage("创建失败");
			}
		}else{
			result.setMessage("创建者信息异常或不存在");
		}
		return result;
	}
}
