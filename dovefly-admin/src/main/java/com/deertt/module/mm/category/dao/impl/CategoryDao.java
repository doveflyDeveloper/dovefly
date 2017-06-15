package com.deertt.module.mm.category.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.mm.category.dao.ICategoryDao;
import com.deertt.module.mm.category.util.ICategoryConstants;
import com.deertt.module.mm.category.vo.CategoryVo;
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
public class CategoryDao extends DvBaseDao<CategoryVo, Integer> implements ICategoryDao, ICategoryConstants {

	public int insert(CategoryVo vo) {
		Object[] objs = { vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(CategoryVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<CategoryVo>() {
			public Object[] getArgs(CategoryVo vo) {
				return new Object[]{ vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at() };
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

	public CategoryVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<CategoryVo>(CategoryVo.class));
	}

	public CategoryVo findByCondition(String queryCondition) {
		List<CategoryVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(CategoryVo vo) {
		Object[] objs = { vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final CategoryVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<CategoryVo>() {
			public Object[] getArgs(CategoryVo vo) {
				return new Object[]{ vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
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

	public List<CategoryVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<CategoryVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<CategoryVo>(CategoryVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<CategoryVo>(CategoryVo.class), startIndex, size); 
		}
	}

	public int updateParentName(Integer id, String parent_name){
		StringBuilder sql = new StringBuilder("UPDATE ");
		sql.append(TABLE_NAME);
		sql.append(" SET parent_name = '");
		sql.append(parent_name);
		sql.append("' WHERE parent_id = '");
		sql.append(id);
		sql.append("'");
		return super.update(sql.toString());
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
		CategoryVo vo = super.queryForObject(strsql, new Object[]{ id }, new RowMapper<CategoryVo>() {
			@Override
			public CategoryVo mapRow(ResultSet rs, int i) throws SQLException {
				CategoryVo vo = new CategoryVo();
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
