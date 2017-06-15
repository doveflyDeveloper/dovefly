<#macro insertObj>vo.get${table.tablePk?cap_first}()<#list table.columnsWithoutPk as column><#if !'modify_by, modify_at, modify_ip'?contains(column.columnName)>, vo.get${column.columnName?cap_first}()</#if></#list></#macro>
<#macro updateObj><#list table.columnsWithoutPk as column><#if !'create_by, create_at, create_ip'?contains(column.columnName)>vo.get${column.columnName?cap_first}(), </#if></#list>vo.get${table.tablePk?cap_first}()</#macro>
package ${project.javaPackageName}.${table.tableFormatName?lower_case}.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.deertt.frame.base.dao.impl.CircleVoArray;
import com.deertt.frame.base.dao.impl.DvBaseDao;
import com.deertt.utils.helper.DvSqlHelper;

import ${project.javaPackageName}.${table.tableFormatName?lower_case}.dao.I${table.tableFormatName?cap_first}Dao;
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.util.I${table.tableFormatName?cap_first}Constants;
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.vo.${table.tableFormatName?cap_first}Vo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author ${project.authorName}
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Repository
public class ${table.tableFormatName?cap_first}Dao extends DvBaseDao<${table.tableFormatName?cap_first}Vo, ${table.tablePkDataType}> implements I${table.tableFormatName?cap_first}Dao, I${table.tableFormatName?cap_first}Constants {

	public int insert(${table.tableFormatName?cap_first}Vo vo) {
	<#if table.tablePkDataType = 'String'>
		vo.set${table.tablePk?cap_first}(GenerateIDFactory.generateID());
		Object[] objs = { <@insertObj/> };
		return super.update(SQL_INSERT, objs);
	<#elseif table.tablePkDataType = 'Integer' || table.tablePkDataType = 'int'>
		Object[] objs = { <@insertObj/> };
		vo.set${table.tablePk?cap_first}(super.insertForIntKey(SQL_INSERT, objs));
		return 1;
	<#elseif table.tablePkDataType = 'Long' || table.tablePkDataType = 'long'>
		Object[] objs = { <@insertObj/> };
		vo.set${table.tablePk?cap_first}(super.insertForLongKey(SQL_INSERT, objs));
		return 1;
	<#else>
		Object[] objs = { <@insertObj/> };
		return super.update(SQL_INSERT, objs);
	</#if>
	}

	public int insert(${table.tableFormatName?cap_first}Vo[] vos) {
	<#if table.tablePkDataType = 'String'>
		${table.tablePkDataType}[] ids = GenerateIDFactory.generateIDs(vos.length);
		for(int i = 0; i < vos.length; i++) {
			vos[i].set${table.tablePk?cap_first}(ids[i]);
		}
	</#if>
		return super.batchUpdate(SQL_INSERT, vos, new CircleVoArray<${table.tableFormatName?cap_first}Vo>() {
			public Object[] getArgs(${table.tableFormatName?cap_first}Vo vo) {
				return new Object[]{ <@insertObj/> };
			}
		});
	}

	public int delete(${table.tablePkDataType} id) {
		return super.update(SQL_DELETE_BY_ID, new Object[] { id });
	}

	public int delete(${table.tablePkDataType}[] ids) {
		if (ids == null || ids.length == 0)
			return 0;
		StringBuilder sql = new StringBuilder(SQL_DELETE_MULTI_BY_IDS);
		sql.append(" WHERE ${table.tablePk} IN (");
		<#if table.tablePkDataType = 'String'>
		sql.append(DvSqlHelper.parseToSQLStringApos(ids)); //把ids数组转换为'id1','id2','id3'
		<#elseif table.tablePkDataType = 'Integer' || table.tablePkDataType = 'int'>
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		<#elseif table.tablePkDataType = 'Long' || table.tablePkDataType = 'long'>
		sql.append(DvSqlHelper.parseToSQLIntegerApos(ids)); //把ids数组转换为id1,id2,id3
		<#else>
		sql.append(DvSqlHelper.parseToSQLStringApos(ids)); //把ids数组转换为'id1','id2','id3'
		</#if>
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

	public ${table.tableFormatName?cap_first}Vo find(${table.tablePkDataType} id) {
		return super.queryForObject(SQL_FIND_BY_ID, new Object[] { id }, new BeanPropertyRowMapper<${table.tableFormatName?cap_first}Vo>(${table.tableFormatName?cap_first}Vo.class));
	}

	public ${table.tableFormatName?cap_first}Vo findByCondition(String queryCondition) {
		List<${table.tableFormatName?cap_first}Vo> vos = queryByCondition(queryCondition, null, 0, 1, true);
		return (vos != null && !vos.isEmpty()) ? vos.get(0) : null;
	}

	public int update(${table.tableFormatName?cap_first}Vo vo) {
		Object[] objs = { <@updateObj/> };
		return super.update(SQL_UPDATE_BY_ID, objs);
	}

	public int update(final ${table.tableFormatName?cap_first}Vo[] vos) {
		return super.batchUpdate(SQL_UPDATE_BY_ID, vos, new CircleVoArray<${table.tableFormatName?cap_first}Vo>() {
			public Object[] getArgs(${table.tableFormatName?cap_first}Vo vo) {
				return new Object[]{ <@updateObj/> };
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

	public List<${table.tableFormatName?cap_first}Vo> queryByCondition(String queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) {
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

	public List<${table.tableFormatName?cap_first}Vo> queryByCondition(String sql, int startIndex, int size) {
		if(size <= 0) {
			return super.query(sql, new BeanPropertyRowMapper<${table.tableFormatName?cap_first}Vo>(${table.tableFormatName?cap_first}Vo.class));
		} else {
			return super.query(sql, new BeanPropertyRowMapper<${table.tableFormatName?cap_first}Vo>(${table.tableFormatName?cap_first}Vo.class), startIndex, size); 
		}
	}

}
