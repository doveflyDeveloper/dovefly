<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付宝支付订单信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("alipayBillController");
	
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
					<div class="table_title">支付宝支付订单信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/pay_alipay_bill"></div>
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
						<td class="label">余额：</td>
						<td class="field"><c:out value="${bean.balance }"/></td>
						<td class="label">收入金额：</td>
						<td class="field"><c:out value="${bean.income }"/></td>
					</tr>
					<tr>
						<td class="label">支出金额：</td>
						<td class="field"><c:out value="${bean.outcome }"/></td>
						<td class="label">交易付款时间：</td>
						<td class="field"><c:out value="${bean.trans_date }"/></td>
					</tr>
					<tr>
						<td class="label">业务类型：</td>
						<td class="field"><c:out value="${bean.trans_code_msg }"/></td>
						<td class="label">子业务类型：</td>
						<td class="field"><c:out value="${bean.sub_trans_code_msg }"/></td>
					</tr>
					<tr>
						<td class="label">商户订单号：</td>
						<td class="field"><c:out value="${bean.merchant_out_order_no }"/></td>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.memo }"/></td>
					</tr>
					<tr>
						<td class="label">买家支付宝人民币资金账号：</td>
						<td class="field"><c:out value="${bean.buyer_account }"/></td>
						<td class="label">卖家支付宝人民币资金账号：</td>
						<td class="field"><c:out value="${bean.seller_account }"/></td>
					</tr>
					<tr>
						<td class="label">卖家姓名：</td>
						<td class="field"><c:out value="${bean.seller_fullname }"/></td>
						<td class="label">货币代码156（人民币）：</td>
						<td class="field"><c:out value="${bean.currency }"/></td>
					</tr>
					<tr>
						<td class="label">充值网银流水号：</td>
						<td class="field"><c:out value="${bean.deposit_bank_no }"/></td>
						<td class="label">商品名称：</td>
						<td class="field"><c:out value="${bean.goods_title }"/></td>
					</tr>
					<tr>
						<td class="label">账务序列号：</td>
						<td class="field"><c:out value="${bean.iw_account_log_id }"/></td>
						<td class="label">合作者身份id：</td>
						<td class="field"><c:out value="${bean.partner_id }"/></td>
					</tr>
					<tr>
						<td class="label">交易服务费：</td>
						<td class="field"><c:out value="${bean.service_fee }"/></td>
						<td class="label">交易服务费率：</td>
						<td class="field"><c:out value="${bean.service_fee_ratio }"/></td>
					</tr>
					<tr>
						<td class="label">交易总金额：</td>
						<td class="field"><c:out value="${bean.total_fee }"/></td>
						<td class="label">支付宝交易号：</td>
						<td class="field"><c:out value="${bean.trade_no }"/></td>
					</tr>
					<tr>
						<td class="label">累积退款金额：</td>
						<td class="field"><c:out value="${bean.trade_refund_amount }"/></td>
						<td class="label">签约产品：</td>
						<td class="field"><c:out value="${bean.sign_product_name }"/></td>
					</tr>
					<tr>
						<td class="label">费率：</td>
						<td class="field"><c:out value="${bean.rate }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
