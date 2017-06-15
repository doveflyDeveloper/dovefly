<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path;
String error = request.getAttribute("loginMsg") == null ? "" : request.getAttribute("loginMsg").toString();
%>
<!DOCTYPE html>
<html>
<head>
<title>登录-汀鹿商城订单管理平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<meta name="renderer" content="webkit">
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
<link rel="icon" type="image/x-icon" href="/favicon.ico"/>
<link rel="bookmark" type="image/x-icon" href="/favicon.ico"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/login.css">
<style type="text/css">
<!--
#validateCodeImg {
	vertical-align: top;
	border: solid 1px #CCC;
	cursor: pointer;
}

-->
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.min.js"></script>
<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
<script type="text/javascript">

	if(window.top != window.self) {
		window.top.location.href = "<%=basePath%>/login.jsp";
	}

	function reloadValidateCode() {
		document.getElementById("validateCodeImg").src = "<%=basePath%>/validateCode?_=" + new Date();
	}

	$(document).ready(function() {
		var obj = new WxLogin({
			id:"output",
			appid: "wx9881984beab9d84d",
			scope: "snsapi_login",
			redirect_uri: "<%=request.getScheme() + "://"+request.getServerName()%>/doWechatLogin",
			state: "",
			style: "black",
			href: "https://ohebpbf7x.qnssl.com/weixin.css"
		});

		$(".content .con_right .left").click(function (e) {
			$(this).css({ "color": "#333333", "border-bottom": "2px solid #2e558e" });
			$(".content .con_right .right").css({ "color": "#999999", "border-bottom": "2px solid #dedede" });
			$(".content .con_right ul .con_r_left").css("display", "block");
			$(".content .con_right ul .con_r_right").css("display", "none");
		});
		$(".content .con_right .right").click(function (e) {
			$(this).css({ "color": "#333333", "border-bottom": "2px solid #2e558e" });
			$(".content .con_right .left").css({ "color": "#999999", "border-bottom": "2px solid #dedede" });
			$(".content .con_right ul .con_r_right").css("display", "block");
			$(".content .con_right ul .con_r_left").css("display", "none");
		});
	});

</script>
</head>
<body style="overflow:hidden">
	<div class="pagewrap">
		<div class="main">
			<div class="header"></div>
			<div class="content">
			<div class="con_left"></div>
				<div class="con_right">
					<div class="con_r_top">
						<a href="javascript:;" class="left" style="color: #999999; border-bottom-width: 2px; border-bottom-style: solid; border-bottom-color: #dedede;">微信登录</a>
						<a href="javascript:;" class="right" style="color: #333333; border-bottom-width: 2px; border-bottom-style: solid; border-bottom-color: #2e558e;">账号登录</a>
					</div>
					<ul>
						<li class="con_r_left" style="display: none;">
							<div class="erweima">
								<div class="qrcode">
									<div id="output" style="width: 100%; position: relative"></div>
								</div>
								<!-- <font color="red"><%=error %></font> -->
							</div>
							<!--
							<div style="height: 70px">
								<p>微信扫码 安全便捷</p>
							</div> -->
						</li>

						<li class="con_r_right" style="display: block;">
							<center><font color="red"><%=error %></font></center>
							<form name="form1" method="post" action="<%=basePath%>/doLogin" autocomplete="off">
				 			<div class="user">
								<div><span class="user-icon"></span>
									<input type="text" id="account" name="account" placeholder="输入账号" value="" autocomplete="off">
								</div>

								<div><span class="mima-icon"></span>
									<input type="password" id="password" name="password" placeholder="输入密码" value="" autocomplete="off">
								</div>

								<div><span class="yzmz-icon"></span>
									<input id="vdcode" type="text" name="validateCode" placeholder="验证码" value="" style=" width:150px;" maxlength="4" autocomplete="off">
									<img id="validateCodeImg" src="<%=basePath%>/validateCode" alt="看不清？点击更换" title="看不清？点击更换" onclick="javascript:reloadValidateCode();"/>
								</div>
							</div><br>
							<button id="btn_Login" type="submit">登 录</button>
							</form>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>