package com.deertt.base.verify;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller//通过注解方式指定控制器
@RequestMapping("/verifyController.jhtml")//指定Controller访问路径
public class verifyController {

	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="cmd=buildVerifyCode")
	public void buildVerifyCode(HttpServletRequest request,HttpServletResponse response) throws Exception {

		VerifyCodeBuilder  builder = new VerifyCodeBuilder();
		//创建内存图像 
		BufferedImage image = builder.buildVerifyCodeImage(); 
		
		String sRand = builder.getsRand();
		
		//将认证码存入session 
		request.getSession().setAttribute("verifyCodeInSession",sRand); 

		//设置页面不缓存 
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		ImageIO.write(image, "JPEG", response.getOutputStream());
		
	}
	
	
	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="cmd=checkVerifyCode")
	public void checkVerifyCode(HttpServletRequest request,HttpServletResponse response) throws Exception {

		String verifyCode = request.getParameter("verifyCode");
		String verifyCodeInSession = (String)request.getSession().getAttribute("verifyCodeInSession"); 
		
		if(StringUtils.equalsIgnoreCase(verifyCode, verifyCodeInSession)) {
			//设置页面不缓存 
			response.getWriter().print("200");
			return;
		}
		
		response.getWriter().print("500");
		
	}
	
	
	
}

