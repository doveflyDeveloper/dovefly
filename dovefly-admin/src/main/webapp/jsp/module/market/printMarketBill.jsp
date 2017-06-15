<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Expires" content="-1" />
<title>订单: ${bean.bill_code}</title>
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
	function ok_onclick() {
		window.print();
		return false;//若要关闭窗口，可返回true;
	}
</script>
</head>
<body>
	<div>
	【订&nbsp;&nbsp;单&nbsp;&nbsp;号】<c:out value="${bean.bill_code }"/><br>
	【店&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;铺】<c:out value="${bean.market_name }"/><br>
	【收&nbsp;&nbsp;货&nbsp;&nbsp;人】<c:out value="${bean.ship_name }"/> &nbsp;/&nbsp; <c:out value="${bean.ship_mobile }"/><br>
	【收货地址】<c:out value="${bean.ship_addr }"/><br>
	【下单时间】${fn:substring(bean.bill_time, 0, 19) }<br>
	【支付方式】<dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/>&nbsp;[<dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/>]<br>
	【订单金额】￥<c:out value="${bean.user_coin_amount }"/><br>
	【优惠金额】￥<c:out value="${bean.discount_amount }"/><br>
	【实付金额】￥<c:out value="${bean.pay_amount }"/><br>
	【备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注】&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${bean.remark }"/><br><br>
	</div>
	<br>
	<div>
		<table class="list_table">
			<caption style="text-align:left;">销售明细：</caption>
			<tr>
				<th style="width:5cm;">品名</th>
				<th style="width:1.5cm;">单价</th>
				<th style="width:1.5cm;">数量</th>
			</tr>
			<c:forEach var="detail" varStatus="status" items="${bean.details }">
			<tr>
				<td style="text-align:left;"><c:out value="${detail.goods_name }"/></td>
				<td style="text-align:center;"><c:out value="${detail.unit_price }"/></td>
				<td style="text-align:center;"><c:out value="${detail.quantity }"/></td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	
</body>
</html>
