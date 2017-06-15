package com.deertt.module.sys.school.web;

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
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.school.service.ISchoolService;
import com.deertt.module.sys.school.util.ISchoolConstants;
import com.deertt.module.sys.school.vo.SchoolVo;
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
@RequestMapping("/schoolController")
public class SchoolController extends DvBaseController implements ISchoolConstants {
	
	@Autowired
	protected ISchoolService service;
	
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
		return JSP_PREFIX + "/insertSchool";
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
		SchoolVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertSchool";
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
	public String insert(HttpServletRequest request, SchoolVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setCity_name(DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", vo.getCity_id()));
		vo.setWarehouse_name(DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_WAREHOUSE", vo.getWarehouse_id()));
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
	public String update(HttpServletRequest request, SchoolVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		vo.setCity_name(DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", vo.getCity_id()));
		vo.setWarehouse_name(DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_WAREHOUSE", vo.getWarehouse_id()));
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
		String msg = "删除成功！";
		try {
			service.delete(id);
		} catch (Exception e) {
			msg = "学校正在被关联使用，无法删除！";
		}
		return redirectWithTip(DEFAULT_REDIRECT, attr, msg);
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
		String msg = "删除成功！";
		try {
			service.delete(DvStringHelper.parseStringToIntegerArray(ids, ","));
		} catch (Exception e) {
			msg = "学校正在被关联使用，无法删除！";
		}
		return redirectWithTip(DEFAULT_REDIRECT, attr, msg);
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
		SchoolVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailSchool";
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
		List<SchoolVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listSchool";
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
		return JSP_PREFIX + "/referenceSchool";
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/setWarehouse", produces={"application/json;charset=UTF-8"})
	public Object setWarehouse(HttpServletRequest request) throws Exception {
		Integer warehouse_id = Integer.valueOf(request.getParameter("warehouse_id"));
		String school_ids = request.getParameter("school_ids");
		service.setWarehouse(warehouse_id, school_ids);
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("设置成功！");
		return result;
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/setManager", produces={"application/json;charset=UTF-8"})
	public Object setManager(HttpServletRequest request) throws Exception {
		Integer manager_id = Integer.valueOf(request.getParameter("manager_id"));
		String school_ids = request.getParameter("school_ids");
		service.setManager(manager_id, school_ids);
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("设置成功！");
		return result;
	}
	
	
	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/find4Locate/{id}")
	public String find4Locate(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		SchoolVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/locateSchool";
	}
	
	/**
	 * 百度地址更新
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxLocate/{id}", produces={"application/json;charset=UTF-8"})
	public Object ajaxLocate(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		HandleResult result = new HandleResult();
		try {
			SchoolVo bean = service.find(id);
			bean.setBaidu_uid(request.getParameter("baidu_uid"));
			bean.setBaidu_title(request.getParameter("baidu_title"));
			bean.setBaidu_address(request.getParameter("baidu_address"));
			bean.setBaidu_longtitude(Double.valueOf(request.getParameter("baidu_longtitude")));
			bean.setBaidu_latitude(Double.valueOf(request.getParameter("baidu_latitude")));
			service.update(bean);
			result.setSuccess(true);
			result.setMessage("更新地址成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("更新地址失败！");
		}
		return result;
	}
	
	/**
	 * 对学校排序页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/find4Sort/{id}")
	public String find4Sort(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		SchoolVo bean = service.find(id);
		String queryCondition = "parent_id = '" + id + "'";
		String orderStr = "sort asc";
		List<SchoolVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/sortSchool";
	}
	
	
	/**
	 * 对学校排序
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/sort/{id}", produces={"application/json;charset=UTF-8"})
	public Object sort(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		HandleResult result = new HandleResult();
		try {
			String sort_ids = request.getParameter("sort_ids");
			if(StringUtils.isNotEmpty(sort_ids)){
				service.sort(sort_ids);
			}
			result.setSuccess(true);
			result.setMessage("排序成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("排序失败！");
		}
		return result;
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
			
			lQuery.add(DvSqlHelper.buildQueryStr("manager_name", request.getParameter("manager_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("school_name", request.getParameter("school_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			
			String has_manager = request.getParameter("has_manager");
			if ("1".equals(has_manager)) {
				lQuery.add("manager_id is not null");
			} else if ("0".equals(has_manager)) {
				lQuery.add("manager_id is null");
			}
			
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
}
