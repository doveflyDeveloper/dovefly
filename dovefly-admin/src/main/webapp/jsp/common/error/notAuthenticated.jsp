<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>认证失败</title>
<style type="text/css">
<!--

-->
</style>
<script language="javascript" type="text/javascript">
	var second = document.getElementById('totalSecond').textContent;

	if (navigator.appName.indexOf("Explorer") > -1)
	{
		second = document.getElementById('totalSecond').innerText;
	} else
	{
		second = document.getElementById('totalSecond').textContent;
	}


	setInterval("redirect()", 1000);
	function redirect()
	{
		if (second < 0)
		{
			top.window.location.href = '/login.jsp';
		} else
		{
			if (navigator.appName.indexOf("Explorer") > -1)
			{
				document.getElementById('totalSecond').innerText = second--;
			} else
			{
				document.getElementById('totalSecond').textContent = second--;
			}
		}
	}
</script>
</head>
<body>
	您还没有登陆，或会话已超时并失效，请重新登录！<br/>
	<span id="totalSecond">5</span>

	<img src="/resources/images/key1" />
	<form id="form" name="form" method="get" target="top">
	
	</form>
</body>
</html>
