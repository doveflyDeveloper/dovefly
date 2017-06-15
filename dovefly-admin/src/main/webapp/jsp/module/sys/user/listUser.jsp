<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>用户管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("userController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 新增 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildCreateAction());
	}
	
	/** 复制 */	
	function copy_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildCopyAction(id));
	}
	
	/** 删除 */
	function deleteMulti_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "1")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
	
		var ids = findSelections("dv_checkbox", "value");
		
		dvConfirm("您确定要删除这些数据吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteMultiAction(ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 修改 */
	function find_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildFindAction(id));
	}
	
	/** 查看 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildDetailAction(id));
		} else {//勾选复选框查看
			var ids = findSelections("dv_checkbox", "value");
			if(!ids || !ids.length) {
				dvAlert("请选择一条记录！");
		  		return false;
			}
			if(ids.length > 1) {
				dvAlert("只能选择一条记录！");
		  		return false;
			}
			formHelper.jSubmit(formHelper.buildDetailAction(ids));
		}
	}
	
	/** 授权 */
	function findForAuthRole_onclick(id){
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildAction("userController", "findForAuthRole" , id));
	}
	
	/** 导出列表 */
	function exportList_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("userController", "exportList", ids));
	}
	
	/** 重置密码 */
	function forceResetPwd_onclick(id){
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		var id = $(sels[0]).val();
		var account = $(sels[0]).attr("account");
		
		var html = '<form id="form_forceResetPwd">' + 
		'<table class="insert_table">' + 
		'	<tr>' + 
		'		<td class="label">账号：</td>' + 
		'		<td class="field">' + account + '</td>' + 
		'	</tr>' + 
		'	<tr>' + 
		'		<td class="label"><span class="required_red">* </span>新密码：</td>' + 
		'		<td class="field">' + 
		'			<input type="password" name="password" class="input" inputName="新密码" value="' + account + '" maxlength="50" validate="notNull"/>' + 
		'		</td>' + 
		'	</tr>' + 
		'	<tr>' + 
		'		<td class="label"><span class="required_red">* </span>确认密码：</td>' + 
		'		<td class="field">' + 
		'			<input type="password" name="confirm_password" class="input" inputName="确认密码" value="' + account + '" maxlength="50" validate="notNull;equalsTo(\'password\')"/>' + 
		'		</td>' + 
		'	</tr>' + 
		'</table>' + 
		'<input type="hidden" name="id" value="' + id + '"/>' + 
		'<input type="hidden" name="account" value="' + account + '"/>' + 
		'</form>';
		
		dvPrompt(html, '重置密码', {
			bottomText: '<span class="left_ts">默认密码重置为当前用户名！</span>',
			submit: function(v, h, f) {
				if (validateForm("form_forceResetPwd")) {
					$.ajax({
						type: "POST",
						async: true,
						dataType: "json",
						url: context_path + "/userController/forceResetPwd",
						data: $("#form_forceResetPwd").serialize(),
						success: function(result){
							if(result){//存在
								if(result.success){
									dvTip(result.message, "success");
								} else {
									dvTip(result.message, "error");
								}
							}
						}
					});
					return true;
			    } else {
			    	return false;
			    }
			}
		});
	}

	/** 启用 */
	function enable_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || !ids.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		dvConfirm("您确定要启用这些用户吗？", 
			function() {
				formHelper.submit(formHelper.buildAction("userController", "enable", ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 停用 */
	function disable_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || !ids.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		dvConfirm("您确定要停用这些用户吗？", 
			function() {
				formHelper.submit(formHelper.buildAction("userController", "disable", ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}

	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
	
	});

</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">查询条件&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_user"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<c:if test="${sessionScope.DV_USER_VO.headquartersRole}">
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						</c:if>
						<td class="label">学校：</td>
						<td class="field">
							<input type="text" name="school_name" class="input" maxlength="50" value="<c:out value="${param.school_name }"/>"/>
						</td>
						<td class="label">用户姓名：</td>
						<td class="field">
							<input type="text" name="real_name" class="input" maxlength="20" value="<c:out value="${param.real_name }"/>"/>	
						</td>
					</tr>
					<tr>
						<td class="label">联系电话：</td>
						<td class="field">
							<input type="text" name="mobile" class="input" maxlength="11" value="<c:out value="${param.mobile }"/>"/>	
						</td>
						<td class="label">微信昵称：</td>
						<td class="field">
							<input type="text" name="wechat_account" class="input" maxlength="30" value="<c:out value="${param.wechat_account }"/>"/>	
						</td>
						<td class="label">用户状态：</td>
						<td class="field">
							<dv:radio name="status" hasEmpty="true" dicKeyword="DIC_STATUS" defaultValue="${param.status }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">用户列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${sessionScope.DV_USER_VO.superAdminRole }">
						<input type="button" name="insert" value="新增" class="button" onclick="add_onclick();"/>
						<input type="button" name="update" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="authorize" value="角色授权" class="button" onclick="findForAuthRole_onclick();"/>
						<input type="button" name="forceResetPwd" value="重置密码" class="button" onclick="forceResetPwd_onclick();"/>
						</c:if>
						<c:if test="${sessionScope.DV_USER_VO.superAdminRole || sessionScope.DV_USER_VO.cityManagerRole }">
						<input type="button" name="enable" value="启用" class="button" onclick="enable_onclick();"/>
						<input type="button" name="desable" value="停用" class="button" onclick="disable_onclick();"/>
						</c:if>
						<input type="button" name="exportList" value="导出用户" class="button" onclick="exportList_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="5%">微信头像</th>
						<th width="10%">微信昵称</th>
						<th width="10%">用户姓名</th>
						<th width="10%">联系电话</th>
						<th width="5%">城市</th>
                        <th width="17%">学校</th>
						<th width="5%">用户状态</th>
						<th width="11%">创建时间</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" account="${bean.account }" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><img class="thumbnail" src="${bean.wechat_avatar }"/></td>
						<td><c:out value="${bean.wechat_account }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.real_name }"/></a></td>
						<td><c:out value="${bean.mobile }"/></td>
						<td><c:out value="${bean.city_name }"/></td>
						<td><c:out value="${bean.school_name }"/></td>
						<td><dv:display dicKeyword="DIC_STATUS" value="${bean.status }"/></td>
						<td>${fn:substring(bean.create_at, 0, 19) }</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="page_div">
			<jsp:include page="/jsp/include/page.jsp"/>
		</div>
	</form>
</body>
</html>
