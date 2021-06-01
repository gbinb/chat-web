package cn.fetosoft.chat.adapter;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/12 19:32
 */
public abstract class AdapterServiceName {

	/**
	 * 获取图形验证码
	 */
	public static final String OBTAIN_CAPCHA = "obtainCaptcha";

	/**
	 * 验证token
	 */
	public static final String VERIFY_TOKEN = "verifyToken";

	/**
	 * 登录
	 */
	public static final String ADMIN_LOGIN = "adminLogin";

	/**
	 * 查询用户信息
	 */
	public static final String QUERY_USERS = "queryUsers";

	/**
	 * 查询群信息
	 */
	public static final String QUERY_GROUP = "queryGroup";

	/**
	 * 查询群成员信息
	 */
	public static final String QUERY_GROUP_MEMBER = "queryGroupMember";

	/**
	 * 查询消息文件
	 */
	public static final String QUERY_MSG_FILE = "queryMsgFile";

	/**
	 * 查询app升级版本
	 */
	public static final String QUERY_APP_UPGRADE = "queryAppUpgrade";

	/**
	 * 新增AppUpgrade
	 */
	public static final String CREATE_APP_UPGRADE = "createAppUpgrade";

	/**
	 * 更新AppUpgrade
	 */
	public static final String UPDATE_APP_UPGRADE = "updateAppUpgrade";

	/**
	 * 删除AppUpgrade
	 */
	public static final String REMOVE_APP_UPGRADE = "removeAppUpgrade";
}
