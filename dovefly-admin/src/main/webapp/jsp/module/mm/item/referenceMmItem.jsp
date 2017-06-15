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
<title>货品资料参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("mmItemController");
	
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
			obj.barcode = $(sels[i]).attr("barcode");
			obj.name = $(sels[i]).attr("item_name");
			obj.weight = $(sels[i]).attr("weight");
			obj.shelf_life = $(sels[i]).attr("shelf_life");
			obj.storage_type = $(sels[i]).attr("storage_type");
			obj.producer = $(sels[i]).attr("producer");
			obj.image = $(sels[i]).attr("image");
			obj.images = $(sels[i]).attr("images");
			obj.description = $(sels[i]).attr("description");
			obj.remark = $(sels[i]).attr("remark");
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
						<td class="label">货品条形码：</td>
						<td class="field">
							<input type="text" name="barcode" class="input" maxlength="20" inputName="货品条形码" value="<c:out value="${param.barcode }"/>"/>	
						</td>
						<td class="label">货品名称：</td>
						<td class="field">
							<input type="text" name="name" class="input" maxlength="100" inputName="货品名称" value="<c:out value="${param.name }"/>"/>	
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">货品资料列表&nbsp;</span>
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
						<th width="5%">缩略图</th>
						<th width="10%">货品条形码</th>
						<th width="10%">货品名称</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" item_name="<c:out value="${bean.name }"/>" barcode="<c:out value="${bean.barcode }"/>" weight="<c:out value="${bean.weight }"/>" shelf_life="<c:out value="${bean.shelf_life }"/>" storage_type="<c:out value="${bean.storage_type }"/>" producer="<c:out value="${bean.producer }"/>" image="<c:out value="${bean.image }"/>" images="<c:out value="${bean.images }"/>" description="<c:out value="${bean.description }"/>" remark="<c:out value="${bean.remark }"/>"  onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.id }"/>" item_name="<c:out value="${bean.name }"/>" barcode="<c:out value="${bean.barcode }"/>" weight="<c:out value="${bean.weight }"/>" shelf_life="<c:out value="${bean.shelf_life }"/>" storage_type="<c:out value="${bean.storage_type }"/>" producer="<c:out value="${bean.producer }"/>" image="<c:out value="${bean.image }"/>" images="<c:out value="${bean.images }"/>" description="<c:out value="${bean.description }"/>" remark="<c:out value="${bean.remark }"/>"  onclick="selectRadio(this)"/>
						<%}	%>		
						</td>
						<td>${status.count }</td>
						<td><img class="thumbnail" src="<c:out value="${bean.image }"/>?imageView2/0/w/50" rel="<c:out value="${bean.image }"/>?imageView2/0/w/200"/></td>
						<td><c:out value="${bean.barcode }"/></td>
						<td><c:out value="${bean.name }"/></td>
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