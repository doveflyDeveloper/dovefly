<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信扫码支付</title>
<style type="text/css">
<!--
body {
	font-family: "Microsoft Yahei";
	color: #ffffff;
	background: transparent;
}

.impowerBox {
	line-height: 1.6;
	position: relative;
	display: inline-block;
	width: 100%;
	vertical-align: middle;
	z-index: 1;
	text-align: center;
}

.impowerBox .title {
	text-align: center;
	font-size: 20px;
}

.impowerBox .qrcode {
	width: 280px;
	margin-top: 15px;
	border: 1px solid #E2E2E2;
}

.impowerBox .info {
	width: 280px;
	margin: 0 auto;
}

.impowerBox .status.status_browser {
	text-align: center;
}

.impowerBox .status.normal {
	margin-top: 15px;
	background-color: #232323;
	border-radius: 100px;
	-moz-border-radius: 100px;
	-webkit-border-radius: 100px;
	box-shadow: inset 0 5px 10px -5px #191919, 0 1px 0 0 #444444;
	-moz-box-shadow: inset 0 5px 10px -5px #191919, 0 1px 0 0 #444444;
	-webkit-box-shadow: inset 0 5px 10px -5px #191919, 0 1px 0 0 #444444;
}

.impowerBox .status {
	padding: 7px 14px;
	text-align: left;
}

.impowerBox .status p {
	font-size: 13px;
}

h1, h2, h3, h4, h5, h6, p {
	margin: 0;
	font-weight: 400;
}
-->
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/qrcode.min.js"></script>
</head>
<body>
<body style="padding: 50px; background-color: rgb(51, 51, 51);">
	<div class="main impowerBox">
		<div class="loginPanel normalPanel">
			<div class="title">微信支付</div>
			<div class="waiting panelContent">
				<div id="qrcode"></div>
				<div class="info">
					<div class="status status_browser js_status normal" id="wx_default_tip">
						<p>请使用微信扫描二维码付款</p>
						<p>admin.deertt.com</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div>${error_msg }</div>
	<script type="text/javascript">
		var qrcode = new QRCode(document.getElementById("qrcode"));
		qrcode.makeCode("${code_url }");
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