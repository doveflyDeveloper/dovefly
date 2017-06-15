package com.deertt.module.sys.warehouse.web;

import java.math.BigDecimal;
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
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.warehouse.service.IWarehouseService;
import com.deertt.module.sys.warehouse.util.IWarehouseConstants;
import com.deertt.module.sys.warehouse.vo.WarehouseVo;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Controller
@RequestMapping("/warehouseController")
public class WarehouseController extends DvBaseController implements IWarehouseConstants {
	
	@Autowired
	protected IWarehouseService service;
	
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
		return JSP_PREFIX + "/insertWarehouse";
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
		WarehouseVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertWarehouse";
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
	public String insert(HttpServletRequest request, WarehouseVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setBalance_amount(BigDecimal.ZERO);
		vo.setLocked_amount(BigDecimal.ZERO);
		vo.setHalfway_amount(BigDecimal.ZERO);
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
	public String update(HttpServletRequest request, WarehouseVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		WarehouseVo bean = service.find(vo.getId());
		vo.setBalance_amount(bean.getBalance_amount());
		vo.setLocked_amount(bean.getLocked_amount());
		vo.setHalfway_amount(bean.getHalfway_amount());
		service.update(vo);
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
		WarehouseVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailWarehouse";
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
		List<WarehouseVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listWarehouse";
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/loadWarehouse", produces={"application/json;charset=UTF-8"})
	public Object loadOprationManagers(HttpServletRequest request) throws Exception {
		List<WarehouseVo> beans = service.queryByCondition("city_id = " + LoginHelper.getCityId(), null);
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setData(beans);
		result.setMessage("Ajax请求数据成功！");
		return result;
	}
	
	/**
	 * 修改货仓信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/changeWarehouseInfo", produces={"application/json;charset=UTF-8"})
	public Object changeWarehouseInfo(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		try {
			WarehouseVo bean = service.find(LoginHelper.getWarehouseId());
			bean.setWarehouse_name(request.getParameter("warehouse_name"));
			bean.setWarehouse_desc(request.getParameter("warehouse_desc"));
			bean.setWarehouse_area(request.getParameter("warehouse_area"));
			bean.setStart_amount(new BigDecimal(request.getParameter("start_amount")));
			service.update(bean);
			result.setSuccess(true);
			result.setMessage("货仓信息修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("货仓信息修改失败！");
		}
		return result;
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
			lQuery.add(DvSqlHelper.buildQueryStr("city_id", request.getParameter("city_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("warehouse_name", request.getParameter("warehouse_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
