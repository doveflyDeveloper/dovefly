<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>充值交易管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("reportController");
	
	/** 查询 */
	function query_onclick() {
		if (validateForm()){
			formHelper.jSubmit(formHelper.buildAction("reportController", "reportShopkeeperRecharge"));
		}
	}
	
	/** 充值列表导出 */
	function exportList_onclick() {
		formHelper.submit(formHelper.buildAction("reportController", "exportShopkeeperRecharge"));
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
					<div class="table_title">查询条件&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/recharge"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<td class="label">店长ID：</td>
						<td class="field">
							<input type="text" name="receive_id" class="input" value="${param.receive_id }" inputName="店长ID"/>
						</td>
						<td class="label">店长姓名：</td>
						<td class="field">
						<input type="text" name="receive_name" class="input" value="${param.receive_name }" inputName="店长姓名"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">充值交易列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					   <input type="button" name="exportList" value="导出" class="button" onclick="exportList_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="10%">充值单号</th>
						<th width="10%">店长ID</th>
						<th width="10%">店长姓名</th>
						<th width="10%">充值时间</th>
						<th width="10%">充值金额</th>
						<th width="10%">支付方式</th>
						<th width="10%">充值状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.bill_code }"/></td>
						<td><c:out value="${bean.receive_id }"/></td>
						<td><c:out value="${bean.receive_name }"/></td>
						<td>${fn:substring(bean.recharge_time, 0, 19) }</td>
						<td><c:out value="${bean.recharge_amount }"/></td>
						<td><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/></td>
						<td><dv:display dicKeyword="DIC_RECHARGE_STATUS" value="${bean.pay_status }"/></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
