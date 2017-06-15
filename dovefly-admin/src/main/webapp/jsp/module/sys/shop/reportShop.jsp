<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
%>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>店铺统计</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/highcharts/exporting.js"></script>
<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("shopReportController");
	
	/** 查询 */
	function query_onclick() {
		if (validateForm()){
			formHelper.jSubmit(formHelper.buildAction("shopReportController", "reportShop"));
		}
	}
	
	/** 批量导出 */
	function export_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("shopReportController", "exportShop", ids));
	}
		
	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
		
		var shop_count_by_week = JSON.parse('${chartData }');
		var categories = [];
		for (var i = 0; i < shop_count_by_week.length; i++) {
			var obj = shop_count_by_week[i];
			if ($.inArray(obj.weeks, categories) < 0) {
				categories.push(obj.weeks);
			}
		}
		categories = categories.sort();
		var shop_data_spline = [];
		
		if ("${'warehouse' == group_by}" == "true") {
			var warehouse = {};
			for (var i = 0; i < shop_count_by_week.length; i++) {
				var obj = shop_count_by_week[i];
				if (warehouse.name != obj.warehouse_name) {
					var d = [];
					for (var j = 0; j < categories.length; j++) {d.push(0);}
					warehouse = {name: obj.warehouse_name, data: d};
					warehouse.data.splice(categories.indexOf(obj.weeks), 1, obj.shop_count);
					shop_data_spline.push(warehouse);
				} else {
					warehouse.data.splice(categories.indexOf(obj.weeks), 1, obj.shop_count);
				}
			}
		} else if ("${'manager' == group_by}" == "true") {
			var manager = {};
			for (var i = 0; i < shop_count_by_week.length; i++) {
				var obj = shop_count_by_week[i];
				if (manager.name != (obj.manager_name || "--")) {
					var d = [];
					for (var j = 0; j < categories.length; j++) {d.push(0);}
					manager = {name: (obj.manager_name || "--"), data: d};
					manager.data.splice(categories.indexOf(obj.weeks), 1, obj.shop_count);
					shop_data_spline.push(manager);
				} else {
					manager.data.splice(categories.indexOf(obj.weeks), 1, obj.shop_count);
				}
			}
		} else if ("${'school' == group_by}" == "true") {
			var school = {};
			for (var i = 0; i < shop_count_by_week.length; i++) {
				var obj = shop_count_by_week[i];
				if (school.name != obj.school_name) {
					var d = [];
					for (var j = 0; j < categories.length; j++) {d.push(0);}
					school = {name: obj.school_name, data: d};
					school.data.splice(categories.indexOf(obj.weeks), 1, obj.shop_count);
					shop_data_spline.push(school);
				} else {
					school.data.splice(categories.indexOf(obj.weeks), 1, obj.shop_count);
				}
			}
		}
		
	    $('#shop_line').highcharts({
	        chart: {
	            type: 'spline'
	        },
	        title: {
	            text: '店铺注册走势图'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	        	categories: categories
	        },
	        yAxis: {
	            title: {
	                text: '店铺注册量 (个)'
	            },
	            min: 0
	        },
	        plotOptions: {
	            spline: {
	                marker: {
	                    enabled: true
	                }
	            }
	        },
		    exporting: {
				url:'<%=request.getContextPath()%>/chartExportController/exportChart'
			},
			credits: {
				enabled: false,
			},
	        series: shop_data_spline
	    });
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_shop"></div>
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
						<td class="label">货仓：</td>
						<td class="field">
							<dv:select name="warehouse_id" dicKeyword="DIC_SYS_WAREHOUSE" defaultValue="${param.warehouse_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						</c:if>
						<td class="label"><span class="required_red">* </span>统计周期：</td>
						<td class="field">
							<input type="text" name="bill_date_from" class="half_input Wdate" value="${bill_date_from }" inputName="起始日期" validate="notNull"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="bill_date_to" class="half_input Wdate" value="${bill_date_to }" inputName="终止日期" validate="notNull"/>
						</td>
						<c:if test="${sessionScope.DV_USER_VO.headquartersRole || sessionScope.DV_USER_VO.cityManagerRole}">
						<td class="label">分组统计：</td>
						<td class="field">
							<dv:radio name="group_by" dicKeyword="" defaultValue="${group_by }" options="{\"warehouse\":\"货仓\",\"manager\":\"运营主管\",\"school\":\"学校\"}"/>
						</td>
						</c:if>
						<c:if test="${sessionScope.DV_USER_VO.operationManagerRole}">
						<td class="label">分组统计：</td>
						<td class="field">
							<dv:radio name="group_by" dicKeyword="" defaultValue="${group_by }" options="{\"school\":\"学校\"}"/>
						</td>
						</c:if>
					</tr>
					<tr>
						<td class="label">运营主管：</td>
						<td class="field">
							<input type="text" name="manager_name" class="input" maxlength="20" value="<c:out value="${param.manager_name }"/>"/>
						</td>
						<td class="label">学校：</td>
						<td class="field">
							<input type="text" name="school_name" class="input" maxlength="20" value="<c:out value="${param.school_name }"/>"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">统计图表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu"></div>
				</div>
			</div>
			<div class="padding_2_div">
				<div id="shop_line" style="height:300px; width:98%;"></div>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">统计列表&nbsp;</div>
				</div>
				<div class="right_div">
				   <div class="right_menu">
				      <input type="button" name="export" value="导出" class="button" onclick="export_onclick();"/>
				   </div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">序号</th>
						<th width="5%">货仓</th>
						<c:if test="${'manager' == group_by}"><th width="10%">运营主管</th></c:if>
						<c:if test="${'school' == group_by}"><th width="10%">学校</th></c:if>
						<th width="10%">周统计</th>
						<th width="10%">店铺数量（个）</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>${status.count }</td>
						<td><c:out value="${bean.warehouse_name }"/></td>
						<c:if test="${'manager' == group_by}"><td><c:out value="${bean.manager_name }"/></td></c:if>
						<c:if test="${'school' == group_by}"><td><c:out value="${bean.school_name }"/></td></c:if>
						<td><c:out value="${bean.weeks }"/></td>
						<td><c:out value="${bean.shop_count }"/></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
