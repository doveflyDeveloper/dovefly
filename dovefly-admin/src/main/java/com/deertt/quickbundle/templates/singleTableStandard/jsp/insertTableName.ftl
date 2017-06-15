<#macro buildTableColumn_insertInput>
	<#list table.columnsIsBuild?chunk(2) as row> 
					<tr>
			<#list row as column>
				<#--处理dv.dictionary.select(表单域类型)-->
						<td class="label"><#if column.validateType?index_of("notNull") gte 0><span class="required_red">* </span></#if>${column.columnNameChinese}：</td>
						<td class="field">
				<#if column.inputType='dv.dictionary.select'>
							<dv:select name="${column.columnName}" dicKeyword="${column.dicTypeKeyword}" defaultValue="${r"${"}bean.${column.columnName} }" attributes="inputName='${column.columnNameChinese}' validate='${column.validateType}'" hasEmpty="true"/>
				<#--处理dv.dictionary.checkbox(表单域类型)-->
				<#elseif column.inputType='dv.dictionary.checkbox'>
							<dv:checkbox name="${column.columnName}" dicKeyword="${column.dicTypeKeyword}" defaultValue="${r"${"}bean.${column.columnName} }" attributes="inputName='${column.columnNameChinese}' validate='${column.validateType}'"/>
				<#--处理dv.dictionary.radio(表单域类型)-->
				<#elseif column.inputType='dv.dictionary.radio'>
							<dv:radio name="${column.columnName}" dicKeyword="${column.dicTypeKeyword}" defaultValue="${r"${"}bean.${column.columnName} }" attributes="inputName='${column.columnNameChinese}' validate='${column.validateType}'"/>
				<#--处理dv.htmlArea(html编辑器)-->
				<#elseif column.inputType='dv.htmlArea'>
							<textarea name="${column.columnName}" class="textarea_limit_words" inputName="${column.columnNameChinese}" validate="${column.validateType}">${r"${"}bean.${column.columnName} }</textarea>
				<#--处理dv.listReference(列表参照)-->
				<#elseif column.inputType='dv.listReference'>
							<input type="hidden" name="${column.columnName}_id" value="<c:out value="${r"${"}bean.${column.columnName}_id }"/>"/>
							<input type="text" name="${column.columnName}" class="reference" maxlength="${column.columnSize}" inputName="${column.columnNameChinese}" value="<c:out value="${r"${"}bean.${column.columnName} }"/>" validate="${column.validateType}"/><span class="referenceSpan" onclick="referenceOrg();"></span>
				<#--处理dv.orgReference(组织结构参照)-->
				<#elseif column.inputType='dv.orgReference'>
							<input type="hidden" name="${column.columnName}_id" value="<c:out value="${r"${"}bean.${column.columnName}_id }"/>"/>
							<input type="text" name="${column.columnName}" class="reference" maxlength="${column.columnSize}" inputName="${column.columnNameChinese}" value="<c:out value="${r"${"}bean.${column.columnName} }"/>" validate="${column.validateType}"/><span class="referenceSpan" onclick="referenceOrg();"></span>
				<#--处理default(默认)-->
				<#elseif column.inputType='text' || column.inputType=''>
					<#--处理textarea，大于1000个字符-->
					<#if column.dataType='String' && column.columnSize gte 1000>
							<textarea name="${column.columnName}" class="input" inputName="${column.columnNameChinese}">${r"${"}bean.${column.columnName} }</textarea>
					<#--处理时间参照-->
					<#elseif column.dataType='Timestamp'>
							<input type="text" name="${column.columnName}" class="input Wdate" inputName="${column.columnNameChinese}" value="${r"${"}fn:substring(bean.${column.columnName}, 0, 19) }" validate="${column.validateType}" format="both"/>	
					<#--处理日期参照-->
					<#elseif column.dataType='Date'>
							<input type="text" name="${column.columnName}" class="input Wdate" inputName="${column.columnNameChinese}" value="${r"${"}fn:substring(bean.${column.columnName}, 0, 19) }" validate="${column.validateType}"/>	
					<#--处理数字-->
					<#elseif column.dataType='BigDecimal' || column.dataType='Long' || column.dataType='Integer'>
							<input type="text" name="${column.columnName}" class="input" inputName="${column.columnNameChinese}" value="<c:out value="${r"${"}bean.${column.columnName} }"/>" validate="${column.validateType}"/>
					<#--默认其它类型的字段。处理普通的text，小于1000个字符-->
					<#else>
							<input type="text" name="${column.columnName}" class="input" inputName="${column.columnNameChinese}" value="<c:out value="${r"${"}bean.${column.columnName} }"/>" maxlength="${column.columnSize}" validate="${column.validateType}"/>
					</#if>
				<#--处理textarea-->
				<#elseif column.inputType='textarea'>
							<textarea name="${column.columnName}" class="textarea" rows="2" cols="24" inputName="${column.columnNameChinese}" validate="${column.validateType}"><c:out value="${r"${"}bean.${column.columnName} }"/></textarea>
				</#if>
						</td>
			</#list>
					</tr>
	</#list>
	<#if table.hasFiles='1'>
					<tr>
						<td class="label">附件：</td>
						<td class="field">
							<jsp:include page="/jsp/include/upload/uploadifyEdit.jsp" flush="true">
								<jsp:param name="fileUploadName" value="common_file_upload"/>
								<jsp:param name="fileListName" value="commonFileList"/>
							</jsp:include>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
	</#if>
</#macro>
<#macro notBuildTableColumn_hiddenInput>
	<#list table.columnsNotBuild as column>
		<input type="hidden" name="${column.columnName}" value="<c:out value="${r"${"}bean.${column.columnName} }"/>"/>
	</#list>
</#macro>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${table.tableNameChinese}<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller");
	
	/** 保存 */
	function save_onclick() {
		if(validateForm()){
			if(<%=isModify%>){
				update_onclick();
			} else {
				insert_onclick();
			}
		}
	}
	
	/** 新增保存 */
	function insert_onclick() {
		formHelper.jSubmit(formHelper.buildInsertAction());
	}
	
	/** 修改保存 */
	function update_onclick() {
		formHelper.jSubmit(formHelper.buildUpdateAction());
	}
	
	$(document).ready(function() {
	
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">${table.tableNameChinese}<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/${table.tableName}"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<@buildTableColumn_insertInput/>
				</table>
			</div>
			<#if table.hasSubTabs='1'>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>页签一</DT>
					<DD>
						<div>
							<iframe	rel="<%=request.getContextPath()%>/${project.jspSourcePath}/${table.tableFormatName?lower_case}/tip${table.tableFormatName?cap_first}.jsp" width="100%" height="300px" frameborder="0" scrolling="auto"></iframe>
						</div>
					</DD>
				</DL>
			</div>
			</#if>
		</div>
		<input type="hidden" name="${table.tablePk}" value="<c:out value="${r"${"}bean.${table.tablePk} }"/>"/>
		<@notBuildTableColumn_hiddenInput/>
	</form>
</body>
</html>
