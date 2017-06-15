<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>超市信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("marketController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
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
					<div class="table_title">超市信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_market"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
						<td class="label">店铺：</td>
						<td class="field"><c:out value="${bean.market_name }"/>(店长：<c:out value="${bean.shopkeeper_name }"/>)</td>
					</tr>
					<tr>
						<td class="label">店铺描述：</td>
						<td class="field"><c:out value="${bean.market_desc }"/></td>
						<td class="label">配送区域说明：</td>
						<td class="field"><c:out value="${bean.market_area }"/></td>
					</tr>
					<tr>
						<td class="label">运营状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_SYS_SHOP_STATUS" value="${bean.market_status }" /></td>
						<td class="label">起送价：</td>
						<td class="field"><c:out value="${bean.start_amount }"/></td>
					</tr>
					<tr>
						<td class="label">账号余额：</td>
						<td class="field"><c:out value="${bean.balance_amount }"/></td>
						<td class="label">冻结资金：</td>
						<td class="field"><c:out value="${bean.locked_amount }"/></td>
					</tr>
					<tr>
						<td class="label">待收金额：</td>
						<td class="field"><c:out value="${bean.halfway_amount }"/></td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
