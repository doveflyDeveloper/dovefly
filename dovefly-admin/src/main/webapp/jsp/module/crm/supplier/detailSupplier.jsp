<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("supplierController");
	
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
					<div class="table_title">供应商信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/supplier"></div>
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
						<td class="label">供应商：</td>
						<td class="field"><c:out value="${bean.name }"/></td>
						<td class="label">公司邮箱：</td>
						<td class="field"><c:out value="${bean.email }"/></td>
					</tr>
					<tr>
						<td class="label">公司电话：</td>
						<td class="field"><c:out value="${bean.tel }"/></td>
						<td class="label">公司地址：</td>
						<td class="field"><c:out value="${bean.address }"/></td>
					</tr>
					<tr>
						<td class="label">联系人：</td>
						<td class="field"><c:out value="${bean.linkman }"/></td>
						<td class="label">联系人电话：</td>
						<td class="field"><c:out value="${bean.linkman_mobile }"/></td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
