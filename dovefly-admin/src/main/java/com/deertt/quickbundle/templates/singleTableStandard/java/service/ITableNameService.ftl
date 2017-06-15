package ${project.javaPackageName}.${table.tableFormatName?lower_case}.service;

import com.deertt.frame.base.service.IDvBaseService;
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.dao.I${table.tableFormatName?cap_first}Dao;
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.vo.${table.tableFormatName?cap_first}Vo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author ${project.authorName}
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface I${table.tableFormatName?cap_first}Service extends IDvBaseService<I${table.tableFormatName?cap_first}Dao, ${table.tableFormatName?cap_first}Vo, ${table.tablePkDataType}> {

}
