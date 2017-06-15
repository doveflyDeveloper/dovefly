package com.deertt.module.report.web;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.fund.apply.service.IApplyService;
import com.deertt.module.fund.apply.vo.ApplyVo;
import com.deertt.module.fund.interest.service.IInterestService;
import com.deertt.module.fund.interest.vo.InterestVo;
import com.deertt.module.fund.recharge.service.IRechargeService;
import com.deertt.module.fund.recharge.vo.RechargeVo;
import com.deertt.module.fund.refund.service.IRefundService;
import com.deertt.module.fund.refund.vo.RefundVo;
import com.deertt.module.fund.transition.service.ITransitionService;
import com.deertt.module.fund.transition.vo.TransitionVo;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.purchase.bill.service.IPurchaseBillService;
import com.deertt.module.report.service.IReportService;
import com.deertt.module.report.util.IReportConstants;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.shop.vo.ShopVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.vo.TradeBillVo;
import com.deertt.utils.helper.DvSqlHelper;
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
@RequestMapping("/reportController")
public class ReportController extends DvBaseController implements IReportConstants {
	
	@Autowired
	protected IReportService service;
	
	@Autowired
	protected IPurchaseBillService purchaseService;
	
	@Autowired
	protected IOrderBillService orderService;
	
	@Autowired
	protected ITradeBillService tradeService;
	
	@Autowired
	protected IApplyService applyService;
	
	@Autowired
	protected IRefundService refundService;
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected IShopService shopService;
	
	@Autowired
	protected IInterestService interestService;
	
	@Autowired
	protected ITransitionService transitionService; 
	
	@Autowired
	protected IRechargeService serviceRecharge;
	
