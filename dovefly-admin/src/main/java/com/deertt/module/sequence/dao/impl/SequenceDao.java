package com.deertt.module.sequence.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.sequence.dao.ISequenceDao;
import com.deertt.module.sequence.util.ISequenceConstants;
import com.deertt.module.sequence.vo.SequenceVo;
import com.deertt.utils.helper.DvSqlHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class SequenceDao extends DvBaseDao<SequenceVo, Integer> implements ISequenceDao, ISequenceConstants {
	
	public int insert(SequenceVo vo) {
		Object[] objs = { vo.getSequence_name(), vo.getSequence_value(), vo.getSequence_step(), vo.getVersion() };
		vo.setId(super.insertForLongKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(SequenceVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<SequenceVo>() {
			public Object[] getArgs(SequenceVo vo) {
				return new Object[]{ vo.getSequence_name(), vo.getSequence_value(), vo.getSequence_step(), vo.getVersion() };
			}
		});
	}

	public int delete(Integer id) {
		return super.update(SQL_DELETE_BY_ID, new Object[] { id });
	}

	public int delete(Integer[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		sql.append(" WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}

	public int deleteByCondition(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		return super.update(sql.toString());
	}

	public SequenceVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<SequenceVo>(SequenceVo.class));
	}

	public SequenceVo findByCondition(String queryCondition) {
		List<SequenceVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(SequenceVo vo) {
		Object[] objs = { vo.getSequence_name(), vo.getSequence_value(), vo.getSequence_step(), vo.getVersion(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final SequenceVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<SequenceVo>() {
			public Object[] getArgs(SequenceVo vo) {
				return new Object[]{ vo.getSequence_name(), vo.getSequence_value(), vo.getSequence_step(), vo.getVersion(), vo.getId() };
			}
		});
	}

	public int getRecordCount(String queryCondition) {
		StringBuilder sql = new StringBuilder(SQL_COUNT + DEFAULT_SQL_WHERE_USABLE);
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		return super.queryForInt(sql.toString());
	}

	public List<SequenceVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
		StringBuilder sql = new StringBuilder();
		if(selectAllClumn) {
			sql.append(SQL_QUERY_ALL_EXPORT + DEFAULT_SQL_WHERE_USABLE);
		} else {
			sql.append(SQL_QUERY_ALL + DEFAULT_SQL_WHERE_USABLE);
		}
		if (queryCondition != null && queryCondition.trim().length() > 0) {
			sql.append(DEFAULT_SQL_CONTACT_KEYWORD); //where后加上查询条件
			sql.append(queryCondition);
		}
		if(orderStr != null && orderStr.trim().length() > 0) {
			sql.append(ORDER_BY_SYMBOL);
			sql.append(orderStr);
		} else {
			sql.append(DEFAULT_ORDER_BY_CODE);
		}
		return this.queryByCondition(sql.toString(), startIndex, size);
	}

	public List<SequenceVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<SequenceVo>(SequenceVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<SequenceVo>(SequenceVo.class), startIndex, size); 
		}
	}
}
