<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户申请<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("userApplyController");
	
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
					<div class="table_title">用户申请<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_user_apply"></div>
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
						<td class="label">城市id：</td>
						<td class="field">
							<input type="text" name="city_id" class="input" inputName="城市id" value="<c:out value="${bean.city_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">城市：</td>
						<td class="field">
							<input type="text" name="city_name" class="input" inputName="城市" value="<c:out value="${bean.city_name }"/>" maxlength="20" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">用户id：</td>
						<td class="field">
							<input type="text" name="user_id" class="input" inputName="用户id" value="<c:out value="${bean.user_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">用户：</td>
						<td class="field">
							<input type="text" name="user_name" class="input" inputName="用户" value="<c:out value="${bean.user_name }"/>" maxlength="20" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">申请类型：</td>
						<td class="field">
							<input type="text" name="apply_type" class="input" inputName="申请类型" value="<c:out value="${bean.apply_type }"/>" maxlength="1" validate=""/>
						</td>
						<td class="label">通知状态：</td>
						<td class="field">
							<input type="text" name="notify_status" class="input" inputName="通知状态" value="<c:out value="${bean.notify_status }"/>" maxlength="2" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">通知消息：</td>
						<td class="field">
							<input type="text" name="notify_msg" class="input" inputName="通知消息" value="<c:out value="${bean.notify_msg }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
		<input type="hidden" name="create_by" value="<c:out value="${bean.create_by }"/>"/>
		<input type="hidden" name="create_at" value="<c:out value="${bean.create_at }"/>"/>
		<input type="hidden" name="modify_by" value="<c:out value="${bean.modify_by }"/>"/>
		<input type="hidden" name="modify_at" value="<c:out value="${bean.modify_at }"/>"/>
	</form>
</body>
</html>
