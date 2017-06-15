<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典类型<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("dictTypeController");
	
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
	

	/** 数据字典类型关键词不能重复 */
	function notRepeat(s, thisInput) { // 不能为空
		var result = true;
		if(s.length == 0) 
			return true;
		var defaultValue = document.getElementById("keyword").defaultValue;
		if (defaultValue == s) {
			return true;
		}
		
		$.ajax({
			type: "POST",
			async: false,
			dataType: "json",
			url: context_path + "/dictTypeController/checkRepeat",
			data: {keyword: $.trim($("input[name='keyword']").val())},
			success: function(result){
				if(result){//存在
					if(result.success){
						result = true;
					} else {
						var inputName = getInputName(thisInput);
						validateFailed(inputName + "已经存在，请重新输入 ", thisInput);
						result = false;
					}
				}
			}
		});
		return result;
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
					<div class="table_title">字典类型<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_dict_type"></div>
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
					<tr>
						<td class="label"><span class="required_red">* </span>标识关键词：</td>
						<td class="field">
							<input type="text" id="keyword" name="keyword" class="input" inputName="标识关键词" value="<c:out value="${bean.keyword }"/>" maxlength="50" validate="notNull;notRepeat"/>
						</td>
						<td class="label"><span class="required_red">* </span>字典名称：</td>
						<td class="field">
							<input type="text" name="name" class="input" inputName="字典名称" value="<c:out value="${bean.name }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label">状态：</td>
						<td class="field">
							<dv:select name="status" dicKeyword="DIC_STATUS" defaultValue="${bean.status }" attributes="inputName='状态'"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
	</form>
</body>
</html>
