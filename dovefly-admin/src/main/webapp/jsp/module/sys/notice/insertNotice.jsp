<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统公告<%=isModify ? "修改" : "新增"%></title>
<link href="<%=request.getContextPath()%>/resources/js/umeditor/themes/default/css/umeditor.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.config.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.min.js"></script>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("noticeController");
	
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
	
	/** 保存并发布 */
	function saveAndPublish_onclick() {
		formHelper.jSubmit(formHelper.buildAction("noticeController", "saveAndPublish"));
	}
	
	$(document).ready(function() {
		var um = UM.getEditor('content');
		um.options.imageUrl = context_path + "/ueditor/image/notice";

	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">系统公告<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_notice"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="save" value="保存并发布" class="button" onclick="saveAndPublish_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>标题：</td>
						<td class="field">
							<input type="text" name="title" class="input" inputName="标题" value="<c:out value="${bean.title }"/>" maxlength="100" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>类型：</td>
						<td class="field">
							<dv:select name="type" dicKeyword="DIC_NOTICE_TYPE" hasEmpty="true" defaultValue="${bean.type }" attributes="inputName='类型' validate='notNull'"/>
						</td>
					</tr>
					<tr>
						<td class="label">公告附件：</td>
						<td class="field">
							<jsp:include page="/jsp/include/upload/uploadifyEdit.jsp" flush="true">
								<jsp:param name="fileUploadName" value="common_file_upload"/>
								<jsp:param name="fileListName" value="commonFileList"/>
							</jsp:include>
						</td>
						<td class="label"></td>
						<td class="field">
						</td>
					</tr>
					<tr>
						<td class="label">新闻内容：</td>
						<td class="field" colspan="3">
							<!--style给定宽度可以影响编辑器的最终宽度-->
							<script id="content" name="content" type="text/plain" style="width:800px;height:400px;" inputName="新闻内容">${bean.content }</script>
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field"></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="read_times" value="<c:out value="${bean.read_times }"/>"/>
		<input type="hidden" name="issue_status" value="<c:out value="${bean.issue_status }"/>"/>
		<input type="hidden" name="issue_at" value="<c:out value="${bean.issue_at }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
