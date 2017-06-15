<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账单明细信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("transitionController");
	
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
					<div class="table_title">账单明细信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/fund_transition"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">用户账号：</td>
						<td class="field"><c:out value="${bean.user_name }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label">交易编码：</td>
						<td class="field"><c:out value="${bean.transition_code }"/></td>
						<td class="label">交易类型：</td>
						<td><dv:display dicKeyword="DIC_TRANSITION_TYPE" value="${bean.transition_type }"/></td>
					</tr>
					<tr>
						<td class="label">交易时间：</td>
						<td class="field">${fn:substring(bean.transition_time, 0, 19) }</td>
						<td class="label">交易金额：</td>
						<td class="field"><c:out value="${bean.transition_amount }"/></td>
					</tr>
					<tr>
						<td class="label">当前余额：</td>
						<td class="field"><c:out value="${bean.balance_amount }"/></td>
						<td class="label">交易概述：</td>
						<td class="field"><c:out value="${bean.brief }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
