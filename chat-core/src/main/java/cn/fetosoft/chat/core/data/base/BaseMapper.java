package cn.fetosoft.chat.core.data.base;

import java.util.List;

/**
 * @author guobingbing
 * @create 2020-02-27 10:27
 */
public interface BaseMapper<K, T, F extends BaseForm> {

	/**
	 * 通过主键查询Entity
	 * @author guobingbing
	 * @date 2016/12/5
	 * @param k
	 * @return
	 */
	T selectByPrimaryKey(K k);

	/**
	 * 查询列表
	 * @param form
	 * @return
	 */
	List<T> selectListByForm(F form);

	/**
	 * 查询记录数
	 * @param form
	 * @return
	 */
	int selectCountByForm(F form);

	/**
	 * 根据主键删除记录
	 * @author guobingbing
	 * @date 2016/12/5
	 * @param k
	 * @return
	 */
	int deleteByPrimaryKey(K k);

	/**
	 * 新增记录
	 * @author guobingbing
	 * @date 2016/12/5
	 * @param t
	 * @return
	 */
	int insert(T t);

	/**
	 * 更新记录
	 * @author guobingbing
	 * @date 2016/12/5
	 * @param t
	 * @return
	 */
	int updateByPrimaryKeySelective(T t);
}
