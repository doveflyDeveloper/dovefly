<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>超市<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("marketController");
	
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
					<div class="table_title">超市<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_market"></div>
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
						<td class="label"><span class="required_red">* </span>超市：</td>
						<td class="field">
							<input type="text" name="market_name" class="input" inputName="超市" value="<c:out value="${bean.market_name }"/>" maxlength="20" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">超市描述：</td>
						<td class="field">
							<input type="text" name="market_desc" class="input" inputName="超市描述" value="<c:out value="${bean.market_desc }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label">配送区域说明：</td>
						<td class="field">
							<input type="text" name="market_area" class="input" inputName="配送区域说明" value="<c:out value="${bean.market_area }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>起送价：</td>
						<td class="field">
							<input type="text" name="start_amount" class="input" inputName="起送价" value="<c:out value="${bean.start_amount }"/>" validate="notNull"/>
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
		<input type="hidden" name="shopkeeper_id" value="<c:out value="${bean.shopkeeper_id }"/>"/>
		<input type="hidden" name="shopkeeper_name" value="<c:out value="${bean.shopkeeper_name }"/>"/>
		<input type="hidden" name="market_status" value="<c:out value="${bean.market_status }"/>"/>
		<input type="hidden" name="market_sort" value="<c:out value="${bean.market_sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
