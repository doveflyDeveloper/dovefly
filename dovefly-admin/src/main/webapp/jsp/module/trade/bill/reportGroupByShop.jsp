<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
%>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>销售订单统计</title>
<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("tradeBillReportController");
	
	/** 查询 */
	function query_onclick() {
		if (validateForm()){
			formHelper.jSubmit(formHelper.buildAction("tradeBillReportController", "reportGroupByShop"));
		}
	}
	
	/** 查询店长数据 */
	function reportManager_onclick(manager_id) {
		var params = "bill_date_from=${bill_date_from }&bill_date_to=${bill_date_to }&manager_id=" + manager_id;
		var url = context_path + "/tradeBillReportController/reportManagerGroupByShop?" + params;
		dvOpenDialog(url, "店长销售统计", 900, 450, null);
	}
	
	/** 查询学校销售数据 */
	function reportManagerSchool_onclick(manager_id) {
		var params = "bill_date_from=${bill_date_from }&bill_date_to=${bill_date_to }&manager_id=" + manager_id;
		var url = context_path + "/tradeBillReportController/reportManagerGroupBySchool?" + params;
		dvOpenDialog(url, "学校销售统计", 900, 450, null);
	}
	
	/** 批量导出 */
	function export_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("tradeBillReportController", "exportGroupByShop", ids));
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/trade_bill"></div>
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
						<td class="label">城市：</td>
						<td class="field">
							<c:if test="${sessionScope.DV_USER_VO.headquartersRole }">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
							</c:if>
							<c:if test="${!sessionScope.DV_USER_VO.headquartersRole }">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${sessionScope.DV_USER_VO.city_id}" ignoreValues="1" hasEmpty="true" attributes="disabled"/>
							</c:if>
						</td>
						<td class="label"><span class="required_red">* </span>统计周期（订单日期）：</td>
						<td class="field">
							<input type="text" name="bill_date_from" class="half_input Wdate" value="${bill_date_from }" inputName="起始日期" validate="notNull"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="bill_date_to" class="half_input Wdate" value="${bill_date_to }" inputName="终止日期" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">分组统计：</td>
						<td class="field">
							<c:if test="${sessionScope.DV_USER_VO.headquartersRole}">
							<dv:radio name="group_by" dicKeyword="" defaultValue="${group_by }" options="{\"city\":\"城市\",\"manager\":\"运营主管\",\"school\":\"学校\",\"shop\":\"店长\"}"/>
							</c:if>
							<c:if test="${sessionScope.DV_USER_VO.cityManagerRole}">
							<dv:radio name="group_by" dicKeyword="" defaultValue="${group_by }" options="{\"manager\":\"运营主管\",\"school\":\"学校\",\"shop\":\"店长\"}"/>
							</c:if>
							<c:if test="${sessionScope.DV_USER_VO.operationManagerRole}">
							<dv:radio name="group_by" dicKeyword="" defaultValue="${group_by }" options="{\"school\":\"学校\",\"shop\":\"店铺\"}"/>
							</c:if>
							<c:if test="${sessionScope.DV_USER_VO.shopkeeperRole}">
							<dv:radio name="group_by" dicKeyword="" defaultValue="${group_by }" options="{\"shop\":\"店铺\"}"/>
							</c:if>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
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
						<th width="5%">城市</th>
						<c:if test="${'manager' == group_by}"><th width="20%">运营主管</th></c:if>
						<c:if test="${'school' == group_by}"><th width="20%">学校</th></c:if>
						<c:if test="${'shop' == group_by}">
						<th width="10%">学校</th>	
						<th width="10%">店铺</th>	
						</c:if>
						<th width="10%">订单量（笔）</th>
						<th width="10%">订单总额（元）</th>
						<c:if test="${'manager' == group_by}"><th width="20%"></th></c:if>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>${status.count }</td>
						<td><dv:display dicKeyword="DIC_SYS_CITY" value="${bean.city_id }"/></td>
						<c:if test="${'manager' == group_by}"><td><c:out value="${bean.manager_name }"/></td></c:if>
						<c:if test="${'school' == group_by}"><td><c:out value="${bean.school_name }"/></td></c:if>
						<c:if test="${'shop' == group_by}">
						<td><c:out value="${bean.school_name }"/></td>
						<td><c:out value="${bean.shop_name }"/></td>
						</c:if>
						<td><c:out value="${bean.bill_count }"/></td>
						<td><c:out value="${bean.bill_amount }"/></td>
						<c:if test="${'manager' == group_by}">
						<td>
							<input type="button" name="querySchool" value="学校销售统计" class="button" onclick="reportManagerSchool_onclick('<c:out value="${bean.manager_id }"/>');"/>
						    <input type="button" name="queryShop" value="店长销售统计" class="button" onclick="reportManager_onclick('<c:out value="${bean.manager_id }"/>');"/>
						</td>   
						</c:if>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
