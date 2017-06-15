package com.deertt.module.sys.notification.web;

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
import com.deertt.module.sys.notification.service.INotificationService;
import com.deertt.module.sys.notification.util.INotificationConstants;
import com.deertt.module.sys.notification.vo.NotificationVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;
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
@RequestMapping("/notificationController")
public class NotificationController extends DvBaseController implements INotificationConstants {
	
	@Autowired
	protected INotificationService service;
	
	@Autowired
	protected IUserService userService;
	
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
		return JSP_PREFIX + "/insertNotification";
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
		NotificationVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertNotification";
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
	public String insert(HttpServletRequest request, RedirectAttributes attr) throws Exception {
		String send_to = request.getParameter("send_to");
		String city_id = request.getParameter("city_id");
		String user_ids = request.getParameter("user_ids");
		String queryCondition = "status = '1'";
		if (StringUtils.isNotEmpty(city_id)) {
			queryCondition += " and city_id = " + city_id;
		}
		if (StringUtils.isNotEmpty(user_ids)) {
			queryCondition += " and id in (" + user_ids + ")";
		}
		
		if ("all".equals(send_to)) {
			
		} else if ("shopkeeper".equals(send_to)) {
			queryCondition += " and manage_shop_id > 0";
		} else if ("customer".equals(send_to)) {
			queryCondition += " and (manage_warehouse_id + manage_shop_id + manage_market_id) = 0";  
		} else if ("someone".equals(send_to)) {
			queryCondition += " and id in (" + user_ids + ")";
		}
		
		List<UserVo> users = userService.queryByCondition(queryCondition, null);;
		
		String message = request.getParameter("message");
		String notify_way = request.getParameter("notify_way");
		
		for (UserVo user : users) {
			service.addNotification(user.getId(), user.getReal_name(), user.getWechat_id(), user.getMobile(), IUserConstants.TABLE_NAME + "-" + user.getId() + "-" + "notify", notify_way, message);
		}
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
	public String update(HttpServletRequest request, NotificationVo vo, RedirectAttributes attr) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		service.update(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attr);
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
		NotificationVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailNotification";
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
		List<NotificationVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listNotification";
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
		return JSP_PREFIX + "/referenceNotification";
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
			lQuery.add(DvSqlHelper.buildQueryStr("user_id", request.getParameter("user_id_from"), DvSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("user_id", request.getParameter("user_id_to"), DvSqlHelper.TYPE_NUMBER_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("user_name", request.getParameter("user_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("wechat_id", request.getParameter("wechat_id"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("mobile", request.getParameter("mobile"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_type", request.getParameter("notify_type"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_way", request.getParameter("notify_way"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("message", request.getParameter("message"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("expect_notify_time", request.getParameter("expect_notify_time_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("expect_notify_time", request.getParameter("expect_notify_time_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_time", request.getParameter("notify_time_from"), DvSqlHelper.TYPE_DATE_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_time", request.getParameter("notify_time_to"), DvSqlHelper.TYPE_DATE_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_limit_times", request.getParameter("notify_limit_times_from"), DvSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_limit_times", request.getParameter("notify_limit_times_to"), DvSqlHelper.TYPE_NUMBER_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_times", request.getParameter("notify_times_from"), DvSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_times", request.getParameter("notify_times_to"), DvSqlHelper.TYPE_NUMBER_LESS_EQUAL));
			lQuery.add(DvSqlHelper.buildQueryStr("notify_status", request.getParameter("notify_status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			lQuery.add(DvSqlHelper.buildQueryStr("remark", request.getParameter("remark"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
