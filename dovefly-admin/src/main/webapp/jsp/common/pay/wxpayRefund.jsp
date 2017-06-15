<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信退款</title>
<style type="text/css">
<!--
-->
</style>
</head>
<body>
	<div>${error_msg }</div>
	<script type="text/javascript">
		var checkPayStatus = function () {
			$.get("${check_url }", {}, function(result) {
				if (result && result.success) {
					window.clearInterval(intervalId);
					location.href = "${return_url }";
				}
			})
		}
		var intervalId = window.setInterval(checkPayStatus, 2000);
	</script>
</body>
</html>
