<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Expires" content="-1" />
<title>进货订单-www.deertt.com</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
</script>
</head>
<body>
	<div>
		<table class="detail_table">
			<tr>
				<td class="label" style="width:100px;">单号：</td>
				<td class="field"><c:out value="${bean.bill_code }"/></td>
			</tr>
			<tr>
				<td class="label">收货人：</td>
				<td class="field">${bean.rcv_name }-${bean.rcv_mobile }</td>
			</tr>
			<tr>
				<td class="label">收货地址：</td>
				<td class="field">${bean.rcv_address }</td>
			</tr>
			<tr>
				<td class="label">下单时间：</td>
				<td class="field">${fn:substring(bean.bill_time, 0, 19) }</td>
			</tr>
			<tr>
				<td class="label">支付方式：</td>
				<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/>[<dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/>]</td>
			</tr>
			<tr>
				<td class="label">实付金额：</td>
				<td class="field"><c:out value="${bean.real_amount }"/></td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td class="field" colspan="3"><c:out value="${bean.remark }"/></td>
			</tr>
		</table>
	</div>
	<div>
		<table class="list_table">
			<tr>
				<th>品名</th>
				<th width="8%">单价</th>
				<th width="8%">进货数量</th>
				<th width="8%">进货小计</th>
			</tr>
			<c:forEach var="detail" varStatus="status" items="${bean.details }">
			<tr>
				<td><c:out value="${detail.goods_name }"/></td>
				<td><c:out value="${detail.sale_price }"/></td>
				<td><c:out value="${detail.quantity }"/></td>
				<td><c:out value="${detail.sub_total }"/></td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<c:if test="${bean.express != null}">
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
	</c:if>
</body>
</html>
