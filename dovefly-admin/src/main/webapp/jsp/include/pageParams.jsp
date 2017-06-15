<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.frame.base.web.page.DvPageVo"%>
<%@ page import="com.deertt.frame.base.util.IGlobalConstants"%>
<%
	//翻页
	DvPageVo pageVo = new DvPageVo();
	try {
		if (request.getAttribute(IGlobalConstants.DV_PAGE_VO) != null) {
			pageVo = (DvPageVo) request.getAttribute(IGlobalConstants.DV_PAGE_VO);
		} else {
			String current_page = request.getParameter(IGlobalConstants.DV_CURRENT_PAGE);
			String page_size = request.getParameter(IGlobalConstants.DV_PAGE_SIZE);
			if (current_page != null) {
				pageVo.setCurrentPage(Integer.valueOf(current_page));
			}
			if (page_size != null) {
				pageVo.setPageSize(Integer.valueOf(page_size));
			}
		}
	} catch (Exception e) {

	}
%>
<input name="<%=IGlobalConstants.DV_CURRENT_PAGE %>" type="hidden" value="<%=(pageVo.getCurrentPage() == 0) ? 1 : pageVo.getCurrentPage()%>">
<input name="<%=IGlobalConstants.DV_PAGE_SIZE %>" type="hidden" value="<%=pageVo.getPageSize()%>">
