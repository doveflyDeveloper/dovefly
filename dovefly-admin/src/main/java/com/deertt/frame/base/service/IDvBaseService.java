package com.deertt.frame.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IDvBaseService<DAO, T, ID extends Serializable> {

	/**
	 * 获取数据访问接口
	 * 
	 * @return
	 */
	public DAO getDao();

	/**
	 * 设置数据访问接口
	 * 
	 * @param dao
	 */
	public void setDao(DAO dao);

	/**
	 * 插入单条记录
	 * 
	 * @param vo 用于添加的VO对象
	 * @return 成功插入的记录数
	 */
	public int insert(T vo);
	
	/**
	 * 插入多条记录
	 *
	 * @param vos 用于添加的VO对象数组
	 * @return 成功插入的记录数
	 */
	public int insert(T[] vos);

	/**
	 * 删除单条记录
	 * 
	 * @param id 用于删除的记录的id
	 * @return 成功删除的记录数
	 */
	public int delete(ID id);

	/**
	 * 删除多条记录
	 * 
	 * @param ids 用于删除的记录的ids
	 * @return 成功删除的记录数
	 */
	public int delete(ID[] ids);

	/**
	 * 根据Id进行查询
	 * 
	 * @param id 用于查找的id
	 * @return 查询到的VO对象
	 */
	public T find(ID id);

	/**
	 * 更新单条记录
	 * 
	 * @param vo 用于更新的VO对象
	 * @return 成功更新的记录数
	 */
	public int update(T vo);

	/**
	 * 批量更新修改多条记录
	 * 
	 * @param vos 更新的VO对象数组
	 * @return 成功更新的记录数
	 */
	public int update(T[] vos);
	
	/**
	 * 保存单条记录（ID有值则更新，否则插入）
	 * 
	 * @param vo 用于插入或更新的VO对象
	 * @return 成功插入或更新的记录数
	 */
	public int save(T vo);
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{成功插入的记录数, 成功更新的记录数}	
	 */
	public int[] insertUpdateBatch(T[] vos);

	/**
	 * 查询总记录数，带查询条件
	 * 
	 * @param queryCondition 查询条件
	 * @return 总记录数
	 */
	public int getRecordCount(String queryCondition);
	
	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部
	 *
	 * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr 排序字段
	 * @return 查询到的VO列表
	 */
	public List<T> queryByCondition(String queryCondition, String orderStr);
	
	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符
	 *
	 * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr 排序字符
	 * @param startIndex 开始位置(第一条是1，第二条是2...)
	 * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
	 * @return 查询到的VO列表
	 */
	public List<T> queryByCondition(String queryCondition, String orderStr, int startIndex, int size);

	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
	 *
	 * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
	 * @param orderStr 排序字符
	 * @param startIndex 开始位置(第一条是1，第二条是2...)
	 * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
	 * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
	 * @return 查询到的VO列表
	 */
	public List<T> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn);
	
	/**
	 * 执行SQL，对于只更新一两个状态字段信息的SQL，可直接执行SQL语句
	 * @param sql
	 * @return 成功更新的记录数
	 */
	public int update(String sql);
	
	public int update(String sql, Object[] args);
	
	public int queryForInt(String sql);
	
	public Map<String, Object> queryForMap(String sql);
	
	public List<Map<String, Object>> queryForMaps(String sql);
	
	/**
	 * 生成业务单号（前缀+yyMMddHHmmss+4位随机数）共17位
	 * @param prefix 业务单号前缀
	 * @return
	 */
	public String generateBillCode(String prefix);
}