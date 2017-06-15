<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.help.util.IHelpConstants"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助指南信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	
	function feedback_onclick(module) {
		dvAlert("暂未实现，敬请期待！");
	}
	
	function edit_onclick(module) {
		top.rightFrame.addTab('72', '帮助指南', '<%=request.getContextPath()%>/helpController/edit/' + module);
	}
	
	$(document).ready(function() {
	
	});
	
</script>
</head>
<body>
    <div style="margin: 1px; padding:10px; border: 1px solid #FFB400;max-width:900px;max-height:450px;min-width:400px;min-height:50px;overflow:auto;">
        <div class="tip_title">操作指南</div>
        <div class="tip_list">
        	<c:if test="${bean.description == null || bean.description == ''}">
        	暂无此内容！<br/><br/>
        	</c:if>
			<c:out value="${bean.description }" escapeXml="false"/>
        </div>
        <div style="text-align:right;">
			<shiro:hasPermission name="<%=IHelpConstants.PERM_WRITE %>">
			<span class="left_ts"><a id="bindWexin" href="javascript:edit_onclick('${module }');">编辑此帮助</a></span>&nbsp;&nbsp;&nbsp;
			</shiro:hasPermission>
			<span class="left_ts"><a id="bindWexin" href="javascript:feedback_onclick('${module }');">反馈问题</a></span>&nbsp;&nbsp;&nbsp;
        </div>
    </div>
</body>
</html>
