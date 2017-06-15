<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.frame.base.web.page.DvPageVo"%>
<%@ page import="com.deertt.frame.base.util.IGlobalConstants"%>
<%
	//翻页
	DvPageVo pageVo = new DvPageVo();
	if (request.getAttribute(IGlobalConstants.DV_PAGE_VO) != null) {
		pageVo = (DvPageVo) request.getAttribute(IGlobalConstants.DV_PAGE_VO);
	}

%>
<div>
	<div style="display: inline; float: left;">
		<span>当前第&nbsp;<%=pageVo.getCurrentPage()%>/<%=pageVo.getPageCount()%>&nbsp;页，每页显示&nbsp;<%=pageVo.getPageSize()%>&nbsp;条，共&nbsp;<%=pageVo.getRecordCount()%>&nbsp;条纪录</span>
	</div>
	<div style="display: inline; float: right;">
		<span>
			<%if (pageVo.getCurrentPage() > 1) {%><a href="JavaScript:firstPage();"> <%}%><img src="<%=request.getContextPath()%>/resources/images/<%=(pageVo.getCurrentPage() > 1) ? "page_first.gif" : "page_first1.gif"%>" width="21" height="16" align="top" /><%if (pageVo.getCurrentPage() > 1) {%></a><%}%> 
			<%if (pageVo.getCurrentPage() > 1) {%><a href="JavaScript:upPage();"><%}%><img src="<%=request.getContextPath()%>/resources/images/<%=(pageVo.getCurrentPage() > 1) ? "page_pre.gif" : "page_pre1.gif"%>" width="41" height="16" align="top" /><%if (pageVo.getCurrentPage() > 1) {%></a><%}%>
			<%if (pageVo.getPageCount() >= pageVo.getCurrentPage() + 1) {%><a href="JavaScript:downPage();"><%}%><img src="<%=request.getContextPath()%>/resources/images/<%=(pageVo.getPageCount() >= pageVo.getCurrentPage() + 1) ? "page_next.gif" : "page_next1.gif"%>" width="41" height="16" align="top"/><%if (pageVo.getPageCount() >= pageVo.getCurrentPage() + 1) {%></a><%}%> 
			<%if (pageVo.getPageCount() > pageVo.getCurrentPage()) {%><a href="JavaScript:lastPage();"><%}%><img src="<%=request.getContextPath()%>/resources/images/<%=(pageVo.getPageCount() > pageVo.getCurrentPage()) ? "page_last.gif" : "page_last1.gif"%>" width="21" height="16" align="top" /><%if (pageVo.getPageCount() > pageVo.getCurrentPage()) {%></a><%}%> 
			<span style="vertical-align: top;">&nbsp;转到第</span><input name="dv_current_page_input" type="text" style="height: 10px; width: 20px; line-height: 10px" size="5" value="<%=pageVo.getCurrentPage()%>" onkeyup="if(event.keyCode == 13){goAppointedPage();}"/><span style="vertical-align: top;">页</span> 
			<img src="<%=request.getContextPath()%>/resources/images/page_go.jpg" width="29" height="16" align="top" onClick="javascript:goAppointedPage()" /> 
			
			<input name="dv_current_page" type="hidden" value="<%=(pageVo.getCurrentPage() == 0) ? 1 : pageVo.getCurrentPage() %>">
			<input name="dv_page_size" type="hidden" value="<%=pageVo.getPageSize()%>">
		</span>
	</div>
</div>
<script type="text/javascript">

//翻页相关
function firstPage() { //首页
	form.dv_current_page.value = 1;
	form.submit();
}
function upPage() {  //上一页
	form.dv_current_page.value--;
	form.submit();
}
function downPage() {  //下一页
	form.dv_current_page.value++;
	form.submit();
}
function lastPage() {  //末页
	form.dv_current_page.value=<%=pageVo.getPageCount()%>;
	form.submit();
}
function goAppointedPage() { //跳转到某页
	checkPageNoKey();
	form.submit();
}
function checkPageNoKey() {
	form.dv_current_page.value = form.dv_current_page_input.value;
	if (form.dv_current_page_input.value <= 0) {
		form.dv_current_page.value = 1;
	}
}
</script>