package cn.fetosoft.chat.core.data.mapper;

import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.MsgFile;
import cn.fetosoft.chat.core.data.form.MsgFileForm;

/**
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/11 15:34
 */
public interface MsgFileMapper extends BaseMapper<Long, MsgFile, MsgFileForm> {

	/**
	 * 删除 by fileId
	 * @param fileId 文件ID
	 * @return
	 */
	int deleteByFileId(String fileId);

	/**
	 * 更新已读状态
	 * @param msgFile
	 * @return
	 */
	int updateReadByFileId(MsgFile msgFile);
}
