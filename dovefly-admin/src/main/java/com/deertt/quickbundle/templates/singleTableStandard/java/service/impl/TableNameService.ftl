package ${project.javaPackageName}.${table.tableFormatName?lower_case}.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deertt.frame.base.service.impl.DvBaseService;
<#if table.hasFiles='1'>

import com.deertt.module.tcommonfile.service.ITCommonFileService;
</#if>
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.dao.I${table.tableFormatName?cap_first}Dao;
import ${project.javaPackageName}.${table.tableFormatName?lower_case}.service.I${table.tableFormatName?cap_first}Service;
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
@Service
public class ${table.tableFormatName?cap_first}Service extends DvBaseService<I${table.tableFormatName?cap_first}Dao, ${table.tableFormatName?cap_first}Vo, ${table.tablePkDataType}> implements I${table.tableFormatName?cap_first}Service, I${table.tableFormatName?cap_first}Constants {
	<#if table.hasFiles='1'>
	
	@Autowired
	public ITCommonFileService fileService;
	</#if>
	
	<#if table.hasFiles='1'>
	@Transactional
	public int insert(${table.tableFormatName?cap_first}Vo vo) {
		int sum = super.insert(vo);
		fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, String.valueOf(vo.get${table.tablePk?cap_first}()));
		return sum;
	}

	@Transactional
	public int insert(${table.tableFormatName?cap_first}Vo[] vos) {
		int sum = super.insert(vos);
		for (${table.tableFormatName?cap_first}Vo vo : vos) {
			fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, String.valueOf(vo.get${table.tablePk?cap_first}()));
		}
		return sum;
	}

	public ${table.tableFormatName?cap_first}Vo find(${table.tablePkDataType} id) {
		${table.tableFormatName?cap_first}Vo vo = super.find(id);
		vo.setFiles(fileService.queryFiles(TABLE_NAME, TABLE_PRIMARY_KEY, String.valueOf(vo.get${table.tablePk?cap_first}())));
		return vo;
	}

	@Transactional
	public int update(${table.tableFormatName?cap_first}Vo vo) {
		int sum = super.update(vo);
		fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, String.valueOf(vo.get${table.tablePk?cap_first}()));
		return sum;
	}

	@Transactional
	public int update(${table.tableFormatName?cap_first}Vo[] vos) {
		int sum = super.update(vos);
		for (${table.tableFormatName?cap_first}Vo vo : vos) {
			fileService.insertUpdateBatch(vo.getFiles(), TABLE_NAME, TABLE_PRIMARY_KEY, String.valueOf(vo.get${table.tablePk?cap_first}()));
		}
		return sum;
	}
	</#if>
}
