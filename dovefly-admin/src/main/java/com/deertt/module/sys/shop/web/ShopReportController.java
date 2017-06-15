package com.deertt.module.sys.shop.web;

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

import com.alibaba.fastjson.JSONArray;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.office.excel.write.DvExcelWriter;


@Controller
@RequestMapping("/shopReportController")
public class ShopReportController extends ShopController {
     
	@Autowired 
	IShopService shopService;
	
	/**
	 * 店铺数据汇总
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping(value="/reportShop", produces={"application/json;charset=UTF-8"})
	public Object reportShop(HttpServletRequest request) throws Exception {
		Integer warehouse_id = null;
		Integer manager_id = null;
		String school_name = null;
		String manager_name = null;
		String s = request.getParameter("school_name");
		school_name = StringUtils.isEmpty(s) ? null : s;
		String d = request.getParameter("manager_name");
		manager_name = StringUtils.isEmpty(d) ? null : d;
		
		if (LoginHelper.isHeadquartersRole()) {
			String w = request.getParameter("warehouse_id");
			warehouse_id = StringUtils.isEmpty(w) ? null : Integer.parseInt(w);
		}
		
		if (LoginHelper.isCityManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
		}
		
		if (LoginHelper.isOperationManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
			manager_id = LoginHelper.getUserId();
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
				group_by = "warehouse";
			} else if (LoginHelper.isCityManagerRole()) {
				group_by = "manager";
			} else if (LoginHelper.isOperationManagerRole()) {
				group_by = "school";
			} 
		}
		request.setAttribute("group_by", group_by);
		
		List<Map<String, Object>> beans = null;
		if ("warehouse".equals(group_by)) {//货仓
			beans = shopService.reportGroupByWarehouse(warehouse_id, bill_date_from, bill_date_to);
		} else if ("manager".equals(group_by)) {//运营主管
			beans = shopService.reportGroupByManager(warehouse_id, manager_name, bill_date_from, bill_date_to);
		} else if ("school".equals(group_by)) {//学校
			beans = shopService.reportGroupBySchool(warehouse_id, manager_id, school_name, bill_date_from, bill_date_to);
		}
		
		request.setAttribute(REQUEST_BEANS, beans);
		request.setAttribute("chartData", JSONArray.toJSONString(beans));
		
		return JSP_PREFIX + "/reportShop";
      }
	
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportShop/{ids}")
	public void exportShop(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Integer warehouse_id = null;
		Integer manager_id = null;
		String school_name = null;
		String manager_name = null;
		String s = request.getParameter("school_name");
		school_name = StringUtils.isEmpty(s) ? null : s;
		String d = request.getParameter("manager_name");
		manager_name = StringUtils.isEmpty(d) ? null : d;
		
		if (LoginHelper.isHeadquartersRole()) {
			String w = request.getParameter("warehouse_id");
			warehouse_id = StringUtils.isEmpty(w) ? null : Integer.parseInt(w);
		}
		
		if (LoginHelper.isCityManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
		}
		
		if (LoginHelper.isOperationManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
			manager_id = LoginHelper.getUserId();
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
				group_by = "warehouse";
			} else if (LoginHelper.isCityManagerRole()) {
				group_by = "manager";
			} else if (LoginHelper.isOperationManagerRole()) {
				group_by = "school";
			} 
		}
		request.setAttribute("group_by", group_by);
		
		String templateFileName = null;
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		
		List<Map<String, Object>> beans = null;
		if ("warehouse".equals(group_by)) {//货仓
			beans = shopService.reportGroupByWarehouse(warehouse_id, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByWarehouse.xls";
		} else if ("manager".equals(group_by)) {//运营主管
			beans = shopService.reportGroupByManager(warehouse_id, manager_name, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByManager.xls";
		} else if ("school".equals(group_by)) {//学校
			beans = shopService.reportGroupBySchool(warehouse_id, manager_id, school_name, bill_date_from, bill_date_to);
			templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupBySchool.xls";
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("warehouse_name", warehouse_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_WAREHOUSE", warehouse_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));
		
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
}
