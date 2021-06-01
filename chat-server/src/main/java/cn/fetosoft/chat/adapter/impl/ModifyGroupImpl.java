package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.ModifyGroupReq;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.BaseResponse;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Group;
import cn.fetosoft.chat.core.data.service.GroupService;
import cn.fetosoft.chat.core.enums.RespCode;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 修改群信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2020/12/30 16:29
 */
@Component(ChatServiceName.MODIFY_GROUP)
public class ModifyGroupImpl extends AbstractChatHandle<ModifyGroupReq, BaseResponse> {

	@Autowired
	private GroupService groupService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected ModifyGroupReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, ModifyGroupReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<BaseResponse> doHandle(ModifyGroupReq req) {
		RespResult<BaseResponse> result = RespResult.create(RespCode.HANDLE_ERROR);
		Group group = groupService.selectByGroupId(req.getGroupId());
		if(group!=null){
			if(group.getOwnerId().equals(req.getUserId())){
				group.setName(req.getName());
				if(StringUtils.isNotBlank(req.getAvatar())) {
					group.setAvatar(req.getAvatar());
				}
				group.setTopic(req.getTopic());
				group.setModifyTime(new Date());
				int flag = groupService.updateByPrimaryKeySelective(group);
				if(flag>0){
					result.setStatus(RespCode.SUCCESS);
					result.setMessage("修改成功");
				}else{
					result.setMessage("修改失败");
				}
			}else{
				result.setMessage("无权限操作群组");
			}
		}else{
			result.setMessage("群组不存在");
		}
		return result;
	}
}
