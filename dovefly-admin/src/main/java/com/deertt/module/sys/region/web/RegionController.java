package com.deertt.module.sys.region.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.region.service.IRegionService;
import com.deertt.module.sys.region.util.IRegionConstants;
import com.deertt.module.sys.region.vo.RegionVo;
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
@RequestMapping("/regionController")
public class RegionController extends DvBaseController implements IRegionConstants {
	
	@Autowired
	protected IRegionService service;
	
	/**
	 * 区域管理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manageRegion")
	public String manageRegion(HttpServletRequest request) throws Exception {
		request.setAttribute("nodes", loadRegions(request));
		return JSP_PREFIX + "/manageRegion";
	}
	
	/**
	 * 参照信息查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reference")
	public String reference(HttpServletRequest request) throws Exception {
		if ("tree".equals(request.getParameter("referType"))) {
			request.setAttribute("nodes", loadRegions(request));
			return JSP_PREFIX + "/referenceRegionTree";
		} else {
			query(request);
			return JSP_PREFIX + "/referenceRegionSearch";
		}
	}
	
	/**
	 * 新增
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxAdd/{parent_id}", produces={"application/json;charset=UTF-8"})
	public Object ajaxAdd(HttpServletRequest request, @PathVariable("parent_id") Integer parent_id) throws Exception {
		HandleResult result = new HandleResult();
		try {
			RegionVo parentVo = service.find(parent_id);
			RegionVo vo = new RegionVo();
			DvVoHelper.markCreateStamp(request, vo);
			if (parentVo.getCity_id() != null) {//临时解决省级暂无挂钩城市问题
				vo.setCity_id(parentVo.getCity_id());
				vo.setCity_name(parentVo.getCity_name());
			}
			
			if (parentVo.getWarehouse_id() != null) {
				vo.setWarehouse_id(parentVo.getWarehouse_id());
				vo.setWarehouse_name(parentVo.getWarehouse_name());
			}
			
			vo.setParent_id(parentVo.getId());
			vo.setParent_code(parentVo.getCode());
			vo.setParent_name(parentVo.getName());
			vo.setParent_full_name(parentVo.getFull_name());
			vo.setCode(service.generateNextCode(vo.getParent_id()));
			vo.setName(request.getParameter("name"));
			vo.setFull_name(vo.getParent_full_name() + "-" + vo.getName());
			vo.setLevel(vo.getCode().length()/REG_CODE_LENGTH);
			vo.setIs_leaf(DV_YES);
			vo.setSearch_times(0);
			service.insert(vo);
			result.setSuccess(true);
			result.setMessage("添加成功！");
			result.setData(vo);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("添加失败！");
		}
		
		return result;
	}
	
	/**
	 * 修改
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxUpdate/{id}", produces={"application/json;charset=UTF-8"})
	public Object ajaxUpdate(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		HandleResult result = new HandleResult();
		try {
			RegionVo vo = service.find(id);
			vo.setName(request.getParameter("name"));
			vo.setFull_name(vo.getParent_full_name() + "-" + vo.getName());
			DvVoHelper.markModifyStamp(request, vo);
			service.updateCascade(vo);
			result.setSuccess(true);
			result.setMessage("修改成功！");
			result.setData(vo);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改失败！");
		}
		
		return result;
	}
	
	/**
	 * 检查是否允许删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/checkCanDelete/{id}", produces={"application/json;charset=UTF-8"})
	public Object checkCanDelete(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		return service.checkCanDelete(id);
	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/ajaxDelete/{id}", produces={"application/json;charset=UTF-8"})
	public Object ajaxDelete(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		HandleResult result = new HandleResult();
		try {
			service.deleteCascade(id);
			result.setSuccess(true);
			result.setMessage("删除成功！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除失败！");
		}
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
		RegionVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/locateRegion";
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
			RegionVo bean = service.find(id);
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
		String orderStr = "parent_code asc, sort asc";//String orderStr = "create_at desc";
		List<RegionVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listRegion";
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
	 * 异步加载节点
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/loadRegions", produces={"application/json;charset=UTF-8"})
	public String loadRegions(HttpServletRequest request) throws Exception {
		List<String> lQuery = new ArrayList<String>();
		if (LoginHelper.isShopkeeperRole() || LoginHelper.isCityManagerRole()) {
			lQuery.add(DvSqlHelper.buildQueryStr("city_id", LoginHelper.getCityId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
		}
		lQuery.add(DvSqlHelper.buildQueryStr("parent_id", request.getParameter("id"), DvSqlHelper.TYPE_CHAR_EQUAL));
		String queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		if(StringUtils.isEmpty(queryCondition)){
			queryCondition = "(level = 1 or level = 2)";
		}
		String orderStr = "level asc, sort asc";
		List<RegionVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		StringBuffer sb = new StringBuffer("[");
		if(beans != null && beans.size() > 0){
			for (int i = 0; i < beans.size(); i++) {
				RegionVo vo = beans.get(i);
				String str = "{id:" + vo.getId() + ", pId:" + vo.getParent_id() + ", code:\"" + vo.getCode() + "\"" + ", name:\"" + vo.getName() + "\"" + ", full_name:\"" + vo.getFull_name() + "\"" + ", region_level:" + vo.getLevel() + ("1".equals(vo.getIs_leaf()) ? "" : ", isParent:true") + (i + 1 == beans.size() ? "}" : "},");
				sb.append(str);
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 对当前节点的所有子节点排序页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/find4Sort/{id}")
	public String find4Sort(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		RegionVo bean = service.find(id);
		String queryCondition = "parent_id = '" + id + "'";
		String orderStr = "sort asc";
		List<RegionVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/sortRegion";
	}
	
	
	/**
	 * 对当前节点的所有子节点排序
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
		String region_ids = request.getParameter("region_ids");
		service.setWarehouse(warehouse_id, region_ids);
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
		String region_ids = request.getParameter("region_ids");
		service.setManager(manager_id, region_ids);
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("设置成功！");
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
			if (LoginHelper.isCityManagerRole() || LoginHelper.isOperationManagerRole() || LoginHelper.isShopkeeperRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", LoginHelper.getCityId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			lQuery.add(DvSqlHelper.buildQueryStr("name", request.getParameter("name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("full_name", request.getParameter("full_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("manager_name", request.getParameter("manager_name"), DvSqlHelper.TYPE_CHAR_LIKE));

			String has_manager = request.getParameter("has_manager");
			if ("1".equals(has_manager)) {
				lQuery.add("manager_id is not null");
			} else if ("0".equals(has_manager)) {
				lQuery.add("manager_id is null");
			}
			
			lQuery.add(DvSqlHelper.buildQueryStr("level", request.getParameter("level"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CHAR_EQUAL));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
