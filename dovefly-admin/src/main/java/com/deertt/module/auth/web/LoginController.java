package com.deertt.module.auth.web;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.web.DvBaseController;
import com.deertt.frame.base.web.vo.HandleResult;
import com.deertt.module.auth.util.LoginHelper;
import com.deertt.module.auth.util.ValidateCode;
import com.deertt.module.sys.user.service.IUserService;
import com.deertt.module.sys.user.vo.UserVo;
import com.deertt.utils.helper.DvHttpHelper;
import com.deertt.utils.helper.encryt.Digest;

@Controller
public class LoginController extends DvBaseController {
	
	@Autowired
	protected IUserService userService;
	
	/**
	 * 跳转到登录页面 
	 * @param user
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	/**
	 * 微信登陆 
	 * @param user
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doWechatLogin")
	public String doWechatLogin(HttpServletRequest request, RedirectAttributes attrs){
		String result = "redirect:/login";
		String code = request.getParameter("code");
		if (code == null) {
			attrs.addFlashAttribute("loginMsg", "用户拒绝微信授权登录！");
			return "redirect:/login";
		}
		
		String appid = "wx9881984beab9d84d";
		String appSecret = "d2f0c55c75928a2df05b1c7a8ccc9441";
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
//		logger.info("微信登录授权URL：" + url);
		String wxResult = DvHttpHelper.get(url);
		/*正确返回
		{ 
			"access_token":"ACCESS_TOKEN", 
			"expires_in":7200, 
			"refresh_token":"REFRESH_TOKEN",
			"openid":"OPENID", 
			"scope":"SCOPE",
			"unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
		错误返回
		{"errcode":40029,"errmsg":"invalid code"}
		*/
//		logger.info("微信登录授权结果：" + wxResult);
		
		JSONObject json = JSONObject.fromObject(wxResult);
		
		String unionid = json.optString("unionid", null);
		if (unionid == null) {
			attrs.addFlashAttribute("loginMsg", "用户微信授权失败！");
			result = "redirect:/login";
		} else {
			UserVo user = userService.findUserByWechatUnionId(unionid);
			if (user == null) {
				attrs.addFlashAttribute("loginMsg", "此微信用户系统内不存在！");
				result = "redirect:/login";
//			} else if (user.isCustomerRole()) {
//				attrs.addFlashAttribute("loginMsg", "此用户类型未在本系统授权登录！");
//				result = "redirect:/login";
			}
			Subject currUser = SecurityUtils.getSubject();
			if (!currUser.isAuthenticated()) {
				//result = checkUser(currUser, user.getAccount(), user.getPassword(), attrs);
			} else {// 重复登录，注销上一个用户再登陆
				String account = (String) currUser.getPrincipal();
				if (!account.equalsIgnoreCase(user.getAccount())) {// 如果登录名不同
					currUser.logout();
					//result = checkUser(currUser, user.getAccount(), user.getPassword(), attrs);
				}
				result = "redirect:/index";
			}
		}
		
		return result;
	}
	
	/**
	 * 登陆 
	 * @param user
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/doLogin", produces={"application/json;charset=UTF-8"})
	public Object doLogin(UserVo user, HttpSession session, HttpServletRequest request){
		HandleResult result = new HandleResult();
		String message = "";
		UserVo loginUser = null;
		
		user.setPassword(Digest.hex2Base64(Digest.SHA1(user.getAccount() + user.getPassword(), Digest.Cipher.HEX)));
		String sessionValidateCode = (String) session.getAttribute("validateCode");
		String submitCode = WebUtils.getCleanParam(request, "validateCode");
		if (StringUtils.isEmpty(submitCode) || !StringUtils.equalsIgnoreCase(sessionValidateCode, submitCode.toLowerCase())) {
			message = "验证码错误！";
		} else {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
			try {
				Subject currUser = SecurityUtils.getSubject();
//				token.setRememberMe(true);
				currUser.login(token);
				loginUser = userService.findFullUserInfoByAccount(user.getAccount());
				userService.updateLoginInfo(loginUser.getId());
				LoginHelper.setUser(loginUser);
				result.setSuccess(true);
				result.setData(loginUser);
			} catch (UnknownAccountException uae) {
				message = "此账号不存在！";
			} catch (IncorrectCredentialsException ice) {
				message = "账号密码错误！";
			} catch (LockedAccountException lae) {
				message = "此账号已停用！";
			} catch (AuthenticationException ae) {
				message = "账号未授权！";
			} catch (Exception e) {
				message = "登录异常！";
			} finally {
				token.clear();
			}
		}
		
		result.setMessage(message);
		return result;
	}
	
    /**
     * 跳转到登录后首页
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(){
        return "main";
    }

	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "login";
	}

	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null);
		request.getSession().setAttribute("validateCode", verifyCode);
		response.setContentType("image/jpeg");
		BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 100, 32, 10, true, Color.WHITE, Color.BLACK, null);
		ImageIO.write(bim, "JPEG", response.getOutputStream());
	}
}
