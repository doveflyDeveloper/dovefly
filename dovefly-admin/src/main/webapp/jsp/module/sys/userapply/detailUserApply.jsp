<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户申请信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("userApplyController");
	
	/** 通过 */
	function pass_onclick() {
		
		if('${bean.user.shopkeeperRole }' == 'true') {
			dvAlert('此用户目前已经是店长身份了，不需要重复审核，<br>可能用户存在重复申请开店的操作造成');
			return false;
		}
		
		dvConfirm("申请通过后将给此用户发送账号通知，您确认要通过此申请吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("userApplyController", "pass", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 拒绝 */
	function deny_onclick() {
		dvPrompt(window.denyDivHtml, '填写拒绝信息', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts"></span>',
			submit: function(v, h, f) {
				if (!validateForm("deny_table")) {return false;}
				$("#reason").val(f['deny_reason']);
				formHelper.jSubmit(formHelper.buildAction("userApplyController", "deny", "${bean.id }"));
				return true;
			}
		});
	}
	
	$(document).ready(function() {
		window.denyDivHtml = $("#denyDiv").html();
		$("#denyDiv").html("");
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">申请信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_user_apply"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.status == '0'}">
						<input type="button" name="pass" value="通过" class="button" onclick="pass_onclick();"/>
						<input type="button" name="deny" value="拒绝" class="button" onclick="deny_onclick();"/>
						</c:if>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
						<td class="label">学校：</td>
						<td class="field"><c:out value="${bean.school_name }"/></td>
					</tr>
					<tr>
						<td class="label">用户：</td>
						<td class="field"><c:out value="${bean.user_name }"/></td>
					</tr>
					<tr>
						<td class="label">通知状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_USER_APPLY_NOTIFY_STATUS" value="${bean.notify_status }"/></td>
						<td class="label">处理状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_USER_APPLY_STATUS" value="${bean.status }"/></td>
					</tr>
					<tr>
						<td class="label">通知消息：</td>
						<td class="field" colspan="3"><c:out value="${bean.notify_msg }"/></td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" id="reason" name="reason" value=""/>
	</form>
	<div id="denyDiv" style="display:none;">
		<table id="deny_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>拒绝原因：</td>
				<td class="field">
					<textarea id="deny_reason" name="deny_reason" class="textarea" style="width:80%; height:60px;" inputName="拒绝原因"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
