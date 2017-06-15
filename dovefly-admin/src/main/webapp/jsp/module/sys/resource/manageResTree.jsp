<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.resource.vo.ResourceVo" %>
<%@ page import="java.util.List" %>
<%
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
	
	/** 查看 */
	function detail_onclick(id){
		form.target="resourceMain";
		form.action="<%=request.getContextPath()%>/resourceController/detail/" + id;
		form.submit();
	}
	
	var setting = {
		data: {
			key: {
				title: "t"
			},
			simpleData: {
				enable: true
			}
		},
		view: {
			fontCss: getFont,
			nameIsHTML: true
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
		{ id:"<%=vo.getId() %>", pId:"<%=vo.getParent_id() %>", t:"", name:"<%=vo.getName() %>", click:"javascript:detail_onclick('<%=vo.getId() %>')"<%=("1".equals(vo.getIs_leaf())) ? "" : ", isParent:true, open:true, font:{'font-weight':'bold'}" %><%=("000".equals(vo.getCode())) ? ", iconOpen:'../resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/img/diy/1_open.png', iconClose:'../resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/img/diy/1_close.png'" : "" %>}<%= (i+1 == beans.size()) ? "" : "," %>
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
					<!--  -->
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



