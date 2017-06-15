<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统消息通知信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("notificationController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
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
					<div class="table_title">系统消息通知信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/notification"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<!-- <input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/> -->
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">用户id：</td>
						<td class="field"><c:out value="${bean.user_id }"/></td>
						<td class="label">用户名称：</td>
						<td class="field"><c:out value="${bean.user_name }"/></td>
					</tr>
					<tr>
						<td class="label">微信id：</td>
						<td class="field"><c:out value="${bean.wechat_id }"/></td>
						<td class="label">手机：</td>
						<td class="field"><c:out value="${bean.mobile }"/></td>
					</tr>
					<tr>
						<td class="label">消息类型：</td>
						<td class="field"><c:out value="${bean.notify_type }"/></td>
						<td class="label">通知渠道：</td>
						<td class="field"><dv:display dicKeyword="DIC_NOTIFY_WAY" value="${bean.notify_way }"/></td>
					</tr>
					<tr>
						<td class="label">通知消息：</td>
						<td class="field"><c:out value="${bean.message }"/></td>
						<td class="label">期望通知时间：</td>
						<td class="field">${fn:substring(bean.expect_notify_time, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">通知时间：</td>
						<td class="field">${fn:substring(bean.notify_time, 0, 19) }</td>
						<td class="label">限定通知次数：</td>
						<td class="field"><c:out value="${bean.notify_limit_times }"/></td>
					</tr>
					<tr>
						<td class="label">通知次数：</td>
						<td class="field"><c:out value="${bean.notify_times }"/></td>
						<td class="label">通知结果状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_NOTIFY_STATUS" value="${bean.notify_status }"/></td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field" colspan="3"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
