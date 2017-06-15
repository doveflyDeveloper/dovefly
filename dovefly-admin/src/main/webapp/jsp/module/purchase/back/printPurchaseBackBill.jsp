<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Expires" content="-1" />
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
<body style="width:7.8cm;padding:2px;margin:2px;">
	<h2 style="text-align:center;">采购退货单</h2>
	<div>
	【城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市】<c:out value="${bean.city_name }"/> <br>
	【货&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;仓】<c:out value="${bean.warehouse_name }"/><br>
	【供&nbsp;&nbsp;应&nbsp;&nbsp;商】<c:out value="${bean.supplier_name }"/><br>
	【退货单号】<c:out value="${bean.bill_code }"/><br>
	【退货日期】<c:out value="${bean.bill_date }"/><br>
	【退货金额】￥<c:out value="${bean.amount }"/><br>
	【退货状态】<dv:display dicKeyword="DIC_PURCHASE_BACK_BILL_STATUS" value="${bean.status }"/><br>
	</div>
	<br>
	<div>
		<table class="list_table">
			<caption style="text-align:left;">商品明细：</caption>
			<tr>
				<th style="width:3cm;">品名</th>
				<th style="width:1.5cm;">单价</th>
				<th style="width:2.0cm;">总量<br><span style="font-size:8px;">(规格×数量)</span></th>
				<th style="width:1.5cm;">小计</th>
			</tr>
			<c:forEach var="detail" varStatus="status" items="${bean.details }">
			<tr>
				<td style="text-align:left;"><c:out value="${detail.goods_name }"/></td>
				<td style="text-align:center;"><c:out value="${detail.unit_price }"/></td>
				<td style="text-align:center;"><c:out value="${detail.quantity }"/><br><span style="font-size:8px;">(<c:out value="${detail.spec }"/>×<c:out value="${detail.spec_quantity }"/>)</span></td>
				<td style="text-align:center;"><c:out value="${detail.sub_total }"/></td>
			</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>