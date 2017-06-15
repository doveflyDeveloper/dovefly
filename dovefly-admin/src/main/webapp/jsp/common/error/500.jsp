<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<%
	String message = "";
	if(exception != null) {
		message = exception.getMessage() == null ? "系统问题，请联系管理员" : exception.getMessage();
	}
%>
<head>
<title>异常</title>
</head>
<body style="background-color: white;">
	<div id="error_img" align="center" style="padding-top: 50px">
		<img src="<%=request.getContextPath() %>/resources/images/error.gif" height="211" width="329" /><br/>
	</div>
	<div id="error_message">
		错误信息:<%=message%><br/><br/>
		<button onclick="document.getElementById('error_stack_trace').style.display = 'block';">查看详细</button>
	</div>
	<div id="error_stack_trace" style="display:none; color:gray;">
		<hr/>
		<%if(exception != null) {exception.printStackTrace(new java.io.PrintWriter(out));}%>
		<hr/>
	</div>
</body>
</html>
