<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("supplierController");
	
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
					<div class="table_title">供应商<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/supplier"></div>
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
				    	<td class="label"><span class="required_red">* </span>城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${bean.city_id }" hasEmpty="true" attributes="inputName='城市' validate='notNull'"/>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>供应商：</td>
						<td class="field">
							<input type="text" name="name" class="input" inputName="供应商" value="<c:out value="${bean.name }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="label">公司邮箱：</td>
						<td class="field">
							<input type="text" name="email" class="input" inputName="公司邮箱" value="<c:out value="${bean.email }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>公司电话：</td>
						<td class="field">
							<input type="text" name="tel" class="input" inputName="公司电话" value="<c:out value="${bean.tel }"/>" maxlength="20" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>公司地址：</td>
						<td class="field">
							<input type="text" name="address" class="input" inputName="公司地址" value="<c:out value="${bean.address }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>联系人：</td>
						<td class="field">
							<input type="text" name="linkman" class="input" inputName="联系人" value="<c:out value="${bean.linkman }"/>" maxlength="10" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>联系人电话：</td>
						<td class="field">
							<input type="text" name="linkman_mobile" class="input" inputName="联系人电话" value="<c:out value="${bean.linkman_mobile }"/>" maxlength="11" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
