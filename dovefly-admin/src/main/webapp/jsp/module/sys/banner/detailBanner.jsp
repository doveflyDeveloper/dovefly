<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站插页信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("bannerController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 复制 */
	function copy_onclick() {
		formHelper.jSubmit(formHelper.buildCopyAction("${bean.id }"));
	}
	
	/** 预览 */
	function preview_onclick(url) {
		window.open(url);
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
					<div class="table_title">网站插页信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_banner"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="copy" value="复制到其他设备" class="button" onclick="copy_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">标题：</td>
						<td class="field"><c:out value="${bean.title }"/></td>
						<td class="label">设备类型：</td>
						<td class="field"><dv:display dicKeyword="DIC_BANNER_DEVICE" value="${bean.device }"/></td>
					</tr>
					<tr>
						<td class="label">起始日期：</td>
						<td class="field">${fn:substring(bean.start_time, 0, 10) }</td>
						<td class="label">终止日期：</td>
						<td class="field">${fn:substring(bean.end_time, 0, 10) }</td>
					</tr>
					<tr>
						<td class="label">跳转地址：</td>
						<td class="field" colspan="3"><c:out value="${bean.url }"/> <input type="button" name="preview" value="预览" class="button" onclick="preview_onclick('<c:out value="${bean.url }"/>');" /></td>
					</tr>
					<tr>
						<td class="label">图片：</td>
						<td class="field" colspan="3"><img style="max-width:500px;max-height:500px;" src="<c:out value="${bean.image }"/>"/></td>
					</tr>
					<tr>
						<td class="label">状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_STATUS" value="${bean.status }"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
