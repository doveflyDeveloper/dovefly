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
<title>超市参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("marketController");
	
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
			obj.city_id = $(sels[i]).attr("city_id");
			obj.city_name = $(sels[i]).attr("city_name");
			obj.shopkeeper_id = $(sels[i]).attr("shopkeeper_id");
			obj.shopkeeper_name = $(sels[i]).attr("shopkeeper_name");
			obj.market_name = $(sels[i]).attr("market_name");
			obj.market_desc = $(sels[i]).attr("market_desc");
			obj.market_area = $(sels[i]).attr("market_area");
			obj.market_status = $(sels[i]).attr("market_status");
			obj.market_sort = $(sels[i]).attr("market_sort");
			obj.start_amount = $(sels[i]).attr("start_amount");
			obj.balance_amount = $(sels[i]).attr("balance_amount");
			obj.locked_amount = $(sels[i]).attr("locked_amount");
			obj.halfway_amount = $(sels[i]).attr("halfway_amount");
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
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						<td class="label">超市：</td>
						<td class="field">
							<input type="text" name="market_name" class="input" maxlength="20" value="<c:out value="${param.market_name }"/>"/>
						</td>
						<td class="label">运营状态：</td>
						<td class="field">
							<dv:radio name="market_status" hasEmpty="true" dicKeyword="DIC_SYS_SHOP_STATUS" defaultValue="${param.market_status }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">超市列表&nbsp;</span>
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
						<th width="5%">城市</th>
						<th width="10%">超市</th>
						<th width="10%">店长</th>
						<th width="5%">起送价</th>
						<th width="5%">账号余额</th>
						<th width="5%">冻结资金</th>
						<th width="5%">待收金额</th>
						<th width="5%">运营状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" city_id="<c:out value="${bean.city_id }"/>" city_name="<c:out value="${bean.city_name }"/>" shopkeeper_id="<c:out value="${bean.shopkeeper_id }"/>" shopkeeper_name="<c:out value="${bean.shopkeeper_name }"/>" market_name="<c:out value="${bean.market_name }"/>" market_desc="<c:out value="${bean.market_desc }"/>" market_area="<c:out value="${bean.market_area }"/>" market_status="<c:out value="${bean.market_status }"/>" market_sort="<c:out value="${bean.market_sort }"/>" start_amount="<c:out value="${bean.start_amount }"/>" balance_amount="<c:out value="${bean.balance_amount }"/>" locked_amount="<c:out value="${bean.locked_amount }"/>" halfway_amount="<c:out value="${bean.halfway_amount }"/>"  onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.id }"/>" city_id="<c:out value="${bean.city_id }"/>" city_name="<c:out value="${bean.city_name }"/>" shopkeeper_id="<c:out value="${bean.shopkeeper_id }"/>" shopkeeper_name="<c:out value="${bean.shopkeeper_name }"/>" market_name="<c:out value="${bean.market_name }"/>" market_desc="<c:out value="${bean.market_desc }"/>" market_area="<c:out value="${bean.market_area }"/>" market_status="<c:out value="${bean.market_status }"/>" market_sort="<c:out value="${bean.market_sort }"/>" start_amount="<c:out value="${bean.start_amount }"/>" balance_amount="<c:out value="${bean.balance_amount }"/>" locked_amount="<c:out value="${bean.locked_amount }"/>" halfway_amount="<c:out value="${bean.halfway_amount }"/>"  onclick="selectRadio(this)"/>
						<%}	%>		
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.city_name }"/></td>
						<td><c:out value="${bean.school_name }"/></td>
						<td><c:out value="${bean.warehouse_name }"/></td>
						<td><c:out value="${bean.manager_name }"/></td>
						<td><c:out value="${bean.market_name }"/></td>
						<td><c:out value="${bean.shopkeeper_name }"/></td>
						<td><c:out value="${bean.start_amount }"/></td>
						<td><c:out value="${bean.balance_amount }"/></td>
						<td><c:out value="${bean.locked_amount }"/></td>
						<td><c:out value="${bean.halfway_amount }"/></td>
						<td><dv:display dicKeyword="DIC_SYS_SHOP_STATUS" value="${bean.market_status }" /></td>
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