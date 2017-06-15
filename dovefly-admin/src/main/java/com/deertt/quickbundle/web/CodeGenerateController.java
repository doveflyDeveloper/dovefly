package com.deertt.quickbundle.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.quickbundle.FreeMarkerUtil;
import com.deertt.quickbundle.db.DBHelper;
import com.deertt.quickbundle.vo.Column;
import com.deertt.quickbundle.vo.Project;
import com.deertt.quickbundle.vo.Table;
import com.deertt.utils.helper.DvSqlHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

@Controller
@RequestMapping("/codeGenerateController")
public class CodeGenerateController extends DvBaseController {

	public final static String CONTROLLER = "/codeGenerateController";
	
	public final static String JSP_PREFIX = "jsp/module/quickbundle";

	/**
	 * 加载默认配置信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadConfig")
	public String loadConfig(HttpServletRequest request) throws Exception {
		Project project = new Project();
		request.setAttribute(REQUEST_BEAN, project);
		return JSP_PREFIX + "/loadConfig";
	}
	
	/**
	 * 异步请求链接数据库，获取所有表名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/connectDB", produces={"application/json;charset=UTF-8"})
	public List<String> connectDB(HttpServletRequest request, HttpServletResponse response, Project project) throws Exception {
		DBHelper db = new DBHelper(project.getDriver(),project.getUrl(),project.getUserName(),project.getPassword());
		List<String> list = db.getTableNames();
//		return JSONArray.fromObject(list).toString();
		return list;
	}
	
	/**
	 * 异步请求链接数据库，获取所有表名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/connectDB2")
	public Object connectDB2(HttpServletRequest request, HttpServletResponse response, Project project) throws Exception {
		DBHelper db = new DBHelper(project.getDriver(),project.getUrl(),project.getUserName(),project.getPassword());
		List<String> list = db.getTableNames();
		//return JSONArray.fromObject(list).toString();
		return list;
	}

	/**
	 * 加载表信息以进行个性个配置
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTable")
	public String loadTable(HttpServletRequest request, Project project) throws Exception {
		DBHelper db = new DBHelper(project.getDriver(),project.getUrl(),project.getUserName(),project.getPassword());
		Table table = db.getTable(DvSqlHelper.escapeSqlValue(request.getParameter("table_name")));
		request.setAttribute(REQUEST_BEAN, table);  
		List<DvDictionaryData> validateBeans = DvDictionaryFactory.getSingleton().getDictDatas("DIC_QB_VALIDATE");
		request.setAttribute("validateBeans", validateBeans);
		return JSP_PREFIX + "/generateTableCode";
	}
	
	/**
	 * 从页面表单获取信息注入vo，并插入单条记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/generateTable")
	public String generateTable(HttpServletRequest request, Project project, Table table) throws Exception {
		String tablePk = table.getTablePk();
		for (Column col : table.getColumns()) {
			if (col.getColumnName().equals(tablePk)) {
				if (col.getDataType().equals("int")) {
					col.setDataType("Integer");
				} else if (col.getDataType().equals("long")) {
					col.setDataType("Long");
				}
				table.setTablePkDataType(col.getDataType());
			}
		}
		FreeMarkerUtil.generateFiles(project, table);
		return "redirect:" + CONTROLLER + "/loadConfig";
	}

}