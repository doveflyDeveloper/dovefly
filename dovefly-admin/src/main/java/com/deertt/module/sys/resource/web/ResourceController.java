package com.deertt.module.sys.resource.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.deertt.module.sys.resource.service.IResourceService;
import com.deertt.module.sys.resource.util.IResourceConstants;
import com.deertt.module.sys.resource.vo.ResourceVo;
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
@RequestMapping("/resourceController")
public class ResourceController extends DvBaseController implements IResourceConstants {
	
	@Autowired
	protected IResourceService service;
	
	/**
	 * 新增页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/create/{parent_id}")
	public String create(HttpServletRequest request, @PathVariable("parent_id") Integer parent_id) throws Exception {
		ResourceVo parentVo = service.find(parent_id);
		ResourceVo bean = new ResourceVo();
		bean.setCode(service.generateNextCode(parent_id));
		bean.setParent_id(parentVo.getId());
		bean.setParent_code(parentVo.getCode());
		bean.setParent_name(parentVo.getName());
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertResource";
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
		ResourceVo bean = service.find(id);
		bean.setId(null);
		bean.setCode(service.generateNextCode(bean.getParent_id()));
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertResource";
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
		ResourceVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertResource";
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
	public String insert(HttpServletRequest request, ResourceVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setLevel(vo.getCode().length()/RES_CODE_LENGTH);
		vo.setIs_leaf(DV_YES);
		service.insert(vo);
		attrs.addFlashAttribute("refreshResourceTree", true);
		return "redirect:" + CONTROLLER + "/detail/" + vo.getId();
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
	public String update(HttpServletRequest request, ResourceVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		service.update(vo);
		attrs.addFlashAttribute("refreshResourceTree", true);
		return "redirect:" + CONTROLLER + "/detail/" + vo.getId();
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
	public String delete(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attrs) throws Exception {
		service.delete(id);
		attrs.addFlashAttribute("refreshResourceTree", true);
		return "redirect:" + CONTROLLER + "/manageResIndex";
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
	public String deleteMulti(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attrs) throws Exception {
		service.delete(DvStringHelper.parseStringToIntegerArray(ids, ","));
		attrs.addFlashAttribute("refreshResourceTree", true);
		return "redirect:" + CONTROLLER + "/manageResIndex";
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
		ResourceVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailResource";
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
		List<ResourceVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listResource";
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
		request.setAttribute(REQUEST_QUERY_CONDITION, "");
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
		return JSP_PREFIX + "/referenceResource";
	}
	
	/**
	 * 系统功能菜单
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resourceTree")
	public String resourceTreeNode(HttpServletRequest request) throws Exception {
		List<ResourceVo> beans = service.queryMenusByUserId(LoginHelper.getUserId());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reference/resourceTree";
	}
	
	/**
	 * 系统功能菜单2
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resourceTree2")
	public String resourceTreeNode2(HttpServletRequest request) throws Exception {
		String queryCondition = "type != '003'";
		String orderStr = "level asc, sort asc";
		List<ResourceVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/reference/resourceTree2";
	}
	
	/**
	 * 资源管理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/manageResFrame")
	public String manageResFrame(HttpServletRequest request) throws Exception {
		return JSP_PREFIX + "/manageResFrame";
	}

	/**
	 * 功能菜单管理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manageResTree")
	public String manageResTree(HttpServletRequest request) throws Exception {
		String queryCondition = getQueryCondition(request);
		String orderStr = "level asc, sort asc";
		List<ResourceVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/manageResTree";
	}
	
	/**
	 * 功能菜单管理首页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manageResIndex")
	public String manageResIndex(HttpServletRequest request) throws Exception {
		return JSP_PREFIX + "/manageResIndex";
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
		ResourceVo bean = service.find(id);
		String queryCondition = "parent_id = '" + id + "'";
		String orderStr = "sort asc";
		List<ResourceVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/sortResource";
	}
	
	/**
	 * 对当前节点的所有子节点排序
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/sort/{id}")
	public String sort(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attrs) throws Exception {
		String sort_ids = request.getParameter("sort_ids");
		if(StringUtils.isNotEmpty(sort_ids)){
			service.sort(sort_ids);
		}
		attrs.addFlashAttribute("refreshResourceTree", true);
		return "redirect:" + CONTROLLER + "/find4Sort/" + id;
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/loadTaskTag", produces={"application/json;charset=UTF-8"})
	public Object loadTaskTag(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		String sql = "";
		if (LoginHelper.isHeadquartersRole()) {
			sql = SQL_QUERY_TASK_TAG_CASHER;
		} else if (LoginHelper.isCityManagerRole()) {
			sql = SQL_QUERY_TASK_TAG_CITYMANAGER;
		} else if (LoginHelper.isOperationManagerRole()) {
			sql = SQL_QUERY_TASK_TAG_OPERATIONMANAGER;
		} else if (LoginHelper.isShopkeeperRole()) {
			sql = SQL_QUERY_TASK_TAG_SHOPKEEPER;
		} else {
			sql = SQL_QUERY_TASK_TAG;
		}
		
		sql = sql.replaceAll("#city_id#", LoginHelper.getCityId().toString()).replaceAll("#warehouse_id#", LoginHelper.getWarehouseId().toString()).replaceAll("#user_id#", LoginHelper.getUserId().toString()).replaceAll("#shop_id#", LoginHelper.getShopId().toString());
		
		Map<String, Object> map = service.queryForMap(sql);

		result.setSuccess(true);
		result.setMessage("Ajax请求数据成功！");
		result.setData(map);
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
			lQuery.add(DvSqlHelper.buildQueryStr("id", request.getParameter("id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("code", request.getParameter("code"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("name", request.getParameter("name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("parent_id", request.getParameter("parent_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("type", request.getParameter("type"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("is_leaf", request.getParameter("is_leaf"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("remark", request.getParameter("remark"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CHAR_EQUAL));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
