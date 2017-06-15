<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对账信息<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("checkBillController");
	
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
					<div class="table_title">对账信息<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/check_bill"></div>
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
						<td class="label">支付账单表：</td>
						<td class="field">
							<input type="text" name="pay_table_name" class="input" inputName="支付账单表" value="<c:out value="${bean.pay_table_name }"/>" maxlength="30" validate=""/>
						</td>
						<td class="label">支付账单id：</td>
						<td class="field">
							<input type="text" name="pay_bill_id" class="input" inputName="支付账单id" value="<c:out value="${bean.pay_bill_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">单据类型：</td>
						<td class="field">
							<input type="text" name="bill_type" class="input" inputName="单据类型" value="<c:out value="${bean.bill_type }"/>" maxlength="10" validate=""/>
						</td>
						<td class="label">商户订单表：</td>
						<td class="field">
							<input type="text" name="bill_table_name" class="input" inputName="商户订单表" value="<c:out value="${bean.bill_table_name }"/>" maxlength="30" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">商户订单id：</td>
						<td class="field">
							<input type="text" name="bill_id" class="input" inputName="商户订单id" value="<c:out value="${bean.bill_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">商户订单号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="input" inputName="商户订单号" value="<c:out value="${bean.bill_code }"/>" maxlength="20" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">对账时间：</td>
						<td class="field">
							<input type="text" name="check_time" class="input Wdate" inputName="对账时间" value="${fn:substring(bean.check_time, 0, 19) }" validate="" format="both"/>	
						</td>
						<td class="label">对账信息：</td>
						<td class="field">
							<input type="text" name="check_msg" class="input" inputName="对账信息" value="<c:out value="${bean.check_msg }"/>" maxlength="200" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">对账结果：</td>
						<td class="field">
							<input type="text" name="check_status" class="input" inputName="对账结果" value="<c:out value="${bean.check_status }"/>" maxlength="1" validate=""/>
						</td>
						<td class="label">处理结果：</td>
						<td class="field">
							<input type="text" name="deal_status" class="input" inputName="处理结果" value="<c:out value="${bean.deal_status }"/>" maxlength="1" validate=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
	</form>
</body>
</html>
