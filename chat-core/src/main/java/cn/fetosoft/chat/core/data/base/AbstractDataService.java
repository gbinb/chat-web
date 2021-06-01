package cn.fetosoft.chat.core.data.base;


import java.util.List;

/**
 * @author guobingbing
 * @create 2020-02-27 11:08
 */
public abstract class AbstractDataService<K,T,F extends BaseForm> implements BaseDataService<K,T,F> {

	/**
	 * 返回BaseDAO接口实现
	 * @author guobingbing
	 * @date 2016/12/6
	 * @return
	 */
	protected abstract BaseMapper<K, T, F> getBaseMapper();

	@Override
	public T selectByPrimaryKey(K k){
		return getBaseMapper().selectByPrimaryKey(k);
	}

	@Override
	public List<T> selectListByForm(F form){
		return this.getBaseMapper().selectListByForm(form);
	}

	@Override
	public int selectCountByForm(F form){
		return this.getBaseMapper().selectCountByForm(form);
	}

	@Override
	public int deleteByPrimaryKey(K k){
		return getBaseMapper().deleteByPrimaryKey(k);
	}

	@Override
	public int insert(T t){
		return getBaseMapper().insert(t);
	}

	@Override
	public int updateByPrimaryKeySelective(T t){
		return getBaseMapper().updateByPrimaryKeySelective(t);
	}

}
