<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Expires" content="-1" />
<title>小鹿汀汀订单发货单</title>
<style type="text/css">
<!--
div {
	font-size: 16px;
	padding:0px;
}
.list_table {
	font-size: 16px;
	padding:0px;
}
.list_table td {
	text-align:left;
	font-size: 16px;
	padding:0px;
}

-->
</style>
<script type="text/javascript">
/** 确定 */
function ok_onclick() {
	window.print();
	return false;//若要关闭窗口，可返回true;
}
</script>
</head>
<body style="width:8cm;padding:0px;margin:0px;">
	<div>
	【订&nbsp;&nbsp;单&nbsp;&nbsp;号】<c:out value="${bean.bill_code }"/><br>
	【收&nbsp;&nbsp;货&nbsp;&nbsp;人】<c:out value="${bean.rcv_name }"/> &nbsp;/&nbsp; <c:out value="${bean.rcv_mobile }"/><br>
	【收货地址】<c:out value="${bean.rcv_address }"/><br>
	【下单时间】${fn:substring(bean.bill_time, 0, 19) }<br>
	【支付方式】<dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/>&nbsp;[<dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/>]<br>
	【订单金额】￥<c:out value="${bean.total_amount }"/><br>
	【优惠金额】￥<c:out value="${bean.use_coin_amount }"/><br>
	【应付金额】￥<c:out value="${bean.real_amount }"/><br>
	【实付金额】￥<c:out value="${bean.real_amount }"/><br>
	【备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注】&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${bean.remark }"/><br><br>
	【客户签收】<span style="text-decoration:underline">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;</span>
	</div>
	<br>
	<div>
		<table class="list_table">
			<caption style="text-align:left;">商品明细：</caption>
			<tr>
				<th style="width:5cm;">品名</th>
				<th style="width:1.5cm;">单价</th>
				<th style="width:1.5cm;">数量</th>
			</tr>
			<c:forEach var="detail" varStatus="status" items="${bean.details }">
			<tr>
				<td style="text-align:left;">&nbsp;<c:out value="${detail.goods_name }"/></td>
				<td style="text-align:center;">&nbsp;<c:out value="${detail.sale_price }"/></td>
				<td style="text-align:center;">&nbsp;<c:out value="${detail.quantity }"/></td>
			</tr>
			</c:forEach>
		</table>
	</div>
<%-- 	<c:if test="${bean.express != null}">
	<div>
		<table class="detail_table">
			<tr>
				<td class="label">快递公司：</td>
				<td class="field"><dv:display dicKeyword="DIC_EXPRESS_COMPANY" value="${bean.express.exp_company }" /></td>
				<td class="label">快递单号：</td>
				<td class="field"><c:out value="${bean.express.exp_tracking_no }"/></td>
			</tr>
			<tr>
				<td class="label">快递日期：</td>
				<td class="field"><c:out value="${bean.express.exp_date }"/></td>
				<td class="label">快递费用：</td>
				<td class="field"><c:out value="${bean.express.exp_amount }"/></td>
			</tr>
			<tr>
				<td class="label">发件人姓名：</td>
				<td class="field"><c:out value="${bean.express.sender_name }"/></td>
				<td class="label">发件人电话：</td>
				<td class="field"><c:out value="${bean.express.sender_mobile }"/></td>
			</tr>
			<tr>
				<td class="label">发件人地址：</td>
				<td class="field" colspan="3"><c:out value="${bean.express.sender_address }"/></td>
			</tr>
			<tr>
				<td class="label">收件人姓名：</td>
				<td class="field"><c:out value="${bean.express.receiver_name }"/></td>
				<td class="label">收件人电话：</td>
				<td class="field"><c:out value="${bean.express.receiver_mobile }"/></td>
			</tr>
			<tr>
				<td class="label">收件人地址：</td>
				<td class="field" colspan="3"><c:out value="${bean.express.receiver_address }"/></td>
			</tr>
		</table>
	</div>
	</c:if> --%>
</body>
</html>
