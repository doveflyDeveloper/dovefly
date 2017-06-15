<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>提现申请管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("applyController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 查看 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildDetailAction(id));
		} else {//勾选复选框查看
			var ids = findSelections("dv_checkbox", "value");
			if(!ids || !ids.length) {
				dvAlert("请选择一条记录！");
		  		return false;
			}
			if(ids.length > 1) {
				dvAlert("只能选择一条记录！");
		  		return false;
			}
			formHelper.jSubmit(formHelper.buildDetailAction(ids));
		}
	}
	
	/** 批量导出 */
	function export_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("applyController", "exportList", ids));
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/fund_apply"></div>
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
						<td class="label">用户姓名：</td>
						<td class="field">
							<input type="text" name="user_name" class="input" maxlength="20" value="<c:out value="${param.user_name }"/>"/>
						</td>
						<td class="label">申请时间：</td>
						<td class="field">
							<input type="text" name="apply_time_from" class="half_input Wdate" value="${fn:substring(param.apply_time_from, 0, 10) }"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="apply_time_to" class="half_input Wdate" value="${fn:substring(param.apply_time_to, 0, 10) }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">提现申请列表&nbsp;</div>
					<dv:radioButton name="status" dicKeyword="DIC_FUND_APPLY_STATUS" defaultValue="${param.status }" hasEmpty="true"/>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${sessionScope.DV_USER_VO.headquartersRole || sessionScope.DV_USER_VO.cashierRole}">
						<input type="button" name="deal" value="处理" class="button" onclick="detail_onclick();"/>
						<input type="button" name="export" value="导出" class="button" onclick="export_onclick();"/>
						</c:if>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="10%">申请单号</th>
						<th width="5%">店铺ID</th>
						<th width="5%">店铺</th>
						<th width="5%">用户ID</th>
						<th width="5%">申请用户</th>
						<th width="10%">申请时间</th>
						<th width="5%">提现金额</th>
						<th width="10%">收款账户</th>
						<th width="10%">收款人姓名</th>
						<th width="5%">申请提现方式</th>
						<th width="5%">支付状态</th>
						<th width="5%">处理状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.bill_code }"/></td>
						<td><c:out value="${bean.store_id }"/></td>
						<td><c:out value="${bean.store_name }"/></td>
						<td><c:out value="${bean.user_id }"/></td>
						<td><c:out value="${bean.user_name }"/></td>
						<td>${fn:substring(bean.apply_time, 0, 19) }</td>
						<td><c:out value="${bean.apply_amount }"/></td>
						<td><c:out value="${bean.receive_account }"/></td>
						<td><c:out value="${bean.receive_real_name }"/></td>
					    <td><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.apply_to }"/></td>
						<td><dv:display dicKeyword="DIC_FUND_APPLY_PAY_STATUS" value="${bean.pay_status }"/></td>
						<td><dv:display dicKeyword="DIC_FUND_APPLY_STATUS" value="${bean.status }"/></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="page_div">
			<jsp:include page="/jsp/include/page.jsp"/>
		</div>
	</form>
</body>
</html>
