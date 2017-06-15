<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.tcommonfile.vo.TCommonFileVo" %>
<%@ page import="com.deertt.module.tcommonfile.util.ITCommonFileConstants" %>

<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=ITCommonFileConstants.TABLE_NAME_CHINESE %><%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("tCommonFileController");
	
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
					<div class="table_title"><%=ITCommonFileConstants.TABLE_NAME_CHINESE %><%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%><%=ITCommonFileConstants.TIP_JSP_URL%>"></div>
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
						<td class="label">${TABLE_COLUMN_CHINESE.owner_table_name }：</td>
						<td class="field">
							<input type="text" name="owner_table_name" class="input" inputName="${TABLE_COLUMN_CHINESE.owner_table_name }" value="<c:out value="${bean.owner_table_name }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_bill_id }：</td>
						<td class="field">
							<input type="text" name="owner_bill_id" class="input" inputName="${TABLE_COLUMN_CHINESE.owner_bill_id }" value="<c:out value="${bean.owner_bill_id }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_column_name }：</td>
						<td class="field">
							<input type="text" name="owner_column_name" class="input" inputName="${TABLE_COLUMN_CHINESE.owner_column_name }" value="<c:out value="${bean.owner_column_name }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.file_name }：</td>
						<td class="field">
							<input type="text" name="file_name" class="input" inputName="${TABLE_COLUMN_CHINESE.file_name }" value="<c:out value="${bean.file_name }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.file_url }：</td>
						<td class="field">
							<input type="text" name="file_url" class="input" inputName="${TABLE_COLUMN_CHINESE.file_url }" value="<c:out value="${bean.file_url }"/>" maxlength="200" validate=""/>
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.file_type }：</td>
						<td class="field">
							<input type="text" name="file_type" class="input" inputName="${TABLE_COLUMN_CHINESE.file_type }" value="<c:out value="${bean.file_type }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.file_size }：</td>
						<td class="field">
							<input type="text" name="file_size" class="input" inputName="${TABLE_COLUMN_CHINESE.file_size }" value="<c:out value="${bean.file_size }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>		
		<input type="hidden" name="file_id" value="<c:out value="${bean.file_id }"/>"/>
		<input type="hidden" name="usable_state" value="<c:out value="${bean.usable_state }"/>"/>
		<input type="hidden" name="order_code" value="<c:out value="${bean.order_code }"/>"/>
		<input type="hidden" name="create_user_id" value="<c:out value="${bean.create_user_id }"/>"/>
		<input type="hidden" name="create_time" value="<c:out value="${bean.create_time }"/>"/>
		<input type="hidden" name="create_ip" value="<c:out value="${bean.create_ip }"/>"/>
		<input type="hidden" name="modify_user_id" value="<c:out value="${bean.modify_user_id }"/>"/>
		<input type="hidden" name="modify_time" value="<c:out value="${bean.modify_time }"/>"/>
		<input type="hidden" name="modify_ip" value="<c:out value="${bean.modify_ip }"/>"/>
		<input type="hidden" name="reserved_1" value="<c:out value="${bean.reserved_1 }"/>"/>
		<input type="hidden" name="reserved_2" value="<c:out value="${bean.reserved_2 }"/>"/>
		<input type="hidden" name="reserved_3" value="<c:out value="${bean.reserved_3 }"/>"/>
		<input type="hidden" name="reserved_4" value="<c:out value="${bean.reserved_4 }"/>"/>
		<input type="hidden" name="reserved_5" value="<c:out value="${bean.reserved_5 }"/>"/>
	</form>
</body>
</html>
