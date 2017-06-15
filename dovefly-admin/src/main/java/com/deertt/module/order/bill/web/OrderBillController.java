package com.deertt.module.order.bill.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.order.bill.service.IOrderBillService;
import com.deertt.module.order.bill.util.IOrderBillConstants;
import com.deertt.module.order.bill.vo.OrderBillVo;
import com.deertt.module.order.bill.vo.OrderDetailVo;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.shop.service.IShopService;
import com.deertt.module.sys.shop.vo.ShopVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;
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
@RequestMapping("/orderBillController")
public class OrderBillController extends DvBaseController implements IOrderBillConstants {
	
	@Autowired
	protected IOrderBillService service;
	
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected IShopService shopService;
	
	@Autowired
	protected IWarehouseService warehouseService;
	
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
		OrderBillVo bean = new OrderBillVo();
		bean.setCity_id(LoginHelper.getCityId());
		bean.setCity_name(LoginHelper.getCityName());
		bean.setWarehouse_id(LoginHelper.getShop().getWarehouse_id());
		bean.setWarehouse_name(LoginHelper.getShop().getWarehouse_name());
		bean.setShop_id(LoginHelper.getShopId());
		bean.setShop_name(LoginHelper.getShopName());
		bean.setBill_date(new java.sql.Date(System.currentTimeMillis()));
		bean.setBill_time(DvDateHelper.getSysTimestamp());
		bean.setReal_amount(BigDecimal.ZERO);
		bean.setTotal_amount(BigDecimal.ZERO);
		bean.setIncome_amount(BigDecimal.ZERO);
		bean.setRcv_name(LoginHelper.getUser().getReal_name());
		bean.setRcv_mobile(LoginHelper.getUser().getMobile());
		bean.setRcv_address(LoginHelper.getUser().getAddress());
		request.setAttribute(REQUEST_BEAN, bean);
		
		ShopVo shop = shopService.find(bean.getShop_id());
		request.setAttribute("shop", shop);
		
		BigDecimal start_amount = BigDecimal.ZERO;
		if (LoginHelper.isShopkeeperRole()) {
			start_amount = warehouseService.find(bean.getWarehouse_id()).getStart_amount();
		}
		
		request.setAttribute("start_amount", start_amount);
		
