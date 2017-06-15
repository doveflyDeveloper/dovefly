package com.deertt.module.sys.user.web;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.project.ApplicationConfig;
import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.sys.dict.DvDictionaryFactory;
import com.deertt.module.sys.role.service.IRoleService;
import com.deertt.module.sys.role.vo.RoleVo;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.util.IUserConstants;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.utils.helper.DvHttpHelper;
import com.deertt.utils.helper.DvSqlHelper;
import com.deertt.utils.helper.DvVoHelper;
import com.deertt.utils.helper.date.DvDateHelper;
import com.deertt.utils.helper.encryt.Digest;
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
@RequestMapping("/userController")
public class UserController extends DvBaseController implements IUserConstants {
	
	@Autowired
	protected IUserService service;
	
	@Autowired
	protected IRoleService roleService;
	
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
		return JSP_PREFIX + "/insertUser";
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
		UserVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/insertUser";
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
	public String insert(HttpServletRequest request, UserVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markCreateStamp(request, vo);
		vo.setCity_name(DvDictionaryFactory.getSingleton().getDictData("DIC_SYS_CITY", vo.getCity_id()));
//		vo.setSchool_name(regionService.find(vo.getSchool_id()).getName());
		vo.setPassword(Digest.hex2Base64(Digest.SHA1(vo.getAccount() + vo.getPassword(), Digest.Cipher.HEX)));
		vo.setCoin_quantity(0);
		service.saveAndAuthorizeRole(vo);
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
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
	public String update(HttpServletRequest request, UserVo vo, RedirectAttributes attrs) throws Exception {
		DvVoHelper.markModifyStamp(request, vo);
		UserVo bean = service.find(vo.getId());
		bean.setAddress(vo.getAddress());
		bean.setEmail(vo.getEmail());
	    bean.setSchool_id(vo.getSchool_id());
	    bean.setSchool_name(vo.getSchool_name());
		bean.setMobile(vo.getMobile());
		bean.setReal_name(vo.getReal_name());
		bean.setRemark(vo.getRemark());
		bean.setModify_at(vo.getModify_at());
		bean.setModify_by(vo.getModify_by());
		service.update(bean);
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}
	
	/**
	 * 修改用户部分信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@ResponseBody
	@RequestMapping(value="/change", produces={"application/json;charset=UTF-8"})
	public Object change(HttpServletRequest request, UserVo vo) throws Exception {
		HandleResult result = new HandleResult();
		DvVoHelper.markModifyStamp(request, vo);
		service.change(vo);
		
//		service.test(vo.getId());
		
		UserVo user = LoginHelper.getUser();
		user.setAddress(vo.getAddress());
		user.setEmail(vo.getEmail());
		user.setReal_name(vo.getReal_name());
		user.setAlipay_account(vo.getAlipay_account());
		result.setSuccess(true);
		result.setMessage("修改成功！");
		return result;
	}
	
	/**
	 * 导出列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/exportList/{ids}")
	public void exportList(HttpServletRequest request, HttpServletResponse response, @PathVariable(REQUEST_IDS) String ids) throws Exception {		
		response.reset();  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("用户信息导出_" + DvDateHelper.getJoinedSysDateTime() + ".xls", "UTF-8"));  
		response.setContentType("application/octet-stream; charset=utf-8");  
		
		if (StringUtils.isEmpty(ids) || "all".equals(ids)) {
			DvPageVo pageVo = new DvPageVo(10000, 10000);
			request.setAttribute(DV_PAGE_VO, pageVo);
		} else {
			String queryCondition = "id IN (" + ids + ")";
			request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
		}
		query(request); //通过查询取到beans，保证查询列表跟批量导出数据一致 
		List<UserVo> beans = (List<UserVo>)request.getAttribute(REQUEST_BEANS);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(REQUEST_BEANS, beans);
		
		DvDictionaryFactory dic = DvDictionaryFactory.getSingleton();
		for (UserVo o : beans) {
//			o.setUser_type(dic.getDictData("DIC_USER_TYPE", o.getUser_type()));
//			o.setShop_status(dic.getDictData("DIC_SHOP_WORK_STATUS", o.getShop_status()));
			o.setStatus(dic.getDictData("DIC_STATUS", o.getStatus()));
		}

		String projectBasePath = request.getSession().getServletContext().getRealPath(File.separator);
		String templateFileName = projectBasePath + XLS_TEMPLATE_BASE_PATH + "/exportList.xls";
		DvExcelWriter.writeExcel(templateFileName, dataMap, response.getOutputStream());
	}
	
	/**
	 * 修改用户密码（用户修改自己的密码，需要原密码）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@ResponseBody
	@RequestMapping(value="/resetPwd", produces={"application/json;charset=UTF-8"})
	public Object resetPwd(HttpServletRequest request, UserVo vo) throws Exception {
		HandleResult result = new HandleResult();
		String oldPwd = Digest.hex2Base64(Digest.SHA1(vo.getAccount() + request.getParameter("old_password"), Digest.Cipher.HEX));
		UserVo user = service.find(vo.getId());
		if (oldPwd.equals(user.getPassword())) {
			DvVoHelper.markModifyStamp(request, vo);
			vo.setPassword(Digest.hex2Base64(Digest.SHA1(vo.getAccount() + vo.getPassword(), Digest.Cipher.HEX)));
			vo.setPwd_reset("0");
			service.changePwd(vo);
			LoginHelper.getUser().setAlipay_account(vo.getPassword());
			result.setSuccess(true);
			result.setMessage("密码修改成功！");
		} else {
			result.setSuccess(false);
			result.setMessage("原密码输入错误！");
		}
		return result;
	}
	
	/**
	 * 修改用户密码（管理员强制重置密码，不需要原密码）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@ResponseBody
	@RequestMapping(value="/forceResetPwd", produces={"application/json;charset=UTF-8"})
	public Object forceResetPwd(HttpServletRequest request, UserVo vo) throws Exception {
		HandleResult result = new HandleResult();
		DvVoHelper.markModifyStamp(request, vo);
		vo.setPassword(Digest.hex2Base64(Digest.SHA1(vo.getAccount() + vo.getPassword(), Digest.Cipher.HEX)));
		vo.setPwd_reset("1");
		service.changePwd(vo);
		result.setSuccess(true);
		result.setMessage("密码重置成功！");
		return result;
	}
	
	/**
	 * 启用
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/enable/{ids}")
	public String enable(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attr) throws Exception {
		service.enable(DvStringHelper.parseStringToIntegerArray(ids, ","));
		return redirectWithTip(DEFAULT_REDIRECT, attr);
	}
	
	/**
	 * 禁用
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/disable/{ids}")
	public String disable(HttpServletRequest request, @PathVariable(REQUEST_IDS) String ids, RedirectAttributes attr) throws Exception {
		service.disable(DvStringHelper.parseStringToIntegerArray(ids, ","));
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
		UserVo bean = service.find(id);
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/detailUser";
	}
	
	/**
	 * 查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_READ)
	@RequestMapping("/userInfo")
	public String userInfo(HttpServletRequest request) throws Exception {
		UserVo bean = service.findFull(LoginHelper.getUserId());
		request.setAttribute(REQUEST_BEAN, bean);
		return JSP_PREFIX + "/userInfo";
	}
	
	/**
	 * 查看
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * 		
	 */
	@RequestMapping("/qrcode/{type}")
	public String qrcode(HttpServletRequest request, @PathVariable("type") String type) throws Exception {
//		String url = "http://wx.deertt.com/show/qrcode";
		String url = ApplicationConfig.DEERTT_QRCODE_URL;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("user", LoginHelper.getUserId());
		JSONObject result = JSONObject.fromObject(DvHttpHelper.post(url, params));
		
		request.setAttribute("qrcode", result.get("qrcode").toString().replace("https", "http"));
		return JSP_PREFIX + "/qrcode";
	}
	
	/**
	 * 异步请求，返回JSON
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/reloadUser", produces={"application/json;charset=UTF-8"})
	public Object reloadUser(HttpServletRequest request) throws Exception {
		UserVo bean = service.find(LoginHelper.getUserId());
		UserVo sessionUser = LoginHelper.getUser();
		sessionUser.setWechat_account(bean.getWechat_account());
		sessionUser.setWechat_avatar(bean.getWechat_avatar());
		sessionUser.setWechat_id(bean.getWechat_id());
		sessionUser.setWechat_unionid(bean.getWechat_unionid());
//		sessionUser.setWechat_version(bean.getWechat_version());
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setData(bean);
		result.setMessage("Ajax请求数据成功！");
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
	@RequestMapping(value="/loadOperationManagers", produces={"application/json;charset=UTF-8"})
	public Object loadOprationManagers(HttpServletRequest request) throws Exception {
		List<UserVo> beans = service.queryByCondition(" id IN (SELECT user_id FROM sys_authority WHERE role_id = " + RoleVo.ROLE_OPERATION_MANAGER + ") AND city_id = " + LoginHelper.getCityId() + " AND status = '1'", null);
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setData(beans);
		result.setMessage("Ajax请求数据成功！");
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
		String orderStr = "";//String orderStr = "create_at desc";
		List<UserVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());
		request.setAttribute(REQUEST_BEANS, beans);
		return JSP_PREFIX + "/listUser";
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
		return JSP_PREFIX + "/referenceUser";
	}
	
	/**
	 * 授权页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/findForAuthRole/{id}")
	public String findForAuthRole(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id) throws Exception {
		UserVo bean = service.findFull(id);
		request.setAttribute(REQUEST_BEAN, bean);
		String queryCondition = null;
		List<RoleVo> roleBeans = roleService.queryByCondition(queryCondition, null, -1, -1);
		if(roleBeans != null){
			for (RoleVo roleVo : roleBeans) {
				if(bean.getRoles() != null){
					for (RoleVo selectedVo : bean.getRoles()) {
						if(selectedVo.getId().equals(roleVo.getId())){
							roleVo.setChecked(true);
							break;
						}
					}
				}
			}
		}
		request.setAttribute("roleBeans", roleBeans);
		request.setAttribute(REQUEST_IS_MODIFY, 1);
		return JSP_PREFIX + "/findForAuthRole";
	}
	
	/**
	 * 给用户授权
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(PERM_WRITE)
	@RequestMapping("/authRole/{id}")
	public String authRole(HttpServletRequest request, @PathVariable(REQUEST_ID) Integer id, RedirectAttributes attrs) throws Exception {
		String role_ids_str = request.getParameter("role_ids");
		service.executeAuthorize(id, DvStringHelper.parseStringToIntegerArray(role_ids_str, ","));
		return redirectWithTip(DEFAULT_REDIRECT, attrs);
	}
	
	/**
	 * 首页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/welcome")
	public String welcome(HttpServletRequest request) throws Exception {
		UserVo bean = service.findFull(LoginHelper.getUserId());
		
		request.setAttribute(REQUEST_BEAN, bean);
		
		if (bean.isHeadquartersRole()) {
			return "welcome_headquarters";
		} else if (bean.isCityManagerRole()){
			return "welcome_citymanager";
		} else if (bean.isOperationManagerRole()){
			return "welcome_operationmanager";
		} else if (bean.isShopkeeperRole()) {
			return "welcome_shopkeeper";
		}
		
		return "welcome";
	}
	
	/**
	 * 检查账号是否已存在
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/existsAccount", produces={"application/json;charset=UTF-8"})
	public Object existsAccount(HttpServletRequest request) throws Exception {
		UserVo user = service.findUserByAccount(request.getParameter("account"));
		HandleResult result = new HandleResult();
		result.setSuccess(true);
		result.setMessage("Ajax请求数据成功！");
		result.setData(user == null ? null : user.getAccount());
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
			lQuery.add(DvSqlHelper.buildQueryStr("id", request.getParameter("selected_user_ids"), DvSqlHelper.TYPE_CUSTOM, "not in(", ")"));
			if (LoginHelper.isHeadquartersRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", request.getParameter("city_id"), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isCityManagerRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("city_id", LoginHelper.getCityId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			} else if (LoginHelper.isOperationManagerRole()) {
				lQuery.add("school_id IN (SELECT id FROM sys_school WHERE manager_id = " + LoginHelper.getUserId() + ")");
			} else if (LoginHelper.isShopkeeperRole()) {
				lQuery.add(DvSqlHelper.buildQueryStr("id", LoginHelper.getUserId(), DvSqlHelper.TYPE_NUMBER_EQUAL));
			}
			
			if ("job".equals(request.getParameter("reference"))) {
				lQuery.add("manage_warehouse_id != 0");
			}
			lQuery.add(DvSqlHelper.buildQueryStr("school_name", request.getParameter("school_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("real_name", request.getParameter("real_name"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("mobile", request.getParameter("mobile"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("wechat_account", request.getParameter("wechat_account"), DvSqlHelper.TYPE_CHAR_LIKE));
			lQuery.add(DvSqlHelper.buildQueryStr("status", request.getParameter("status"), DvSqlHelper.TYPE_CUSTOM, "='", "'"));
			queryCondition = DvSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
		}
		return queryCondition;
	}
	
}
