package com.deertt.module.trade.bill.web;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.trade.bill.service.ITradeBillService;
import com.deertt.module.trade.bill.util.ITradeBillConstants;
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
@RequestMapping("/tradeBillController")
public class TradeBillController extends DvBaseController implements ITradeBillConstants {
	
	@Autowired
	protected ITradeBillService service;
	
	/**
	 * 查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/detail/{id}")
	public String detail(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		TradeBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailTradeBill";
	}
	
	/**
	 * 订单发货（订单变已完结状态+资金清算）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/send/{id}")
	public String send(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.deliver(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
//	/**
//	 * 订单取消（订单变已取消状态+退款）
//	 * 
//	 * @param request
//	 * @return
//	 * @throws Exception
//	 */
//	@RequiresPermissions(PERM_WRITE)
//	@RequestMapping("/cancel/{id}")
//	public String cancel(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
//		service.cancel(id);
//		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
//	}
	
	/**
	 * 确认退款
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/agreeRefund/{id}")
	public String agreeRefund(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.agreeRefund(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 同意退货
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/agreeReturnGoods/{id}")
	public String agreeReturnGoods(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.agreeReturn(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 拒绝退货
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/denyReturnGoods/{id}")
	public String denyReturnGoods(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.denyReturn(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 打印
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/print/{id}")
	public String print(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		TradeBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/printTradeBill";
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
		String orderStr = "id desc";
		List<TradeBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		
		List<Map<String, Object>> countMapList = service.reportCountGroupByStatus(queryCondition.replaceAll("status ='\\w+'", "1=1"));
		Map<String, Object> countMap = new HashMap<String, Object>();
		for (Map<String, Object> map : countMapList) {
			countMap.put((String)map.get("status") + "", map.get("bill_count"));
		}
		countMap.put("", countMap.get("null"));//汇总行
		request.setAttribute(REQUEST_RADIO_BUTTON_DATA_MAP, countMap);//DvRadioButtonTag自定义标签里用
		
		return JSP_PREFIX + "/listTradeBill";
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
		request.setAttribute("requestFrom", "menu");
		return query(request);
	}
	
	/**
	 * 查询支付状态
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/checkPayStatus/{id}", produces={"application/json;charset=UTF-8"})
	public Object checkPayStatus(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		HandleResult result = new HandleResult();
		TradeBillVo bean = service.find(id);
		result.setSuccess(TradeBillVo.PAY_STATUS_SUCCESS.equals(bean.getPay_status()));
		return result;
	}
	
	/**
	 * 导出列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportList/{ids}")
	public void exportList(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长销售订单列表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<TradeBillVo> beans = (List<TradeBillVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (TradeBillVo t : beans) {
			t.setPay_type(dic.getDictData("DIC_PAY_TYPE", t.getPay_type()));
			t.setPay_status(dic.getDictData("DIC_PAY_STATUS", t.getPay_status()));
			t.setStatus(dic.getDictData("DIC_TRADE_BILL_STATUS", t.getStatus()));
		}

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportList.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 导出列表明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportListDetail/{ids}")
	public void exportListDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长销售订单明细_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
//		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		String queryCondition = getQueryCondition(request);
//		DvPageVo pageVo = super.transctPageVo(request, service.getRecordCount(queryCondition));
//		String orderStr = "id desc";
		List<Map<String, Object>> beans = service.queryListDetails(queryCondition);

//		List<Map<String, Object>> beans = (List<Map<String, Object>>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (Map<String, Object> m : beans) {
			m.put("pay_type", dic.getDictData("DIC_PAY_TYPE", m.get("pay_type")));
			m.put("pay_status", dic.getDictData("DIC_PAY_STATUS", m.get("pay_status")));
			m.put("status", dic.getDictData("DIC_TRADE_BILL_STATUS", m.get("status")));
		}

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportListDetail.xls";
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
			if (LoginHelper.isHeadquartersRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", request.getParameter("city_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isCityManagerRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", LoginHelper.getCityId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isOperationManagerRole()) {
				//性能有点问题，暂时先这样
				lQuery.add("shop_id IN (SELECT id FROM sys_shop WHERE manager_id = " + LoginHelper.getUserId() + ")");
			} else if (LoginHelper.isShopkeeperRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("shop_id", LoginHelper.getUserId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			if("menu".equals(request.getAttribute("requestFrom"))){
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
				lQuery.add(DvSqlHelper.buildQueryStr("bill_date", bill_date_from, DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
				lQuery.add(DvSqlHelper.buildQueryStr("bill_date", bill_date_to, DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			}
			lQuery.add(DvSqlHelper.buildQueryStr("bill_code", request.getParameter("bill_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("school_name", request.getParameter("school_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("shop_id", request.getParameter("shop_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("buyer_name", request.getParameter("buyer_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
