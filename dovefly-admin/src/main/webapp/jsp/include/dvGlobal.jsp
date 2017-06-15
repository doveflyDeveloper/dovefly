<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/jsp/common/error/500.jsp" %>
<%@ page import="com.deertt.utils.helper.DvConfig" %>
<%@ page import="com.deertt.frame.base.web.vo.HandleResult" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://tags.deertt.com/form" prefix="dv"%>
<%@ taglib uri="http://functions.deertt.com/field" prefix="dvf"%>

<%
	if(DvConfig.systemDebugMode()) {
		System.out.println(request.getRequestURL());
	}

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
	//String referer = request.getHeader("referer");
	//System.out.println("referer=" + referer);
	
	//if (referer == null || !referer.startsWith(basePath)) {
	//	response.sendRedirect(basePath);
	//}

%>
<script language="javascript"> 
	var context_path = "<%=request.getContextPath()%>";
	var currServerBeforeClient =  <%=System.currentTimeMillis() %> - (new Date()).getTime();
</script>
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<meta name="renderer" content="webkit">
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" type="image/x-icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/frame.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/menu.css"/>
<!-- <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/skin.css"/> -->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/temp.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/common.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/validate.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jquery-powerFloat/powerFloat.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/kandyTabs4/kandytabs.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/jBox/Skins/Gray/jbox.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/uploadify-3.2/uploadify.css"/>

<!-- 

 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-powerFloat/jquery-powerFloat-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/kandyTabs4/kandytabs.pack.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/date/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jBox/jquery.jBox.src.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jBox/i18n/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dragsort-0.5.1/jquery.dragsort-0.5.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/uploadify-3.2/jquery.uploadify-zh.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/uploadify-3.2/uploadify-common.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dv-form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dv-map.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dv-behavior.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dv-validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/dv-tools.js"></script>


<script language="javascript"> 
	/** 页面跳转后在新页面显示上一步的操作结果 */
<%
	HandleResult handleResult = (HandleResult) request.getAttribute("handleResult");
	String tipMessage = "操作成功！";
	String tipType = "success";
	boolean tipShowForLoad = false;
	if(handleResult != null){
		tipShowForLoad = true;
		if(!handleResult.isSuccess()){
			tipType = "error";
		}
		if(handleResult.getMessage() != null){
			tipMessage = handleResult.getMessage();
		}
	}
%>
	function showHandleResult(message, type){
		if(!message) message = "操作成功！";
		if(!type) type = "success";
		dvTip(message, type);
	}
<%if(tipShowForLoad){%>	
	$(document).ready(function() {
		if(Math.abs(top.serverBeforeClient - currServerBeforeClient) < 2000){
			setTimeout(function() {
				showHandleResult('<%=tipMessage %>', '<%=tipType %>');
			}, 300);
		}
	});
<%}%>
</script>
