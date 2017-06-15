<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货仓<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("warehouseController");
	
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
					<div class="table_title">货仓<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_warehouse"></div>
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
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
						<td class="label">仓管员：</td>
						<td class="field"><c:out value="${bean.manager_name }"/></td>
					</tr>
					<tr>
						<td class="label">账户金额：</td>
						<td class="field"><c:out value="${bean.balance_amount }"/></td>
						<td class="label">待收款金额：</td>
						<td class="field"><c:out value="${bean.halfway_amount }"/></td>
					</tr>
					<tr>
						<td class="label">待提款金额：</td>
						<td class="field"><c:out value="${bean.locked_amount }"/></td>
						<td class="label"><span class="required_red">* </span>起送价：</td>
						<td class="field">
							<input type="text" name="start_amount" class="input" inputName="起送价" value="<c:out value="${bean.start_amount }"/>" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>货仓：</td>
						<td class="field">
							<input type="text" name="warehouse_name" class="input" inputName="货仓" value="<c:out value="${bean.warehouse_name }"/>" maxlength="10" validate="notNull"/>
						</td>
						<td class="label">货仓描述：</td>
						<td class="field">
							<input type="text" name="warehouse_desc" class="input" inputName="货仓描述" value="<c:out value="${bean.warehouse_desc }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">配送区域：</td>
						<td class="field">
							<input type="text" name="warehouse_area" class="input" inputName="配送区域" value="<c:out value="${bean.warehouse_area }"/>" maxlength="100" validate=""/>
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
		<input type="hidden" name="city_id" value="<c:out value="${bean.city_id }"/>"/>
		<input type="hidden" name="city_name" value="<c:out value="${bean.city_name }"/>"/>
		<input type="hidden" name="manager_id" value="<c:out value="${bean.manager_id }"/>"/>
		<input type="hidden" name="manager_name" value="<c:out value="${bean.manager_name }"/>"/>
		<input type="hidden" name="balance_amount" value="<c:out value="${bean.balance_amount }"/>"/>
		<input type="hidden" name="halfway_amount" value="<c:out value="${bean.halfway_amount }"/>"/>
		<input type="hidden" name="locked_amount" value="<c:out value="${bean.locked_amount }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
