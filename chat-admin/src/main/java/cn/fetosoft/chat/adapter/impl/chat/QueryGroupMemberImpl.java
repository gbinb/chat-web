package cn.fetosoft.chat.adapter.impl.chat;

import cn.fetosoft.chat.adapter.AdapterServiceName;
import cn.fetosoft.chat.adapter.req.chat.QueryGroupMemberReq;
import cn.fetosoft.chat.adapter.res.chat.QueryGroupMemberRes;
import cn.fetosoft.chat.adapter.res.chat.ShareMember;
import cn.fetosoft.chat.adapter.res.chat.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.service.GroupMemberService;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import cn.fetosoft.chat.core.utils.StringProccUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 查询群成员信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/28 11:01
 */
@Component(AdapterServiceName.QUERY_GROUP_MEMBER)
public class QueryGroupMemberImpl extends AbstractChatHandle<QueryGroupMemberReq, QueryGroupMemberRes> {

	@Autowired
	private GroupMemberService memberService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected QueryGroupMemberReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, QueryGroupMemberReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<QueryGroupMemberRes> doHandle(QueryGroupMemberReq req) {
		RespResult<QueryGroupMemberRes> result = RespResult.createSuccess();
		QueryGroupMemberRes res = new QueryGroupMemberRes();
		result.setData(res);

		List<User> memberList = memberService.getGroupMembers(req.getGroupId());
		if(!CollectionUtils.isEmpty(memberList)){
			for(User u : memberList){
				ShareMember member = new ShareMember();
				ShareUser su = new ShareUser(u.getUserId(), u.getNickName());
				su.setAvatar(u.getAvatar());
				su.setMobile(StringProccUtil.proceeMobile(u.getMobile()));
				su.setCountry(u.getCountry());
				su.setSignature(u.getSignature());
				member.setUser(su);
				res.addMemebr(member);
			}
		}
		return result;
	}
}
