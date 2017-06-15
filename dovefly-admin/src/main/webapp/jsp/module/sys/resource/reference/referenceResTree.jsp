<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.resource.vo.ResourceVo" %>
<%@ page import="java.util.List" %>
<%
	//默认是radio
	String inputType = request.getParameter("inputType");
	if(!"checkbox".equalsIgnoreCase(inputType)){
		inputType = "radio";
	}
	//回调函数
	String callback = request.getParameter("callback");
	
	List<ResourceVo> beans = (List<ResourceVo>) request.getAttribute("beans");

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>系统功能管理</title>
<style type="text/css">
<!--
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
-->
</style>
<script type="text/javascript"> 
	var formHelper = new DvFormHelper("resourceController");
	
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
			obj.org_code = nodes[i].org_code;
			obj.org_name = nodes[i].name;
			datas.push(obj);
		}	
		parent.parent.<%=callback%>(datas);
		return true;//若要关闭窗口，可返回true;
	}
	
	/** 清空 */
	function clear_onclick() {
		var datas = new Array();
		var obj = new Object();
		obj.id = "";
		obj.org_code = "";
		obj.org_name = "";
		datas.push(obj);
		parent.parent.<%=callback%>(datas);
		return true;//若要关闭窗口，可返回true;
	}
	
	/** 展开/折叠全部 */
	function expandAll_onclick(btn){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		if($(btn).val() == '折叠'){
			zTree.expandAll(false);//展开全部
			$(btn).val('展开');
		} else {
			zTree.expandAll(true);//折叠全部
			$(btn).val('折叠');
		}
	}
	
//--------------------------------------------------------
	var setting = {
		async: {
			enable: true,
			url:"<%=request.getContextPath()%>/resourceController/asyncReferenceResTree",
			autoParam:["id=id"],
			otherParam:{"otherParam":"zTreeAsyncTest"}
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			fontCss: getFont,
			nameIsHTML: true
		},
		check: {
			enable: true,
			chkStyle: "<%=inputType%>",
			radioType: "all",
			chkboxType: { "Y" : "", "N" : "" }
		}
	};
	
	function getFont(treeId, node) {
		return node.font ? node.font : {};
	}

	var zNodes = [
<%
if(beans != null && beans.size() > 0) {
	for(int i = 0; i < beans.size(); i++){
		ResourceVo vo = beans.get(i);
%>
		{ id:"<%=vo.getId() %>", pId:"<%=vo.getParent_id() %>", name:"<%=vo.getName() %>", code:"<%=vo.getCode() %>"<%=("1".equals(vo.getIs_leaf())) ? "" : ", isParent:true, open:true, font:{'font-weight':'bold'}" %><%=("000".equals(vo.getCode())) ? ", iconOpen:'../html/JQuery_zTree_v3.5.15/css/zTreeStyle/img/diy/1_open.png', iconClose:'../html/JQuery_zTree_v3.5.15/css/zTreeStyle/img/diy/1_close.png'" : "" %>}<%= (i+1 == beans.size()) ? "" : "," %>
<%
	}
}
%>
	];

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
</script> 
</head>
<body>
<form id="form" name="form" method="get">
	<div class="border_div" style="margin:1px;">
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">资源树&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<!-- <input type="button" name="expandAll" value="折叠" class="button" onclick="expandAll_onclick(this);"/> -->
					<input type="button" name="clear" value="清空" class="button" onclick="clear_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</form>
</body>
</html>



