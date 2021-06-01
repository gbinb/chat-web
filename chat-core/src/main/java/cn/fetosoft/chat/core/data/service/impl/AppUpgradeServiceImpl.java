package cn.fetosoft.chat.core.data.service.impl;

import cn.fetosoft.chat.core.data.base.AbstractDataService;
import cn.fetosoft.chat.core.data.base.BaseMapper;
import cn.fetosoft.chat.core.data.entity.AppUpgrade;
import cn.fetosoft.chat.core.data.form.AppUpgradeForm;
import cn.fetosoft.chat.core.data.mapper.AppUpgradeMapper;
import cn.fetosoft.chat.core.data.service.AppUpgradeService;
import cn.fetosoft.chat.core.data.vo.AppUpgradeVO;
import cn.fetosoft.chat.core.enums.DateFormatEnum;
import cn.fetosoft.chat.core.enums.YesOrNo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * APP升级信息
 * @author guobingbing
 * @version 1.0
 * @wechat t_gbinb
 * @create 2021/2/24 10:12
 */
@Service
public class AppUpgradeServiceImpl extends AbstractDataService<Integer, AppUpgrade, AppUpgradeForm>
		implements AppUpgradeService {

	@Autowired
	private AppUpgradeMapper appUpgradeMapper;

	/**
	 * 返回BaseDAO接口实现
	 *
	 * @return
	 * @author guobingbing
	 * @date 2016/12/6
	 */
	@Override
	protected BaseMapper<Integer, AppUpgrade, AppUpgradeForm> getBaseMapper() {
		return appUpgradeMapper;
	}

	/**
	 * 查找升级信息
	 *
	 * @param version
	 * @param versionType
	 * @return
	 */
	@Override
	public AppUpgrade getAppUpgrade(String version, String versionType) {
		AppUpgradeForm form = new AppUpgradeForm();
		form.setVersion(version);
		form.setVersionType(versionType);
		return appUpgradeMapper.selectByVersionAndType(form);
	}

	/**
	 * 查询列表
	 *
	 * @param form
	 * @return
	 */
	@Override
	public List<AppUpgradeVO> selectListVo(AppUpgradeForm form) {
		List<AppUpgradeVO> voList = new ArrayList<>();
		List<AppUpgrade> list = appUpgradeMapper.selectListByForm(form);
		if(!CollectionUtils.isEmpty(list)){
			for(AppUpgrade u : list){
				AppUpgradeVO vo = new AppUpgradeVO();
				BeanUtils.copyProperties(u, vo);
				vo.setForceUpgradeText(YesOrNo.Yes.getCode().equals(u.getForceUpgrade())?"是":"否");
				vo.setIsSkipText(YesOrNo.Yes.getCode().equals(u.getIsSkip())?"是":"否");
				if(u.getCreateTime()!=null){
					vo.setCreateTime(DateFormatEnum.YMD_HMS.dateToString(u.getCreateTime()));
				}
				voList.add(vo);
			}
		}
		return voList;
	}
}
