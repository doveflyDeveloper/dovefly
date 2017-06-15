package com.deertt.module.sc.blog.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sc.blog.service.IBlogService;
import com.deertt.module.sc.blog.util.IBlogConstants;
import com.deertt.module.sc.blog.vo.BlogVo;
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
@RequestMapping("/blogController")
public class BlogController extends DvBaseController implements IBlogConstants {
	
	@Autowired
	protected IBlogService service;
	
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
		return JSP_PREFIX + "/insertBlog";
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
		BlogVo bean = service.find(id);
		bean.setId(null);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/insertBlog";
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
		BlogVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertBlog";
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
	public String insert(HttpServletRequest request, BlogVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setIssue_status(BlogVo.ISSUE_STATUS_DRAFT);
//		vo.setIssue_time(null);
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
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
	public String update(HttpServletRequest request, BlogVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
//		vo.setIssue_status(BlogVo.ISSUE_STATUS_DRAFT);
//		vo.setIssue_time(null);
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		service.update(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}
	
	/**
	 * 预览草稿
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/preview")
	public String preview(HttpServletRequest request, BlogVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setIssue_status(BlogVo.ISSUE_STATUS_DRAFT);
		vo.setIssue_time(null);
		request.setAttribute(REQUEST_BEAN, vo);
		return JSP_PREFIX + "/previewBlog";
	}
	
	/**
	 * 保存并发表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/saveAndIssue")
	public String saveAndIssue(HttpServletRequest request, BlogVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setIssue_status(BlogVo.ISSUE_STATUS_ISSUE);
//		vo.setIssue_time(DvDateHelper.getSysTimestamp());
		vo.setCity_id(LoginHelper.getCityId());
		vo.setCity_name(LoginHelper.getCityName());
		service.save(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}
	
	/**
	 * 发表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/issue/{id}")
	public String issue(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		BlogVo bean = service.find(id);
		bean.setIssue_status(BlogVo.ISSUE_STATUS_ISSUE);
//		bean.setIssue_time(DvDateHelper.getSysTimestamp());
		service.update(bean);
		return redirectWithTip(CONTROLLER + "/detail/" + bean.getId(), attr);
	}
	
	/**
	 * 取消发表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/cancelIssue/{id}")
	public String cancelIssue(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attr) throws Exception {
		BlogVo bean = service.find(id);
		bean.setIssue_status(BlogVo.ISSUE_STATUS_CANCEL);
		bean.setIssue_time(null);
		service.update(bean);
		return redirectWithTip(CONTROLLER + "/detail/" + bean.getId(), attr);
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
		BlogVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailBlog";
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
		List<BlogVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		
		
//		List<BlogVo> beans2 = service.queryByCondition("", null);
//		String str = "";
//		System.out.println("=======================================================================");
//		for (BlogVo g : beans2) {
//			str += getImages(g).substring(1);
//		}
//		System.out.println(str.replaceAll(",", "\",\""));
//		System.out.println("=======================================================================");
//
//		
		
		return JSP_PREFIX + "/listBlog";
	}
	
	
	public static String getImages(BlogVo blog) throws Exception {
		String str = ",";
		String image = blog.getImage();
		String description = blog.getDescription();		
		
//		int begin = images.indexOf("\"path\":\"");
//		int end = images.indexOf("\"}");
//		System.out.println(begin);
//		System.out.println(end);
//		while (begin >= 0 && end >= 0) {
//			str += images.substring(begin + 8, end) + ",";
//			
//			images = images.substring(end + 2);
//			begin = images.indexOf("\"path\":\"");
//			end = images.indexOf("\"}");
//		}
//		str = str.replaceAll(image + ",", "");
//		System.out.println(str);
		
//		String str2 = ",";
		str += image + ",";
//		int begin = description.indexOf("http://img.deertt.com/resource/blog/");
//		int end = description.indexOf("\"", begin);
////		System.out.println(begin);
////		System.out.println(end);
//		while (begin >= 0 && end >= 0) {
//			String img = description.substring(begin + "http://img.deertt.com/resource/blog/".length(), end) + ",";
//			if (!str.contains(img)) {
//				str += img;
//			}
//			
//			description = description.substring(end + 1);
//			begin = description.indexOf("http://img.deertt.com/resource/blog/");
//			end = description.indexOf("\"", begin);
//		}
//		str = str.replaceAll(image + ",", "");
//		System.out.println(str);
		return str;
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
			lQuery.add(DvSqlHelper.buildQueryStr("school_id", request.getParameter("school_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("title", request.getParameter("title"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("type", request.getParameter("type"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("issue_status", request.getParameter("issue_status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("issue_time", request.getParameter("issue_time_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("issue_time", request.getParameter("issue_time_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("issue_to", request.getParameter("issue_to"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
