<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汀豆使用规则信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("coinRuleController");
	
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
					<div class="table_title">汀豆使用规则信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_coin_rule"></div>
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
						<td class="label">发行单位类型：(w-货仓，s-店铺，m-超市)：</td>
						<td class="field"><c:out value="${bean.store_type }"/></td>
					</tr>
					<tr>
						<td class="label">发行单位：</td>
						<td class="field"><c:out value="${bean.store_name }"/></td>
						<td class="label">支付方：(platform-平台，personal-个人)：</td>
						<td class="field"><c:out value="${bean.who_pay }"/></td>
					</tr>
					<tr>
						<td class="label">单笔限用金额：</td>
						<td class="field"><c:out value="${bean.reach_amount }"/></td>
						<td class="label">单笔限用汀豆数量：</td>
						<td class="field"><c:out value="${bean.limit_quantity }"/></td>
					</tr>
					<tr>
						<td class="label">返还汀豆：</td>
						<td class="field"><c:out value="${bean.send_quantity }"/></td>
						<td class="label">活动开始时间：</td>
						<td class="field">${fn:substring(bean.start_time, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">活动结束时间：</td>
						<td class="field">${fn:substring(bean.end_time, 0, 19) }</td>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
