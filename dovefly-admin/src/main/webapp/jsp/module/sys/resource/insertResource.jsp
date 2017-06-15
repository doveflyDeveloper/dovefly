<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统功能<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("resourceController");
	
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
	
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">系统功能<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_resource"></div>
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
						<td class="label"><span class="required_red">* </span>资源编号：</td>
						<td class="field">
							<input type="text" name="code" class="readonly" inputName="资源编号" value="<c:out value="${bean.code }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>资源名称：</td>
						<td class="field">
							<input type="text" name="name" class="input" inputName="资源名称" value="<c:out value="${bean.name }"/>" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">父级资源编号：</td>
						<td class="field">
							<input type="text" name="parent_code" class="readonly" inputName="父级资源编号" value="<c:out value="${bean.parent_code }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">父级资源名称：</td>
						<td class="field">
							<input type="text" name="parent_name" class="readonly" inputName="父级资源名称" value="<c:out value="${bean.parent_name }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>资源类型：</td>
						<td class="field">
							<dv:radio name="type" dicKeyword="DIC_RESOURCE_TYPE" defaultValue="${bean.type }" attributes="inputName='资源类型' validate='notNull'"/>
						</td>
						<td class="label">资源权限：</td>
						<td class="field">
							<input type="text" name="permission" class="input" inputName="资源权限" value="<c:out value="${bean.permission }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">资源地址：</td>
						<td class="field" colspan="3">
							<input type="text" name="url" class="full_input" inputName="资源地址" value="<c:out value="${bean.url }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field" colspan="3">
							<textarea name="remark" class="textarea" inputName="备注" validate=""><c:out value="${bean.remark }"/></textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="parent_id" value="<c:out value="${bean.parent_id }"/>"/>
		<input type="hidden" name="level" value="<c:out value="${bean.level }"/>"/>
		<input type="hidden" name="is_leaf" value="<c:out value="${bean.is_leaf }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
