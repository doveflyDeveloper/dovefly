<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("userController");
	
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
		reference(context_path + "/regionController/reference?level=4", "选择学校", 900, 450, "referenceRegion_callback", "radio", "search", options);
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
			$("input[name='school_id']").val(obj.school_id);
			$("input[name='school_name']").val(obj.school_name);
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
					<div class="table_title">用户<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_user"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<c:if test="${isModify != '1' }">
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>城市：</td>
						<td class="field" colspan="3">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${bean.city_id }" attributes="inputName='城市' validate='notNull'"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>账号：</td>
						<td class="field">
							<input type="text" name="account" class="input" inputName="账号" value="" maxlength="50" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>密码：</td>
						<td class="field">
							<input type="password" name="password" class="input" inputName="密码" value="" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>姓名：</td>
						<td class="field">
							<input type="text" name="real_name" class="input" inputName="姓名" value="<c:out value="${bean.real_name }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="label">邮箱：</td>
						<td class="field">
							<input type="text" name="email" class="input" inputName="邮箱" value="<c:out value="${bean.email }"/>" maxlength="50" validate="isEmail"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>手机号码：</td>
						<td class="field">
							<input type="text" name="mobile" class="input" inputName="手机号码" value="<c:out value="${bean.mobile }"/>" maxlength="11" validate="notNull;isMobile"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>地址：</td>
						<td class="field" colspan="3">
							<input type="text" name="address" class="full_input" inputName="地址" value="<c:out value="${bean.address }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field" colspan="3">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
				</c:if>
				<c:if test="${isModify == '1' }">
				<table class="insert_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
					</tr>
					<tr>
						<td class="label">账号：</td>
						<td class="field"><c:out value="${bean.account }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>姓名：</td>
						<td class="field">
							<input type="text" name="real_name" class="input" inputName="姓名" value="<c:out value="${bean.real_name }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="label">邮箱：</td>
						<td class="field">
							<input type="text" name="email" class="input" inputName="邮箱" value="<c:out value="${bean.email }"/>" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<td class="label">学校：</td>
						<td class="field">
							<input type="hidden" name="school_id" value="<c:out value="${bean.school_id }"/>"/>
							<input type="text" name="school_name" class="reference" value="<c:out value="${bean.school_name }"/>" /><span class="referenceSpan" onclick="referenceRegion();"></span>
			            </td>
			            <td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>手机号码：</td>
						<td class="field" colspan="3">
							<input type="text" name="mobile" class="input" inputName="手机号码" value="<c:out value="${bean.mobile }"/>" maxlength="11" validate="notNull;isMobile"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>地址：</td>
						<td class="field" colspan="3">
							<input type="text" name="address" class="full_input" inputName="地址" value="<c:out value="${bean.address }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field" colspan="3">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
				</c:if>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
	</form>
</body>
</html>
