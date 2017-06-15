package com.deertt.module.sys.region.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.sys.region.dao.IRegionDao;
import com.deertt.module.sys.region.util.IRegionConstants;
import com.deertt.module.sys.region.vo.RegionVo;
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
public class RegionDao extends DvBaseDao<RegionVo, Integer> implements IRegionDao, IRegionConstants {

	public int insert(RegionVo vo) {
		Object[] objs = { vo.getCity_id(), vo.getCity_name(), vo.getWarehouse_id(), vo.getWarehouse_name(), vo.getManager_id(), vo.getManager_name(), vo.getCode(), vo.getName(), vo.getFull_name(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getParent_full_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getBaidu_uid(), vo.getBaidu_title(), vo.getBaidu_address(), vo.getBaidu_longtitude(), vo.getBaidu_latitude(), vo.getSearch_times(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(RegionVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<RegionVo>() {
			public Object[] getArgs(RegionVo vo) {
				return new Object[]{ vo.getCity_id(), vo.getCity_name(), vo.getWarehouse_id(), vo.getWarehouse_name(), vo.getManager_id(), vo.getManager_name(), vo.getCode(), vo.getName(), vo.getFull_name(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getParent_full_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getBaidu_uid(), vo.getBaidu_title(), vo.getBaidu_address(), vo.getBaidu_longtitude(), vo.getBaidu_latitude(), vo.getSearch_times(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
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

	public RegionVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
	}

	public RegionVo findByCondition(String queryCondition) {
		List<RegionVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(RegionVo vo) {
		Object[] objs = { vo.getCity_id(), vo.getCity_name(), vo.getWarehouse_id(), vo.getWarehouse_name(), vo.getManager_id(), vo.getManager_name(), vo.getCode(), vo.getName(), vo.getFull_name(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getParent_full_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getBaidu_uid(), vo.getBaidu_title(), vo.getBaidu_address(), vo.getBaidu_longtitude(), vo.getBaidu_latitude(), vo.getSearch_times(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final RegionVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<RegionVo>() {
			public Object[] getArgs(RegionVo vo) {
				return new Object[]{ vo.getCity_id(), vo.getCity_name(), vo.getWarehouse_id(), vo.getWarehouse_name(), vo.getManager_id(), vo.getManager_name(), vo.getCode(), vo.getName(), vo.getFull_name(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getParent_full_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getBaidu_uid(), vo.getBaidu_title(), vo.getBaidu_address(), vo.getBaidu_longtitude(), vo.getBaidu_latitude(), vo.getSearch_times(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
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

	public List<RegionVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<RegionVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<RegionVo>(RegionVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<RegionVo>(RegionVo.class), startIndex, size); 
		}
	}
	
	public int updateChildrenNames(RegionVo vo){
		int maxLevel = 10;//理想情况下层级深度不会超过10层，同时也防止环形树导致无线更新。
		int sum = 0;
		/*
		UPDATE sys_region w, sys_region wp 
		SET w.parent_name = wp.name,
		w.parent_full_name = wp.full_name,
		w.full_name = concat(wp.full_name, '-', w.name)
		WHERE w.code like '000011%' and w.level = 3 and w.parent_id = wp.id
		*/
		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE ").append(TABLE_NAME).append(" w, ").append(TABLE_NAME).append(" wp ")
		.append(" SET w.parent_name = wp.name, ")
		.append(" w.parent_full_name = wp.full_name, ")
		.append(" w.full_name = concat(wp.full_name, '-', w.name) ")
		.append(" WHERE w.parent_id = wp.id ")
		.append(" and w.code like '").append(vo.getCode()).append("%' ")
		.append(" and w.level = #level#");
		for (int level = vo.getLevel() + 1; level < maxLevel; level++) {
			String s = sql.toString().replace("#level#", level + "");
//			System.out.println(s);
			int result = super.update(s);
			if (result > 0) {
				sum += result;
			} else {
				break;//说明没有子节点了，则更新结束
			}
		}
		return sum;
	}

	public int updateIs_leaf(Integer[] ids, String is_leaf){
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder("UPDATE ");
		sql.append(TABLE_NAME);
		sql.append(" SET is_leaf = '");
		sql.append(is_leaf);
		sql.append("' WHERE id IN (");
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		sql.append(")");
		return super.update(sql.toString());
	}

	public String queryChildNodeMaxCode(Integer id){
		String code = null;
		String strsql = "SELECT max(code) code FROM " + TABLE_NAME + " WHERE parent_id = ?";
		RegionVo vo = super.queryForObject(strsql, new Object[]{ id }, new RowMapper<RegionVo>() {
			@Override
			public RegionVo mapRow(ResultSet rs, int i) throws SQLException {
				RegionVo vo = new RegionVo();
				vo.setCode(rs.getString("code"));
				return vo;
			}
		});
		if(vo != null){
			code = vo.getCode();
		}
		return code;
	}
	
	public int sort(String sort_ids) {
		if (sort_ids == null || sort_ids.length() == 0)
			return 0;
		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE ").append(TABLE_NAME).append(" SET sort = FIND_IN_SET(id, '").append(sort_ids).append("') ");
		sql.append(" WHERE id IN (").append(sort_ids).append(")");
		return super.update(sql.toString());
	}

}
