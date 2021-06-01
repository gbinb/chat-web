package cn.fetosoft.chat.adapter.impl;

import cn.fetosoft.chat.adapter.ChatServiceName;
import cn.fetosoft.chat.adapter.req.SearchUserReq;
import cn.fetosoft.chat.adapter.res.SearchUserRes;
import cn.fetosoft.chat.adapter.res.ShareUser;
import cn.fetosoft.chat.core.adapter.AbstractChatHandle;
import cn.fetosoft.chat.core.adapter.RespResult;
import cn.fetosoft.chat.core.data.entity.Friend;
import cn.fetosoft.chat.core.data.entity.User;
import cn.fetosoft.chat.core.data.form.UserForm;
import cn.fetosoft.chat.core.data.service.FriendService;
import cn.fetosoft.chat.core.data.service.UserService;
import cn.fetosoft.chat.core.enums.Relationship;
import cn.fetosoft.chat.core.exceptions.ConversionException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 搜索好友
 * @author guobingbing
 * @create 2020/12/16 19:11
 */
@Component(ChatServiceName.SEARCH_USER)
public class SearchUserImpl extends AbstractChatHandle<SearchUserReq, SearchUserRes> {

	@Autowired
	private UserService userService;
	@Autowired
	private FriendService friendService;

	/**
	 * 解析参数
	 *
	 * @param jsonObject
	 * @return
	 */
	@Override
	protected SearchUserReq convertParam(JSONObject jsonObject) throws ConversionException {
		return JSON.toJavaObject(jsonObject, SearchUserReq.class);
	}

	/**
	 * 详细业务处理
	 *
	 * @param req
	 * @return
	 */
	@Override
	protected RespResult<SearchUserRes> doHandle(SearchUserReq req) {
		RespResult<SearchUserRes> result = RespResult.createSuccess();
		SearchUserRes res = new SearchUserRes();
		result.setData(res);

		UserForm form = new UserForm();
		form.setRows(0);
		form.setMobile(req.getMobile());
		List<cn.fetosoft.chat.core.data.entity.User> list = userService.selectListByForm(form);
		if(!CollectionUtils.isEmpty(list)){
			for(User user : list){
				ShareUser addUser = new ShareUser(user.getUserId(), user.getNickName());
				addUser.setMobile(user.getMobile());
				addUser.setAvatar(user.getAvatar());
				addUser.setCountry(user.getCountry());
				if(user.getUserId().equals(req.getUserId())){
					addUser.setRelationship(Relationship.Myself.getCode());
				}else{
					Friend friend = friendService.getFriend(req.getUserId(), user.getUserId());
					if(friend!=null){
						addUser.setRelationship(Relationship.Friend.getCode());
					}else{
						addUser.setRelationship(Relationship.Stranger.getCode());
					}
				}
				res.addUser(addUser);
			}
		}
		return result;
	}
}
