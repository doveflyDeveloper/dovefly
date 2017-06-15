package ${project.javaPackageName}.${table.tableFormatName?lower_case}.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

<#if table.hasFiles='1'>
import com.deertt.module.tcommonfile.vo.CommonFileAware;
<#else>
import com.deertt.frame.base.vo.DvBaseVo;
</#if>

/**
 * 功能、用途、现存BUG:
 * 
 * @author ${project.authorName}
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class ${table.tableFormatName?cap_first}Vo extends <#if table.hasFiles='1'>CommonFileAware<#else>DvBaseVo</#if> {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	<#list table.columns as column>
	//${column.columnNameChinese}
	private ${column.dataType} ${column.columnName};
	
	</#list>
	//----------结束vo的属性----------
	@Override
	public boolean isNew() {
		<#if table.tablePkDataType = 'String'>
		return ${table.tablePk} == null;
		<#elseif table.tablePkDataType = 'Integer' || table.tablePkDataType = 'int'>
		return ${table.tablePk} == null || ${table.tablePk} == 0;
		<#elseif table.tablePkDataType = 'Long' || table.tablePkDataType = 'long'>
		return ${table.tablePk} == null || ${table.tablePk} == 0;
		<#else>
		return ${table.tablePk} == null;
		</#if>
	}
	//----------开始vo的setter和getter方法----------
	<#list table.columns as column>
	/** 获取${column.columnNameChinese} */
	public ${column.dataType} get${column.columnName?cap_first}() {
		return ${column.columnName};
	}
	
	/** 设置${column.columnNameChinese} */
	public void set${column.columnName?cap_first}(${column.dataType} ${column.columnName}) {
		this.${column.columnName} = ${column.columnName};
	}
	
	</#list> 
	//----------结束vo的setter和getter方法----------
}