package com.deertt.frame.base.web.tag;

import com.deertt.frame.base.util.IGlobalConstants;
import com.deertt.frame.base.web.page.DvPageVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @author: fengcm
 * Date: 11-10-19
 * Time: 上午10:02.
 */
public class DvPageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private DvPageVo pageVo;
	
	public DvPageVo getPageVo() {
		return pageVo;
	}

	public void setPageVo(DvPageVo pageVo) {
		this.pageVo = pageVo;
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			if(pageVo == null) 
				pageVo = (DvPageVo) request.getAttribute(IGlobalConstants.DV_PAGE_VO);
			if(pageVo == null)
				pageVo = new DvPageVo();
			
			StringBuilder sb = new StringBuilder();
			sb.append("<div>\r\n");
			sb.append("<div style='display: inline; float: left;'>\r\n");
			sb.append("<span>当前第&nbsp;" + pageVo.getCurrentPage() + "/" + pageVo.getPageCount() + "&nbsp;页，每页显示&nbsp;" + pageVo.getPageSize() + "&nbsp;条，共&nbsp;" + pageVo.getRecordCount() + "&nbsp;条纪录</span>\r\n");
			sb.append("</div>\r\n");
			sb.append("<div style='display: inline; float: right;'>\r\n");
			sb.append("<span>\r\n");
			
			if(pageVo.getCurrentPage() > 1)
				sb.append("<a href='JavaScript:firstPage();'><img src='" + request.getContextPath() + "/images/page_first.gif' width='21' height='16' align='top' /></a> \r\n");
			else
				sb.append("<img src='" + request.getContextPath() + "/images/page_first1.gif' width='21' height='16' align='top' /> \r\n");
				
			if(pageVo.getCurrentPage() > 1)
				sb.append("<a href='JavaScript:upPage();'><img src='" + request.getContextPath() + "/images/page_pre.gif' width='41' height='16' align='top' /></a> \r\n");
			else
				sb.append("<img src='" + request.getContextPath() + "/images/page_pre1.gif' width='41' height='16' align='top' /> \r\n");
			
			if(pageVo.getPageCount() >= pageVo.getCurrentPage() + 1)
				sb.append("<a href='JavaScript:downPage();'><img src='" + request.getContextPath() + "/images/page_next.gif' width='41' height='16' align='top' /></a> \r\n");
			else
				sb.append("<img src='" + request.getContextPath() + "/images/page_next1.gif' width='41' height='16' align='top' /> \r\n");

			if(pageVo.getPageCount() > pageVo.getCurrentPage())
				sb.append("<a href='JavaScript:lastPage();'><img src='" + request.getContextPath() + "/images/page_last.gif' width='21' height='16' align='top' /></a> \r\n");
			else
				sb.append("<img src='" + request.getContextPath() + "/images/page_last1.gif' width='21' height='16' align='top' /> \r\n");

			sb.append("<span style='vertical-align: top;'>&nbsp;转到第</span><input name='dv_current_page_input' type='text' style='height: 10px; width: 20px; line-height: 10px' size='5' value='" + pageVo.getCurrentPage() + "' onkeyup='if(event.keyCode == 13){goAppointedPage();}'/><span style='vertical-align: top;'>页</span> \r\n");
			
			sb.append("<img src='" + request.getContextPath() + "/images/page_go.jpg' width='29' height='16' align='top' onClick='javascript:goAppointedPage()' /> \r\n");
			sb.append("<input name='dv_current_page' type='hidden' value='" + (pageVo.getCurrentPage() == 0 ? 1 : pageVo.getCurrentPage()) + "'/> \r\n");
			sb.append("<input name='dv_page_size' type='hidden' value='" + pageVo.getPageSize() + "'/> \r\n");
			sb.append("<span>\r\n");
			sb.append("</div>\r\n");
			sb.append("</div>\r\n");
			
			
			sb.append("<script type='text/javascript' language='javascript'>\r\n");
			sb.append("//翻页相关\r\n");
			sb.append("function firstPage() { //首页\r\n");
			sb.append("form.dv_current_page.value = 1;\r\n");
			sb.append("form.submit();\r\n");
			sb.append("}\r\n");
			
			
			sb.append("function upPage(){  //上一页\r\n");
			sb.append("form.dv_current_page.value--;\r\n");
			sb.append("form.submit();\r\n");
			sb.append("}\r\n");
			
			sb.append("function downPage(){  //下一页\r\n");
			sb.append("form.dv_current_page.value++;\r\n");
			sb.append("form.submit();\r\n");
			sb.append("}\r\n");
			sb.append("function lastPage(){  //末页\r\n");
			sb.append("form.dv_current_page.value=<%=pageVo.getPageCount()%>;\r\n");
			sb.append("form.submit();\r\n");
			sb.append("}\r\n");
			sb.append("function goAppointedPage() { //跳转到某页\r\n");
			sb.append("checkPageNoKey();\r\n");
			sb.append("form.submit();\r\n");
			sb.append("}\r\n");
			sb.append("function checkPageNoKey() {\r\n");
			sb.append("form.dv_current_page.value = form.dv_current_page_INPUT.value;\r\n");
			sb.append("if (form.dv_current_page_INPUT.value <= 0) {\r\n");
			sb.append("form.dv_current_page.value = 1;\r\n");
			sb.append("}\r\n");
			sb.append("}\r\n");
			sb.append("</script>\r\n");
			
			this.pageContext.getOut().print(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
}