		return JSP_PREFIX + "/insertOrderBill";
	}
	
	/**
	 * 订单提交，锁定卖家库存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/saveAndSubmit")
	public String saveAndSubmit(HttpServletRequest request, OrderBillVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
        String pay_type = request.getParameter("pay_type");
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		vo.setSchool_id(LoginHelper.getSchoolId());
		vo.setSchool_name(LoginHelper.getSchoolName());
		vo.setManager_id(LoginHelper.getManagerId());
		vo.setManager_name(LoginHelper.getManagerName());
		vo.setWarehouse_id(LoginHelper.getShop().getWarehouse_id());
		vo.setWarehouse_name(LoginHelper.getShop().getWarehouse_name());
		vo.setShop_id(LoginHelper.getShopId());
		vo.setShop_name(LoginHelper.getShopName());
		vo.setBill_code(service.generateBillCode(OrderBillVo.BILL_CODE_PREFIX));
		vo.setBill_date(new java.sql.Date(System.currentTimeMillis()));
		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setPay_type(pay_type);
		vo.setStatus(OrderBillVo.STATUS_EDIT);
		
		//如果使用汀豆，从买家账号扣除
		if (vo.getUse_coin_quantity() > 0) {
			userService.reduceCoin_quantity(shopService.find(vo.getShop_id()).getShopkeeper_id(), vo.getUse_coin_quantity());
		}
	    
		vo.setDetails(wrapDetails(request));
		service.makeOrder(vo);
		return redirectWithTip(CONTROLLER + "/detail/" + vo.getId(), attr);
	}

	/**
	 * 从Request中封装订单明细列表数据
	 * @param request
	 * @return
	 */
	private List<OrderDetailVo> wrapDetails(HttpServletRequest request) {
		List<OrderDetailVo> list = new ArrayList<OrderDetailVo>();
		String[] detail_ids = request.getParameterValues("detail_id");
		String[] goods_ids = request.getParameterValues("goods_id");
		String[] goods_images = request.getParameterValues("goods_image");
		String[] goods_names = request.getParameterValues("goods_name");
		String[] goods_sale_prices = request.getParameterValues("goods_sale_price");
		String[] goods_quantitys = request.getParameterValues("goods_quantity");
		if (detail_ids != null && detail_ids.length > 0) {
			for (int i = 0; i < detail_ids.length; i++) {
				OrderDetailVo detail = new OrderDetailVo();
				detail.setId(StringUtils.isEmpty(detail_ids[i]) ? null : Integer.parseInt(detail_ids[i]));
				detail.setBill_id(null);
				detail.setGoods_id(StringUtils.isEmpty(goods_ids[i]) ? null : Integer.parseInt(goods_ids[i]));
				detail.setGoods_name(goods_names[i]);
				detail.setGoods_image(goods_images[i]);
				detail.setSale_price(StringUtils.isEmpty(goods_sale_prices[i]) ? BigDecimal.ZERO : new BigDecimal(goods_sale_prices[i]));
				detail.setQuantity(StringUtils.isEmpty(goods_quantitys[i]) ? BigDecimal.ZERO : new BigDecimal(goods_quantitys[i]));
				detail.setSub_total(detail.getSale_price().multiply(detail.getQuantity()));
				detail.setStatus(DV_YES);
				list.add(detail);
			}
		}
		return list;
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
		OrderBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		
		if (LoginHelper.isCityManagerRole()) {
			String queryCondition = "city_id = " + LoginHelper.getCityId() + " and status = '" + OrderBillVo.STATUS_SUBMIT + "' and id < " + bean.getId();
			
			List<OrderBillVo> list = service.queryByCondition(queryCondition, "id desc", 0, 1);
			if (list != null && list.size() > 0) {
				request.setAttribute("nextBean", list.get(0));
			}
		}
		
		BigDecimal start_amount = warehouseService.find(bean.getWarehouse_id()).getStart_amount();
		request.setAttribute("start_amount", start_amount);
		
		ShopVo shop = shopService.find(bean.getShop_id());
		request.setAttribute("shop", shop);
		
		return JSP_PREFIX + "/detailOrderBill";
	}
	
	/**
	 * 货到付款订单提交
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/codPayAndSubmit/{id}")
	public String codPayAndSubmit(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.codPayAndSubmit(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 订单发货处理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/send/{id}")
	public String send(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, OrderBillVo vo, RedirectAttributes attr) throws Exception {
		OrderBillVo bean = service.findFull(id);
		service.deliver(bean);
		return redirectWithTip(CONTROLLER + "/detail/" + bean.getId(), attr);
	}
	
	/**
	 * 订单确认收货
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/receive/{id}")
	public String receive(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.receive(id, false);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 打印页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/print/{id}")
	public String print(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		OrderBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/printOrderBill";
	}
	
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
	 * 确认退货
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
		List<OrderBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		
		List<Map<String, Object>> countMapList = service.reportCountGroupByStatus(queryCondition.replaceAll("status ='\\w+'", "1=1"));
		Map<String, Object> countMap = new HashMap<String, Object>();
		for (Map<String, Object> map : countMapList) {
			countMap.put((String)map.get("status") + "", map.get("bill_count"));
		}
		countMap.put("", countMap.get("null"));//汇总行
		request.setAttribute(REQUEST_RADIO_BUTTON_DATA_MAP, countMap);//DvRadioButtonTag自定义标签里用
		return JSP_PREFIX + "/listOrderBill";
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
		OrderBillVo bean = service.find(id);
		HandleResult result = new HandleResult();
		result.setSuccess(OrderBillVo.PAY_STATUS_SUCCESS.equals(bean.getPay_status()));
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长进货订单列表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<OrderBillVo> beans = (List<OrderBillVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (OrderBillVo o : beans) {
			o.setPay_type(dic.getDictData("DIC_PAY_TYPE", o.getPay_type()));
			o.setPay_status(dic.getDictData("DIC_PAY_STATUS", o.getPay_status()));
			o.setRefund_type(dic.getDictData("DIC_PAY_TYPE", o.getRefund_type()));
			o.setRefund_status(dic.getDictData("DIC_ORDER_BILL_REFUND_STATUS", o.getRefund_status()));
			o.setStatus(dic.getDictData("DIC_ORDER_BILL_STATUS", o.getStatus()));
			o.setUse_coin_amount(o.getUse_coin_amount() == null ? BigDecimal.ZERO : o.getUse_coin_amount());
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长进货订单明细_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
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
			m.put("refund_type", dic.getDictData("DIC_PAY_TYPE", m.get("refund_type")));
			m.put("refund_status", dic.getDictData("DIC_ORDER_BILL_REFUND_STATUS", m.get("refund_status")));
			m.put("status", dic.getDictData("DIC_ORDER_BILL_STATUS", m.get("status")));
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
				lQuery.add(DvSqlHelper.buildQueryStr("warehouse_id", LoginHelper.getWarehouseId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isOperationManagerRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("manager_id", LoginHelper.getUserId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isShopkeeperRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("shop_id", LoginHelper.getShopId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			if ("menu".equals(request.getAttribute("requestFrom"))){
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
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
}
