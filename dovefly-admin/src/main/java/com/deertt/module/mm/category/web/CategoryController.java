package com.deertt.module.mm.category.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.mm.category.service.ICategoryService;
import com.deertt.module.mm.category.util.ICategoryConstants;
import com.deertt.module.mm.category.vo.CategoryVo;
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
@RequestMapping("/categoryController")
public class CategoryController extends DvBaseController implements ICategoryConstants {
	
	@Autowired
	protected ICategoryService service;
	
	/**
	 * 区域管理
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manageCategory")
	public String manageCategory(HttpServletRequest request) throws Exception {
		return JSP_PREFIX + "/manageCategory";
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
			CategoryVo parentVo = service.find(parent_id);
			CategoryVo vo = new CategoryVo();
			DvVoHelper.markCreateStamp(request, vo);
			vo.setParent_id(parentVo.getId());
			vo.setParent_code(parentVo.getCode());
			vo.setParent_name(parentVo.getName());
			vo.setCode(service.generateNextCode(vo.getParent_id()));
			vo.setName(request.getParameter("name"));
			vo.setLevel(vo.getCode().length()/CAT_CODE_LENGTH);
			vo.setIs_leaf(DV_YES);
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
			CategoryVo vo = service.find(id);
			vo.setName(request.getParameter("name"));
			DvVoHelper.markModifyStamp(request, vo);
			service.updateCascade(vo);
			result.setSuccess(true);
			result.setMessage("修改成功！");
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
	 * 查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/detail/{id}")
	public String detail(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		CategoryVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailCategory";
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
		List<CategoryVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listCategory";
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
	 * 异步加载节点
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/loadCategorys", produces={"application/json;charset=UTF-8"})
	public String loadCategorys(HttpServletRequest request) throws Exception {
		List<String> lQuery = new ArrayList<String>();
		lQuery.add(DvSqlHelper.buildQueryStr("parent_id", request.getParameter("id"), DvSqlHelper.TYPE_CHAR_EQUAL));
		String queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		if(StringUtils.isEmpty(queryCondition)){
			queryCondition = "(level = 1 or level = 2)";
		}
		String orderStr = "level asc, sort asc";
		List<CategoryVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		StringBuffer sb = new StringBuffer("[");
		if(beans != null && beans.size() > 0){
			for (int i = 0; i < beans.size(); i++) {
				CategoryVo vo = beans.get(i);
				String str = "{id:" + vo.getId() + ", pId:" + vo.getParent_id() + ", name:\"" + vo.getName() + "\"" + ("1".equals(vo.getIs_leaf()) ? "" : ", isParent:true") + (i + 1 == beans.size() ? "}" : "},");
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
		CategoryVo bean = service.find(id);
		String queryCondition = "parent_id = '" + id + "'";
		String orderStr = "sort asc";
		List<CategoryVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/sortCategory";
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
		attrs.addFlashAttribute("refreshCategoryTree", true);
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
	@RequestMapping(value="/ajaxQuery", produces={"application/json;charset=UTF-8"})
	public Object ajaxQuery(HttpServletRequest request) throws Exception {
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("Ajax请求数据成功！");
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
