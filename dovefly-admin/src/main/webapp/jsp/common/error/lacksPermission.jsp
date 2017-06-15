<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问受限</title>
<style type="text/css">
<!--

-->
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<div style="position:absolute; left: 100px; top: 150px">
		<img src="<%=request.getContextPath() %>/resources/images/key.jpg" width="100" height="100" align="middle"/>
		<span class="login_txt_bt">您没有访问此功能的权限！</span>
	</div> 
</body>
</html>
