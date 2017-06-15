<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<meta name="renderer" content="webkit">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/skin.css"/>
<title>页面头</title>
<style type="text/css">

<!--
.bt{
	padding: 2px 2px 0px 2px;
	margin: 4px;
}

-->
</style>
<script type="text/javascript">
	function logout() {
		if (confirm("您确定要退出系统吗？")){
			top.location = "<%=request.getContextPath()%>/logout";
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0">
	<table class="admin_topbg" width="100%" height="64" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="61%" height="64">
				<div class="logo">
					
				</div>
			</td>
			<td width="39%" valign="top" align="right">
				<span class="admin_txt">欢迎您，${sessionScope.DV_USER_VO.real_name }</span>
				<span><input type="image" class="bt" src="resources/images/out.gif" alt="退出" onClick="logout();"></span>
			</td>
		</tr>
	</table>
</body>
</html>
