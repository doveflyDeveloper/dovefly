package com.deertt.module.fund.refund.web;

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

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.fund.refund.service.IRefundService;
import com.deertt.module.fund.refund.util.IRefundConstants;
import com.deertt.module.fund.refund.vo.RefundVo;
import com.deertt.module.sys.dict.DvDictionaryFactory;
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
@RequestMapping("/refundController")
public class RefundController extends DvBaseController implements IRefundConstants {
	
	@Autowired
	protected IRefundService service;
	
	/**
	 * 支付处理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/deal/{id}")
	public String deal(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		String redirect = "";
		RefundVo bean = service.find(id);
		if (RefundVo.STATUS_UNDEAL.equals(bean.getStatus())) {//未处理状态
			if (RefundVo.REFUND_TO_WXPAY.equals(bean.getRefund_to())) {//退款到微信
				redirect = "redirect:/pay/toWxpayRefund/" + bean.getBill_code();
			} else if (RefundVo.REFUND_TO_ALIPAY.equals(bean.getRefund_to())) {//退款到支付宝
				redirect = "redirect:/pay/toAlipayRefund/" + bean.getBill_code();
			} else {
				throw new BusinessException("不支持的退款类型");
			}
		}
		return redirect;
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
	public String detail(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		RefundVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailRefund";
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
		List<RefundVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		
		List<Map<String, Object>> countMapList = service.reportCountGroupByStatus(queryCondition.replaceAll("status ='\\w+'", "1=1"));
		Map<String, Object> countMap = new HashMap<String, Object>();
		for (Map<String, Object> map : countMapList) {
			countMap.put((String)map.get("status") + "", map.get("bill_count"));
		}
		countMap.put("", countMap.get("null"));//汇总行
		request.setAttribute(REQUEST_RADIO_BUTTON_DATA_MAP, countMap);//DvRadioButtonTag自定义标签里用
		
		return JSP_PREFIX + "/listRefund";
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
		return query(request);
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("退货申请_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<RefundVo> beans = (List<RefundVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (RefundVo o : beans) {
			o.setPay_status(dic.getDictData("DIC_REFUND_PAY_STATUS", o.getPay_status()));
			o.setStatus(dic.getDictData("DIC_REFUND_STATUS", o.getStatus()));
		    o.setRefund_to(dic.getDictData("DIC_PAY_TYPE",o.getRefund_to()));
		}

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportList.xls";
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
			if (LoginHelper.isCityManagerRole() || LoginHelper.isShopkeeperRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("buyer_id", LoginHelper.getStoreId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			lQuery.add(DvSqlHelper.buildQueryStr("refer_bill_code", request.getParameter("refer_bill_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_code", request.getParameter("bill_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("refund_time", request.getParameter("refund_time_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("refund_time", request.getParameter("refund_time_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("refund_to", request.getParameter("refund_to"), DvSqlHelper.TYPE_CHAR_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("pay_status", request.getParameter("pay_status"), DvSqlHelper.TYPE_CUSTOM, "= '", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
