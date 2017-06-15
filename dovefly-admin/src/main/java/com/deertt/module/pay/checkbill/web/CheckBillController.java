package com.deertt.module.pay.checkbill.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.pay.checkbill.service.ICheckBillService;
import com.deertt.module.pay.checkbill.util.ICheckBillConstants;
import com.deertt.module.pay.checkbill.vo.CheckBillVo;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.string.DvStringHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Controller
@RequestMapping("/checkBillController")
public class CheckBillController extends DvBaseController implements ICheckBillConstants {
	
	@Autowired
	protected ICheckBillService service;
	
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
		CheckBillVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailCheckBill";
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
		List<CheckBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listCheckBill";
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
			lQuery.add(DvSqlHelper.buildQueryStr("pay_table_name", request.getParameter("pay_table_name"), DvSqlHelper.TYPE_CHAR_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("pay_bill_id", request.getParameter("pay_bill_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_type", request.getParameter("bill_type"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_table_name", request.getParameter("bill_table_name"), DvSqlHelper.TYPE_CHAR_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_id", request.getParameter("bill_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_code", request.getParameter("bill_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("check_status", request.getParameter("check_status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("deal_status", request.getParameter("deal_status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
