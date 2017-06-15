package com.deertt.module.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaFormAuthenticationFilter extends FormAuthenticationFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaFormAuthenticationFilter.class);  

	public CaptchaFormAuthenticationFilter() {
	}

	@Override
	/**
	 * 登录验证
	 */
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		CaptchaUsernamePasswordToken token = createToken(request, response);
		try {
			/* 图形验证码验证 */
			doCaptchaValidate((HttpServletRequest) request, token);
			Subject subject = getSubject(request, response);
			subject.login(token);// 正常验证
			logger.info(token.getUsername() + "登录成功");
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			logger.info(token.getUsername() + "登录失败--" + e);
			return onLoginFailure(token, e, request, response);
		}
	}

	// 验证码校验
	protected void doCaptchaValidate(HttpServletRequest request,
			CaptchaUsernamePasswordToken token) {
		// session中的图形码字符串
		String captcha = (String) request.getSession().getAttribute("validateCode");
		// 比对
		//测试环境不比对验证码
		//		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
		//			throw new IncorrectCaptchaException("验证码错误！");
		//		}
	}

	@Override
	protected CaptchaUsernamePasswordToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = WebUtils.getCleanParam(request, "account");
		String password = WebUtils.getCleanParam(request, "password");
		String captcha = WebUtils.getCleanParam(request, "validateCode");
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);

		return new CaptchaUsernamePasswordToken(username,
				password.toCharArray(), rememberMe, host, captcha);
	}

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	// 保存异常对象到request
	@Override
	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		request.setAttribute(getFailureKeyAttribute(), ae);
	}

}
