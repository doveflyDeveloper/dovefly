<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.notice.vo.NoticeVo" %>
<%@ page import="com.deertt.frame.report.chart.vo.ChartVo" %>
<%@ page import="com.deertt.module.sys.notice.util.INoticeConstants" %>

<%
	boolean show = request.getAttribute("show") == null ? false : true;
	ChartVo chart = request.getAttribute("chart") == null ? new ChartVo() : (ChartVo)request.getAttribute("chart");
	String chartJSON = request.getAttribute("chartJSON") == null ? "{}" : (String)request.getAttribute("chartJSON");
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/exporting.js"></script>
<script type="text/javascript">
	var formHelper = new DvFormHelper("chartExportController");

	function query_onclick() {
		formHelper.jSubmit(formHelper.buildAction("chartExportController", "showChart"));
	}
	var highchart_defaults = {
		title: {
			text: '图标标题',
			x: -20 //center
		},
		subtitle: {
			text: '图标副标题',
			x: -20
		},
		xAxis: {
			categories: []
		},
		yAxis: {
			title: {
				text: '纵轴标题'
			},
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}]
		},
		tooltip: {
			valueSuffix: '°C'
		},
		legend: {
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'middle',
			borderWidth: 0
		},
		exporting: {
			url:'<%=request.getContextPath()%>/chartExportController/exportChart',
			width: 800
		},
		credits: {
			enabled: true,
			text: 'www.deertt.com',
			href: '<%=request.getContextPath()%>'
		},
		series: []
	};
	
	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
		<%if(show) {%>
		var data_options = <%=chartJSON %>;
		var settings = jQuery.extend(true, {}, highchart_defaults, data_options);
		$('#container').highcharts(settings);
		<%}%>
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
					<div class="table_title_tip" rel="<%=request.getContextPath()%>"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多条件</label>&nbsp;&nbsp;
						<shiro:hasPermission name="<%=INoticeConstants.PERM_READ %>">
						<input type="button" name="Submit" value="查询" class="button" onclick="query_onclick();">
						</shiro:hasPermission>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<td class="label">公告标题：</td>
						<td class="field">
							<input type="text" name="notice_title" class="input" maxlength="30" value="<c:out value="${param.notice_title }"/>"/>	
						</td>
						<td class="label">公告类型：</td>
						<td class="field">
							<dv:select name="notice_type" dicKeyword="DIC_NOTICE_TYPE" defaultValue="${param.notice_type }" hasEmpty="true"/>
						</td>
					</tr>
					<tr>
						<td class="label">发布状态：</td>
						<td class="field">
							<dv:select name="issue_state" dicKeyword="DIC_ISSUE_STATE" defaultValue="${param.issue_state }" hasEmpty="true"/>
						</td>
						<td class="label">发布时间：</td>
						<td class="field">
							<input type="text" name="issue_time_from" class="half_input Wdate" value="<c:out value="${param.issue_time_from }"/>"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="issue_time_to" class="half_input Wdate" value="<c:out value="${param.issue_time_to }"/>"/>	
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					<shiro:hasPermission name="<%=INoticeConstants.PERM_WRITE %>">
						<input type="button" name="insert" value="新增" class="button" onclick="add_onclick();"/>
					</shiro:hasPermission>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<div id="container" style="min-width:800px;height:400px"></div>
			</div>
		</div>
	</form>
</body>
</html>