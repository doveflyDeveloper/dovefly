package com.deertt.module.mm.statistics.web;

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

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.mm.goods.service.IGoodsWService;
import com.deertt.module.mm.goods.vo.GoodsWVo;
import com.deertt.module.mm.statistics.service.IStatisticsBillService;
import com.deertt.module.mm.statistics.util.IStatisticsBillConstants;
import com.deertt.module.mm.statistics.vo.StatisticsBillVo;
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
@RequestMapping("/statisticsBillController")
public class StatisticsBillController extends DvBaseController implements IStatisticsBillConstants {
	
	@Autowired
	protected IStatisticsBillService service;
	
	@Autowired
	protected IGoodsWService goodsWService;
	
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
		StatisticsBillVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailStatisticsBill";
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
		String orderStr = "id desc";//String orderStr = "create_at desc";
		List<StatisticsBillVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listStatisticsBill";
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
//		request.setAttribute(REQUEST_QUERY_CONDITION, "");
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("商品库存盘点表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		List<GoodsWVo> beans = goodsWService.queryByCondition("city_id = " + LoginHelper.getCityId() + " and status = '1'", null);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "statistics_detail_import_list_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 批量导出
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportList/{ids}")
	public void exportList(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(IStatisticsBillConstants.TABLE_NAME_CHINESE + "批量导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if(StringUtils.isEmpty(ids) || "all".equals(ids)){
			DvPageVo pageVo = new DvPageVo(2000, 2000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN ('" + ids.replaceAll(",", "','") + "')";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<StatisticsBillVo> beans = (List<StatisticsBillVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "statisticsbill_export_list_template.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 导出一条单据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/exportDetail/{id}")
	public void exportDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(IStatisticsBillConstants.TABLE_NAME_CHINESE + "导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		detail(request, id); //通过查询取到bean  
		StatisticsBillVo bean = (StatisticsBillVo)request.getAttribute(REQUEST_BEAN);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEAN, bean);
		List<StatisticsBillVo> beans = new ArrayList<StatisticsBillVo>();
		dataMap.put(REQUEST_BEANS, beans);

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "statistics_detail_export_detail_template.xls";
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
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", "0", DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isCityManagerRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("warehouse_id", LoginHelper.getWarehouseId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isHeadquartersRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", request.getParameter("city_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
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
