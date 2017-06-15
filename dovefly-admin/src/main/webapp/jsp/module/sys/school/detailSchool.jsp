<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学校信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("schoolController");
	
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
					<div class="table_title">学校信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_school"></div>
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
						<td class="label">货仓：</td>
						<td class="field"><c:out value="${bean.warehouse_name }"/></td>
					</tr>
					<tr>
						<td class="label">运营主管：</td>
						<td class="field"><c:out value="${bean.manager_name }"/></td>
						<td class="label">学校：</td>
						<td class="field"><c:out value="${bean.school_name }"/></td>
					</tr>
					<tr>
						<td class="label">开店数量：</td>
						<td class="field"><c:out value="${bean.shop_count }"/></td>
						<td class="label">用户数量：</td>
						<td class="field"><c:out value="${bean.user_count }"/></td>
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
