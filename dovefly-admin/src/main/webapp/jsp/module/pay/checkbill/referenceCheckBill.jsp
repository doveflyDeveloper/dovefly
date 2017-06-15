<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//默认是radio
	String inputType = request.getParameter("inputType");
	if(!"checkbox".equalsIgnoreCase(inputType)){
		inputType = "radio";
	}
	//回调函数
	String callback = request.getParameter("callback");

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>对账信息参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("checkBillController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildReferenceAction());
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
	
	/** 确定 */
	function ok_onclick() {
		var sels = findSelections("dv_<%=inputType%>");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		var datas = new Array();
		for (var i = 0; i < sels.length; i++) {
			var obj = new Object();
			obj.id = $(sels[i]).val();
			obj.pay_table_name = $(sels[i]).attr("pay_table_name");
			obj.pay_bill_id = $(sels[i]).attr("pay_bill_id");
			obj.bill_type = $(sels[i]).attr("bill_type");
			obj.bill_table_name = $(sels[i]).attr("bill_table_name");
			obj.bill_id = $(sels[i]).attr("bill_id");
			obj.bill_code = $(sels[i]).attr("bill_code");
			obj.check_time = $(sels[i]).attr("check_time");
			obj.check_msg = $(sels[i]).attr("check_msg");
			obj.check_status = $(sels[i]).attr("check_status");
			obj.deal_status = $(sels[i]).attr("deal_status");
			datas.push(obj);
		}	
		parent.<%=callback%>(datas); 
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
					<span class="table_title">查询条件&nbsp;</span>
				</div>
				<div class="right_div">
					<span class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
						<input type="button" name="ok" value="确定" class="button" onclick="ok_onclick();"/>
					</span>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<td class="label">支付账单表：</td>
						<td class="field">
							<input type="text" name="pay_table_name" class="input" maxlength="30" inputName="支付账单表" value="<c:out value="${param.pay_table_name }"/>"/>	
						</td>
						<td class="label">支付账单id：</td>
						<td class="field">
							<input type="text" name="pay_bill_id" class="input" maxlength="10" inputName="支付账单id" value="<c:out value="${param.pay_bill_id }"/>"/>	
						</td>
					</tr>
					<tr>
						<td class="label">单据类型：</td>
						<td class="field">
							<input type="text" name="bill_type" class="input" maxlength="10" inputName="单据类型" value="<c:out value="${param.bill_type }"/>"/>	
						</td>
						<td class="label">商户订单表：</td>
						<td class="field">
							<input type="text" name="bill_table_name" class="input" maxlength="30" inputName="商户订单表" value="<c:out value="${param.bill_table_name }"/>"/>	
						</td>
					</tr>
					<tr class="hidden">
						<td class="label">商户订单id：</td>
						<td class="field">
							<input type="text" name="bill_id" class="input" maxlength="10" inputName="商户订单id" value="<c:out value="${param.bill_id }"/>"/>	
						</td>
						<td class="label">商户订单号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="input" maxlength="20" inputName="商户订单号" value="<c:out value="${param.bill_code }"/>"/>	
						</td>
					</tr>
					<tr class="hidden">
						<td class="label">对账时间：</td>
						<td class="field">
							<input type="text" name="check_time_from" class="half_input Wdate" inputName="对账时间" value="${fn:substring(param.check_time_from, 0, 19) }" format="both"/>&nbsp;&nbsp;~&nbsp;	
							<input type="text" name="check_time_to" class="half_input Wdate" inputName="对账时间" value="${fn:substring(param.check_time_to, 0, 19) }" format="both"/>	
						</td>
						<td class="label">对账信息：</td>
						<td class="field">
							<input type="text" name="check_msg" class="input" maxlength="200" inputName="对账信息" value="<c:out value="${param.check_msg }"/>"/>	
						</td>
					</tr>
					<tr class="hidden">
						<td class="label">对账结果：</td>
						<td class="field">
							<input type="text" name="check_status" class="input" maxlength="1" inputName="对账结果" value="<c:out value="${param.check_status }"/>"/>	
						</td>
						<td class="label">处理结果：</td>
						<td class="field">
							<input type="text" name="deal_status" class="input" maxlength="1" inputName="处理结果" value="<c:out value="${param.deal_status }"/>"/>	
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">对账信息列表&nbsp;</span>
				</div>
				<div class="right_div">
					<span class="right_menu">
					</span>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
							<%} %>
						</th>
						<th width="5%">序号</th>
						<th width="10%">支付账单表</th>
						<th width="10%">支付账单id</th>
						<th width="10%">单据类型</th>
						<th width="10%">商户订单表</th>
						<th width="10%">商户订单id</th>
						<th width="10%">商户订单号</th>
						<th width="10%">对账时间</th>
						<th width="10%">对账信息</th>
						<th width="10%">对账结果</th>
						<th width="10%">处理结果</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" pay_table_name="<c:out value="${bean.pay_table_name }"/>" pay_bill_id="<c:out value="${bean.pay_bill_id }"/>" bill_type="<c:out value="${bean.bill_type }"/>" bill_table_name="<c:out value="${bean.bill_table_name }"/>" bill_id="<c:out value="${bean.bill_id }"/>" bill_code="<c:out value="${bean.bill_code }"/>" check_time="<c:out value="${bean.check_time }"/>" check_msg="<c:out value="${bean.check_msg }"/>" check_status="<c:out value="${bean.check_status }"/>" deal_status="<c:out value="${bean.deal_status }"/>"  onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.id }"/>" pay_table_name="<c:out value="${bean.pay_table_name }"/>" pay_bill_id="<c:out value="${bean.pay_bill_id }"/>" bill_type="<c:out value="${bean.bill_type }"/>" bill_table_name="<c:out value="${bean.bill_table_name }"/>" bill_id="<c:out value="${bean.bill_id }"/>" bill_code="<c:out value="${bean.bill_code }"/>" check_time="<c:out value="${bean.check_time }"/>" check_msg="<c:out value="${bean.check_msg }"/>" check_status="<c:out value="${bean.check_status }"/>" deal_status="<c:out value="${bean.deal_status }"/>"  onclick="selectRadio(this)"/>
						<%}	%>		
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.pay_table_name }"/></td>
						<td><c:out value="${bean.pay_bill_id }"/></td>
						<td><c:out value="${bean.bill_type }"/></td>
						<td><c:out value="${bean.bill_table_name }"/></td>
						<td><c:out value="${bean.bill_id }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.bill_code }"/></a></td>
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
		<input type="hidden" name="inputType" value="<%=inputType %>"/>
		<input type="hidden" name="callback" value="<%=callback %>"/>
	</form>
</body>
</html>