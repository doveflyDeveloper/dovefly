<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>汀豆使用规则<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("coinRuleController");
	
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
					<div class="table_title">汀豆使用规则<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_coin_rule"></div>
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
						<td class="label"><span class="required_red">* </span>单笔限用金额：</td>
						<td class="field">
							<input type="text" name="reach_amount" class="input" inputName="单笔限用金额" value="<c:out value="${bean.reach_amount }"/>" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>单笔限用汀豆数量：</td>
						<td class="field">
							<input type="text" name="limit_quantity" class="input" inputName="单笔限用汀豆数量" value="<c:out value="${bean.limit_quantity }"/>" validate="notNull"/>
						</td>
					</tr>
					<tr>
					    <td class="label">活动开始时间：</td>
						<td class="field">
							<input type="text" name="start_time" class="input Wdate" inputName="活动开始时间" value="${fn:substring(bean.start_time, 0, 19) }" validate="" format="both"/>	
						</td>
					    <td class="label">活动结束时间：</td>
						<td class="field">
							<input type="text" name="end_time" class="input Wdate" inputName="活动结束时间" value="${fn:substring(bean.end_time, 0, 19) }" validate="" format="both"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>返还汀豆：</td>
						<td class="field">
							<input type="text" name="send_quantity" class="input" inputName="返还汀豆" value="<c:out value="${bean.send_quantity }"/>" validate="notNull"/>
						</td>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
		<input type="hidden" name="create_by" value="<c:out value="${bean.create_by }"/>"/>
		<input type="hidden" name="create_at" value="<c:out value="${bean.create_at }"/>"/>
		<input type="hidden" name="modify_by" value="<c:out value="${bean.modify_by }"/>"/>
		<input type="hidden" name="modify_at" value="<c:out value="${bean.modify_at }"/>"/>
	</form>
</body>
</html>
