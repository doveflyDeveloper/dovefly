<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//默认是radio
	String inputType = request.getParameter("inputType");
	if(!"checkbox".equalsIgnoreCase(inputType)){
		inputType = "radio";
	}
	//回调函数
	String callback = request.getParameter("callback");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>区域参照</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<style type="text/css">
<!--
//.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
//.ztree li ul.level0 {padding:0; background:none;}

-->
</style>
<script type="text/javascript">
	var zNodes = ${nodes };
	
	/** 确定 */
	function ok_onclick() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		if(!nodes || !nodes.length) {
	  		dvTip("请先选择记录！");
	  		return false;
		}
		var datas = new Array();
		for (var i = 0; i < nodes.length; i++) {
			var obj = new Object();
			obj.id = nodes[i].id;
			obj.code = nodes[i].code;
			obj.name = nodes[i].name;
			obj.full_name = nodes[i].full_name;
			datas.push(obj);
		}	
		parent.<%=callback%>(datas);
		return true;//若要关闭窗口，可返回true;
	}
	
	/** 清空 */
	function clear_onclick() {
		var datas = new Array();
		var obj = new Object();
		obj.id = "";
		obj.code = "";
		obj.name = "";
		obj.full_name = "";
		datas.push(obj);
		parent.<%=callback%>(datas);
		return true;//若要关闭窗口，可返回true;
	}
	
	var setting = {
		async: {
			enable: true,
			url:"<%=request.getContextPath()%>/regionController/loadRegions",
			autoParam:["id"],
			//autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		},
		check: {
			enable: true,
			chkStyle: "<%=inputType%>",
			radioType: "all",
			chkboxType: { "Y" : "", "N" : "" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
</script> 
</head>
<body>
	<div class="border_div" style="margin:1px;">
		<!--
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">区域&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
				</div>
			</div>
		</div>
		-->
		<div class="padding_2_div">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>