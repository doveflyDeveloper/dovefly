<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//默认是radio
	String inputType = request.getParameter("inputType");
	if(!"checkbox".equalsIgnoreCase(inputType)){
		inputType = "radio";
	}
	
	//默认是all
	String referType = request.getParameter("referType");
	if(!"tree".equalsIgnoreCase(referType) && !"search".equalsIgnoreCase(referType)){
		referType = "all";
	}
	
	//回调函数
	String callback = request.getParameter("callback");

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>系统功能管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
	
	});
</script>
</head>
<body>
	<div class="border_div" >
		<div class="padding_2_div">
			<DL id="sub_tab">
			<%if("all".equalsIgnoreCase(referType) || "tree".equalsIgnoreCase(referType)){ %>
				<DT>组织机构树</DT>
				<DD>
					<div>
						<iframe rel="<%=request.getContextPath()%>/orgController/referenceOrgTree?inputType=<%=inputType %>&callback=<%=callback %>" width="100%" height="288px" frameborder="0" scrolling="auto"></iframe>
					</div>
				</DD>
			<%
			}
			if("all".equalsIgnoreCase(referType) || "search".equalsIgnoreCase(referType)){ %>
				<DT>组织机构查询</DT>
				<DD>
					<div>
						<iframe rel="<%=request.getContextPath()%>/orgController/reference?inputType=<%=inputType %>&callback=<%=callback %>" width="100%" height="288px" frameborder="0" scrolling="auto"></iframe>
					</div>
				</DD>
			<%} %>
			</DL>
		</div>
	</div>
</body>
</html>
