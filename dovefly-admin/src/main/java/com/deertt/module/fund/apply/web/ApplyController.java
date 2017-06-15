package com.deertt.module.fund.apply.web;

import java.io.File;
import java.math.BigDecimal;
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

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.fund.apply.service.IApplyService;
import com.deertt.module.fund.apply.util.IApplyConstants;
import com.deertt.module.fund.apply.vo.ApplyVo;
import com.deertt.module.fund.transition.service.ITransitionService;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;
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
@RequestMapping("/applyController")
public class ApplyController extends DvBaseController implements IApplyConstants {
	
	@Autowired
	protected IApplyService service;
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected ITransitionService transitionService;
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/add", produces={"application/json;charset=UTF-8"})
	public Object add(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		try {
			String receive_account = request.getParameter("receive_account");
			String receive_real_name = request.getParameter("receive_real_name");
			String apply_to = request.getParameter("apply_to");
			BigDecimal apply_amount = new BigDecimal(request.getParameter("apply_amount"));
			if (apply_amount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new BusinessException("提现金额必须大于零！");
			}
			
			if (ApplyVo.APPLY_TO_WXPAY.equals(apply_to)) {
				receive_account = LoginHelper.getUser().getWechat_id();
			}
			
			if (LoginHelper.isCityManagerRole()) {
				service.addWApply(LoginHelper.getWarehouseId(), LoginHelper.getWarehouseName(), LoginHelper.getUserId(), LoginHelper.getUserName(), receive_account, receive_real_name, apply_to, apply_amount);
			} else if (LoginHelper.isShopkeeperRole()) {
				service.addSApply(LoginHelper.getShopId(), LoginHelper.getShopName(), LoginHelper.getUserId(), LoginHelper.getUserName(), receive_account, receive_real_name, apply_to, apply_amount);
			} else if (LoginHelper.isMarketSellerRole()) {
				service.addMApply(LoginHelper.getMarketId(), LoginHelper.getMarketName(), LoginHelper.getUserId(), LoginHelper.getUserName(), receive_account, receive_real_name, apply_to, apply_amount);
			}
			
			UserVo user = userService.findFull(LoginHelper.getUserId());
			
			result.setSuccess(true);
			result.setMessage("提现申请提交成功，系统将在24小时内处理完成，请您耐心等待！");
			result.setData(user);
			LoginHelper.setUser(user);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return result;
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
		ApplyVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailApply";
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/checkAccount/{id}", produces={"application/json;charset=UTF-8"})
	public Object checkAccount(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		HandleResult result = new HandleResult();
		String msg = "";
		ApplyVo bean = service.find(id);
		UserVo user = userService.findFull(bean.getUser_id());
		Map<String, Object> map = null;
		if (user.isShopkeeperRole()) {
			map = transitionService.queryForMap(SQL_CHECK_AMOUNT_SHOP.replaceAll("#shop_id#", bean.getStore_id().toString()));
			
			boolean balanceAmountEqualsAllAmount = map.get("real_amount").toString().equals(map.get("all_amount").toString());
			msg += "用户当前总金额(" + map.get("real_amount") + ")<br>"
					+ " " + (balanceAmountEqualsAllAmount ? "=" : "<span class='left_ts'>≠</span>") + " <br>"
					+ " + 充值金额(" + map.get("recharge_amount") 
					+ ")<br> + 店长销售收入(" + map.get("trade_amount") 
					+ ")<br> + 店长退货回款(" + map.get("order_back_amount") 
					+ ")<br> + 店长进货订单取消回款(" + map.get("order_refund_amount") 
					+ ")<br> - 店长进货支出(" + map.get("order_amount") 
					+ ")<br> - 已提现金额(" + map.get("withdraw_amount") 
					+ ")<br> - 还息金额(" + map.get("interest_amount") 
					+ ")<br>";
			
			if (balanceAmountEqualsAllAmount) {
				msg += "用户资金<span class='left_ts'>正常</span>";
				result.setSuccess(true);
				result.setMessage(msg);
			} else {
				msg += "用户资金<span class='left_ts'>异常</span>，暂时不能提现，请及时联系系统管理员！";
				result.setSuccess(false);
				result.setMessage(msg);
			}
			
		} else if (user.isCityManagerRole()) {
			if (user.getCity_id() == 7) {//仅支持遵义城市经理提现
				map = transitionService.queryForMap(SQL_CHECK_AMOUNT_CITYMANAGER.replaceAll("#user_id#", user.getId().toString()));
				
				boolean balanceAmountEqualsAllAmount = map.get("real_amount").toString().equals(map.get("all_amount").toString());
				msg += "用户当前总金额(" + map.get("real_amount") + ")<br>"
						+ " " + (balanceAmountEqualsAllAmount ? "=" : "<span class='left_ts'>≠</span>") + " <br>"
						+ " + 充值金额(" + map.get("recharge_amount") 
						+ ")<br> + 经理直销收入(" + map.get("trade_amount") 
						+ ")<br> - 店长退货支出(" + map.get("order_back_amount") 
						+ ")<br> + 店长进货营收(" + map.get("order_amount") 
						+ ")<br> - 已提现金额(" + map.get("withdraw_amount") 
						+ ")<br> - 代付优惠券金额(" + map.get("coupon_amount") 
						+ ")<br> - 还息金额(" + map.get("interest_amount") 
						+ ")<br>";
				
				if (balanceAmountEqualsAllAmount) {
					msg += "用户资金<span class='left_ts'>正常</span>";
					result.setSuccess(true);
					result.setMessage(msg);
				} else {
					msg += "用户资金<span class='left_ts'>异常</span>，暂时不能提现，请及时联系系统管理员！";
					result.setSuccess(false);
					result.setMessage(msg);
				}
				
			} else {
				msg += "目前系统暂不支持城市经理提现！";
				result.setSuccess(false);
				result.setMessage(msg);
			}
			
		} else {
			msg += "目前系统仅支持店长提现！";
			result.setSuccess(false);
			result.setMessage(msg);
		}
		return result;
	}
	
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
		ApplyVo bean = service.find(id);
		if (ApplyVo.STATUS_UNDEAL.equals(bean.getStatus())) {//未处理状态
			if (ApplyVo.APPLY_TO_WXPAY.equals(bean.getApply_to())) {//提现到微信
				redirect = "redirect:/pay/toWxpayBatchPay/" + bean.getBill_code();
			} else if (ApplyVo.APPLY_TO_ALIPAY.equals(bean.getApply_to())) {//提现到支付宝
				redirect = "redirect:/pay/toAlipayBatchPay/" + bean.getBill_code();
			} else {
				throw new BusinessException("不支持的提现类型！");
			}
		}
		return redirect;
	}
		
	/**
	 * 拒绝
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/deny/{id}")
	public String deny(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		String reason = request.getParameter("reason");
		service.deny(id, reason);
		return redirectWithTip("/applyController/detail/" + id, attr);
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
		List<ApplyVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		
		List<Map<String, Object>> countMapList = service.reportCountGroupByStatus(queryCondition.replaceAll("status ='\\w+'", "1=1"));
		Map<String, Object> countMap = new HashMap<String, Object>();
		for (Map<String, Object> map : countMapList) {
			countMap.put((String)map.get("status") + "", map.get("bill_count"));
		}
		countMap.put("", countMap.get("null"));//汇总行
		request.setAttribute(REQUEST_RADIO_BUTTON_DATA_MAP, countMap);//DvRadioButtonTag自定义标签里用
		
		return JSP_PREFIX + "/listApply";
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("提现申请_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<ApplyVo> beans = (List<ApplyVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (ApplyVo o : beans) {
			o.setPay_status(dic.getDictData("DIC_FUND_APPLY_PAY_STATUS", o.getPay_status()));
			o.setStatus(dic.getDictData("DIC_FUND_APPLY_STATUS", o.getStatus()));
			o.setApply_to(dic.getDictData("DIC_PAY_TYPE", o.getApply_to()));
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
				lQuery.add(DvSqlHelper.buildQueryStr("store_id", LoginHelper.getStoreId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			
			lQuery.add(DvSqlHelper.buildQueryStr("user_name", request.getParameter("user_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("apply_time", request.getParameter("apply_time_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("apply_time", request.getParameter("apply_time_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
