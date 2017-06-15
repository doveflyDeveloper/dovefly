<#--处理各列的循环查询输入框-->
<#macro buildTableColumn_queryInput>
		<#list table.columnsIsBuildList?chunk(2) as row> 
					<tr<#if row_index gt 1 > class="hidden"</#if>>
				<#list row as column>
						<td class="label">${column.columnNameChinese}：</td>
						<td class="field">
					<#--处理dv.dictionary.select (表单域类型)-->
					<#if column.inputType='dv.dictionary.select'>
							<dv:select name="${column.columnName}" dicKeyword="${column.dicTypeKeyword}" defaultValue="${r"${"}param.${column.columnName} }" hasEmpty="true"/>
					<#--处理dv.dictionary.checkbox (表单域类型)-->
					<#elseif column.inputType='dv.dictionary.checkbox'>
							<dv:checkbox name="${column.columnName}" dicKeyword="${column.dicTypeKeyword}" defaultValue="${r"${"}param.${column.columnName} }"/>
					<#--处理dv.dictionary.radio (表单域类型)-->
					<#elseif column.inputType='dv.dictionary.radio'>
							<dv:radio name="${column.columnName}" dicKeyword="${column.dicTypeKeyword}" defaultValue="${r"${"}param.${column.columnName} }"/>
					<#--处理dv.listReference (列表参照)-->
					<#elseif column.inputType='dv.listReference'>
							<input type="hidden" name="${column.columnName}_id" value="<c:out value="${r"${"}param.${column.columnName}_id }"/>"/>
							<input type="text" name="${column.columnName}_name" class="reference" value="<c:out value="${r"${"}param.${column.columnName}_name }"/>"/><span class="referenceSpan" onclick="referenceOrg();"></span>
					<#--处理dv.orgReference (组织结构参照)-->
					<#elseif column.inputType='dv.orgReference'>
							<input type="hidden" name="${column.columnName}_id" value="<c:out value="${r"${"}param.${column.columnName}_id }"/>"/>
							<input type="text" name="${column.columnName}_name" class="reference" value="<c:out value="${r"${"}param.${column.columnName}_name }"/>"/><span class="referenceSpan" onclick="referenceOrg();"></span>
					<#--处理default(默认)-->
					<#elseif column.inputType='text' || column.inputType='' || column.inputType='dv.htmlArea'>
						<#--处理时间参照-->
						<#if column.dataType='Timestamp'>
							<input type="text" name="${column.columnName}_from" class="half_input Wdate" value="${r"${"}fn:substring(param.${column.columnName}_from, 0, 19) }" format="both"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="${column.columnName}_to" class="half_input Wdate" value="${r"${"}fn:substring(param.${column.columnName}_to, 0, 19) }" format="both"/>
						<#--处理日期参照-->
						<#elseif column.dataType='Date'>
							<input type="text" name="${column.columnName}_from" class="half_input Wdate" value="${r"${"}fn:substring(param.${column.columnName}_from, 0, 10) }"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="${column.columnName}_to" class="half_input Wdate" value="${r"${"}fn:substring(param.${column.columnName}_to, 0, 10) }"/>
						<#--处理数字-->
						<#elseif column.dataType='BigDecimal' || column.dataType='Long' || column.dataType='Integer'>
							<input type="text" name="${column.columnName}" class="input" maxlength="${column.columnSize}" value="<c:out value="${r"${"}param.${column.columnName} }"/>"/>
						<#--其他字段都按此处理。处理普通的text-->
						<#else>
							<input type="text" name="${column.columnName}" class="input" maxlength="${column.columnSize}" value="<c:out value="${r"${"}param.${column.columnName} }"/>"/>
						</#if>
					</#if>
						</td>
				</#list>
					</tr>
		</#list>
</#macro>
<#macro buildTableColumn_listHeader>
	<#list table.columnsIsBuildList as column> 
						<th width="10%">${column.columnNameChinese}</th>
	</#list>
</#macro>
<#macro buildTableColumn_listLayout>
	<#list table.columnsIsBuildList as column> 
			<#--处理dv.dictionary.select checkbox radio (表单域类型)-->
			<#if column.inputType='dv.dictionary.select' || column.inputType='dv.dictionary.checkbox' || column.inputType='dv.dictionary.radio'>
						<td><dv:display dicKeyword="${column.dicTypeKeyword}" value="${r"${"}bean.${column.columnName} }"/></td>
			<#--处理textarea-->
			<#elseif column.inputType='dv.htmlArea'>
						<td><c:out value="${r"${"}bean.${column.columnName} }"/></td>
			<#--处理时间戳参照-->
			<#elseif column.inputType='text' && column.dataType='Timestamp'>
						<td>${r"${"}fn:substring(bean.${column.columnName}, 0, 19) }</td>
			<#--处理日期参照-->
			<#elseif column.inputType='text' && column.dataType='Date'>
						<td>${r"${"}fn:substring(bean.${column.columnName}, 0, 10) }</td>
			<#else>
				<#--关键列，即超链接点击可查看详细的列，如单据的名称等-->
				<#if column.columnName=table.keyColumn>
						<td><a href="javascript:detail_onclick('<c:out value="${r"${"}bean.${table.tablePk} }"/>')"><c:out value="${r"${"}bean.${column.columnName} }"/></a></td>
				<#else>
						<td><c:out value="${r"${"}bean.${column.columnName} }"/></td>
				</#if>
			</#if>				
	</#list>
</#macro>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>${table.tableNameChinese}管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 新增 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildCreateAction());
	}
	
	/** 复制 */	
	function copy_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildCopyAction(id));
	}
	
	/** 删除 */
	function deleteMulti_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "1")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
	
		var ids = findSelections("dv_checkbox", "value");
		
		dvConfirm("您确定要删除这些数据吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteMultiAction(ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 修改 */
	function find_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildFindAction(id));
	}
	
	/** 查看 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildDetailAction(id));
		} else {//勾选复选框查看
			var ids = findSelections("dv_checkbox", "value");
			if(!ids || !ids.length) {
				dvAlert("请选择一条记录！");
		  		return false;
			}
			if(ids.length > 1) {
				dvAlert("只能选择一条记录！");
		  		return false;
			}
			formHelper.jSubmit(formHelper.buildDetailAction(ids));
		}
	}
	<#if table.hasImpExp='1'>
	
	/** 导入 */
	function importExcel_onclick() {
		var redirectUrl = "${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller/importExcel";
		var downloadUrl = "${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller/downloadTemplate";
		var title = "${table.tableNameChinese}导入";
		dvOpenImportDialog(redirectUrl, downloadUrl, title);
	}
	
	/** 导出 */
	function exportDetail_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		var id = $(sels[0]).val();
		formHelper.submit(formHelper.buildAction("${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller", "exportDetail", id));
	}
	
	/** 批量导出 */
	function exportList_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";	
		formHelper.submit(formHelper.buildAction("${table.tableFormatName?substring(0,1)?lower_case}${table.tableFormatName?substring(1)}Controller", "exportList", ids));
	}
	</#if>
	
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
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/${table.tableName}"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${r"${"}param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<@buildTableColumn_queryInput/>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">${table.tableNameChinese}列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="insert" value="新增" class="button" onclick="add_onclick();"/>
						<input type="button" name="update" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="delete" value="删除" class="button" onclick="deleteMulti_onclick();"/>
						<#if table.hasImpExp='1'>
						<input type="button" name="importExcel" value="导入" class="button" onclick="importExcel_onclick();"/>
						<input type="button" name="exportDetail" value="导出" class="button" onclick="exportDetail_onclick();"/>
						<input type="button" name="exportList" value="批量导出" class="button" onclick="exportList_onclick();"/>
						</#if>
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
						<@buildTableColumn_listHeader/>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${r"${"}beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${r"${"}bean.${table.tablePk} }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${r"${"}status.count }</td>
						<@buildTableColumn_listLayout/>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="page_div">
			<jsp:include page="/jsp/include/page.jsp"/>
		</div>
	</form>
</body>
</html>
