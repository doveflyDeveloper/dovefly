<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值交易信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("rechargeController");
	
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
					<div class="table_title">充值交易信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/recharge"></div>
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
						<td class="label">交易单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">充值时间：</td>
						<td class="field">${fn:substring(bean.recharge_time, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">充值金额：</td>
						<td class="field"><c:out value="${bean.recharge_amount }"/></td>
						<td class="label">收款用户id：</td>
						<td class="field"><c:out value="${bean.receive_id }"/></td>
					</tr>
					<tr>
						<td class="label">收款人账号：</td>
						<td class="field"><c:out value="${bean.receive_account }"/></td>
						<td class="label">收款人姓名：</td>
						<td class="field"><c:out value="${bean.receive_real_name }"/></td>
					</tr>
					<tr>
						<td class="label">交易概述：</td>
						<td class="field"><c:out value="${bean.brief }"/></td>
						<td class="label">支付方式：</td>
						<td class="field"><c:out value="${bean.pay_type }"/></td>
					</tr>
					<tr>
						<td class="label">支付回执编号：</td>
						<td class="field"><c:out value="${bean.pay_code }"/></td>
						<td class="label">支付金额：</td>
						<td class="field"><c:out value="${bean.pay_amount }"/></td>
					</tr>
					<tr>
						<td class="label">支付时间：</td>
						<td class="field">${fn:substring(bean.pay_time, 0, 19) }</td>
						<td class="label">支付状态：</td>
						<td class="field"><c:out value="${bean.pay_status }"/></td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
