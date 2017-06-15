<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.tcommonfile.vo.TCommonFileVo" %>
<%@ page import="com.deertt.module.tcommonfile.util.ITCommonFileConstants" %>
<%
	
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=ITCommonFileConstants.TABLE_NAME_CHINESE %>信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("tCommonFileController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("<c:out value="${bean.file_id }"/>"));
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
					<div class="table_title"><%=ITCommonFileConstants.TABLE_NAME_CHINESE %>信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%><%=ITCommonFileConstants.TIP_JSP_URL%>"></div>
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
						<td class="label">${TABLE_COLUMN_CHINESE.owner_table_name }：</td>
						<td class="field"><c:out value="${bean.owner_table_name }"/></td>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_bill_id }：</td>
						<td class="field"><c:out value="${bean.owner_bill_id }"/></td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_column_name }：</td>
						<td class="field"><c:out value="${bean.owner_column_name }"/></td>
						<td class="label">${TABLE_COLUMN_CHINESE.file_name }：</td>
						<td class="field"><c:out value="${bean.file_name }"/></td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.file_url }：</td>
						<td class="field"><c:out value="${bean.file_url }"/></td>
						<td class="label">${TABLE_COLUMN_CHINESE.file_type }：</td>
						<td class="field"><c:out value="${bean.file_type }"/></td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.file_size }：</td>
						<td class="field"><c:out value="${bean.file_size }"/></td>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
