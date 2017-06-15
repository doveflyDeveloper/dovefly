<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值交易<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("rechargeController");
	
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
					<div class="table_title">充值交易<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/recharge"></div>
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
						<td class="label">交易单号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="input" inputName="交易单号" value="<c:out value="${bean.bill_code }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">充值时间：</td>
						<td class="field">
							<input type="text" name="recharge_time" class="input Wdate" inputName="充值时间" value="${fn:substring(bean.recharge_time, 0, 19) }" validate="" format="both"/>	
						</td>
					</tr>
					<tr>
						<td class="label">充值金额：</td>
						<td class="field">
							<input type="text" name="recharge_amount" class="input" inputName="充值金额" value="<c:out value="${bean.recharge_amount }"/>" validate=""/>
						</td>
						<td class="label">收款用户id：</td>
						<td class="field">
							<input type="text" name="receive_id" class="input" inputName="收款用户id" value="<c:out value="${bean.receive_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">收款人账号：</td>
						<td class="field">
							<input type="text" name="receive_account" class="input" inputName="收款人账号" value="<c:out value="${bean.receive_account }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">收款人姓名：</td>
						<td class="field">
							<input type="text" name="receive_real_name" class="input" inputName="收款人姓名" value="<c:out value="${bean.receive_real_name }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">交易概述：</td>
						<td class="field">
							<input type="text" name="brief" class="input" inputName="交易概述" value="<c:out value="${bean.brief }"/>" maxlength="200" validate=""/>
						</td>
						<td class="label">支付方式：</td>
						<td class="field">
							<input type="text" name="pay_type" class="input" inputName="支付方式" value="<c:out value="${bean.pay_type }"/>" maxlength="10" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">支付回执编号：</td>
						<td class="field">
							<input type="text" name="pay_code" class="input" inputName="支付回执编号" value="<c:out value="${bean.pay_code }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">支付金额：</td>
						<td class="field">
							<input type="text" name="pay_amount" class="input" inputName="支付金额" value="<c:out value="${bean.pay_amount }"/>" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">支付时间：</td>
						<td class="field">
							<input type="text" name="pay_time" class="input Wdate" inputName="支付时间" value="${fn:substring(bean.pay_time, 0, 19) }" validate="" format="both"/>	
						</td>
						<td class="label">支付状态：</td>
						<td class="field">
							<input type="text" name="pay_status" class="input" inputName="支付状态" value="<c:out value="${bean.pay_status }"/>" maxlength="1" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="200" validate=""/>
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
