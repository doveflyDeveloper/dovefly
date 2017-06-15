package com.deertt.module.trade.bill.web;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.utils.helper.date.DvDateHelper;
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
@RequestMapping("/tradeBillReportController")
public class TradeBillReportController extends TradeBillController {
	
	@Autowired
	protected ITradeBillService service;
	
	/**
	 * 统计店长订单销售数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/reportGroupByShop")
	public String reportGroupByShop(HttpServletRequest request) throws Exception {
		
		Integer city_id = null;
		if (LoginHelper.isHeadquartersRole()) {
			String c = request.getParameter("city_id");
			city_id = StringUtils.isEmpty(c) ? null : Integer.parseInt(c);
		}
		
		if (LoginHelper.isCityManagerRole()) {
			city_id = LoginHelper.getCityId();
		}
		
		Integer manager_id = null;
		if (LoginHelper.isOperationManagerRole()) {
			city_id = LoginHelper.getCityId();
			manager_id = LoginHelper.getUserId();
		}
		
		Integer shop_id = null;
		if (LoginHelper.isShopkeeperRole()) {
			shop_id = LoginHelper.getShopId();
		}
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		String group_by = request.getParameter("group_by");
		if (StringUtils.isEmpty(group_by)) {
			if (LoginHelper.isHeadquartersRole()) {
				group_by = "city";
			} else if (LoginHelper.isCityManagerRole()) {
				group_by = "manager";
			} else if (LoginHelper.isOperationManagerRole()) {
				group_by = "school";
			} else if (LoginHelper.isShopkeeperRole()) {
				group_by = "shop";
			}
		}
		request.setAttribute("group_by", group_by);
		
		List<Map<String, Object>> beans = null;
		if ("city".equals(group_by)) {//城市
			beans = service.reportGroupByCity(city_id, bill_date_from, bill_date_to);
		} else if ("manager".equals(group_by)) {//运营主管
			beans = service.reportGroupByManager(city_id, manager_id, bill_date_from, bill_date_to);
		} else if ("school".equals(group_by)) {//学校
			beans = service.reportGroupBySchool(city_id, manager_id, bill_date_from, bill_date_to);
		} else if ("shop".equals(group_by)) {//店铺
			beans = service.reportGroupByShop(city_id, manager_id, shop_id, bill_date_from, bill_date_to);
		}
		
		request.setAttribute(REQUEST_BEANS, beans);
		
		return JSP_PREFIX + "/reportGroupByShop";
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportGroupByShop/{ids}")
	public void exportGroupByShop(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("销售订单统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		
		Integer city_id = null;
		if (LoginHelper.isHeadquartersRole()) {
			String c = request.getParameter("city_id");
			city_id = StringUtils.isEmpty(c) ? null : Integer.parseInt(c);
		}
		
		if (LoginHelper.isCityManagerRole()) {
			city_id = LoginHelper.getCityId();
		}
		
		Integer manager_id = null;
		if (LoginHelper.isOperationManagerRole()) {
			city_id = LoginHelper.getCityId();
			manager_id = LoginHelper.getUserId();
		}
		
		Integer shop_id = null;
		if (LoginHelper.isShopkeeperRole()) {
			shop_id = LoginHelper.getShopId();
		}
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		String group_by = request.getParameter("group_by");
		if (StringUtils.isEmpty(group_by)) {
			if (LoginHelper.isHeadquartersRole()) {
				group_by = "city";
			} else if (LoginHelper.isCityManagerRole()) {
				group_by = "manager";
			} else if (LoginHelper.isOperationManagerRole()) {
				group_by = "school";
			} else if (LoginHelper.isShopkeeperRole()) {
				group_by = "shop";
			}
		}
		request.setAttribute("group_by", group_by);
		
		List<Map<String, Object>> beans = null;
		
		String templateFileName = null;
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		
		if ("city".equals(group_by)) {//城市
			beans = service.reportGroupByCity(city_id, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByCity.xls";
		} else if ("manager".equals(group_by)) {//运营主管
			beans = service.reportGroupByManager(city_id, manager_id, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByManager.xls";
		} else if ("school".equals(group_by)) {//学校
			beans = service.reportGroupBySchool(city_id, manager_id, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupBySchool.xls";
		} else if ("shop".equals(group_by)) {//店铺
			beans = service.reportGroupByShop(city_id, manager_id, shop_id, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByShop.xls";
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("city_name", city_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", city_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));
		
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 运营主管统计各个店长的进货订单数量及金额
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/reportManagerGroupByShop")
	public String reportManagerGroupByShop(HttpServletRequest request) throws Exception {
		
		Integer city_id = null;
		Integer manager_id = null;
		String m = request.getParameter("manager_id");
		manager_id = StringUtils.isEmpty(m) ? 0 : Integer.parseInt(m);
		request.setAttribute("manager_id", manager_id);
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		List<Map<String, Object>> beans = service.reportGroupByShop(city_id, manager_id, null, bill_date_from, bill_date_to);
		
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reportManagerGroupByShop";
	}
	  
	/**
	 * 运营主管统计各个学校的下的各个店长进货订单数量及金额
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequiresPermissions(PERM_READ)
	 @RequestMapping("/reportShopkeeper")
	 public String reportShopkeeper(HttpServletRequest request) throws Exception {
		request.setAttribute("requestForm", "Shopkeeper");
		Integer city_id = null;
		Integer manager_id = null;
		String m = request.getParameter("manager_id");
		manager_id = StringUtils.isEmpty(m) ? 0 : Integer.parseInt(m);
		request.setAttribute("manager_id", manager_id);
		
		Integer school_id = null;
		String s = request.getParameter("school_id");
		school_id = StringUtils.isEmpty(s) ? 0 : Integer.parseInt(s);
		request.setAttribute("school_id", school_id);
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		List<Map<String, Object>> beans = service.reportShopkeeper(city_id, manager_id, school_id, bill_date_from, bill_date_to);
		
		request.setAttribute(REQUEST_BEANS, beans);
		 
		return JSP_PREFIX + "/reportShopkeeper";
	 }
	 
	/**
	 * 运营主管统计各个学校的进货订单数量及金额
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/reportManagerGroupBySchool")
	public String reportManagerGroupBySchool(HttpServletRequest request) throws Exception {
		
		Integer city_id = null;
		Integer manager_id = null;
		String m = request.getParameter("manager_id");
		manager_id = StringUtils.isEmpty(m) ? 0 : Integer.parseInt(m);
		request.setAttribute("manager_id", manager_id);
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		List<Map<String, Object>> beans = service.reportGroupBySchool(city_id, manager_id, bill_date_from, bill_date_to);
		
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reportManagerGroupBySchool";
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportManagerGroupByShop/{ids}")
	public void exportManagerGroupByShop(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("销售订单统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Integer city_id = null;
		Integer manager_id = null;
		String m = request.getParameter("manager_id");
		manager_id = StringUtils.isEmpty(m) ? 0 : Integer.parseInt(m);
		request.setAttribute("manager_id", manager_id);
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		List<Map<String, Object>> beans = service.reportGroupByShop(city_id, manager_id, null, bill_date_from, bill_date_to);
		
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByShop.xls";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("city_name", city_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", city_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));
		
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportShopKeeper/{ids}")
	public void exportShopKeeper(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("销售订单统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Integer city_id = null;
		Integer manager_id = null;
		String m = request.getParameter("manager_id");
		manager_id = StringUtils.isEmpty(m) ? 0 : Integer.parseInt(m);
		request.setAttribute("manager_id", manager_id);
		
		Integer school_id = null;
		String s = request.getParameter("school_id");
		school_id = StringUtils.isEmpty(s) ? 0 : Integer.parseInt(s);
		request.setAttribute("school_id", school_id);
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		List<Map<String, Object>> beans = service.reportShopkeeper(city_id, manager_id, school_id, bill_date_from, bill_date_to);
		
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByShopKeeper.xls";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("city_name", city_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", city_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));
		
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	

	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportManagerGroupBySchool/{ids}")
	public void exportManagerGroupBySchool(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("销售订单统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Integer city_id = null;
		Integer manager_id = null;
		String m = request.getParameter("manager_id");
		manager_id = StringUtils.isEmpty(m) ? 0 : Integer.parseInt(m);
		request.setAttribute("manager_id", manager_id);
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		}
		request.setAttribute("bill_date_from", bill_date_from);
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		}
		request.setAttribute("bill_date_to", bill_date_to);
		
		List<Map<String, Object>> beans = service.reportGroupBySchool(city_id, manager_id, bill_date_from, bill_date_to);
		
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupBySchool.xls";
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("city_name", city_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", city_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));
		
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	/**
	 * 统计采购商品的数量及金额
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/reportGroupByGoods")
	public String reportGroupByGoods(HttpServletRequest request) throws Exception {
		
		Integer city_id = null;
		if (LoginHelper.isHeadquartersRole()) {
			String c = request.getParameter("city_id");
			city_id = StringUtils.isEmpty(c) ? null : Integer.parseInt(c);
		}
		
		if (LoginHelper.isCityManagerRole()) {
			city_id = LoginHelper.getCityId();
		}
		
		Integer shop_id = null;
		if (LoginHelper.isShopkeeperRole()) {
			shop_id = LoginHelper.getShopId();
		}
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
			request.setAttribute("bill_date_from", bill_date_from);
		}
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
			request.setAttribute("bill_date_to", bill_date_to);
		}

		List<Map<String, Object>> beans = service.reportGroupByGoods(city_id, shop_id, bill_date_from, bill_date_to);
		
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reportGroupByGoods";
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportGroupByGoods/{ids}")
	public void exportGroupByGoods(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("销售商品统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Integer city_id = null;
		if (LoginHelper.isHeadquartersRole()) {
			String c = request.getParameter("city_id");
			city_id = StringUtils.isEmpty(c) ? null : Integer.parseInt(c);
		}
		
		if (LoginHelper.isCityManagerRole()) {
			city_id = LoginHelper.getCityId();
		}
		
		Integer shop_id = null;
		if (LoginHelper.isShopkeeperRole()) {
			shop_id = LoginHelper.getShopId();
		}
		
		String bill_date_from = request.getParameter("bill_date_from");
		if (bill_date_from == null) {
			bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
			request.setAttribute("bill_date_from", bill_date_from);
		}
		
		String bill_date_to = request.getParameter("bill_date_to");
		if (bill_date_to == null) {
			bill_date_to = DvDateHelper.getCurrentMonthLastDate();
			request.setAttribute("bill_date_to", bill_date_to);
		}

		List<Map<String, Object>> beans = service.reportGroupByGoods(city_id, shop_id, bill_date_from, bill_date_to);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("bill_date_from", bill_date_from);
		dataMap.put("bill_date_to", bill_date_to);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByGoods.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
}
