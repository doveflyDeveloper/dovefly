package com.deertt.module.tcommonfile.web;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.tcommonfile.service.ITCommonFileService;
import com.deertt.module.tcommonfile.util.ITCommonFileConstants;
import com.deertt.module.tcommonfile.vo.TCommonFileVo;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.office.excel.read.DvExcelReader;
import com.deertt.utils.helper.office.excel.read.MapContainer;
import com.deertt.utils.helper.office.excel.write.DvExcelWriter;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Controller
@RequestMapping("/tCommonFileController")
public class TCommonFileController extends DvBaseController {

	public final static String CONTROLLER = "/tCommonFileController";
	
	public final static String DEFAULT_REDIRECT = "/tCommonFileController/queryAll";
	
	public final static String JSP_PREFIX = "jsp/module/tcommonfile";
	
	public final static String XLS_TEMPLATE_BASE_PATH = "files/templates/excel/tcommonfile/";
	
	@Autowired
	protected ITCommonFileService service;
	
	/**
	 * 新增页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create")
	public String create(HttpServletRequest request) throws Exception {
		return JSP_PREFIX + "/insertTCommonFile";
	}
	
	/**
	 * 复制新增页面（根据已有单据信息复制创建新单据）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/copy/{id}")
	public String copy(HttpServletRequest request, @PathVariable(REQUEST_ID) String id) throws Exception {
		TCommonFileVo bean = service.find(id);
		bean.setFile_id(null);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertTCommonFile";
	}
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/find/{id}")
	public String find(HttpServletRequest request, @PathVariable(REQUEST_ID) String id) throws Exception {
		TCommonFileVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertTCommonFile";
	}
	
	/**
	 * 新增保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, TCommonFileVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		service.insert(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}

	/**
	 * 修改保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, TCommonFileVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markModifyStamp(request,vo);
		service.update(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}
	
	/**
	 * 删除单条记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete/{id}")
	public String delete(HttpServletRequest request, @PathVariable(REQUEST_ID) String id, RedirectAttributes attrs) throws Exception {
		service.delete(id);
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}

	/**
	 * 删除多条记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteMulti/{ids}")
	public String deleteMulti(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attrs) throws Exception {
		String[] idStrs = ids.split(",");
		if (idStrs != null && idStrs.length != 0) {
			service.delete(idStrs);
		}
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}
	
	/**
	 * 查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{id}")
	public String detail(HttpServletRequest request, @PathVariable(REQUEST_ID) String id) throws Exception {
		TCommonFileVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailTCommonFile";
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/query")
	public String query(HttpServletRequest request) throws Exception {
		String queryCondition = getQueryCondition(request);
		DvPageVo pageVo = super.transctPageVo(request, service.getRecordCount(queryCondition));
		String orderStr = "";//String orderStr = "create_date desc";
		List<TCommonFileVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listTCommonFile";
	}
	
	/**
	 * 查询全部，清除所有查询条件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAll")
	public String queryAll(HttpServletRequest request) throws Exception {	
		request.setAttribute(REQUEST_QUERY_CONDITION, "");
		return query(request);
	}

	/**
	 * 参照信息查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reference")
	public String reference( HttpServletRequest request) throws Exception {
		query(request);
		return JSP_PREFIX + "/referenceTCommonFile";
	}
	
	/**
	 * 下载模板
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(ITCommonFileConstants.TABLE_NAME_CHINESE + "导入模板.xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "tcommonfile_import_list_template.xls";
		OutputStream outputStream = response.getOutputStream();   
		outputStream.write(FileUtils.readFileToByteArray(new File(templateFileName)));  
		outputStream.flush();
		outputStream.close(); 
		
	}
 	
	/**
	 * 导入列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importExcel")
	public String importExcel(HttpServletRequest request, RedirectAttributes attrs) throws Exception {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);	
		if (map != null) {
			String filePath = (String) map.get(ApplicationConfig.FILE_IMPORT_KEY);
			File excelFile = new File(filePath);
			String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
			File xmlFile = new File(projectBasePath + XLS_TEMPLATE_BASE_PATH + "tcommonfile_import_list_config.xml");

			Map<String, Object> dataMap = DvExcelReader.readExcel(excelFile, xmlFile, new MapContainer() {
				
				@Override
				public Map<String, Object> makeDataContainerMap() {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					List<TCommonFileVo> beans = new ArrayList<TCommonFileVo>();
					dataMap.put(REQUEST_BEANS, beans);
					return dataMap;
				}
			});
			List<TCommonFileVo> beans = (List<TCommonFileVo>) dataMap.get(REQUEST_BEANS);
			for(TCommonFileVo vo : beans) {
				if(vo.getFile_id() != null) {
					DvVoHelper.markModifyStamp(request, vo);
				} else {
					DvVoHelper.markCreateStamp(request, vo);
				}
			}
			service.insertUpdateBatch(beans.toArray(new TCommonFileVo[0]));
		}
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportList/{ids}")
	public void exportList(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(ITCommonFileConstants.TABLE_NAME_CHINESE + "批量导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if(StringUtils.isEmpty(ids) || "all".equals(ids)){
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		}else{
			String queryCondition = "file_id IN ('" + ids.replaceAll(",", "','") + "')";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<TCommonFileVo> beans = (List<TCommonFileVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "tcommonfile_export_list_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 导出一条单据详细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDetail/{id}")
	public void exportDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_ID) String id) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(ITCommonFileConstants.TABLE_NAME_CHINESE + "导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		detail(request, id); //通过查询取到bean  
		TCommonFileVo bean = (TCommonFileVo)request.getAttribute(REQUEST_BEAN);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEAN, bean);
		List<TCommonFileVo> beans = new ArrayList<TCommonFileVo>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "tcommonfile_export_detail_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
		
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxQuery", produces={"application/json;charset=UTF-8"})
	public Object ajaxQuery(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("导入用户信息成功！");
		return result;
	}
	
	/**
	 * 功能: 从request中获得查询条件
	 *
	 * @param request
	 * @return
	 */
	protected String getQueryCondition(HttpServletRequest request) {
		String queryCondition = null;
		if (request.getAttribute(REQUEST_QUERY_CONDITION) != null) {
			queryCondition = request.getAttribute(REQUEST_QUERY_CONDITION).toString();
		} else {
			List<String> lQuery = new ArrayList<String>();
			lQuery.add(DvSqlHelper.buildQueryStr("file_id", request.getParameter("file_id"), DvSqlHelper.TYPE_CHAR_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("owner_table_name", request.getParameter("owner_table_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("owner_bill_id", request.getParameter("owner_bill_id"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("owner_column_name", request.getParameter("owner_column_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("file_name", request.getParameter("file_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("file_url", request.getParameter("file_url"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("file_type", request.getParameter("file_type"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("file_size", request.getParameter("file_size_from"), DvSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("file_size", request.getParameter("file_size_to"), DvSqlHelper.TYPE_NUMBER_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("remark", request.getParameter("remark"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("usable_state", request.getParameter("usable_state"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("order_code", request.getParameter("order_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
