<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.order.back.vo.OrderBackBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>退货单管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("orderBackBillController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 新增 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildCreateAction());
	}
	
	/** 删除 */
	function deleteMulti_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		//其他一些限制操作的校验：如审批状态等。
		if(!validateSelections(sels, "status", "<%=OrderBackBillVo.STATUS_EDIT%>")){
			dvAlert("只有<span class='left_ts'>编辑中</span>状态下的采购退货单才可以删除！");
	  		return false;
		}
	
		var ids = findSelections("dv_checkbox", "value");
		
		dvConfirm("您确定要删除这些数据吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteMultiAction(ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 修改 */
	function find_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		if(!validateSelections(sels, "status", "<%=OrderBackBillVo.STATUS_EDIT%>")){
			dvAlert("只有<span class='left_ts'>编辑中</span>状态下的采购退货单才可以修改！");
	  		return false;
		}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildFindAction(id));
	}
	
	/** 查看 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildAction("orderBackBillController", "detail", id));
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
			formHelper.jSubmit(formHelper.buildAction("orderBackBillController", "detail", ids));
		}
	}
	
	/** 导出列表 */
	function exportList_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("orderBackBillController", "exportList", ids));
	}
	
	/** 导出明细列表 */
	function exportListDetail_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("orderBackBillController", "exportListDetail", ids));
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/order_back_bill"></div>
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
						<td class="label">退货单编号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="input" maxlength="50" value="<c:out value="${param.bill_code }"/>"/>
						</td>
						<td class="label">退货单日期：</td>
						<td class="field">
							<input type="text" name="bill_date_from" class="half_input Wdate" value="${fn:substring(param.bill_date_from, 0, 10) }"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="bill_date_to" class="half_input Wdate" value="${fn:substring(param.bill_date_to, 0, 10) }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">退货单列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${sessionScope.DV_USER_VO.shopkeeperRole}">
						<input type="button" name="insert" value="我要退货" class="button" onclick="add_onclick();"/>
						</c:if>
						<c:if test="${sessionScope.DV_USER_VO.cityManagerRole}">
						<input type="button" name="detail" value="处理" class="button" onclick="detail_onclick();"/>
						</c:if>
						<input type="button" name="exportList" value="导出订单" class="button" onclick="exportList_onclick();"/>
						<input type="button" name="exportListDetail" value="导出明细" class="button" onclick="exportListDetail_onclick();"/>
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
						<th width="10%">退货单编号</th>
						<th width="10%">城市</th>
						<th width="20%">退货仓库</th>
						<th width="10%">店长</th>
						<th width="10%">退货日期</th>
						<th width="10%">退货金额</th>
						<th width="10%">退款状态</th>
						<th width="10%">处理状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" status="<c:out value="${bean.status }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.bill_code }"/></a></td>
						<td><c:out value="${bean.city_name }"/></td>
						<td><c:out value="${bean.warehouse_name }"/></td>
						<td><c:out value="${bean.shop_name }"/></td>
						<td>${fn:substring(bean.bill_date, 0, 10) }</td>
						<td><c:out value="${bean.amount }"/></td>
						<td><dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/></td>
						<td><dv:display dicKeyword="DIC_ORDER_BACK_BILL_STATUS" value="${bean.status }"/></td>
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
