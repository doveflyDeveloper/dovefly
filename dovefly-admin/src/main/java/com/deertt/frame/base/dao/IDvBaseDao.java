package com.deertt.frame.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.deertt.frame.base.dao.impl.CircleVoArray;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IDvBaseDao<T, ID extends Serializable> {
	
	/**
	 * 插入单条记录
	 * 
	 * @param vo 用于添加的VO对象
	 * @return 成功插入的记录数
	 */
	public int insert(T vo);

	/**
	 * 批量插入多条记录
	 * 
	 * @param vos 添加的VO对象数组
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
	 * 批量删除多条记录
	 * 
	 * @param id 用于删除的记录的id数组
	 * @return 成功删除的记录数
	 */
	public int delete(ID[] ids);
	
	/**
	 * 根据指定条件删除多条记录
	 * 
	 * @param id 用于删除的记录的查询条件
	 * @return 成功删除的记录数
	 */
	public int deleteByCondition(String queryCondition);
	
	/**
	 * 根据Id进行查找记录
	 * 
	 * @param id 用于查找的id
	 * @return 查询到的VO对象
	 */
	public T find(ID id);
	
	/**
	 * 根据指定条件查找记录（适用于返回结果最多一条的情况，如果返回多条，则只返回第一条）
	 * 
	 * @param id 用于查找记录的查询条件
	 * @return 查询到的VO对象
	 */
	public T findByCondition(String queryCondition);
	
	/**
	 * 更新单条记录
	 * 
	 * @param vo 用于更新的VO对象
	 * @return 成功更新的记录数
	 */
	public int update(T vo);

	/**
	 * 批量更新多条记录
	 * 
	 * @param vos 添加的VO对象数组
	 * @return 成功更新的记录数
	 */
	public int update(final T[] vos);

	/**
	 * 查询总记录数，带查询条件
	 * 
	 * @param queryCondition 查询条件
	 * @return 总记录数
	 */
	public int getRecordCount(String queryCondition);
	
	/**
	 * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符
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
	 * SQL执行
	 * @param sql 要执行的SQL
	 * @return 成功更新的记录数
	 */
	public int update(String sql);

	/**
	 * SQL执行
	 * @param sql 要执行的SQL
	 * @param args 参数
	 * @return 成功更新的记录数
	 */
	public int update(String sql, Object[] args);
	
	/**
	 * SQL批量执行
	 * @param sql
	 * @param objs
	 * @param cva
	 * @return 成功更新的记录数
	 */
	public int batchUpdate(String sql, T[] objs, CircleVoArray<T> cva);
	
	/**
	 * 查询统计类数值（适用于结果集只有一行的情况下）
	 * @param sql
	 * @return
	 */
	public int queryForInt(String sql);
	
	/**
	 * 查询单个对象（适用于结果集只有一行的情况下）
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return
	 */
	public T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper);

	/**
	 * 查询单行结果集并转化为Map对象
	 * @param sql
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql);
	
	/**
	 * 功能：查询多行结果集并封装为Map列表
	 * @param sql 查询SQL
	 * @return
	 */
	public List<Map<String, Object>> queryForMaps(String sql);
	
	/**
	 * 功能：查询多行结果集并封装为Map列表
	 * @param sql 查询SQL
	 * @return
	 */
	public List<Map<String, Object>> queryForMaps(String sql, Object[] args);

	/**
	 * 功能：查询全部
	 * @param sql 查询SQL
	 * @param rowMapper 对象封装类
	 * @return
	 */
	public List<T> query(String sql, RowMapper<T> rowMapper);

	/**
	 * 功能：查询全部
	 * @param sql 查询SQL
	 * @param args SQL占位符的值
	 * @param rowMapper 对象封装类
	 * @return
	 */
	public List<T> query(String sql, Object[] args, RowMapper<T> rowMapper);

	/**
	 * 功能：分页查询
	 * 
	 * @param sql 查询SQL
	 * @param rowMapper 对象封装类
	 * @param startIndex 开始位置(第一条是1，第二条是2...)
	 * @param size
	 * @return
	 */
	public List<T> query(String sql, RowMapper<T> rowMapper, int startIndex, int size);
	
	/**
	 * 获得序列的下一个值
	 * @param seqName 序列名称
	 * @return
	 */
//	public int getNextVal(String seqName);
}