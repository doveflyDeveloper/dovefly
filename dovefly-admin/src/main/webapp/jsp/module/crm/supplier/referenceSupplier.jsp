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
<title>供应商参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("supplierController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildReferenceAction());
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
			obj.supplier_id = $(sels[i]).val();
			obj.supplier_name = $(sels[i]).attr("supplier_name");
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
					</span>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<c:if test="${sessionScope.DV_USER_VO.superAdminRole || sessionScope.DV_USER_VO.headquartersRole }">
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true" />
						</td>
						</c:if>
						<td class="label">供应商：</td>
						<td class="field">
							<input type="text" name="name" class="input" maxlength="50" value="<c:out value="${param.name }"/>"/>
						</td>
						<td class="label">联系人：</td>
						<td class="field">
							<input type="text" name="linkman" class="input" maxlength="10" value="<c:out value="${param.linkman }"/>"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">供应商列表&nbsp;</span>
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
						<th width="30%">供应商</th>
						<th width="10%">公司电话</th>
						<th width="30%">公司地址</th>
						<th width="10%">联系人</th>
						<th width="10%">联系人电话</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" supplier_name="<c:out value="${bean.name }"/>" onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.id }"/>" supplier_name="<c:out value="${bean.name }"/>" onclick="selectRadio(this)"/>
						<%}	%>		
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.name }"/></td>
						<td><c:out value="${bean.tel }"/></td>
						<td><c:out value="${bean.address }"/></td>
						<td><c:out value="${bean.linkman }"/></td>
						<td><c:out value="${bean.linkman_mobile }"/></td>
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