	/**
	 * 总部人员首页图标数据汇总
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/reportByHeadquarters", produces={"application/json;charset=UTF-8"})
	public Object reportByHeadquarters(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		
		String bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		String bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> purchase_group_by_city = purchaseService.reportGroupByCity(null, bill_date_from, bill_date_to);
		map.put("purchase_group_by_city", purchase_group_by_city);
		
		List<Map<String, Object>> purchase_group_by_date = purchaseService.reportGroupByBillDate(bill_date_from, bill_date_to);
		map.put("purchase_group_by_date", purchase_group_by_date);
		
		List<Map<String, Object>> order_group_by_city = orderService.reportGroupByCity(null, bill_date_from, bill_date_to);
		map.put("order_group_by_city", order_group_by_city);
		
		List<Map<String, Object>> order_group_by_date = orderService.reportGroupByBillDate(null, bill_date_from, bill_date_to);
		map.put("order_group_by_date", order_group_by_date);
		
		List<Map<String, Object>> trade_group_by_city = tradeService.reportGroupByCity(null, bill_date_from, bill_date_to);
		map.put("trade_group_by_city", trade_group_by_city);
		
		List<Map<String, Object>> trade_group_by_date = tradeService.reportGroupByBillDate(null, bill_date_from, bill_date_to);
		map.put("trade_group_by_date", trade_group_by_date);
		
		result.setData(map);
		result.setSuccess(true);
		
		return result;
	}
	
	/**
	 * 城市经理首页图标数据汇总
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/reportByCityManager", produces={"application/json;charset=UTF-8"})
	public Object reportByCityManager(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		
		Integer city_id = LoginHelper.getCityId();
		String bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		String bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> order_group_by_city = orderService.reportGroupByCity(city_id, bill_date_from, bill_date_to);
		map.put("order_group_by_city", order_group_by_city);
		
		List<Map<String, Object>> order_group_by_date = orderService.reportGroupByBillDate(city_id, bill_date_from, bill_date_to);
		map.put("order_group_by_date", order_group_by_date);
		
		List<Map<String, Object>> trade_group_by_city = tradeService.reportGroupByCity(city_id, bill_date_from, bill_date_to);
		map.put("trade_group_by_city", trade_group_by_city);
		
		List<Map<String, Object>> trade_group_by_date = tradeService.reportGroupByBillDate(city_id, bill_date_from, bill_date_to);
		map.put("trade_group_by_date", trade_group_by_date);
		
		result.setData(map);
		result.setSuccess(true);
		
		return result;
	}
	
	/**
	 * 城市经理首页图标数据汇总
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/reportByOperationManager", produces={"application/json;charset=UTF-8"})
	public Object reportByOperationManager(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		
		Integer city_id = LoginHelper.getCityId();
		Integer manager_id = LoginHelper.getUserId();
		String bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		String bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> order_group_by_date = orderService.reportManagerGroupByBillDate(city_id, manager_id, bill_date_from, bill_date_to);
		map.put("order_group_by_date", order_group_by_date);
		
		List<Map<String, Object>> trade_group_by_date = tradeService.reportManagerGroupByBillDate(city_id, manager_id, bill_date_from, bill_date_to);
		map.put("trade_group_by_date", trade_group_by_date);
		
		result.setData(map);
		result.setSuccess(true);
		
		return result;
	}
	
	/**
	 * 城市经理首页图标数据汇总
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/reportByShopkeeper", produces={"application/json;charset=UTF-8"})
	public Object reportByShopkeeper(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		
		Integer city_id = LoginHelper.getCityId();
		Integer shop_id = LoginHelper.getShopId();
		String bill_date_from = DvDateHelper.getCurrentMonthFirstDate();
		String bill_date_to = DvDateHelper.getCurrentMonthLastDate();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> order_group_by_date = orderService.reportShopGroupByBillDate(city_id, shop_id, bill_date_from, bill_date_to);
		map.put("order_group_by_date", order_group_by_date);
		
		List<Map<String, Object>> trade_group_by_date = tradeService.reportShopGroupByBillDate(city_id, shop_id, bill_date_from, bill_date_to);
		map.put("trade_group_by_date", trade_group_by_date);
		
		result.setData(map);
		result.setSuccess(true);
		
		return result;
	}
	
	/**
	 * 店长贷款及利息流水明细查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reportShopkeeperInterest")
	public String reportShopkeeperInterest(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String queryCondition = getQueryCondition(request);
		String queryShopStr = StringUtils.isEmpty(queryCondition) ? "loan_amount > 0" : "loan_amount > 0 AND " + queryCondition.replaceAll("shop_id", "id");
		String queryInterestStr = queryCondition;
		String queryTransitionStr = StringUtils.isEmpty(queryCondition) ? "remark = 'INTEREST' AND store_type = 's'" : "remark = 'INTEREST' AND store_type = 's' AND " + queryCondition.replaceAll("shop_id", "store_id").replaceAll("shop_name", "store_name");
		
		List<ShopVo> shops = shopService.queryByCondition(queryShopStr, null);
		map.put("shops", shops);
		
		List<InterestVo> interests = interestService.queryByCondition(queryInterestStr, null);
		map.put("interests", interests);
		
		List<TransitionVo> transitions = transitionService.queryByCondition(queryTransitionStr, null);
		map.put("transitions", transitions);
		
		request.setAttribute("data", map);
		return JSP_PREFIX + "/reportShopkeeperInterest";
	}

	/**
	 * 店长贷款及利息流水明细下载
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportShopkeeperInterest")
	public void exportShopkeeperInterest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长贷款_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
        Map<String, Object> map = new HashMap<String, Object>();
		
		String queryCondition = getQueryCondition(request);
		String queryShopStr = StringUtils.isEmpty(queryCondition) ? "loan_amount > 0" : "loan_amount > 0 AND " + queryCondition.replaceAll("shop_id", "id");
		String queryInterestStr = queryCondition;
		String queryTransitionStr = StringUtils.isEmpty(queryCondition) ? "remark = 'INTEREST' AND store_type = 's'" : "remark = 'INTEREST' AND store_type = 's' AND " + queryCondition.replaceAll("shop_id", "store_id").replaceAll("shop_name", "store_name");
		
		List<ShopVo> shops = shopService.queryByCondition(queryShopStr, null);
		map.put("shops", shops);
		
		List<InterestVo> interests = interestService.queryByCondition(queryInterestStr, null);
		map.put("interests", interests);
		
		List<TransitionVo> transitions = transitionService.queryByCondition(queryTransitionStr, null);
		map.put("transitions", transitions);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", map);
			
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportShopkeeperInterest.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 店长充值记录查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reportShopkeeperRecharge")
	public String reportShopkeeperRecharge(HttpServletRequest request) throws Exception {
		
		String queryCondition = getQueryCondition(request);
		String queryRechargeStr = queryCondition;
		
		List<RechargeVo> beans = serviceRecharge.queryByCondition(queryRechargeStr, null);
		
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reportShopkeeperRecharge";
	}

	/**
	 * 店长贷款及利息流水明细下载
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportShopkeeperRecharge")
	public void exportShopkeeperRecharge(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长贷款_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		String queryCondition = getQueryCondition(request);
		
		List<RechargeVo> beans = serviceRecharge.queryByCondition(queryCondition, null);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (RechargeVo r : beans) {
			r.setPay_status(dic.getDictData("DIC_RECHARGE_STATUS", r.getPay_status()));
			r.setPay_type(dic.getDictData("DIC_PAY_TYPE", r.getPay_type()));
		}
			
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportShopkeeperRecharge.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
}
	
	/**
	 * 店长对账查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reportShopkeeperCheckBill")
	public String reportShopkeeperCheckBill(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String c = request.getParameter("city_id");
		Integer city_id = StringUtils.isEmpty(c) ? null : Integer.parseInt(c);
		
		String s = request.getParameter("shop_id");
		Integer shop_id = StringUtils.isEmpty(s) ? null : Integer.parseInt(s);
		
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
		
		String queryTradeStr = "city_id = #city_id# AND shop_id = #shop_id# AND pay_status = '1' AND pay_time >= '" + bill_date_from + " 00:00:00' AND pay_time <= '" + bill_date_to + " 23:59:59'";
		if (city_id != null) {
			queryTradeStr = queryTradeStr.replace("#city_id#", city_id.toString());
		} else {
			queryTradeStr = queryTradeStr.replace("city_id = #city_id# AND ", "");
		}
		if (shop_id != null) {
			queryTradeStr = queryTradeStr.replace("#shop_id#", shop_id.toString());
		} else {
			queryTradeStr = queryTradeStr.replace("shop_id = #shop_id# AND ", "");
		}
		List<TradeBillVo> trades = tradeService.queryByCondition(queryTradeStr, null);
		map.put("trades", trades);
		
		String queryApplyStr = "store_type = 's' AND store_id = #store_id# AND pay_status = '2' AND pay_time >= '" + bill_date_from + " 00:00:00' AND pay_time <= '" + bill_date_to + " 23:59:59'";
		if (shop_id != null) {
			queryApplyStr = queryApplyStr.replace("#store_id#", shop_id.toString());
		} else {
			queryApplyStr = queryApplyStr.replace("store_id = #store_id# AND ", "");
		}
		List<ApplyVo> applies = applyService.queryByCondition(queryApplyStr, null);
		map.put("applies", applies);
		
		String queryRefundStr = "bill_type = 't' AND seller_id = #seller_id# AND pay_status = '2' AND pay_time >= '" + bill_date_from + " 00:00:00' AND pay_time <= '" + bill_date_to + " 23:59:59'";
		if (shop_id != null) {
			queryRefundStr = queryRefundStr.replace("#seller_id#", shop_id.toString());
		} else {
			queryRefundStr = queryRefundStr.replace("seller_id = #seller_id# AND ", "");
		}
		List<RefundVo> refunds = refundService.queryByCondition(queryRefundStr, null);
		map.put("refunds", refunds);
		
		request.setAttribute("data", map);
		return JSP_PREFIX + "/reportShopkeeperCheckBill";
	}
	
	/**
	 * 店长对账查询下载
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportShopkeeperCheckBill")
	public void exportShopkeeperCheckBill(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长对账单_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String c = request.getParameter("city_id");
		Integer city_id = StringUtils.isEmpty(c) ? null : Integer.parseInt(c);
		
		String s = request.getParameter("shop_id");
		Integer shop_id = StringUtils.isEmpty(s) ? null : Integer.parseInt(s);
		
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
		
		String queryTradeStr = "city_id = #city_id# AND shop_id = #shop_id# AND pay_status = '1' AND pay_time >= '" + bill_date_from + " 00:00:00' AND pay_time <= '" + bill_date_to + " 23:59:59'";
		if (city_id != null) {
			queryTradeStr = queryTradeStr.replace("#city_id#", city_id.toString());
		} else {
			queryTradeStr = queryTradeStr.replace("city_id = #city_id# AND ", "");
		}
		if (shop_id != null) {
			queryTradeStr = queryTradeStr.replace("#shop_id#", shop_id.toString());
		} else {
			queryTradeStr = queryTradeStr.replace("shop_id = #shop_id# AND ", "");
		}
		List<TradeBillVo> trades = tradeService.queryByCondition(queryTradeStr, null);
		map.put("trades", trades);
		
		String queryApplyStr = "store_type = 's' AND store_id = #store_id# AND pay_status = '2' AND pay_time >= '" + bill_date_from + " 00:00:00' AND pay_time <= '" + bill_date_to + " 23:59:59'";
		if (shop_id != null) {
			queryApplyStr = queryApplyStr.replace("#store_id#", shop_id.toString());
		} else {
			queryApplyStr = queryApplyStr.replace("store_id = #store_id# AND ", "");
		}
		List<ApplyVo> applies = applyService.queryByCondition(queryApplyStr, null);
		map.put("applies", applies);
		
		String queryRefundStr = "bill_type = 't' AND seller_id = #seller_id# AND pay_status = '2' AND pay_time >= '" + bill_date_from + " 00:00:00' AND pay_time <= '" + bill_date_to + " 23:59:59'";
		if (shop_id != null) {
			queryRefundStr = queryRefundStr.replace("#seller_id#", shop_id.toString());
		} else {
			queryRefundStr = queryRefundStr.replace("seller_id = #seller_id# AND ", "");
		}
		List<RefundVo> refunds = refundService.queryByCondition(queryRefundStr, null);
		map.put("refunds", refunds);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (TradeBillVo trade : trades) {
			trade.setPay_type(dic.getDictData("DIC_PAY_TYPE", trade.getPay_type()));
			trade.setPay_status(dic.getDictData("DIC_PAY_STATUS", trade.getPay_status()));
			trade.setStatus(dic.getDictData("DIC_TRADE_BILL_STATUS", trade.getStatus()));
		}
		
		for (ApplyVo apply : applies) {
			apply.setPay_status(dic.getDictData("DIC_FUND_APPLY_PAY_STATUS", apply.getPay_status()));
		}
		
		for (RefundVo refund : refunds) {
			refund.setPay_status(dic.getDictData("DIC_REFUND_PAY_STATUS", refund.getPay_status()));
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", map);
		dataMap.put("city_name", city_id == null ? "全部" : DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", city_id));
		dataMap.put("bill_date_from", request.getParameter("bill_date_from"));
		dataMap.put("bill_date_to", request.getParameter("bill_date_to"));

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportShopkeeperCheckBill.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
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
			lQuery.add(DvSqlHelper.buildQueryStr("shop_id", request.getParameter("shop_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("shop_name", request.getParameter("shop_name"), DvSqlHelper.TYPE_CHAR_LIKE));
//			lQuery.add(DvSqlHelper.buildQueryStr("receive_id", request.getParameter("receive_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
//			lQuery.add(DvSqlHelper.buildQueryStr("receive_name", request.getParameter("receive_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
