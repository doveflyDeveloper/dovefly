<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String chartJSON = request.getAttribute("chartJSON") == null ? "{}" : (String)request.getAttribute("chartJSON");
%>	
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/qrcode.min.js"></script>
<style type="text/css">
<!--
#user_qrcode {
	
}

#user_qrcode table {
	width:99px;
}
#user_qrcode table tr {
	height:3px;
	width:99px;
}
#user_qrcode table tr td {
	height:3px;
}
-->
</style>
<script type="text/javascript">
	
	function goChangePwd_onclick() {
		top.rightFrame.addTab('26', '我的个人信息', '<%=request.getContextPath()%>/userController/userInfo');
	}
	
	$(document).ready(function() {
		
		if ('${bean.pwd_reset }' == '1') {//密码重置提醒
			var msg = '您的账号已被管理员重置，<br>请立即前往“<span class="left_ts" style="cursor:pointer;text-decoration:underline;" onclick="goChangePwd_onclick();">我的个人信息</span>”修改密码！';
			$.jBox.messager(msg, '重要提示', 5000);
		}
		
	});
	
</script>
</head>
<body>
	<div style="height: 4px"></div>
	<div class="border_div" >
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">个人资料&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu"></div>
			</div>
		</div>
		<div class="padding_2_div">
			
		</div>
	</div>
</body>
</html>