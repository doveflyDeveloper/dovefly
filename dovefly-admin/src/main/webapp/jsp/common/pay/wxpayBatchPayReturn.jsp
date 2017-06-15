<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信支付结果</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("");
	
	/** 返回 */
	function go_onclick() {
		window.location.href = "${redirect }";
	}
	
	$(document).ready(function() {
		
	});
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">支付结果&nbsp;</div>
					<div class="table_title_tip" rel=""></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="back" value="返回" class="button" onclick="go_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				${msg }
			</div>
		</div>
	</form>
</body>
</html>