package com.deertt.module.tcommonfile.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.frame.base.factory.GenerateIDFactory;
import com.deertt.utils.helper.DvSqlHelper;

import com.deertt.module.tcommonfile.dao.ITCommonFileDao;
import com.deertt.module.tcommonfile.util.ITCommonFileConstants;
import com.deertt.module.tcommonfile.vo.TCommonFileVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class TCommonFileDao extends DvBaseDao<TCommonFileVo, String> implements ITCommonFileDao, ITCommonFileConstants {

	public int insert(TCommonFileVo vo) {
		vo.setFile_id(GenerateIDFactory.generateID());
		Object[] objs = { vo.getFile_id(), vo.getOwner_table_name(), vo.getOwner_bill_id(), vo.getOwner_column_name(), vo.getFile_name(), vo.getFile_url(), vo.getFile_type(), vo.getFile_size(), vo.getRemark(), vo.getUsable_state(), vo.getOrder_code(), vo.getCreate_user_id(), vo.getCreate_time(), vo.getCreate_ip(), vo.getModify_user_id(), vo.getModify_time(), vo.getModify_ip(), vo.getReserved_1(), vo.getReserved_2(), vo.getReserved_3(), vo.getReserved_4(), vo.getReserved_5() };
		return super.update(SQL_INSERT, objs);
	}

	public int insert(TCommonFileVo[] vos) {
		String[] ids = GenerateIDFactory.generateIDs(vos.length); 
		for(int i = 0; i < vos.length; i++) {
			vos[i].setFile_id(ids[i]);
		}
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<TCommonFileVo>() {
			public Object[] getArgs(TCommonFileVo vo) {
				return new Object[]{ vo.getFile_id(), vo.getOwner_table_name(), vo.getOwner_bill_id(), vo.getOwner_column_name(), vo.getFile_name(), vo.getFile_url(), vo.getFile_type(), vo.getFile_size(), vo.getRemark(), vo.getUsable_state(), vo.getOrder_code(), vo.getCreate_user_id(), vo.getCreate_time(), vo.getCreate_ip(), vo.getModify_user_id(), vo.getModify_time(), vo.getModify_ip(), vo.getReserved_1(), vo.getReserved_2(), vo.getReserved_3(), vo.getReserved_4(), vo.getReserved_5() };
			}
		});
	}

	public int delete(String id) {
		return super.update(SQL_DELETE_BY_ID, new Object[] { id });
	}

	public int delete(String[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		sql.append(" WHERE file_id IN (");
		sql.append(DvSqlHelper.parseToSQLStringApos(ids)); //把ids数组转换为id1,id2,id3
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

	public TCommonFileVo find(String id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<TCommonFileVo>(TCommonFileVo.class));
	}

	public TCommonFileVo findByCondition(String queryCondition) {
		List<TCommonFileVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(TCommonFileVo vo) {
		Object[] objs = { vo.getOwner_table_name(), vo.getOwner_bill_id(), vo.getOwner_column_name(), vo.getFile_name(), vo.getFile_url(), vo.getFile_type(), vo.getFile_size(), vo.getRemark(), vo.getUsable_state(), vo.getOrder_code(), vo.getModify_user_id(), vo.getModify_time(), vo.getModify_ip(), vo.getReserved_1(), vo.getReserved_2(), vo.getReserved_3(), vo.getReserved_4(), vo.getReserved_5(), vo.getFile_id() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final TCommonFileVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<TCommonFileVo>() {
			public Object[] getArgs(TCommonFileVo vo) {
				return new Object[]{ vo.getOwner_table_name(), vo.getOwner_bill_id(), vo.getOwner_column_name(), vo.getFile_name(), vo.getFile_url(), vo.getFile_type(), vo.getFile_size(), vo.getRemark(), vo.getUsable_state(), vo.getOrder_code(), vo.getModify_user_id(), vo.getModify_time(), vo.getModify_ip(), vo.getReserved_1(), vo.getReserved_2(), vo.getReserved_3(), vo.getReserved_4(), vo.getReserved_5(), vo.getFile_id() };
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

	public List<TCommonFileVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<TCommonFileVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<TCommonFileVo>(TCommonFileVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<TCommonFileVo>(TCommonFileVo.class), startIndex, size); 
		}
	}
	
}
