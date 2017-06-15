<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统消息通知<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("notificationController");
	
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
	
	
	/** 新增商品 */
	function add_onclick() {
		var selected_user_ids = $("#user_ids").val();
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/userController/reference?status=1&selected_user_ids=" + selected_user_ids, "选择用户", 900, 450, "referenceUser_callback", "checkbox", "search", options);
	}
	
	/** 新增商品回调 */
	function referenceUser_callback(datas) {
		//dvCloseDialog();// 关闭参照窗口
		// 枚举（循环）对象的所有属性
		var selected_user_ids = $("#user_ids").val();
		for (i in datas) {
			var obj = datas[i];
			selected_user_ids += "," + obj.user_id;
		}
		
		if (selected_user_ids.indexOf(',') == 0) {
			selected_user_ids = selected_user_ids.substring(1);
		}
		
		$("#user_ids").val(selected_user_ids);
		
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
					<div class="table_title">系统消息通知<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/notification"></div>
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
						<td class="label"><span class="required_red">* </span>通知渠道：</td>
						<td class="field">
							<dv:radio name="notify_way" dicKeyword="DIC_NOTIFY_WAY" defaultValue="${param.notify_way }" attributes="inputName='通知渠道' validate='notNull'"/>
						</td>
						<td class="label"><span class="required_red">* </span>设定通知时间：</td>
						<td class="field">
							<input type="text" name="expect_notify_time" class="input Wdate" inputName="设定通知时间" value="${fn:substring(bean.expect_notify_time, 0, 19) }" validate="notNull" format="both"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>通知消息：</td>
						<td class="field" colspan="3">
							<textarea name="message" class="input" style="width:80%; height:120px;" inputName="通知消息" validate="notNull"><c:out value="${bean.message }"/></textarea>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>接收用户群：</td>
						<td class="field">
							<label><input type="radio" class="radio" name="send_to" value="all" inputname="接收用户群" validate="notNull">所有用户</label>&nbsp;&nbsp;
							<label><input type="radio" class="radio" name="send_to" value="shopkeeper" inputname="接收用户群" validate="notNull">所有店长</label>&nbsp;&nbsp;
							<label><input type="radio" class="radio" name="send_to" value="customer" inputname="接收用户群" validate="notNull">所有普通用户</label>&nbsp;&nbsp;
							<label><input type="radio" class="radio" name="send_to" value="someone" inputname="接收用户群" validate="notNull">指定用户</label>&nbsp;&nbsp;
						</td>
						<td class="label"><span class="required_red">* </span>限定城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="" ignoreValues="1" hasEmpty="true" attributes="inputName='限定城市' validate='notNull'" />
						</td>
					</tr>
					<tr id="tr_someone">
						<td class="label">接收用户：</td>
						<td class="field" colspan="3">
							<input type="button" name="add" value="添加用户" class="button" onclick="add_onclick();"/><br>
							<textarea id="user_ids" name="user_ids" class="input" style="width:80%; height:60px;" inputName="接收用户" validate=""></textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
