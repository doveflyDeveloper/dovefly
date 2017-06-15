<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.role.vo.RoleVo" %>
<%@ page import="java.util.List" %>
<%
	List<RoleVo> roleBeans = (List<RoleVo>) request.getAttribute("roleBeans");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户授权</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<style type="text/css">
<!--
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("roleController");
	
	/** 保存 */
	function save_onclick() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		if(!nodes || !nodes.length) {
	  		dvTip("请先选择记录！");
	  		return false;
		}
		var ids = $(nodes).map(function() {
			return this.id;
		}).get().join();
		$("input[name='role_ids']").val(ids);
		formHelper.jSubmit(formHelper.buildAction("userController", "authRole", "${bean.id }"));
	}
	
//--------------------------------------------------------
	var setting = {
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
			chkStyle: "checkbox",
			radioType: "all",
			chkboxType: { "Y" : "ps", "N" : "ps" }
		}
	};
	
	function getFont(treeId, node) {
		return node.font ? node.font : {};
	}

	var zNodes = [
<%
if(roleBeans != null && roleBeans.size() > 0) {
	for(int i = 0; i < roleBeans.size(); i++){
		RoleVo vo = roleBeans.get(i);
%>
		{ id:"<%=vo.getId() %>", pId:"0", name:"<%=vo.getName() %>", checked:<%=vo.isChecked() %>}<%= (i+1 == roleBeans.size()) ? "" : "," %>
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
				<div class="table_title">系统用户&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="ok" value="保存" class="button" onclick="save_onclick();"/>
					<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<table class="detail_table">
				<tr>
					<td class="label">姓名：</td>
					<td class="field"><c:out value="${bean.real_name }"/></td>
					<td class="label">账号：</td>
					<td class="field"><c:out value="${bean.account }"/></td>
				</tr>
			</table>
		</div>
		<div class="padding_2_div">
			<DL id="sub_tab">
				<DT>角色</DT>
				<DD>
					<ul id="treeDemo" class="ztree"></ul>
				</DD>
			</DL>
		</div>
		<div class="padding_2_div">
			
		</div>
	</div>
	<input type="hidden" name="role_ids" value=""/>
</form>
</body>
</html>
