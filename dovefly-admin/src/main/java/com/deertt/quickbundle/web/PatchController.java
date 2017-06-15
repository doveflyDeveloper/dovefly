package com.deertt.quickbundle.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.module.sys.dict.DvDictionaryData;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.quickbundle.db.DBHelper;
import com.deertt.quickbundle.vo.Patch;
import com.deertt.quickbundle.vo.Project;
import com.deertt.quickbundle.vo.Table;
import com.deertt.utils.patch.FilePatchTool;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

@Controller
@RequestMapping("/patchController")
public class PatchController extends DvBaseController {

	public final static String CONTROLLER = "/patchController";
	
	public final static String JSP_PREFIX = "jsp/module/quickbundle/patch";

	/**
	 * 加载默认配置信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadPatch")
	public String loadPatch(HttpServletRequest request) throws Exception {
		Patch patch = new Patch();
		request.setAttribute(REQUEST_BEAN, patch);
		return JSP_PREFIX + "/loadPatch";
	}
	
	/**
	 * 异步请求链接数据库，获取所有表名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/formateFilePath", produces={"application/json;charset=UTF-8"})
	public Object formateFilePath(HttpServletRequest request, HttpServletResponse response, String files_list_str) throws Exception {
		//DBHelper db = new DBHelper(project.getDriver(),project.getUrl(),project.getUserName(),project.getPassword());
		//List<String> list = db.getTableNames();
		//return JSONArray.fromObject(list).toString();
		String str = files_list_str;
		str += "-------";
		return str;
	}

	/**
	 * 加载表信息以进行个性个配置
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTable/{table_name}")
	public String loadTable(HttpServletRequest request, Project project, @PathVariable("table_name") String table_name) throws Exception {
		DBHelper db = new DBHelper(project.getDriver(),project.getUrl(),project.getUserName(),project.getPassword());
		Table table = db.getTable(table_name);
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
	@RequestMapping("/generatePatch")
	public String generatePatch(HttpServletRequest request, Patch patch) throws Exception {
		//FreeMarkerUtil.generateFiles(project, table);
		FilePatchTool.main(null);
		return "redirect:" + CONTROLLER + "/loadPatch";
	}

}