<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
<!--

-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("upload");
	
	function import_onclick() {
		if(validateForm()){
			formHelper.jSubmit("<%=request.getContextPath()%>/upload/importExcel");
		}
		return false;
	}
	function download_onclick() {
		formHelper.submit("<%=request.getContextPath()%>/<c:out value="${param.downloadUrl }"/>");
		return false;
	}
	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
	
	});
</script>
</head>

<body>
	<form id="form" name="form" method="post" enctype="multipart/form-data" target="_parent">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">数据上传</div>
				</div>
				<!-- 
				<div class="right_div">
					 <div class="right_menu">
						<input type="button" name="download" value="下载模板" class="button" onclick="download_onclick();" />
						<input type="button" name="import" value="导入(excel表信息录入系统)" class="button" onclick="import_onclick();"/>
					</div>
				</div>
				 -->
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label" style="width: 10%" >上传文件：</td>
						<td class="field" colspan="3">
							<input type="file" name="myfile" class="input" style="width: 400px" inputName="上传文件" value="" validate="notNull"/>
						</td>
					</tr>					
				</table>
			</div>
		</div>
		<input type="hidden" name="redirectUrl" value="${param.redirectUrl }">
	</form>
</body>
</html>