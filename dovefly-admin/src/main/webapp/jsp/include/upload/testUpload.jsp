<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.dict.data.vo.DictDataVo" %>
<%@ page import="com.deertt.module.sys.dict.data.util.IDictDataConstants" %>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("tDictionaryDataController");
	
	function save_onclick() {
		if(validateForm()){
			if(<%=isModify%>){
				update_onclick();
			} else {
				insert_onclick();
			}
		}
	}
	
	function insert_onclick() {
		formHelper.jSubmit(formHelper.buildInsertAction());
	}
	
	function update_onclick() {
		formHelper.jSubmit(formHelper.buildUpdateAction());
	}
	
	function referenceOrg() {
		reference("<%=request.getContextPath()%>/tDictionaryDataController/reference", "参考", 600, 400, "callback", "checkbox");
	}

	function reference(url, title, width, height, callback, inputType){
		if(url){
			if(url.lastIndexOf(/\?/) > 0){//原URL已带有参数
				url += "&callback=" + callback + "&inputType=" + inputType;
			} else {//原URL无参数
				url += "?callback=" + callback + "&inputType=" + inputType;
			}
		}
		dvOpenDialog(url, title, width, height);
	}
			
	function callback(datas){
		dvCloseDialog();//关闭参照窗口
		// 枚举（循环）对象的所有属性
		for (i in datas) {
			var str = "";
			var obj = datas[i];
			for (prop in obj) {
				str += prop + "=" + obj[prop] + "\n";
			}
			alert(str);
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
					<div class="table_title"><%= isModify ? "修改" : "新增"%>&nbsp;</div>
				</div>
				<div class="right_div">
					<div style="text-align: right">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>${TABLE_COLUMN_CHINESE.type_keyword }：</td>
						<td class="field">
							<input type="text" name="type_keyword" class="readonly" inputName="${TABLE_COLUMN_CHINESE.type_keyword }" value="<c:out value="${bean.type_keyword }"/>" maxlength="100" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>${TABLE_COLUMN_CHINESE.data_key }：</td>
						<td class="field">
							<input type="text" name="data_key" class="input" inputName="${TABLE_COLUMN_CHINESE.data_key }" value="<c:out value="${bean.data_key }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>${TABLE_COLUMN_CHINESE.data_value }：</td>
						<td class="field">
							<input type="text" name="data_value" class="input" inputName="${TABLE_COLUMN_CHINESE.data_value }" value="<c:out value="${bean.data_value }"/>" maxlength="100" validate="notNull"/>
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.data_order }：</td>
						<td class="field">
							<input type="text" name="data_order" class="input" inputName="${TABLE_COLUMN_CHINESE.data_order }" value="<c:out value="${bean.data_order }"/>" maxlength="50" />
						</td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.data_remark }：</td>
						<td class="field">
							<input type="text" name="data_remark" class="input" inputName="${TABLE_COLUMN_CHINESE.data_remark }" value="<c:out value="${bean.data_remark }"/>" maxlength="200" />
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.enabled }：</td>
						<td class="field">
							<dv:select name="enabled" dicKeyword="DIC_STATUS" defaultValue="${bean.enabled }" attributes="inputName='是否启用'"/>
						</td>
					</tr>
					<tr>
						<td class="label">附件：</td>
						<td class="field" colspan="3">
							<jsp:include page="/jsp/include/upload/uploadifyEdit.jsp" flush="true">
								<jsp:param value="fileUploadName" name="common_file_upload"/>
								<jsp:param value="fileListName" name="commonFileList"/>
							</jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<div>
				
			</div>
		</div>		
		<input type="hidden" name="data_id" value="<c:out value="${bean.data_id }"/>"/>
		<input type="hidden" name="type_id" value="<c:out value="${bean.type_id }"/>"/>
	</form>
</body>
</html>
