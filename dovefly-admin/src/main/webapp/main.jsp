<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<meta name="renderer" content="webkit">
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
<link rel="icon" type="image/x-icon" href="/favicon.ico"/>
<link rel="bookmark" type="image/x-icon" href="/favicon.ico"/>
<title>进销存管理系统</title>
<script type="text/javascript">
	top.serverBeforeClient = <%=System.currentTimeMillis() %> - (new Date()).getTime();
	
	function checkLeave() {
		//loginout退出操作
		window.event.returnValue = "关闭此页面将导致用户退出系统。";
	}

</script>
</head>
<frameset rows="64,*" frameborder="no" border="0" framespacing="0">
	<frame name="topFrame" src="<%=request.getContextPath()%>/top.jsp" noresize="noresize" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"/>
	<frameset cols="234,*">
	<!-- <%=request.getContextPath()%>/resourceController/resourceTree2 -->
		<frame name="leftFrame" src="<%=request.getContextPath()%>/resourceController/resourceTree" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="no"/>
		<frame name="rightFrame" src="<%=request.getContextPath()%>/contentFrame.jsp" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="no"/>
	</frameset>
</frameset>
<noframes>
	<body>浏览器不支持Frame</body>
</noframes>
</html>
