package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.Sequence;
import cn.fetosoft.chat.core.data.form.SequenceForm;
import cn.fetosoft.chat.core.data.mapper.SequenceMapper;
import cn.fetosoft.chat.core.data.service.SequenceService;
import cn.fetosoft.chat.core.utils.RandomUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 序列号生成器
 * @author guobingbing
 * @create 2020/12/16 14:55
 */
@Service
public class SequenceServiceImpl extends AbstractDataService<Long, Sequence, SequenceForm> implements SequenceService {

	private static final Logger logger = LoggerFactory.getLogger(SequenceServiceImpl.class);
	@Autowired
	private SequenceMapper sequenceMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Long, Sequence, SequenceForm> getBaseMapper() {
		return sequenceMapper;
	}

	/**
	 * 生成自增序列
	 *
	 * @param category
	 * @return
	 */
	@Override
	public long genSequenceId(String category) throws Exception {
		Sequence sequence = new Sequence();
		sequence.setCategory(category);
		sequence.setCreateTime(new Date());
		int flag = sequenceMapper.insert(sequence);
		if(flag>0) {
			return sequence.getId();
		}else{
			throw new Exception("Gen sequence error");
		}
	}

	/**
	 * 生成指定长度随机数
	 * @param len
	 * @return
	 */
	private String randomChar(int len){
		String str = "ABCDEFqrstuvGHIJKLijklmnopMNOPQRSabcdefghTUVWXYZwxyz1234567890";
		char[] charArr = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for(int i=0; i<len; i++){
			sb.append(charArr[random.nextInt(charArr.length)]);
		}
		return sb.toString();
	}

	/**
	 * 生成用户ID
	 *
	 * @return
	 */
	@Override
	public String genUserId() {
		String userId = null;
		try{
			long id = this.genSequenceId("userId");
			userId = "U" + DateFormatUtils.format(new Date(), "yyMMdd") + this.randomChar(5).toUpperCase() + RandomUtil.formatNumberByZero(6, id);
		}catch(Exception e){
			logger.error("genUserId", e);
		}
		return userId;
	}

	/**
	 * 生成群ID
	 *
	 * @return
	 */
	@Override
	public String genGroupId() {
		String groupId = "";
		try{
			long id = this.genSequenceId("groupId");
			groupId = "G" + DateFormatUtils.format(new Date(), "yyMMdd") + this.randomChar(5).toUpperCase() + RandomUtil.formatNumberByZero(6, id);
		}catch(Exception e){
			logger.error("genGroupId", e);
		}
		return groupId;
	}

	/**
	 * 生成管理员ID
	 *
	 * @return
	 */
	@Override
	public String getAdminId() {
		String adminId = null;
		try{
			long id = this.genSequenceId("adminId");
			adminId = "A" + DateFormatUtils.format(new Date(), "yyMMdd") + this.randomChar(5).toUpperCase() + RandomUtil.formatNumberByZero(6, id);
		}catch(Exception e){
			logger.error("genUserId", e);
		}
		return adminId;
	}
}
