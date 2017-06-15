<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款申请信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("refundController");
	
	/** 支付处理 */
	function pay_onclick() {
		dvConfirm("您确定要退款吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("refundController", "deal", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 拒绝 */
	function deny_onclick() {
		dvPrompt(window.denyDivHtml, '填写拒绝信息', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts"></span>',
			submit: function(v, h, f) {
				if (!validateForm("deny_table")) {return false;}
				$("#reason").val(f['deny_reason']);
				formHelper.jSubmit(formHelper.buildAction("refundController", "deny", "${bean.id }"));
				return true;
			}
		});
	}
	
	$(document).ready(function() {
		window.denyDivHtml = $("#denyDiv").html();
		$("#denyDiv").html("");
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">退款申请信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/refund"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.status == '0'}">
						<input type="button" name="pay" value="退款" class="button" onclick="pay_onclick();"/>
						<!-- <input type="button" name="deny" value="拒绝" class="button" onclick="deny_onclick();"/> -->
						</c:if>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">申请单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">买家id：</td>
						<td class="field"><c:out value="${bean.buyer_id }"/></td>
					</tr>
					<tr>
						<td class="label">买家姓名：</td>
						<td class="field"><c:out value="${bean.buyer_name }"/></td>
						<td class="label">申请时间：</td>
						<td class="field">${fn:substring(bean.refund_time, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">申请金额：</td>
						<td class="field"><c:out value="${bean.refund_amount }"/></td>
						<td class="label">付款去向：</td>
						<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.refund_to }"/></td>
					</tr>
					<tr>
						<td class="label">关联单据：</td>
						<td class="field"><c:out value="${bean.refer_bill_code }"/></td>
						<td class="label">交易概述：</td>
						<td class="field"><c:out value="${bean.brief }"/></td>
					</tr>
					<tr>
						<td class="label">支付回执编号：</td>
						<td class="field"><c:out value="${bean.pay_code }"/></td>
						<td class="label">支付时间：</td>
						<td class="field">${fn:substring(bean.pay_time, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">支付状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_REFUND_PAY_STATUS" value="${bean.pay_status }"/></td>
						<td class="label">支付状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_REFUND_STATUS" value="${bean.status }"/></td>
					</tr>
					<tr>
						<td class="label">支付信息：</td>
						<td class="field"><c:out value="${bean.pay_msg }"/></td>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
	<div id="denyDiv" style="display:none;">
		<table id="deny_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>拒绝原因：</td>
				<td class="field">
					<textarea id="deny_reason" name="deny_reason" class="textarea" inputName="拒绝原因" validate="notNull"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
