package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.MsgFile;
import cn.fetosoft.chat.core.data.form.MsgFileForm;
import cn.fetosoft.chat.core.data.mapper.MsgFileMapper;
import cn.fetosoft.chat.core.data.service.MsgFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 消息文件服务
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/1/11 19:36
 */
@Service
public class MsgFileServiceImpl extends AbstractDataService<Long, MsgFile, MsgFileForm> implements MsgFileService {

	@Autowired
	private MsgFileMapper msgFileMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, MsgFile, MsgFileForm> getBaseMapper() {
		return msgFileMapper;
	}

	/**
	 * 删除 by fileId
	 *
	 * @param fileId 文件ID
	 * @return
	 */
	@Override
	public int deleteByFileId(String fileId) {
		return msgFileMapper.deleteByFileId(fileId);
	}

	/**
	 * 更新已读状态
	 *
	 * @param fileId
	 * @return
	 */
	@Override
	public int updateReadByFileId(String fileId) {
		MsgFile msgFile = new MsgFile();
		msgFile.setFileId(fileId);
		msgFile.setIsRead("1");
		msgFile.setReadTime(new Date());
		return msgFileMapper.updateReadByFileId(msgFile);
	}
}
