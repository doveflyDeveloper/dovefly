package ${project.javaPackageName}.${table.tableFormatName?lower_case}.web;

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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.office.excel.read.DvExcelReader;
import com.deertt.utils.helper.office.excel.read.MapContainer;
import com.deertt.utils.helper.office.excel.write.DvExcelWriter;
import com.deertt.utils.helper.string.DvStringHelper;

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
@Controller
@RequestMapping("/${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller")
public class ${table.tableFormatName?cap_first}Controller extends DvBaseController implements I${table.tableFormatName?cap_first}Constants {
	
	@Autowired
	protected I${table.tableFormatName?cap_first}Service service;
	
	/**
	 * 新增页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request) throws Exception {
		return JSP_PREFIX + "/insert${table.tableFormatName?cap_first}";
	}
	
	/**
	 * 复制新增页面（根据已有单据信息复制创建新单据）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping(value = "/copy/{id}", method = RequestMethod.GET)
	public String copy(HttpServletRequest request, @PathVariable ${table.tablePkDataType} id) throws Exception {
		${table.tableFormatName?cap_first}Vo bean = service.find(id);
		<#if table.tablePkDataType = 'String'>
		bean.set${table.tablePk?cap_first}(null);
		<#elseif table.tablePkDataType = 'Integer' || table.tablePkDataType = 'int' || table.tablePkDataType = 'Long' || table.tablePkDataType = 'long'>
		bean.set${table.tablePk?cap_first}(null);
		<#else>
		bean.set${table.tablePk?cap_first}(null);
		</#if>
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insert${table.tableFormatName?cap_first}";
	}
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/find/{id}")
	public String find(HttpServletRequest request, @PathVariable(REQUEST_ID) ${table.tablePkDataType} id) throws Exception {
		${table.tableFormatName?cap_first}Vo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insert${table.tableFormatName?cap_first}";
	}
	
	/**
	 * 新增保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, ${table.tableFormatName?cap_first}Vo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		<#if table.hasFiles='1'>
		DvVoHelper.populateCommonFiles(request, vo, null);//从request中注入附件信息
		</#if>
		service.insert(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}

	/**
	 * 修改保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/update")
	public String update(HttpServletRequest request, ${table.tableFormatName?cap_first}Vo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		<#if table.hasFiles='1'>
		DvVoHelper.populateCommonFiles(request, vo, null);//从request中注入附件信息
		</#if>
		service.update(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}
	
	/**
	 * 删除单条记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/delete/{id}")
	public String delete(HttpServletRequest request, @PathVariable(REQUEST_ID) ${table.tablePkDataType} id, RedirectAttributes attr) throws Exception {
		service.delete(id);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}

	/**
	 * 删除多条记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/deleteMulti/{ids}")
	public String deleteMulti(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attr) throws Exception {
		<#if table.tablePkDataType = 'String'>
		String[] idStrs = ids.split(",");
		service.delete(idStrs);
		<#elseif table.tablePkDataType = 'Integer' || table.tablePkDataType = 'int'>
		service.delete(DvStringHelper.parseStringToIntegerArray(ids, ","));
		<#elseif table.tablePkDataType = 'Long' || table.tablePkDataType = 'long'>
		String[] idStrs = ids.split(",");
		${table.tablePkDataType}[] idLongs = new ${table.tablePkDataType}[idStrs.length];
		for (int i = 0; i < idStrs.length; i++) {
			idLongs[i] = Long.parseLong(idStrs[i]);
		}
		service.delete(idLongs);
		<#else>
		String[] idStrs = ids.split(",");
		service.delete(idStrs);
		</#if>
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}
	
	/**
	 * 查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/detail/{id}")
	public String detail(HttpServletRequest request, @PathVariable(REQUEST_ID) ${table.tablePkDataType} id) throws Exception {
		${table.tableFormatName?cap_first}Vo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		<#if table.hasFiles='1'>
		request.setAttribute(${table.tableFormatName?cap_first}Vo.COMMON_FILE_LIST_KEY, bean.getFiles());  //把附件列表放入request
		</#if>
		return JSP_PREFIX + "/detail${table.tableFormatName?cap_first}";
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/query")
	public String query(HttpServletRequest request) throws Exception {
		String queryCondition = getQueryCondition(request);
		DvPageVo pageVo = super.transctPageVo(request, service.getRecordCount(queryCondition));
		String orderStr = "";//String orderStr = "create_at desc";
		List<${table.tableFormatName?cap_first}Vo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/list${table.tableFormatName?cap_first}";
	}
	
	/**
	 * 查询全部，清除所有查询条件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
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
		return JSP_PREFIX + "/reference${table.tableFormatName?cap_first}";
	}
	<#if table.hasImpExp='1'>
	
	/**
	 * 下载模板
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(I${table.tableFormatName?cap_first}Constants.TABLE_NAME_CHINESE + "导入模板.xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "${table.tableFormatName?lower_case}_import_list_template.xls";
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
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/importExcel")
	public String importExcel(HttpServletRequest request, RedirectAttributes attr) throws Exception {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if (map != null) {
			String filePath = (String) map.get(UploadConfig.IMPORT_FILE_PATH_KEY);
			File excelFile = new File(filePath);
			String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
			File xmlFile = new File(projectBasePath + XLS_TEMPLATE_BASE_PATH + "${table.tableFormatName?lower_case}_import_list_config.xml");

			Map<String, Object> dataMap = DvExcelReader.readExcel(excelFile, xmlFile, new MapContainer() {
				@Override
				public Map<String, Object> makeDataContainerMap() {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					List<${table.tableFormatName?cap_first}Vo> beans = new ArrayList<${table.tableFormatName?cap_first}Vo>();
					dataMap.put(REQUEST_BEANS, beans);
					return dataMap;
				}
			});
			List<${table.tableFormatName?cap_first}Vo> beans = (List<${table.tableFormatName?cap_first}Vo>) dataMap.get(REQUEST_BEANS);
			for(${table.tableFormatName?cap_first}Vo vo : beans) {
			<#if table.tablePkDataType = 'String'>
				if(vo.get${table.tablePk?cap_first}() != null && vo.get${table.tablePk?cap_first}().trim().length() > 0) {
			<#elseif table.tablePkDataType = 'Integer' || table.tablePkDataType = 'Long'>
				if(vo.get${table.tablePk?cap_first}() != null && vo.get${table.tablePk?cap_first}() > 0) {
			<#elseif table.tablePkDataType = 'int' || table.tablePkDataType = 'long'>
				if(vo.get${table.tablePk?cap_first}() > 0) {
			</#if>
					DvVoHelper.markModifyStamp(request, vo);
			} else {
					DvVoHelper.markCreateStamp(request, vo);
				}
			}
			service.insertUpdateBatch(beans.toArray(new ${table.tableFormatName?cap_first}Vo[0]));
		}
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportList/{ids}")
	public void exportList(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(I${table.tableFormatName?cap_first}Constants.TABLE_NAME_CHINESE + "批量导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if(StringUtils.isEmpty(ids) || "all".equals(ids)){
			DvPageVo pageVo = new DvPageVo(2000, 2000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "${table.tablePk} IN ('" + ids.replaceAll(",", "','") + "')";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<${table.tableFormatName?cap_first}Vo> beans = (List<${table.tableFormatName?cap_first}Vo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "${table.tableFormatName?lower_case}_export_list_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 导出一条单据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportDetail/{id}")
	public void exportDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_ID) ${table.tablePkDataType} id) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(I${table.tableFormatName?cap_first}Constants.TABLE_NAME_CHINESE + "导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		detail(request, id); //通过查询取到bean  
		${table.tableFormatName?cap_first}Vo bean = (${table.tableFormatName?cap_first}Vo)request.getAttribute(REQUEST_BEAN);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEAN, bean);
		List<${table.tableFormatName?cap_first}Vo> beans = new ArrayList<${table.tableFormatName?cap_first}Vo>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "${table.tableFormatName?lower_case}_export_detail_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
		
	}
	</#if>
	
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
		result.setMessage("Ajax请求数据成功！");
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
	<#list table.columns as column> 
		<#--处理 参照 && 数字 -->   
		<#if column.columnName == table.tablePk && (column.dataType='Integer' || column.dataType='int' || column.dataType='Long' || column.dataType='long' || column.dataType='BigInteger' || column.dataType='BigDecimal')>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}"), DvSqlHelper.TYPE_NUMBER_EQUAL));
		<#elseif column.columnName == table.tablePk && column.dataType='String'>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}"), DvSqlHelper.TYPE_CHAR_EQUAL));
		<#elseif column.inputType='dv.listReference' && (column.dataType='Integer' || column.dataType='int' || column.dataType='Long' || column.dataType='long' || column.dataType='BigInteger' || column.dataType='BigDecimal')>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}"), DvSqlHelper.TYPE_NUMBER_EQUAL));
		<#--处理 状态位 || (参照&&字符)，小于3个字符-->
		<#elseif (column.dataType='String' && column.columnSize lte 3) || (column.inputType='dv.listReference' && column.dataType='String')>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
		<#--处理text或textarea，大于3个字符-->
		<#elseif column.dataType='String' && column.columnSize gt 3>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}"), DvSqlHelper.TYPE_CHAR_LIKE));
		<#--处理时间参照-->
		<#elseif column.dataType='Timestamp' || column.dataType='Date'>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
		<#--处理数字-->
		<#elseif column.dataType='Integer' || column.dataType='int' || column.dataType='Long' || column.dataType='long' || column.dataType='Double' || column.dataType='double' || column.dataType='BigInteger' || column.dataType='BigDecimal'>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}_from"), DvSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}_to"), DvSqlHelper.TYPE_NUMBER_LESS_EQUAL));
		<#else>
			lQuery.add(DvSqlHelper.buildQueryStr("${column.columnName}", request.getParameter("${column.columnName}"), DvSqlHelper.TYPE_CHAR_LIKE));
		</#if>
	</#list>
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
