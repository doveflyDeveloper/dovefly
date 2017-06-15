package com.deertt.module.sys.resource.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.module.sys.resource.dao.IResourceDao;
import com.deertt.module.sys.resource.util.IResourceConstants;
import com.deertt.module.sys.resource.vo.ResourceVo;
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
public class ResourceDao extends DvBaseDao<ResourceVo, Integer> implements IResourceDao, IResourceConstants {

	public int insert(ResourceVo vo) {
		Object[] objs = { vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getType(), vo.getUrl(), vo.getPermission(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at(), vo.getModify_by(), vo.getModify_at() };
		vo.setId(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	}

	public int insert(ResourceVo[] vos) {
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<ResourceVo>() {
			public Object[] getArgs(ResourceVo vo) {
				return new Object[]{ vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getType(), vo.getUrl(), vo.getPermission(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getCreate_by(), vo.getCreate_at(), vo.getModify_by(), vo.getModify_at() };
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

	public ResourceVo find(Integer id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<ResourceVo>(ResourceVo.class));
	}

	public ResourceVo findByCondition(String queryCondition) {
		List<ResourceVo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(ResourceVo vo) {
		Object[] objs = { vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getType(), vo.getUrl(), vo.getPermission(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final ResourceVo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<ResourceVo>() {
			public Object[] getArgs(ResourceVo vo) {
				return new Object[]{ vo.getCode(), vo.getName(), vo.getParent_id(), vo.getParent_code(), vo.getParent_name(), vo.getType(), vo.getUrl(), vo.getPermission(), vo.getLevel(), vo.getIs_leaf(), vo.getRemark(), vo.getSort(), vo.getStatus(), vo.getModify_by(), vo.getModify_at(), vo.getId() };
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

	public List<ResourceVo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<ResourceVo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<ResourceVo>(ResourceVo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<ResourceVo>(ResourceVo.class), startIndex, size); 
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
		ResourceVo vo = super.queryForObject(strsql, new Object[]{ id }, new RowMapper<ResourceVo>() {
			@Override
			public ResourceVo mapRow(ResultSet rs, int i) throws SQLException {
				ResourceVo vo = new ResourceVo();
				vo.setCode(rs.getString("code"));
				return vo;
			}
		});
		if(vo != null){
			code = vo.getCode();
		}
		return code;
	}

	public List<ResourceVo> queryResourcesByRoleId(Integer role_id){
		return super.query(SQL_QUERY_RESOURCES_BY_ROLE_ID, new Object[] { role_id }, new BeanPropertyRowMapper<ResourceVo>(ResourceVo.class));
	}

	public List<ResourceVo> queryMenusByUserId(Integer user_id) {
		return super.query(SQL_QUERY_MENUS_BY_USER_ID, new Object[] { user_id }, new BeanPropertyRowMapper<ResourceVo>(ResourceVo.class));
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
