<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>对账信息管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("checkBillController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 新增 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildCreateAction());
	}
	
	/** 复制 */	
	function copy_onclick() {
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
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildCopyAction(id));
	}
	
	/** 删除 */
	function deleteMulti_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "1")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
	
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
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildFindAction(id));
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
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/check_bill"></div>
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
						<td class="label">支付账单表：</td>
						<td class="field">
							<dv:select name="pay_table_name" dicKeyword="" defaultValue="${param.pay_table_name }" hasEmpty="true" options="{\"pay_wx_bill\":\"pay_wx_bill\",\"pay_alipay_bill\":\"pay_alipay_bill\"}" />
						</td>
						<td class="label">商户订单表：</td>
						<td class="field">
							<dv:select name="bill_table_name" dicKeyword="" defaultValue="${param.bill_table_name }" hasEmpty="true" options="{\"order_bill\":\"order_bill\",\"trade_bill\":\"trade_bill\",\"fund_apply\":\"fund_apply\",\"fund_recharge\":\"fund_recharge\"}" />
						</td>
						<td class="label">商户订单号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="input" maxlength="20" value="<c:out value="${param.bill_code }"/>"/>
						</td>
					</tr>
					<tr>
						<td class="label">对账结果：</td>
						<td class="field">
							<dv:radio name="check_status" dicKeyword="" defaultValue="${param.check_status }" hasEmpty="true" options="{\"0\":\"异常\",\"1\":\"正常\"}" />
						</td>
						<td class="label">处理结果：</td>
						<td class="field">
							<dv:radio name="deal_status" dicKeyword="" defaultValue="${param.deal_status }" hasEmpty="true" options="{\"0\":\"未手动处理\",\"1\":\"已手动处理\"}" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">对账信息列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
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
						<th width="10%">支付账单表</th>
						<th width="10%">商户订单表</th>
						<th width="10%">商户订单号</th>
						<th width="10%">对账时间</th>
						<th width="10%">对账信息</th>
						<th width="10%">对账结果</th>
						<th width="10%">处理结果</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.pay_table_name }"/></td>
						<td><c:out value="${bean.bill_table_name }"/></td>
						<td><c:out value="${bean.bill_code }"/></td>
						<td>${fn:substring(bean.check_time, 0, 19) }</td>
						<td><c:out value="${bean.check_msg }"/></td>
						<td><c:out value="${bean.check_status }"/></td>
						<td><c:out value="${bean.deal_status }"/></td>
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
