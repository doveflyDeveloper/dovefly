package com.deertt.module.mm.check.web;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.mm.check.service.IStockCheckBillService;
import com.deertt.module.mm.check.util.IStockCheckBillConstants;
import com.deertt.module.mm.check.vo.StockCheckBillVo;
import com.deertt.module.mm.check.vo.StockCheckDetailVo;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.mm.goods.vo.GoodsWVo;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.office.excel.read.DvExcelReader;
import com.deertt.utils.helper.office.excel.read.MapContainer;
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
@RequestMapping("/stockCheckBillController")
public class StockCheckBillController extends DvBaseController implements IStockCheckBillConstants {
	
	@Autowired
	protected IStockCheckBillService service;
	
	@Autowired
	protected IGoodsWService goodsWService;
	
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
		return JSP_PREFIX + "/insertStockCheckBill";
	}
	
	/**
	 * 复制新增页面（根据已有单据信息复制创建新单据）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping(value = "/copy/{id}", method = RequestMethod.GET)
	public String copy(HttpServletRequest request, @PathVariable Integer id) throws Exception {
		StockCheckBillVo bean = service.findFull(id);
		bean.setId(null);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertStockCheckBill";
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
		StockCheckBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertStockCheckBill";
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
	public String insert(HttpServletRequest request, StockCheckBillVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		service.insertFull(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
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
	public String update(HttpServletRequest request, StockCheckBillVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		service.updateFull(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
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
		service.delete(id);
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
		service.delete(DvStringHelper.parseStringToIntegerArray(ids, ","));
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
		StockCheckBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailStockCheckBill";
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
		List<StockCheckBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listStockCheckBill";
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
	 * 下载模板
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("库存商品盘点表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		List<GoodsWVo> beans = goodsWService.queryByCondition("warehouse_id = " + LoginHelper.getWarehouseId(), "status desc, sort asc");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		dataMap.put("bill_date", DvDateHelper.formatDate(DvDateHelper.getSysDate(), "yyyy-MM-dd"));

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/checkbill_import_list_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
 	
	/**
	 * 导入列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/importExcel")
	public String importExcel(HttpServletRequest request, RedirectAttributes attr) throws Exception {
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);	
		String filePath = (String) map.get(ApplicationConfig.FILE_IMPORT_KEY);
		File excelFile = new File(filePath);
		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		File xmlFile = new File(projectBasePath + XLS_TEMPLATE_BASE_PATH + "/checkbill_import_list_config.xml");

		Map<String, Object> dataMap = DvExcelReader.readExcel(excelFile, xmlFile, new MapContainer() {
			@Override
			public Map<String, Object> makeDataContainerMap() {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<StockCheckDetailVo> beans = new ArrayList<StockCheckDetailVo>();
				dataMap.put(REQUEST_BEANS, beans);
				return dataMap;
			}
		});
		List<StockCheckDetailVo> details = (List<StockCheckDetailVo>) dataMap.get(REQUEST_BEANS);
		
		StockCheckBillVo bean = new StockCheckBillVo();
		DvVoHelper.markCreateStamp(request, bean);
		bean.setBill_code(service.generateBillCode(StockCheckBillVo.BILL_CODE_PREFIX));
		bean.setBill_date(DvDateHelper.getSysDate());
		bean.setBill_time(DvDateHelper.getSysTimestamp());
		bean.setCity_id(LoginHelper.getCityId());
		bean.setCity_name(LoginHelper.getCityName());
		bean.setWarehouse_id(LoginHelper.getWarehouseId());
		bean.setWarehouse_name(LoginHelper.getWarehouseName());
		bean.setDetails(details);
		bean.setStatus(StockCheckBillVo.STATUS_EDIT);
		
		//查询商品表，复制商品名称、图片字段
		List<GoodsWVo> goodsList = goodsWService.queryByCondition("warehouse_id = " + LoginHelper.getWarehouseId(), "status desc, sort asc");
		for (StockCheckDetailVo d : details) {
			for (GoodsWVo g : goodsList) {
				if (d.getGoods_id().equals(g.getId())) {
					d.setGoods_name(g.getName());
					d.setGoods_image(g.getImage());
					
					d.setStock_quantity(g.getStock_sum().add(g.getStock_locked()));
					d.setStock_amount(BigDecimal.ZERO);
					
//					d.setDif_quantity(d.getCheck_quantity().subtract(d.getStock_quantity()));
					d.setDif_amount(BigDecimal.ZERO);
					
					d.setCheck_quantity(d.getStock_quantity().add(d.getDif_quantity()));
					d.setCheck_amount(BigDecimal.ZERO);
					break;
				}
			}
			d.setStatus(DV_YES);
		}
		
		service.insertFull(bean);
		return redirectWithTip(CONTROLLER + "/detail/" + bean.getId(), attr);
	}
	
	/**
	 * 确认盘点数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/confirm/{id}")
	public String confirm(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		StockCheckBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		service.generateStatisticsBill(id);
		return redirectWithTip(CONTROLLER + "/detail/" + bean.getId(), attr);
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
			}
			lQuery.add(DvSqlHelper.buildQueryStr("bill_code", request.getParameter("bill_code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("bill_date", request.getParameter("bill_date_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
