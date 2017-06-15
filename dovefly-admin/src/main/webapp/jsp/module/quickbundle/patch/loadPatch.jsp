<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.quickbundle.vo.Project" %>

<%
  	boolean isModify = (request.getAttribute("isModify") != null);
  	
%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>补丁包生成</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("patchController");
	
	function generatePatch_onclick() {
		if(validateForm()){
			formHelper.jSubmit(formHelper.buildAction("patchController", "generatePatch"));
		}
	}

	function connectDB_onclick() {
		var v = $("#files_list_str").val();
		$("#files_list_str").removeAttr("validate");
		if(validateForm()){
			$("#files_list_str").attr("validate", "notNull");
			$.ajax({
				type: "POST",
				async: false,
				dataType: "text",
				url: context_path + "/patchController/formateFilePath",
				data: $("form").serialize(),
				success: function(result){
					if(result){//存在
						$("#files_list_str").val(result).focus();
					}
				},
				error: function() {
					dvTip("数据库连接失败！", "error");
				}
			});
		}
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
					<div class="table_title">数据库信息</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="创建补丁包" class="button" onclick="generatePatch_onclick();"/>
					</div>
				</div>
			</div>
			<div>
				<table class="query_table">
					<tr>
						<td class="label"><span class="required_red">* </span>用户名：</td>
						<td class="field">
							<input type="text" name="user_name" class="input" maxlength="100" value="<c:out value="${bean.user_name }"/>" inputName="用户名" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>清除历史：</td>
						<td class="field">
							<input type="text" name="is_clear_old" class="input" maxlength="100" value="<c:out value="${bean.is_clear_old }"/>" inputName="清除历史" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>本地SVN路径：</td>
						<td class="field" colspan="3">
							<input type="text" name="svn_patches_dir" class="full_input" maxlength="100" value="<c:out value="${bean.svn_patches_dir }"/>" inputName="本地SVN路径" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>拷贝到本地SVN路径：</td>
						<td class="field">
							<input type="text" name="is_copy_patch" class="input" maxlength="20" value="<c:out value="${bean.is_copy_patch }"/>" inputName="拷贝到本地SVN路径" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>文件列表：</td>
						<td class="field">
							<input type="text" name="document" class="input" maxlength="20" value="<c:out value="${bean.document }"/>" inputName="文件列表" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field">
							<input type="button" name="connect" value="格式化文件路径" class="button" onclick="connectDB_onclick();" />
						</td>
						<td class="label"></td>
						<td class="field">
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>文件列表：</td>
						<td class="field" colspan="3">
							<textarea id="files_list_str" name="files_list_str" rows="20" cols="100" inputName="文件列表" validate="notNull"><c:out value="${bean.files_list_str }"/></textarea>
						</td>
					</tr>
				</table>
			</div>			
		</div>
	</form>
</body>
</html>
