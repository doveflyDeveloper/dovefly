package com.deertt.module.sys.warehouse.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.sys.warehouse.dao.IWarehouseDao;
import com.deertt.module.sys.warehouse.util.IWarehouseConstants;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;
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
public class WarehouseDao extends DvBaseDao<WarehouseVo, Integer> implements IWarehouseDao, IWarehouseConstants {

	public int insert(WarehouseVo vo) {
		Object[] objs = { vo.getCity_id(), vo.getCity_name(), vo.getManager_id(), vo.getManager_name(), vo.getWarehouse_name(), vo.getWarehouse_desc(), vo.getWarehouse_area(), vo.getStart_amount(), vo.getBalance_amount(), vo.getLocked_amount(), vo.getHalfway_amount(), vo.getRemark(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(WarehouseVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<WarehouseVo>() {
			public Object[] getArgs(WarehouseVo vo) {
				return new Object[]{ vo.getCity_id(), vo.getCity_name(), vo.getManager_id(), vo.getManager_name(), vo.getWarehouse_name(), vo.getWarehouse_desc(), vo.getWarehouse_area(), vo.getStart_amount(), vo.getBalance_amount(), vo.getLocked_amount(), vo.getHalfway_amount(), vo.getRemark(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
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

	public WarehouseVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<WarehouseVo>(WarehouseVo.class));
	}

	public WarehouseVo findByCondition(String queryCondition) {
		List<WarehouseVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(WarehouseVo vo) {
		Object[] objs = { vo.getCity_id(), vo.getCity_name(), vo.getManager_id(), vo.getManager_name(), vo.getWarehouse_name(), vo.getWarehouse_desc(), vo.getWarehouse_area(), vo.getStart_amount(), vo.getBalance_amount(), vo.getLocked_amount(), vo.getHalfway_amount(), vo.getRemark(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final WarehouseVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<WarehouseVo>() {
			public Object[] getArgs(WarehouseVo vo) {
				return new Object[]{ vo.getCity_id(), vo.getCity_name(), vo.getManager_id(), vo.getManager_name(), vo.getWarehouse_name(), vo.getWarehouse_desc(), vo.getWarehouse_area(), vo.getStart_amount(), vo.getBalance_amount(), vo.getLocked_amount(), vo.getHalfway_amount(), vo.getRemark(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
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

	public List<WarehouseVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<WarehouseVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<WarehouseVo>(WarehouseVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<WarehouseVo>(WarehouseVo.class), startIndex, size); 
		}
	}

}
