<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>兼职招聘<%=isModify ? "修改" : "新增"%></title>
<link href="<%=request.getContextPath()%>/resources/js/umeditor/themes/default/css/umeditor.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.config.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.min.js"></script>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("jobController");
	
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
	
	/** 保存并发布*/
	function saveAndIssue_onclick() {
		if(validateForm()){
	      formHelper.jSubmit(formHelper.buildAction("jobController", "saveAndIssue"));
		}
	}
	
	// 选择处理人
	function referenceUser() {
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/userController/reference?reference=job", "选择处理人", 900, 450, "referenceUser_callback", "radio", "search", options);
	}

	/**
	 * 参照处理人
	 * 
	 * @param datas
	 */
	function referenceUser_callback(datas) {
	// 枚举（循环）对象的所有属性
		for (i in datas) {
			var obj = datas[i];
			$("input[name='user_id']").val(obj.user_id);
			$("input[name='user_name']").val(obj.user_name);
		}
	}
	
	$(document).ready(function() {
		var um = UM.getEditor('description');
		um.options.imageUrl = context_path + "/ueditor/image/job";
		
		initImages();
		
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">兼职招聘<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sc_job"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="save" value="保存并发布" class="button" onclick="saveAndIssue_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
				   <tr>
				    <td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${bean.city_id }" hasEmpty="true"/>
						</td>
				   <tr>
						<td class="label"><span class="required_red">* </span>标题：</td>
						<td class="field" colspan="3">
							<input type="text" name="title" class="input" inputName="标题" value="<c:out value="${bean.title }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					 <tr>
						<td class="label"><span class="required_red">* </span>兼职类型：</td>
						<td class="field" colspan="3">
							<dv:select name="job_type" dicKeyword="DIC_SC_JOB_TYPE" defaultValue="${bean.job_type }" hasEmpty="true"  attributes=" inputName='兼职类型' validate='notNull'"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>招聘人数：</td>
						<td class="field">
							<input type="text" name="need_num" class="input" inputName="招聘人数" value="<c:out value="${bean.need_num }"/>" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>职位薪资：</td>
						<td class="field">
							<input type="text" name="salary"  class="input" inputName="职位薪资" value="<c:out value="${bean.salary }"/>" maxlength="100"  validate="notNull"/>
							<dv:select name="salary_unit"  dicKeyword="DIC_SC_JOB_SALARY_UNIT" defaultValue="${bean.salary_unit }" hasEmpty="true" attributes="style='width:80px;' inputName='薪资单位' validate='notNull'"/>
						</td>
						<td class="field" colspan="3">
							
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>招聘公司：</td>
						<td class="field">
							<input type="text" name="company" class="input" inputName="招聘公司" value="<c:out value="${bean.company }"/>" maxlength="100" validate="notNull"/>
						</td>
					<tr>
						<td class="label"><span class="required_red">* </span>工作地点：</td>
						<td class="field">
							<input type="text" name="work_place" class="input" inputName="工作地点" value="<c:out value="${bean.work_place }"/>" maxlength="100"  validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>联系人电话：</td>
						<td class="field">
							<input type="text" name="contact_phone" class="input" inputName="联系人电话" value="<c:out value="${bean.contact_phone }"/>" maxlength="100" validate="isMobile"/>
						</td>
					   	<td class="label"><span class="required_red">* </span>处理人：</td>
						<td class="field">
							<input type="hidden" name="user_id" value="<c:out value="${bean.user_id }"/>"/>
							<input type="text" name="user_name" class="reference" inputName="处理人" value="<c:out value="${bean.user_name }"/>" maxlength="50" validate="notNull"/><span class="referenceSpan" onclick="referenceUser();"></span>
						</td>
					<tr>
						<td class="label"><span class="required_red">* </span>工作发布时间：</td>
						<td class="field">
								<input type="text" name="issue_time" class="input Wdate" inputName="发布日期" value="<c:out value="${bean.issue_time }"/>" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>工作起始日期：</td>
						<td class="field">
							<input type="text" name="begin_date" class="input Wdate" inputName="起始日期"  value="<c:out value="${bean.begin_date}"/>" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>工作结束时间：</td>
						<td class="field">
							<input type="text" name="end_date" class="input Wdate" inputName="结束时间"  value="<c:out value="${bean.end_date}"/>"  validate="notNull" />	
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field" colspan="3">
							<script id="description" name="description" type="text/plain" inputName="工作内容描述">${bean.description }</script>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
