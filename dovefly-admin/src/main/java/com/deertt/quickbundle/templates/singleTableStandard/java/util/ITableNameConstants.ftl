<#macro selectAllSql>${table.tablePk}<#list table.columnsWithoutPk as column>, ${column.columnName}</#list></#macro>
<#macro selectShortSql>${table.tablePk}<#list table.columnsIsBuild as column>, ${column.columnName}</#list></#macro>
<#macro insertSql>INSERT INTO ${table.tableName} ( ${table.tablePk}<#list table.columnsWithoutPk as column><#if !'modify_by, modify_at'?contains(column.columnName)>, ${column.columnName}</#if></#list> ) VALUES ( ?<#list table.columnsWithoutPk as column><#if !'modify_by, modify_at'?contains(column.columnName)>, ?</#if></#list> )</#macro>
<#macro updateSql>UPDATE ${table.tableName} SET <#list table.columnsWithoutPk as column><#if !'create_by, create_at'?contains(column.columnName)>${column.columnName} = ?<#if column_has_next>, </#if></#if></#list> WHERE ${table.tablePk} = ?</#macro>
package ${project.javaPackageName}.${table.tableFormatName?lower_case}.util;

import com.deertt.frame.base.util.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author ${project.authorName}
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface I${table.tableFormatName?cap_first}Constants extends IGlobalConstants {
	
	//权限
	public final static String PERM_READ = "${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller:read";
	
	public final static String PERM_WRITE = "${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller:*";
	
	//URL
	public final static String CONTROLLER = "/${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller";
	
	public final static String DEFAULT_REDIRECT = "/${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller/queryAll";
	
	public final static String JSP_PREFIX = "${project.jspSourcePath}/${table.tableFormatName?lower_case}";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "${project.xlsTemplateBasePath}/${table.tableFormatName?lower_case}/";
	
	//Sql语句
	public final static String AFTER_SELECT_ALL   = "<@selectAllSql/>";

	public final static String AFTER_SELECT_SHORT = "<@selectShortSql/>";

	public final static String SQL_INSERT = "<@insertSql/>";
	
	public final static String SQL_DELETE_BY_ID = "DELETE FROM ${table.tableName} WHERE ${table.tablePk} = ?";
	
	public final static String SQL_DELETE_MULTI_BY_IDS = "DELETE FROM ${table.tableName}";

	public final static String SQL_FIND_BY_ID = "SELECT " + AFTER_SELECT_ALL + " FROM ${table.tableName} WHERE ${table.tablePk} = ?";

	public final static String SQL_UPDATE_BY_ID = "<@updateSql/>";
	
	public final static String SQL_COUNT = "SELECT COUNT(${table.tablePk}) FROM ${table.tableName}";
	
	public final static String SQL_QUERY_ALL = "SELECT " + AFTER_SELECT_SHORT + " FROM ${table.tableName}";

	public final static String SQL_QUERY_ALL_EXPORT = "SELECT " + AFTER_SELECT_ALL + " FROM ${table.tableName}";
	
	/**表名*/
	public final static String TABLE_NAME = "${table.tableName}";
	
	/**表名汉化*/
	public final static String TABLE_NAME_CHINESE = "${table.tableNameChinese}";
		
	/**主键列名*/
	public final static String TABLE_PRIMARY_KEY = "${table.tablePk}";
	
	//默认启用的查询条件
	public final static String DEFAULT_SQL_WHERE_USABLE = "";
	
	public final static String DEFAULT_SQL_CONTACT_KEYWORD = " WHERE ";
	
	//默认的排序字段
	public final static String DEFAULT_ORDER_BY_CODE = "";
	
}
