<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
%>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>店长对账数据统计</title>
<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("reportController");
	
	/** 查询 */
	function query_onclick() {
		if (validateForm()){
			formHelper.jSubmit(formHelper.buildAction("reportController", "reportShopkeeperInterest"));
		}
	}
	
	/** 贷款列表导出 */
	function exportList_onclick() {
		formHelper.submit(formHelper.buildAction("reportController", "exportShopkeeperInterest"));
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/"></div>
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
						<td class="label">店铺ID：</td>
						<td class="field">
							<input type="text" name="shop_id" class="input" value="${param.shop_id }" inputName="店铺ID"/>
						</td>
						<td class="label">店铺：</td>
						<td class="field">
						<input type="text" name="shop_name" class="input" value="${param.shop_name }" inputName="店铺姓名"/>
						</td>
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
						<input type="button" name="exportList" value="导出" class="button" onclick="exportList_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>贷款店铺列表</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="5%">城市</th>
									<th width="20%">学校</th>
									<th width="10%">运营主管</th>
									<th width="10%">店铺ID</th>
									<th width="10%">店铺</th>
									<th width="10%">账户余额</th>
									<th width="10%">可贷额度</th>
									<th width="10%">已贷金额</th>
									<th width="10%">利息金额</th>
								</tr>
								<c:forEach var="bean" varStatus="status" items="${data.shops }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${bean.city_name }"/></td>
									<td><c:out value="${bean.school_name }"/></td>
									<td><c:out value="${bean.manager_name }"/></td>
									<td><c:out value="${bean.id }"/></td>
									<td><c:out value="${bean.shop_name }"/></td>
									<td><c:out value="${bean.balance_amount }"/></td>
									<td><c:out value="${bean.loanable_amount}"/></td>
									<td><c:out value="${bean.loan_amount}"/></td>
									<td><c:out value="${bean.interest_amount }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
					<DT>店铺贷款利息流水明细列表</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="10%">店铺ID</th>
									<th width="20%">店铺</th>
									<th width="25%">日期</th>
									<th width="10%">当日本金</th>
									<th width="10%">当日利息</th>
									<th width="10%">当日总利息</th>
									<th width="10%">日利率</th>
								</tr>
								<c:forEach var="bean" varStatus="status" items="${data.interests }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${bean.shop_id }"/></td>
									<td><c:out value="${bean.shop_name }"/></td>
									<td><c:out value="${bean.interest_date }"/></td>
									<td><c:out value="${bean.capital_amount }"/></td>
									<td><c:out value="${bean.interest_amount }"/></td>
									<td><c:out value="${bean.all_interest_amount }"/></td>
									<td><c:out value="${bean.interest_rate }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
					<DT>店铺贷款利息还款明细列表</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="10%">店铺ID</th>
									<th width="20%">店铺</th>
									<th width="25%">还款日期</th>
									<th width="10%">还款金额</th>
									<th width="10%">账户余额</th>
									<th width="20%">简述</th>
								</tr>
								<c:forEach var="bean" varStatus="status" items="${data.transitions }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${bean.store_id }"/></td>
									<td><c:out value="${bean.store_name }"/></td>
									<td>${fn:substring(bean.transition_time, 0, 19) }</td>
									<td><c:out value="${-bean.transition_amount }"/></td>
									<td><c:out value="${bean.balance_amount }"/></td>
									<td><c:out value="${bean.brief }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
