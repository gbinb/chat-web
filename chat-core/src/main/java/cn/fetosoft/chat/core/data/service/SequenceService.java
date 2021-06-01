package cn.fetosoft.chat.core.data.service;

import cn.fetosoft.chat.core.data.base.BaseDataService;
import cn.fetosoft.chat.core.data.entity.Sequence;
import cn.fetosoft.chat.core.data.form.SequenceForm;

/**
 * @author guobingbing
 * @create 2020/12/16 14:53
 */
public interface SequenceService extends BaseDataService<Long, Sequence, SequenceForm> {

	/**
	 * 生成自增序列
	 * @param category
	 * @return
	 */
	long genSequenceId(String category) throws Exception;

	/**
	 * 生成用户ID
	 * @return
	 */
	String genUserId();

	/**
	 * 生成群ID
	 * @return
	 */
	String genGroupId();

	/**
	 * 生成管理员ID
	 * @return
	 */
	String getAdminId();
}
