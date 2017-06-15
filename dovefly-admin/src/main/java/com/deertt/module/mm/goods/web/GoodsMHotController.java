package com.deertt.module.mm.goods.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.deertt.module.mm.goods.service.IGoodsMService;
import com.deertt.module.mm.goods.vo.GoodsMVo;
import com.deertt.module.mm.goods.service.IGoodsMHotService;
import com.deertt.module.mm.goods.util.IGoodsMHotConstants;
import com.deertt.module.mm.goods.vo.GoodsMHotVo;
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
@RequestMapping("/goodsMHotController")
public class GoodsMHotController extends DvBaseController implements IGoodsMHotConstants {
	
	@Autowired
	protected IGoodsMHotService service;
	
	@Autowired
	protected IGoodsMService goodsMService;
	
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
		GoodsMHotVo bean = new GoodsMHotVo();
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertGoodsMHot";
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
		GoodsMHotVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertGoodsMHot";
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
	public String insert(HttpServletRequest request, GoodsMHotVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		GoodsMVo goods = goodsMService.find(vo.getGoods_id());
		vo.setCity_id(goods.getCity_id());
		vo.setCity_name(goods.getCity_name());
		vo.setMarket_id(goods.getMarket_id());
		vo.setMarket_name(goods.getMarket_name());
//		vo.setGoods_id(goods.getId());
//		vo.setGoods_name(goods.getName());
//		vo.setGoods_image(goods.getImage());
		vo.setCost_price(goods.getCost_price());
		vo.setSale_price(goods.getSale_price());
		vo.setMarket_price(goods.getMarket_price());
		service.insert(vo);
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
	public String update(HttpServletRequest request, GoodsMHotVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		GoodsMVo goods = goodsMService.find(vo.getGoods_id());
		vo.setCity_id(goods.getCity_id());
		vo.setCity_name(goods.getCity_name());
		vo.setMarket_id(goods.getMarket_id());
		vo.setMarket_name(goods.getMarket_name());
//		vo.setGoods_id(goods.getId());
//		vo.setGoods_name(goods.getName());
//		vo.setGoods_image(goods.getImage());
		vo.setCost_price(goods.getCost_price());
		vo.setSale_price(goods.getSale_price());
		vo.setMarket_price(goods.getMarket_price());
		service.update(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
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
		String orderStr = "sort asc";
		List<GoodsMHotVo> beans = service.queryByCondition(queryCondition, orderStr);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/sortGoodsMHot";
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
		GoodsMHotVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailGoodsMHot";
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
		String orderStr = "sort asc";//String orderStr = "create_at desc";
		List<GoodsMHotVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listGoodsMHot";
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
			lQuery.add(DvSqlHelper.buildQueryStr("city_id", request.getParameter("city_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("market_id", request.getParameter("market_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("goods_name", request.getParameter("goods_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
