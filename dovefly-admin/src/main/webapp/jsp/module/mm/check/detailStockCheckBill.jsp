<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>库存盘点信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("stockCheckBillController");
	
	/** 确认 */
	function confirm_onclick() {
		dvConfirm("确认后，现有商品库存将以实际盘点数据为准并进行更新，您确定盘点的数据正确无误吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("stockCheckBillController", "confirm", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
		
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
					<div class="table_title">库存盘点信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/check_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.status == '0' }">
						<input type="button" name="confirm" value="确认" class="button" onclick="confirm_onclick();" />
						</c:if>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">货仓：</td>
						<td class="field"><c:out value="${bean.warehouse_name }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label">盘点单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">盘点日期：</td>
						<td class="field">${fn:substring(bean.bill_date, 0, 10) }</td>
					</tr>
					<!-- 
					<tr>
						<td class="label">库存金额：</td>
						<td class="field"><c:out value="${bean.stock_amount }"/></td>
						<td class="label">盘点金额：</td>
						<td class="field"><c:out value="${bean.check_amount }"/></td>
					</tr>
					 -->
				</table>
			</div>
			<div class="padding_2_div">
				<div>
					<table id="detail_list_table" class="list_table">
						<tr>
							<th width="5%">序号</th>
							<th width="5%">缩略图</th>
							<th width="30%">品名</th>
							<th width="10%">库存数量</th>
							<th width="10%">实盘数量</th>
							<th width="10%">盈亏数量</th>
							<!-- 
							<th width="10%">库存金额</th>
							<th width="10%">盘点金额</th>
							<th width="10%">差异金额</th>
							 -->
						</tr>
						<c:forEach var="detail" varStatus="status" items="${bean.details }">
						<tr>
							<td>${status.count }</td>
							<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
							<td><c:out value="${detail.goods_name }"/></td>
							<td><c:out value="${detail.stock_quantity }"/></td>
							<td><c:out value="${detail.check_quantity }"/></td>
							<c:if test="${detail.dif_quantity >= 10 || detail.dif_quantity <= -10}">
							<td><span class="left_ts"><c:out value="${detail.dif_quantity }"/></span></td>
							</c:if>
							<c:if test="${detail.dif_quantity < 10 && detail.dif_quantity > -10}">
							<td><c:out value="${detail.dif_quantity }"/></td>
							</c:if>
							<!-- 
							<td><c:out value="${detail.stock_amount }"/></td>
							<td><c:out value="${detail.check_amount }"/></td>
							<td><span class="left_ts"><c:out value="${detail.dif_amount }"/></span></td>
							<td><c:out value="${detail.dif_amount }"/></td>
							 -->
						</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
