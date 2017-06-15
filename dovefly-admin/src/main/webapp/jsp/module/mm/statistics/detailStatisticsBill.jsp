<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>结算清单信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("statisticsBillController");
	
	/** 导出 */
	function exportDetail_onclick() {
		formHelper.submit(formHelper.buildAction("statisticsBillController", "exportDetail", "${bean.id }"));
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
					<div class="table_title">结算清单信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/statistics_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="export" value="导出" class="button" onclick="exportDetail_onclick();" />
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">结算单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">结算日期：</td>
						<td class="field">${fn:substring(bean.bill_date, 0, 10) }</td>
					</tr>
					<tr>
						<td class="label">期初总金额：</td>
						<td class="field"><c:out value="${bean.origin_amount }"/></td>
						<td class="label">进货总金额：</td>
						<td class="field"><c:out value="${bean.purchase_amount }"/></td>
					</tr>
					<tr>
						<td class="label">售出总金额：</td>
						<td class="field"><c:out value="${bean.order_amount }"/></td>
						<td class="label">库存总金额：</td>
						<td class="field"><c:out value="${bean.stock_amount }"/></td>
					</tr>
					<tr>
						<td class="label">盘点总金额：</td>
						<td class="field"><c:out value="${bean.check_amount }"/></td>
						<td class="label">结转总金额：</td>
						<td class="field"><c:out value="${bean.final_amount }"/></td>
					</tr>
				</table>
			</div>
			<div class="padding_2_div">
				<div>
					<table id="detail_list_table" class="list_table">
						<tr>
							<th width="5%">序号</th>
							<th width="5%">缩略图</th>
							<th width="20%">品名</th>
							<th width="5%">期初库存</th>
							<th width="5%">期初金额</th>
							<th width="5%">进货数量</th>
							<th width="5%">进货金额</th>
							<th width="5%">售出数量</th>
							<th width="5%">售出金额</th>
							<th width="5%">库存数量</th>
							<th width="5%">库存金额</th>
							<th width="5%">盘点数量</th>
							<th width="5%">盘点金额</th>
							<th width="5%">盈亏数量</th>
							<th width="5%">盈亏金额</th>
							<th width="5%">结转库存</th>
							<th width="5%">结转金额</th>
						</tr>
						<c:forEach var="detail" varStatus="status" items="${bean.details }">
						<tr>
							<td>${status.count }</td>
							<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
							<td><c:out value="${detail.goods_name }"/></td>
							<td><c:out value="${detail.origin_quantity }"/></td>
							<td><c:out value="${detail.origin_amount }"/></td>
							<td><c:out value="${detail.purchase_quantity }"/></td>
							<td><c:out value="${detail.purchase_amount }"/></td>
							<td><c:out value="${detail.order_quantity }"/></td>
							<td><c:out value="${detail.order_amount }"/></td>
							<td><c:out value="${detail.stock_quantity }"/></td>
							<td><c:out value="${detail.stock_amount }"/></td>
							<td><c:out value="${detail.check_quantity }"/></td>
							<td><c:out value="${detail.check_amount }"/></td>
							<td><c:out value="${detail.dif_quantity }"/></td>
							<td><c:out value="${detail.dif_amount }"/></td>
							<td><c:out value="${detail.final_quantity }"/></td>
							<td><c:out value="${detail.final_amount }"/></td>
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
