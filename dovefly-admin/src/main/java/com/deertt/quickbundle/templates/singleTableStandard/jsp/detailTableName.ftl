<#macro buildTableColumn_detailDisplay>
	<#list table.columnsIsBuild?chunk(2) as row> 
					<tr>
			<#list row as column>
						<td class="label">${column.columnNameChinese}：</td>
					<#--处理dv.dictionary.select checkbox radio (表单域类型)-->
					<#if column.inputType='dv.dictionary.select' || column.inputType='dv.dictionary.checkbox' || column.inputType='dv.dictionary.radio'>
						<td class="field"><dv:display dicKeyword="${column.dicTypeKeyword}" value="${r"${"}bean.${column.columnName} }"/></td>
					<#--处理textarea，大于1000个字符-->
					<#elseif column.inputType='dv.htmlArea' && column.dataType='String' && column.columnSize gte 1000>
						<td class="field"><c:out value="${r"${"}bean.${column.columnName} }"/></td>
					<#--处理时间戳参照-->
					<#elseif column.inputType='text' && column.dataType='Timestamp'>
						<td class="field">${r"${"}fn:substring(bean.${column.columnName}, 0, 19) }</td>
					<#--处理日期参照-->
					<#elseif column.inputType='text' && column.dataType='Date'>
						<td class="field">${r"${"}fn:substring(bean.${column.columnName}, 0, 10) }</td>
					<#else>
						<td class="field"><c:out value="${r"${"}bean.${column.columnName} }"/></td>
					</#if>
			</#list>
					</tr>
	</#list>
	<#if table.hasFiles='1'>
					<tr>
						<td class="label">附件：</td>
						<td class="field">
							<jsp:include page="/jsp/include/upload/uploadifyDetail.jsp" flush="true">
								<jsp:param name="fileUploadName" value="common_file_upload"/>
								<jsp:param name="fileListName" value="commonFileList"/>
							</jsp:include>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
	</#if>
</#macro>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${table.tableNameChinese}信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${r"${"}bean.${table.tablePk} }"));
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
					<div class="table_title">${table.tableNameChinese}信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/${table.tableName}"></div>
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
					<@buildTableColumn_detailDisplay/>
				</table>
			</div>
			<#if table.hasSubTabs='1'>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>页签一</DT>
					<DD>
						<div>
							<iframe	rel="<%=request.getContextPath()%>/${project.jspSourcePath}/${table.tableFormatName?lower_case}/tip${table.tableFormatName?cap_first}.jsp" frameborder="0" scrolling="auto" width="100%" height="300px"></iframe>
						</div>
					</DD>
				</DL>
			</div>
			</#if>
		</div>
	</form>
</body>
</html>
