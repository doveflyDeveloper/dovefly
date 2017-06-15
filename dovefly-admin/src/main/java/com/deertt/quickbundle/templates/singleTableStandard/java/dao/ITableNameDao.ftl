package ${project.javaPackageName}.${table.tableFormatName?lower_case}.dao;

import com.deertt.frame.base.dao.IDvBaseDao;
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.vo.${table.tableFormatName?cap_first}Vo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author ${project.authorName}
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface I${table.tableFormatName?cap_first}Dao extends IDvBaseDao<${table.tableFormatName?cap_first}Vo, ${table.tablePkDataType}> {

}
