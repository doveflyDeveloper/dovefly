<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>退款申请管理</title>
<style type="text/css">
<!--

-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("refundController");
	
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
	
	/** 批量导出 */
	function export_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("refundController", "exportList", ids));
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
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/refund"></div>
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
						<td class="label">买家姓名：</td>
						<td class="field">
							<input type="text" name="buyer_name" class="input" maxlength="20" value="<c:out value="${param.buyer_name }"/>"/>
						</td>
						<td class="label">关联订单号：</td>
						<td class="field">
							<input type="text" name="refer_bill_code" class="input" maxlength="20" value="<c:out value="${param.refer_bill_code }"/>"/>
						</td>
					</tr>
					<tr>
						<td class="label">申请时间：</td>
						<td class="field">
							<input type="text" name="refund_time_from" class="half_input Wdate" value="${fn:substring(param.refund_time_from, 0, 19) }" format="both"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="refund_time_to" class="half_input Wdate" value="${fn:substring(param.refund_time_to, 0, 19) }" format="both"/>
						</td>
						<td class="label">支付状态：</td>
						<td class="field">
							<dv:radio name="pay_status" dicKeyword="DIC_REFUND_PAY_STATUS" defaultValue="${param.pay_status }" hasEmpty="true"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">退款申请列表&nbsp;</div>
					<dv:radioButton name="status" dicKeyword="DIC_REFUND_STATUS" defaultValue="${param.status }" hasEmpty="true"/>
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
						<th width="5%">卖家ID</th>
						<th width="5%">卖家姓名</th>
						<th width="5%">买家ID</th>
						<th width="5%">买家姓名</th>
						<th width="10%">申请单号</th>
						<th width="10%">申请时间</th>
						<th width="10%">申请金额</th>
						<th width="10%">关联订单号</th>
						<th width="10%">申请退款方式</th>
						<th width="10%">退款状态</th>
						<th width="10%">处理状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.seller_id }"/></td>
						<td><c:out value="${bean.seller_name }"/></td>
						<td><c:out value="${bean.buyer_id }"/></td>
						<td><c:out value="${bean.buyer_name }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.bill_code }"/></a></td>
						<td>${fn:substring(bean.refund_time, 0, 19) }</td>
						<td><c:out value="${bean.refund_amount }"/></td>
						<td><c:out value="${bean.refer_bill_code }"/></td>
						<td><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.refund_to }"/></td>
						<td><dv:display dicKeyword="DIC_REFUND_PAY_STATUS" value="${bean.pay_status }"/></td>
						<td><dv:display dicKeyword="DIC_REFUND_STATUS" value="${bean.status }"/></td>
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
