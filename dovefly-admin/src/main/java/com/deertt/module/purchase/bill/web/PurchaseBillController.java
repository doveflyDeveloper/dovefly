package com.deertt.module.purchase.bill.web;

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

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.purchase.bill.service.IPurchaseBillService;
import com.deertt.module.purchase.bill.util.IPurchaseBillConstants;
import com.deertt.module.purchase.bill.vo.PurchaseBillVo;
import com.deertt.module.purchase.bill.vo.PurchaseDetailVo;
import com.deertt.module.sys.dict.DvDictionaryFactory;
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
@RequestMapping("/purchaseBillController")
public class PurchaseBillController extends DvBaseController implements IPurchaseBillConstants {
	
	@Autowired
	protected IPurchaseBillService service;
	
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
		PurchaseBillVo bean = new PurchaseBillVo();
		bean.setBill_date(new java.sql.Date(System.currentTimeMillis()));
		bean.setBill_time(DvDateHelper.getSysTimestamp());
		bean.setAmount(BigDecimal.ZERO);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertPurchaseBill";
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
		PurchaseBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertPurchaseBill";
	}
	
	/**
	 * 新增保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, PurchaseBillVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		vo.setWarehouse_id(LoginHelper.getWarehouseId());
		vo.setWarehouse_name(LoginHelper.getWarehouseName());
		vo.setBill_code(service.generateBillCode(PurchaseBillVo.BILL_CODE_PREFIX));
//		vo.setBill_date(new java.sql.Date(System.currentTimeMillis()));
//		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setStatus(PurchaseBillVo.STATUS_UNCHECKIN);
		vo.setDetails(wrapDetails(request));
		service.insertFull(vo);
		return redirectWithTip(CONTROLLER + "/detail/" + vo.getId(), attr);
	}

	/**
	 * 修改保存
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/update")
	public String update(HttpServletRequest request, PurchaseBillVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		vo.setWarehouse_id(LoginHelper.getWarehouseId());
		vo.setWarehouse_name(LoginHelper.getWarehouseName());
//		vo.setBill_date(new java.sql.Date(System.currentTimeMillis()));
//		vo.setBill_time(DvDateHelper.getSysTimestamp());
		vo.setStatus(PurchaseBillVo.STATUS_UNCHECKIN);
		vo.setDetails(wrapDetails(request));
		service.updateFull(vo);
		return redirectWithTip(CONTROLLER + "/detail/" + vo.getId(), attr);
	}
	
	/**
	 * 从Request中封装订单明细列表数据
	 * @param request
	 * @return
	 */
	private List<PurchaseDetailVo> wrapDetails(HttpServletRequest request) {
		List<PurchaseDetailVo> list = new ArrayList<PurchaseDetailVo>();
		String[] detail_ids = request.getParameterValues("detail_id");
		String[] goods_ids = request.getParameterValues("goods_id");
		String[] goods_names = request.getParameterValues("goods_name");
		String[] goods_images = request.getParameterValues("goods_image");
		String[] sub_totals = request.getParameterValues("sub_total");
		String[] quantitys = request.getParameterValues("quantity");
		String[] spec_quantitys = request.getParameterValues("spec_quantity");
		String[] specs = request.getParameterValues("spec");
		if (detail_ids != null && detail_ids.length > 0) {
			for (int i = 0; i < detail_ids.length; i++) {
				PurchaseDetailVo detail = new PurchaseDetailVo();
				detail.setId(StringUtils.isEmpty(detail_ids[i]) ? null : Integer.parseInt(detail_ids[i]));
				detail.setBill_id(null);
				detail.setGoods_id(StringUtils.isEmpty(goods_ids[i]) ? null : Integer.parseInt(goods_ids[i]));
				detail.setGoods_name(goods_names[i]);
				detail.setGoods_image(goods_images[i]);
				detail.setQuantity(StringUtils.isEmpty(quantitys[i]) ? BigDecimal.ONE : new BigDecimal(quantitys[i]));
				detail.setSpec(StringUtils.isEmpty(specs[i]) ? BigDecimal.ONE : new BigDecimal(specs[i]));
				detail.setSpec_quantity(StringUtils.isEmpty(spec_quantitys[i]) ? BigDecimal.ONE : new BigDecimal(spec_quantitys[i]));
				detail.setSub_total(StringUtils.isEmpty(sub_totals[i]) ? BigDecimal.ZERO : new BigDecimal(sub_totals[i]));
				detail.setUnit_price(detail.getSub_total().add(new BigDecimal("0.00")).divide(detail.getQuantity(), 2));
				detail.setLast_price(detail.getUnit_price());
				//千万注意小数位数！！！
				
				detail.setStatus(DV_YES);
				list.add(detail);
			}
		}
		return list;
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
		PurchaseBillVo bean = service.find(id);
		if (!PurchaseBillVo.STATUS_UNCHECKIN.equals(bean.getStatus())) {
			throw new BusinessException("只有未入库订单才可以删除！");
		}
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
		String queryCondition = "status != '" + PurchaseBillVo.STATUS_UNCHECKIN + "' and id in (" + ids + ")";
		int count = service.getRecordCount(queryCondition);
		if (count > 0) {
			throw new BusinessException("只有未入库订单才可以删除！");
		}
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
		PurchaseBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailPurchaseBill";
	}
	
	/**
	 * 入库
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/checkin/{id}")
	public String checkin(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.checkin(id);
		return redirectWithTip(CONTROLLER + "/detail/" + id, attr);
	}
	
	/**
	 * 撤回
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/turnback/{id}")
	public String turnback(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		service.turnback(id);
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
		List<PurchaseBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listPurchaseBill";
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
	 * 打印
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/print/{id}")
	public String print(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		PurchaseBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/printPurchaseBill";
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("采购订单列表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<PurchaseBillVo> beans = (List<PurchaseBillVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (PurchaseBillVo p : beans) {
			p.setStatus(dic.getDictData("DIC_PURCHASE_BILL_STATUS", p.getStatus()));
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("采购订单明细_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		String queryCondition = getQueryCondition(request);
		List<Map<String, Object>> beans = service.queryListDetails(queryCondition);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (Map<String, Object> m : beans) {
			m.put("status", dic.getDictData("DIC_PURCHASE_BILL_STATUS", m.get("status")));
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
				lQuery.add(DvSqlHelper.buildQueryStr("warehouse_id", request.getParameter("warehouse_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isCityManagerRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("warehouse_id", LoginHelper.getWarehouseId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			lQuery.add(DvSqlHelper.buildQueryStr("supplier_id", request.getParameter("supplier_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_code", request.getParameter("bill_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}