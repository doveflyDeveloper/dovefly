<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店铺<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("shopController");
	
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
	
	// 选择学校
	function referenceRegion() {
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/regionController/reference", "选择学校", 900, 450, "referenceRegion_callback", "radio", "search", options);
	}
	
	/**
	 * 参照学校
	 * 
	 * @param datas
	 */
	function referenceRegion_callback(datas) {
	// 枚举（循环）对象的所有属性
		for (i in datas) {
			var obj = datas[i];
			$("input[name='school_id']").val(obj.region_id);
			$("input[name='school_name']").val(obj.region_name);
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
					<div class="table_title">店铺<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_shop"></div>
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
						<td class="label"><span class="required_red">* </span>学校：</td>
						<td class="field">
							<input type="hidden" name="school_id" value="<c:out value="${param.school_id }"/>"/>
							<input type="text" name="school_name" class="reference" value="<c:out value="${param.school_name }"/>" inputName="学校" validate="notNull"/><span class="referenceSpan" onclick="referenceSupplier();"></span>
			            </td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>店铺：</td>
						<td class="field">
							<input type="text" name="shop_name" class="input" inputName="店铺" value="<c:out value="${bean.shop_name }"/>" maxlength="20" validate="notNull"/>
						</td>
						<td class="label">店铺描述：</td>
						<td class="field">
							<input type="text" name="shop_desc" class="input" inputName="店铺描述" value="<c:out value="${bean.shop_desc }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">配送区域说明：</td>
						<td class="field">
							<input type="text" name="shop_area" class="input" inputName="配送区域说明" value="<c:out value="${bean.shop_area }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label"><span class="required_red">* </span>起送价：</td>
						<td class="field">
							<input type="text" name="start_amount" class="input" inputName="起送价" value="<c:out value="${bean.start_amount }"/>" validate="notNull"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="warehouse_id" value="<c:out value="${bean.warehouse_id }"/>"/>
		<input type="hidden" name="warehouse_name" value="<c:out value="${bean.warehouse_name }"/>"/>
		<input type="hidden" name="manager_id" value="<c:out value="${bean.manager_id }"/>"/>
		<input type="hidden" name="manager_name" value="<c:out value="${bean.manager_name }"/>"/>
		<input type="hidden" name="shopkeeper_id" value="<c:out value="${bean.shopkeeper_id }"/>"/>
		<input type="hidden" name="shopkeeper_name" value="<c:out value="${bean.shopkeeper_name }"/>"/>
		<input type="hidden" name="shop_status" value="<c:out value="${bean.shop_status }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
