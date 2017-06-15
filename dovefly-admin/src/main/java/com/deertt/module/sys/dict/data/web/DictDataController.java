package com.deertt.module.sys.dict.data.web;

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
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.dict.data.service.IDictDataService;
import com.deertt.module.sys.dict.data.util.IDictDataConstants;
import com.deertt.module.sys.dict.data.vo.DictDataVo;
import com.deertt.module.sys.dict.type.service.IDictTypeService;
import com.deertt.module.sys.dict.type.vo.DictTypeVo;
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
@RequestMapping("/dictDataController")
public class DictDataController extends DvBaseController implements IDictDataConstants {
	
	@Autowired
	protected IDictDataService service;
	
	@Autowired
	protected IDictTypeService dictTypeService;
	/**
	 * 新增页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/create/{type_id}")
	public String create(HttpServletRequest request, @PathVariable("type_id") Integer type_id) throws Exception {
		DictTypeVo dicTypeVo = dictTypeService.find(type_id);
		DictDataVo bean = new DictDataVo();
		bean.setType_id(dicTypeVo.getId());
		bean.setSort(0);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertDictData";
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
		DictDataVo bean = service.find(id);
		bean.setId(null);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertDictData";
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
		DictDataVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertDictData";
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
	public String insert(HttpServletRequest request, DictDataVo vo) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
//		vo.setSort(vo.getSort() == null ? 0 : vo.getSort());
		service.insert(vo);
		DvDictionaryFactory.getSingleton().reloadAll();
		return "redirect:" + CONTROLLER + "/queryAll/" + vo.getType_id();
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
	public String update(HttpServletRequest request, DictDataVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
//		vo.setSort(vo.getSort() == null ? 0 : vo.getSort());
		service.update(vo);
		DvDictionaryFactory.getSingleton().reloadAll();
		return "redirect:" + CONTROLLER + "/queryAll/" + vo.getType_id();
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
	public String delete(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		DictDataVo bean = service.find(id);
		service.delete(id);
		DvDictionaryFactory.getSingleton().reloadAll();
		return "redirect:" + CONTROLLER + "/queryAll/" + bean.getType_id();
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
	public String deleteMulti(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids) throws Exception {
		String[] idStrs = ids.split(",");
		Integer type_id = null;
		if (idStrs != null && idStrs.length != 0) {
			DictDataVo bean = service.find(Integer.valueOf(idStrs[0]));
			type_id = bean.getType_id();
		}
		service.delete(DvStringHelper.parseStringToIntegerArray(ids, ","));
		DvDictionaryFactory.getSingleton().reloadAll();
		return "redirect:" + CONTROLLER + "/queryAll/" + type_id;
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
		DictDataVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailDictData";
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/query/{type_id}")
	public String query(HttpServletRequest request, @PathVariable("type_id") Integer type_id) throws Exception {
		String queryCondition = getQueryCondition(request);
		if (StringUtils.isEmpty(queryCondition)) {
			queryCondition = DvSqlHelper.buildQueryStr("type_id", type_id, DvSqlHelper.TYPE_NUMBER_EQUAL);
		} else {
			queryCondition += " and " + DvSqlHelper.buildQueryStr("type_id", type_id, DvSqlHelper.TYPE_NUMBER_EQUAL);
		}
		DvPageVo pageVo = super.transctPageVo(request, service.getRecordCount(queryCondition));
		String orderStr = "sort asc";//String orderStr = "create_date desc";
		List<DictDataVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		request.setAttribute("type_id", type_id);
		return JSP_PREFIX + "/listDictData";
	}
	
	/**
	 * 查询全部，清除所有查询条件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/queryAll/{type_id}")
	public String queryAll(HttpServletRequest request, @PathVariable("type_id") Integer type_id) throws Exception {	
		request.setAttribute(REQUEST_QUERY_CONDITION, "");
		return query(request, type_id);
	}
	
	/**
	 * 启用字典值
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/enable/{ids}")
	public String enable(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids) throws Exception {
		String[] idStrs = ids.split(",");
		Integer type_id = null;
		if (idStrs != null && idStrs.length != 0) {
			DictDataVo bean = service.find(Integer.valueOf(idStrs[0]));
			type_id = bean.getType_id();
		}
		service.enable(DvStringHelper.parseStringToIntegerArray(ids, ","));
		return "redirect:" + CONTROLLER + "/queryAll/" + type_id;
	}
	
	/**
	 * 禁用字典值
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/disable/{ids}")
	public String disable(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attrs) throws Exception {
		String[] idStrs = ids.split(",");
		Integer type_id = null;
		if (idStrs != null && idStrs.length != 0) {
			DictDataVo bean = service.find(Integer.valueOf(idStrs[0]));
			type_id = bean.getType_id();
		}
		service.disable(DvStringHelper.parseStringToIntegerArray(ids, ","));
		return "redirect:" + CONTROLLER + "/queryAll/" + type_id;
	}
	
	/**
	 * 数据字典排序页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/find4Sort/{type_id}")
	public String find4Sort(HttpServletRequest request, @PathVariable("type_id") Integer type_id) throws Exception {
		String queryCondition = getQueryCondition(request);
		if (StringUtils.isEmpty(queryCondition)) {
			queryCondition = DvSqlHelper.buildQueryStr("type_id", type_id, DvSqlHelper.TYPE_NUMBER_EQUAL);
		} else {
			queryCondition += " and " + DvSqlHelper.buildQueryStr("type_id", type_id, DvSqlHelper.TYPE_NUMBER_EQUAL);
		}
		
		String orderStr = "sort asc";
		List<DictDataVo> beans = service.queryByCondition(queryCondition, orderStr, -1, -1);
		request.setAttribute(REQUEST_BEANS, beans);
		request.setAttribute("type_id", type_id);
		return JSP_PREFIX + "/sortDictData";
	}
	
	/**
	 * 对当前数据字典排序
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/sort/{type_id}")
	public String sort(HttpServletRequest request, @PathVariable("type_id") Integer type_id, RedirectAttributes attrs) throws Exception {
		String sort_ids = request.getParameter("sort_ids");
		if(StringUtils.isNotEmpty(sort_ids)){
			service.sort(sort_ids);
		}
		return "redirect:" + CONTROLLER + "/find4Sort/" + type_id;
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
			lQuery.add(DvSqlHelper.buildQueryStr("type_id", request.getParameter("type_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("dic_key", request.getParameter("dic_key"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("dic_value", request.getParameter("dic_value"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CHAR_EQUAL));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
