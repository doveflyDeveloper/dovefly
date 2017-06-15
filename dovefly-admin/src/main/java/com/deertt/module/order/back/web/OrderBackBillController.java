package com.deertt.module.order.back.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.order.back.service.IOrderBackBillService;
import com.deertt.module.order.back.util.IOrderBackBillConstants;
import com.deertt.module.order.back.vo.OrderBackBillVo;
import com.deertt.module.order.back.vo.OrderBackDetailVo;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.message.service.IMessageService;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.office.excel.write.DvExcelWriter;
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
@RequestMapping("/orderBackBillController")
public class OrderBackBillController extends DvBaseController implements IOrderBackBillConstants {
	
	@Autowired
	protected IOrderBackBillService service;
	
	@Autowired
	protected IMessageService messageService;
	
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
		OrderBackBillVo bean = new OrderBackBillVo();
		bean.setCity_id(LoginHelper.getCityId());
		bean.setCity_name(LoginHelper.getCityName());
		bean.setSchool_id(LoginHelper.getSchoolId());
		bean.setSchool_name(LoginHelper.getSchoolName());
		bean.setManager_id(LoginHelper.getManagerId());
		bean.setManager_name(LoginHelper.getManagerName());
		bean.setWarehouse_id(LoginHelper.getWarehouseId());
		bean.setWarehouse_name(LoginHelper.getWarehouseName());
		bean.setShop_id(LoginHelper.getShopId());
		bean.setShop_name(LoginHelper.getShopName());
//		bean.setBill_code(service.generateBillCode(BackBillVo.BILL_CODE_PREFIX));
//		bean.setBill_date(new java.sql.Date(System.currentTimeMillis()));
//		bean.setBill_time(DvDateHelper.getSysTimestamp());
//		request.setAttribute(REQUEST_BEAN, bean);
		bean.setAmount(BigDecimal.ZERO);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertOrderBackBill";
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
	public String find(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		OrderBackBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertOrderBackBill";
	}
	
	/**
	 * 提交订单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/saveAndSubmit")
	public String saveAndSubmit(HttpServletRequest request, OrderBackBillVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
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
		vo.setBill_code(service.generateBillCode(OrderBackBillVo.BILL_CODE_PREFIX));
		vo.setBill_date(DvDateHelper.getSysDate());
		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setPay_amount(BigDecimal.ZERO);
		vo.setPay_status(OrderBackBillVo.PAY_STATUS_NO);
		vo.setStatus(OrderBackBillVo.STATUS_EDIT);
		vo.setDetails(wrapDetails(request));
		service.saveAndSubmit(vo);
		return redirectWithTip(CONTROLLER + "/detail/" + vo.getId(), attr);
	}

	private List<OrderBackDetailVo> wrapDetails(HttpServletRequest request) {
		List<OrderBackDetailVo> list = new ArrayList<OrderBackDetailVo>();
		String[] detail_ids = request.getParameterValues("detail_id");
		String[] origin_ids = request.getParameterValues("origin_id");
		String[] goods_ids = request.getParameterValues("goods_id");
		String[] goods_images = request.getParameterValues("goods_image");
		String[] goods_names = request.getParameterValues("goods_name");
		String[] goods_unit_prices = request.getParameterValues("goods_unit_price");
		String[] goods_quantitys = request.getParameterValues("goods_quantity");
		if (detail_ids != null && detail_ids.length > 0) {
			for (int i = 0; i < detail_ids.length; i++) {
				OrderBackDetailVo detail = new OrderBackDetailVo();
				detail.setId(StringUtils.isEmpty(detail_ids[i]) ? null : Integer.parseInt(detail_ids[i]));
				detail.setBill_id(null);
				detail.setOrigin_id(StringUtils.isEmpty(origin_ids[i]) ? null : Integer.parseInt(origin_ids[i]));
				detail.setGoods_id(StringUtils.isEmpty(goods_ids[i]) ? null : Integer.parseInt(goods_ids[i]));
				detail.setGoods_name(goods_names[i]);
				detail.setGoods_image(goods_images[i]);
				detail.setUnit_price(StringUtils.isEmpty(goods_unit_prices[i]) ? BigDecimal.ZERO : new BigDecimal(goods_unit_prices[i]));
				detail.setQuantity(StringUtils.isEmpty(goods_quantitys[i]) ? BigDecimal.ZERO : new BigDecimal(goods_quantitys[i]));
				detail.setSub_total(detail.getUnit_price().multiply(detail.getQuantity()));
				detail.setStatus(DV_YES);
				list.add(detail);
			}
		}
		return list;		
	}
	
	/**
	 * 退货单确认收货
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/receive/{id}")
	public String receive(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.receive(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 退回
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/reject/{id}")
	public String reject(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.reject(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
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
	public String delete(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.deleteFull(id);
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
		service.deleteFull(DvStringHelper.parseStringToIntegerArray(ids, ","));
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
	public String detail(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		OrderBackBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailOrderBackBill";
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
		OrderBackBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/printOrderBackBill";
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
		List<OrderBackBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listOrderBackBill";
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长退货单列表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<OrderBackBillVo> beans = (List<OrderBackBillVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (OrderBackBillVo o : beans) {
			o.setStatus(dic.getDictData("DIC_ORDER_BACK_BILL_STATUS", o.getStatus()));
			o.setPay_status(dic.getDictData("DIC_PAY_STATUS", o.getPay_status()));
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("店长退货单明细_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
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
			m.put("status", dic.getDictData("DIC_ORDER_BACK_BILL_STATUS", m.get("status")));
			m.put("pay_type", dic.getDictData("DIC_PAY_TYPE", m.get("pay_type")) );
			m.put("pay_status", dic.getDictData("DIC_PAY_STATUS", m.get("pay_status")));
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
			if (LoginHelper.isShopkeeperRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("shop_id", LoginHelper.getShopId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isCityManagerRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("warehouse_id", LoginHelper.getWarehouseId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			lQuery.add(DvSqlHelper.buildQueryStr("bill_code", request.getParameter("bill_code"), DvSqlHelper.TYPE_CHAR_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
