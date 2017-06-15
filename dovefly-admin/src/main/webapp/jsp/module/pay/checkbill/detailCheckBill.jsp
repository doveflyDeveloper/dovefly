<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>对账信息信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("checkBillController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	$(document).ready(function() {
	
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">对账信息信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/check_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">支付账单表：</td>
						<td class="field"><c:out value="${bean.pay_table_name }"/></td>
						<td class="label">支付账单id：</td>
						<td class="field"><c:out value="${bean.pay_bill_id }"/></td>
					</tr>
					<tr>
						<td class="label">单据类型：</td>
						<td class="field"><c:out value="${bean.bill_type }"/></td>
						<td class="label">商户订单表：</td>
						<td class="field"><c:out value="${bean.bill_table_name }"/></td>
					</tr>
					<tr>
						<td class="label">商户订单id：</td>
						<td class="field"><c:out value="${bean.bill_id }"/></td>
						<td class="label">商户订单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
					</tr>
					<tr>
						<td class="label">对账时间：</td>
						<td class="field">${fn:substring(bean.check_time, 0, 19) }</td>
						<td class="label">对账信息：</td>
						<td class="field"><c:out value="${bean.check_msg }"/></td>
					</tr>
					<tr>
						<td class="label">对账结果：</td>
						<td class="field"><c:out value="${bean.check_status }"/></td>
						<td class="label">处理结果：</td>
						<td class="field"><c:out value="${bean.deal_status }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
