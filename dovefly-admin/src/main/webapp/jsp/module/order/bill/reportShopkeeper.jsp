<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
%>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>进货统计</title>
<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("orderBillReportController");
	
	/** 批量导出 */
	function export_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("orderBillReportController", "exportShopKeeper", ids));
	}
	
	/** 页面加载后需绑定的事件 */
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
					<div class="table_title">统计列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间：<c:out value="${bill_date_from }"/>——<c:out value="${bill_date_to }"/></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					  <input type="button" name="export" value="导出" class="button" onclick="export_onclick();"/> 
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">序号</th>
						<th width="10%">城市</th>
						<th width="10%">运营主管</th>
						<th width="10%">店长</th>
						<th width="10%">订单量（笔）</th>
						<th width="10%">订单总额（元）</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>${status.count }</td>
						<td><c:out value="${bean.city_name }"/></td>
						<td><c:out value="${bean.manager_name }"/></td>
						<td><c:out value="${bean.shop_name }"/></td>
						<td><c:out value="${bean.bill_count }"/></td>
						<td><c:out value="${bean.bill_amount }"/></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<input name="bill_date_from" type="hidden" value="${bill_date_from}"/>
		<input name="bill_date_to" type="hidden" value="${bill_date_to}"/>
		<input name="manager_id" type="hidden" value="${manager_id}"/>
		<input name="school_id" type="hidden" value="${school_id}"/>
	</form>
</body>
</html>
