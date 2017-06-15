<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息详情</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("messageController");
	
	/** 标记为已读 */
	function read_onclick() {
		formHelper.jSubmit(formHelper.buildAction("messageController", "read", "${bean.id }"));
	}
	
	/** 删除 */
	function delete_onclick() {
		dvConfirm("您确定要删除此数据吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteAction("${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
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
					<div class="table_title">消息详情&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_message"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.status == '0' }">
						<input type="button" name="read" value="标记为已读" class="button" onclick="read_onclick();"/>
						</c:if>
						<input type="button" name="del" value="删除" class="button" onclick="delete_onclick();" />
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">消息标题：</td>
						<td class="field"><c:out value="${bean.title }"/></td>
						<td class="label">消息类型：</td>
						<td class="field"><dv:display dicKeyword="DIC_MESSAGE_TYPE" value="${bean.type }"/></td>
					</tr>
					<tr>
						<td class="label">消息时间：</td>
						<td class="field">${fn:substring(bean.create_at, 0, 19) }</td>
						<td class="label">消息状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_MESSAGE_STATUS" value="${bean.status }"/></td>
					</tr>
					<tr>
						<td class="label">消息内容：</td>
						<td class="field" colspan="3"><c:out value="${bean.content }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
