package com.deertt.module.purchase.bill.web;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.purchase.bill.service.IPurchaseBillService;
import com.deertt.module.purchase.bill.util.IPurchaseBillConstants;
import com.deertt.module.sys.dict.DvDictionaryFactory;
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
@RequestMapping("/purchaseBillReportController")
public class PurchaseBillReportController extends DvBaseController implements IPurchaseBillConstants {
	
	@Autowired
	protected IPurchaseBillService service;
	
	/**
	 * 城市经理统计各个店长的采购订单数量及金额
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/reportGroupByWarehouse")
	public String reportGroupByWarehouse(HttpServletRequest request) throws Exception {
		
		Integer warehouse_id = null;
		if (LoginHelper.isCityManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
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
		
		List<Map<String, Object>> beans = service.reportGroupByWarehouse(warehouse_id, bill_date_from, bill_date_to);
		
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reportGroupByWarehouse";
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportGroupByWarehouse/{ids}")
	public void exportGroupByWarehouse(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("采购订单统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");
		
		Integer warehouse_id = null;
		if (LoginHelper.isCityManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
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
		
		List<Map<String, Object>> beans = service.reportGroupByWarehouse(warehouse_id, bill_date_from, bill_date_to);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("warehouse_name", warehouse_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_WAREHOUSE", warehouse_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByWarehouse.xls";
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
		
		Integer warehouse_id = null;
		if (LoginHelper.isCityManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
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

		List<Map<String, Object>> beans = service.reportGroupByGoods(warehouse_id, bill_date_from, bill_date_to);
		
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("采购商品统计_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Integer warehouse_id = null;
		if (LoginHelper.isCityManagerRole()) {
			warehouse_id = LoginHelper.getWarehouseId();
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

		List<Map<String, Object>> beans = service.reportGroupByGoods(warehouse_id, bill_date_from, bill_date_to);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("bill_date_from", bill_date_from);
		dataMap.put("bill_date_to", bill_date_to);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportGroupByGoods.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
}
