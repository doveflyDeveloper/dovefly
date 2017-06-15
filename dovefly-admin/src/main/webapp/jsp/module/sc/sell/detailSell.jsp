<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二手买卖信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("sellController");
	
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
					<div class="table_title">二手买卖信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sc_sell"></div>
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
						<td class="label">标题：</td>
						<td class="field"><c:out value="${bean.title }"/></td>
						<td class="label">类别：</td>
						<td class="field"><c:out value="${bean.type }"/></td>
					</tr>
					<tr>
						<td class="label">新旧程度：</td>
						<td class="field"><c:out value="${bean.is_new }"/></td>
						<td class="label">出手价格：</td>
						<td class="field"><c:out value="${bean.price }"/></td>
					</tr>
					<tr>
						<td class="label">购入价格：</td>
						<td class="field"><c:out value="${bean.old_price }"/></td>
						<td class="label">卖家姓名：</td>
						<td class="field"><c:out value="${bean.seller_name }"/></td>
					</tr>
					<tr>
						<td class="label">卖家手机：</td>
						<td class="field"><c:out value="${bean.seller_mobile }"/></td>
						<td class="label">卖家qq：</td>
						<td class="field"><c:out value="${bean.seller_qq }"/></td>
					</tr>
					<tr>
						<td class="label">卖家微信：</td>
						<td class="field"><c:out value="${bean.seller_weixin }"/></td>
						<td class="label">宝贝首图：</td>
						<td class="field"><c:out value="${bean.image }"/></td>
					</tr>
					<tr>
						<td class="label">宝贝图片集：</td>
						<td class="field"><c:out value="${bean.images }"/></td>
						<td class="label">宝贝描述：</td>
						<td class="field"><c:out value="${bean.description }"/></td>
					</tr>
					<tr>
						<td class="label">发布范围：</td>
						<td class="field"><c:out value="${bean.issue_range }"/></td>
						<td class="label">发布日期：</td>
						<td class="field">${fn:substring(bean.issue_date, 0, 10) }</td>
					</tr>
					<tr>
						<td class="label">截止日期：</td>
						<td class="field">${fn:substring(bean.end_date, 0, 19) }</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
