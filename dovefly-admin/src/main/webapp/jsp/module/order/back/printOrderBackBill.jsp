<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta http-equiv="Expires" content="-1" />
<title>退货单: ${bean.bill_code}</title>
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
	<table class="detail_table">
		<tr>
			<td class="field"> - </td>
			<td class="label"><img src="<%=request.getContextPath() %>/barcode.svg?msg=${bean.bill_code}&height=15&mw=0.5"></td>
		</tr>
	</table>
	<table class="detail_table">
		<tr>
			<td class="label">城市：</td>
			<td class="field"><c:out value="${bean.city_name }"/></td>
		</tr>
		<tr>
			<td class="label">退货单编号：</td>
			<td class="field"><c:out value="${bean.bill_code }"/></td>
			<td class="label">退货单日期：</td>
			<td class="field">${fn:substring(bean.bill_date, 0, 10) }</td>
		</tr>
		<tr>
			<td class="label">退货金额：</td>
			<td class="field"><c:out value="${bean.amount }"/></td>
			<td class="label">备注：</td>
			<td class="field"><c:out value="${bean.remark }"/></td>
		</tr>
	</table>
	<table class="list_table">
		<tr>
			<th width="5%">序号</th>
			<th width="40%">商品名称</th>
			<th width="10%">店长采购价</th>
			<th width="10%">退货数量</th>
			<th width="10%">金额小计</th>
		</tr>
		<c:forEach var="detail" varStatus="status" items="${details }">
		<tr>
			<td>${status.count }</td>
			<td><c:out value="${detail.goods_name }"/></td>
			<td><c:out value="${detail.sale_price }"/></td>
			<td><c:out value="${detail.quantity }"/></td>
			<td><c:out value="${detail.sub_total }"/></td>
		</tr>
		</c:forEach>
	</table>
	<c:if test="${bean.express != null}">
	<table class="detail_table">
		<tr>
			<td class="label">快递公司：</td>
			<td class="field"><c:out value="${bean.express.name }"/></td>
			<td class="label">快递单号：</td>
			<td class="field"><c:out value="${bean.express.code }"/></td>
		</tr>
		<tr>
			<td class="label">快递费用：</td>
			<td class="field"><c:out value="${bean.express.amount }"/></td>
			<td class="label">快递日期：</td>
			<td class="field"><c:out value="${bean.express.date }"/></td>
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
	</c:if>
</body>
</html>
