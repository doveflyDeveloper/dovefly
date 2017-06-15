<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信支付信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("wxBillController");
	
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
					<div class="table_title">微信支付信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/bill_wx"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					<!-- 
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
					 -->
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">交易时间：</td>
						<td class="field">${fn:substring(bean.trade_time, 0, 19) }</td>
						<td class="label">公众账号id：</td>
						<td class="field"><c:out value="${bean.gh_id }"/></td>
					</tr>
					<tr>
						<td class="label">商户号：</td>
						<td class="field"><c:out value="${bean.mch_id }"/></td>
						<td class="label">子商户号：</td>
						<td class="field"><c:out value="${bean.sub_mch }"/></td>
					</tr>
					<tr>
						<td class="label">设备号：</td>
						<td class="field"><c:out value="${bean.device_id }"/></td>
						<td class="label">微信订单号：</td>
						<td class="field"><c:out value="${bean.wx_order }"/></td>
					</tr>
					<tr>
						<td class="label">商户订单号：</td>
						<td class="field"><c:out value="${bean.bz_order }"/></td>
						<td class="label">用户标识：</td>
						<td class="field"><c:out value="${bean.open_id }"/></td>
					</tr>
					<tr>
						<td class="label">交易类型：</td>
						<td class="field"><c:out value="${bean.trade_type }"/></td>
						<td class="label">交易状态：</td>
						<td class="field"><c:out value="${bean.trade_status }"/></td>
					</tr>
					<tr>
						<td class="label">付款银行：</td>
						<td class="field"><c:out value="${bean.bank }"/></td>
						<td class="label">货币种类：</td>
						<td class="field"><c:out value="${bean.currency }"/></td>
					</tr>
					<tr>
						<td class="label">总金额：</td>
						<td class="field"><c:out value="${bean.total_money }"/></td>
						<td class="label">企业红包金额：</td>
						<td class="field"><c:out value="${bean.red_packet_money }"/></td>
					</tr>
					<tr>
						<td class="label">微信退款单号：</td>
						<td class="field"><c:out value="${bean.wx_refund }"/></td>
						<td class="label">商户退款单号：</td>
						<td class="field"><c:out value="${bean.bz_refund }"/></td>
					</tr>
					<tr>
						<td class="label">退款金额：</td>
						<td class="field"><c:out value="${bean.refund_money }"/></td>
						<td class="label">企业红包退款金额：</td>
						<td class="field"><c:out value="${bean.red_packet_refund }"/></td>
					</tr>
					<tr>
						<td class="label">退款类型：</td>
						<td class="field"><c:out value="${bean.refund_type }"/></td>
						<td class="label">退款状态：</td>
						<td class="field"><c:out value="${bean.refund_status }"/></td>
					</tr>
					<tr>
						<td class="label">商品名称：</td>
						<td class="field"><c:out value="${bean.product_name }"/></td>
						<td class="label">商户数据包：</td>
						<td class="field"><c:out value="${bean.bz_data_packet }"/></td>
					</tr>
					<tr>
						<td class="label">手续费：</td>
						<td class="field"><c:out value="${bean.fee }"/></td>
						<td class="label">费率：</td>
						<td class="field"><c:out value="${bean.rate }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
