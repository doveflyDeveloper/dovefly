package com.deertt.frame.base.web;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.frame.base.web.page.DvPageVo;
import com.deertt.frame.base.web.vo.HandleResult;
/**
 * 所有Controller的基类，负责一些公共操作，如分页信息封装，重定向跳转等
 * @author fengcm
 *
 */
public class DvBaseController implements IGlobalConstants {
	
	protected Logger logger = Logger.getLogger(getClass().getName());
	
//	//这个函数有问题，比如删除的时候
//	public String redirectPrePageWithTip(HttpServletRequest request) {
//		markHandleResult(request, true, "操作成功", null);
//		String preUrl = request.getHeader("referer");
//		System.out.println("--preUrl------------------------------------------------");
//		System.out.println(preUrl);
//		if (preUrl == null || preUrl.length() == 0) {
//			preUrl = "/";
//		}
//		return "redirect:" + preUrl;
//	}
	
	public String redirectWithTip(String url, RedirectAttributes attrs) {
		markHandleResult(attrs, true, "操作成功", null);
		return "redirect:" + url;
	}
	
	public String redirectWithTip(String url, RedirectAttributes attrs, String msg) {
		markHandleResult(attrs, true, msg, null);
		return "redirect:" + url;
	}
	
	public void markHandleResult(RedirectAttributes attrs, boolean flag, String msg, Object data) {
		HandleResult handleResult = new HandleResult();
		handleResult.setSuccess(flag);
		handleResult.setMessage(msg);
		handleResult.setData(data);
		attrs.addFlashAttribute("handleResult", handleResult);
	}

	/**
	 * 从request获取分页信息（首先从attribute取值，若为空则从parameter取值），
	 * 并根据当前分页信息和记录数，重新设置分页信息并放回request.setAttribute。
	 * @param request
	 * @param count
	 * @return
	 */
	public DvPageVo transctPageVo(HttpServletRequest request, int count) {
		DvPageVo pageVo = (DvPageVo) request.getAttribute(DV_PAGE_VO);

		int currentPage = 1;
		int pageSize = DvPageVo.DEFAULT_PAGE_SIZE;
		try {
			if (pageVo != null) {
				currentPage = pageVo.getCurrentPage();
				pageSize = pageVo.getPageSize();
			} else {
				String paramCurrentPage = request.getParameter(IGlobalConstants.DV_CURRENT_PAGE);
				String paramPageSize = request.getParameter(IGlobalConstants.DV_PAGE_SIZE);
				currentPage = paramCurrentPage == null ? 1 : Integer.parseInt(paramCurrentPage);
				pageSize = paramPageSize == null ? DvPageVo.DEFAULT_PAGE_SIZE : Integer.parseInt(paramPageSize);
			}
		} catch (Exception e) {

		}

		pageVo = new DvPageVo(count, pageSize);
		pageVo.setCurrentPage(currentPage);

		request.setAttribute(DV_PAGE_VO, pageVo);
		return pageVo;
	}
	
}
