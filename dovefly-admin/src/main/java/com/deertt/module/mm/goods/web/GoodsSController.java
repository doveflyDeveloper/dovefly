package com.deertt.module.mm.goods.web;

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
import com.deertt.module.mm.category.service.ICategoryService;
import com.deertt.module.mm.goods.service.IGoodsSService;
import com.deertt.module.mm.goods.util.IGoodsSConstants;
import com.deertt.module.mm.goods.vo.GoodsSVo;
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
@RequestMapping("/goodsSController")
public class GoodsSController extends DvBaseController implements IGoodsSConstants {
	
	@Autowired
	protected IGoodsSService service;
	
	@Autowired
	protected ICategoryService categoryService;
	
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
		GoodsSVo bean = new GoodsSVo();
		bean.setCity_id(LoginHelper.getCityId());
		bean.setCity_name(LoginHelper.getCityName());
		bean.setShop_id(LoginHelper.getShopId());
		bean.setShop_name(LoginHelper.getShopName());
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertGoodsS";
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
		GoodsSVo bean = service.find(id);
		bean.setId(null);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertGoodsS";
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
		GoodsSVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertGoodsS";
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
	public String insert(HttpServletRequest request, GoodsSVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		vo.setShop_id(LoginHelper.getShopId());
		vo.setShop_name(LoginHelper.getShopName());
		vo.setCategory_code(categoryService.find(vo.getCategory_id()).getCode());
		service.insert(vo);
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
	public String update(HttpServletRequest request, GoodsSVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		vo.setShop_id(LoginHelper.getShopId());
		vo.setShop_name(LoginHelper.getShopName());
		vo.setCategory_code(categoryService.find(vo.getCategory_id()).getCode());
		service.update(vo);
		return redirectWithTip(CONTROLLER + "/detail/" + vo.getId(), attr);
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
		GoodsSVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailGoodsS";
	}
	
	/**
	 * 上架销售
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/onSale/{ids}")
	public String onSale(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attr) throws Exception {
		service.onSale(DvStringHelper.parseStringToIntegerArray(ids, ","));
		return query(request);
	}
	
	/**
	 * 下架停售
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/offSale/{ids}")
	public String offSale(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attr) throws Exception {
		service.offSale(DvStringHelper.parseStringToIntegerArray(ids, ","));
		return query(request);
	}
	
	/**
	 * 排序页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/query4Sort")
	public String query4Sort(HttpServletRequest request) throws Exception {
		String queryCondition = getQueryCondition(request);
		//DvPageVo pageVo = super.transctPageVo(request, service.getRecordCount(queryCondition));
		String orderStr = "sort asc";
		List<GoodsSVo> beans = service.queryByCondition(queryCondition, orderStr);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/sortGoodsS";
	}
	
	@ResponseBody
	@RequestMapping(value="/sort", produces={"application/json;charset=UTF-8"})
	public Object sort(HttpServletRequest request) throws Exception {
		String sort_ids = request.getParameter("sort_ids");
		if(StringUtils.isNotEmpty(sort_ids)){
			service.sort(sort_ids);
		}
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("排序成功！");
		return result;
	}
	
	/**
	 * 检查条形码是否已存在
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/existsBarcode", produces={"application/json;charset=UTF-8"})
	public Object existsBarcode(HttpServletRequest request) throws Exception {
		Integer count = service.getRecordCount("warehouse_id = " + LoginHelper.getWarehouseId() + " and barcode = '" + request.getParameter("barcode") + "'");//(request.getParameter("barcode"));
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setData(count > 0);
		return result;
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
		String orderStr = "sort asc";
		List<GoodsSVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listGoodsS";
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
	 * 参照信息查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reference")
	public String reference( HttpServletRequest request) throws Exception {
		query(request);
		return JSP_PREFIX + "/referenceGoodsS";
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/changeStockSum", produces={"application/json;charset=UTF-8"})
	public Object changeStockSum(HttpServletRequest request, GoodsSVo vo) throws Exception {
//		GoodsSVo goods = service.find(vo.getId());
//		goods.setStock_sum(vo.getStock_sum());
//		service.changeStockSum(goods.getId(), goods.getStock_sum(), goods.getStock_locked());
//		HandleResult result = new HandleResult();
//		result.setSuccess(true);
//		result.setMessage("库存修改成功！");
		
		HandleResult result = new HandleResult();
		result.setSuccess(false);
		result.setMessage("库存修改功能暂时关闭！");
		return result;
	}
	
	/**
	 * 修改包装规格
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/changeSpec/{id}", produces={"application/json;charset=UTF-8"})
	public Object changeSpec(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		GoodsSVo goods = service.find(id);
		goods.setSpec(new BigDecimal(request.getParameter("spec")));
		service.update(goods);
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setData(goods);
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
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("商品库存列表_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<GoodsSVo> beans = (List<GoodsSVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (GoodsSVo g : beans) {
			g.setStatus(dic.getDictData("DIC_GOODS_STATUS", g.getStatus()));
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
			lQuery.add(DvSqlHelper.buildQueryStr("id", request.getParameter("selected_goods_ids"), DvSqlHelper.TYPE_CUSTOM, "not in(", ")"));
			if ("special".equals(request.getParameter("reference"))) {
				//管理员管理特荐商品时，可看到所有商品
			} else if ("back".equals(request.getParameter("reference"))) {
				//退货
				lQuery.add(DvSqlHelper.buildQueryStr("shop_id", LoginHelper.getShopId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else {
				if (LoginHelper.isHeadquartersRole()) {
					lQuery.add(DvSqlHelper.buildQueryStr("city_id", request.getParameter("city_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
				} else {
					lQuery.add(DvSqlHelper.buildQueryStr("shop_id", LoginHelper.getShopId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
				}
			}
			lQuery.add(DvSqlHelper.buildQueryStr("code", request.getParameter("code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("barcode", request.getParameter("barcode"), DvSqlHelper.TYPE_CHAR_LIKE));
			String name = request.getParameter("name");
			if (StringUtils.isNotEmpty(name)) {
				name = name.trim();
				String query_name = "(" + DvSqlHelper.buildQueryStr("name", name, DvSqlHelper.TYPE_CHAR_LIKE);
				String[] names = name.split("\\s");
				if (names.length > 1) {
					for (String str : names) {
						if (StringUtils.isNotEmpty(str)) {
							String query = DvSqlHelper.buildQueryStr("name", str, DvSqlHelper.TYPE_CHAR_LIKE);
							query_name += " or " + query;
						}
					}
				}
				query_name += ")";
				lQuery.add(query_name);
			}
			lQuery.add(DvSqlHelper.buildQueryStr("title", request.getParameter("title"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("tips", request.getParameter("tips"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("type", request.getParameter("type"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("tag", request.getParameter("tag"), DvSqlHelper.TYPE_CHAR_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("producer", request.getParameter("producer"), DvSqlHelper.TYPE_CHAR_LIKE));
			if ("1".equals(request.getParameter("has_stock"))){
				lQuery.add("stock_sum >= 1");
			}
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			String category_id= request.getParameter("category_id");
			if (StringUtils.isNotEmpty(category_id)) {
				lQuery.add(DvSqlHelper.buildQueryStr("category_code", categoryService.find(Integer.parseInt(category_id)).getCode(), DvSqlHelper.TYPE_CUSTOM, " like '", "%'"));
			}
			
			if (LoginHelper.isHeadquartersRole() || LoginHelper.isCityManagerRole()) {
				if ("1".equals(request.getParameter("priceExp"))){
					lQuery.add("(sale_price - cost_price) / cost_price <= 0.1");
				}
			} else if (LoginHelper.isShopkeeperRole()) {
				if ("1".equals(request.getParameter("priceExp"))){
					lQuery.add("(market_price - sale_price) / sale_price <= 0.1");
				}
			}
			
			if (LoginHelper.isCityManagerRole()) {
				if ("1".equals(request.getParameter("safe_line_num"))) {
					lQuery.add("stock_sum < safe_line");
				}
			}
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
