<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>帮助指南<%=isModify ? "修改" : "新增"%></title>
<link href="<%=request.getContextPath()%>/resources/js/umeditor/themes/default/css/umeditor.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.config.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.min.js"></script>

<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("helpController");
	
	/** 保存 */
	function save_onclick() {
		if(validateForm()){
			if(<%=isModify%>){
				update_onclick();
			} else {
				insert_onclick();
			}
		}
	}
	
	/** 新增保存 */
	function insert_onclick() {
		formHelper.jSubmit(formHelper.buildInsertAction());
	}
	
	/** 修改保存 */
	function update_onclick() {
		formHelper.jSubmit(formHelper.buildUpdateAction());
	}
	
	$(document).ready(function() {
		var um = UM.getEditor('description');
		um.options.imageUrl = context_path + "/ueditor/image/help";

	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">帮助指南<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_help"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>功能模块：</td>
						<td class="field">
							<dv:select name="module" dicKeyword="DIC_SYS_HELP_MODULE" hasEmpty="true" defaultValue="${bean.module }" attributes="inputName='模块' validate='notNull'" ></dv:select>
						</td>
						<td class="label">操作页面：</td>
						<td class="field">
							<input type="text" name="operation" class="input" inputName="操作页面" value="<c:out value="${bean.operation }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field"></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field" colspan="3">
							<script id="description" name="description" type="text/plain" inputName="内容">${bean.description }</script>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
