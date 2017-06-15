<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("userController");
	
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
					<div class="table_title">用户信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_user"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
					</tr>
					<tr>
						<td class="label">姓名：</td>
						<td class="field"><c:out value="${bean.real_name }"/></td>
						<td class="label">账号：</td>
						<td class="field"><c:out value="${bean.account }"/></td>
					</tr>
					<tr>
						<td class="label">邮箱：</td>
						<td class="field"><c:out value="${bean.email }"/></td>
						<td class="label">手机号码：</td>
						<td class="field"><c:out value="${bean.mobile }"/></td>
					</tr>
					<tr>
						<td class="label">地址：</td>
						<td class="field" ><c:out value="${bean.address }"/></td>
						<td class="label">创建账号时间：</td>
						<td class="field">${fn:substring(bean.create_at, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">最新登录时间：</td>
						<td class="field">${fn:substring(bean.last_login_time, 0, 19) }</td>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
					<tr>
						<td class="label">汀豆数量：</td>
						<td class="field"><c:out value="${bean.coin_quantity }"/></td>
						<td class="label">微信：</td>
						<td class="field">
							<c:if test="${bean.wechat_avatar != null && bean.wechat_avatar != ''}">
							<img id="img_wechat_avatar" src="${bean.wechat_avatar }" style="width:50px;height:50px;"/><br>
							</c:if>
							<c:out value="${bean.wechat_account }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
