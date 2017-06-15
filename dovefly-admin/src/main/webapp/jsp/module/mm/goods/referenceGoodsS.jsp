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
<title>商品参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("goodsSController");
	
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
		var selected_goods_ids = $("#selected_goods_ids").val() || "0";
		var datas = new Array();
		for (var i = 0; i < sels.length; i++) {
			var obj = new Object();
			obj.id = $(sels[i]).val();
			obj.origin_id = $(sels[i]).attr("origin_id");
			obj.shop_id = $(sels[i]).attr("shop_id");
			obj.shop_name = $(sels[i]).attr("shop_name");
			obj.name = $(sels[i]).attr("goods_name");
			obj.cost_price = $(sels[i]).attr("cost_price");
			obj.sale_price = $(sels[i]).attr("sale_price");
			obj.market_price = $(sels[i]).attr("market_price");
			obj.buy_rule = $(sels[i]).attr("buy_rule");
			obj.stock_sum = $(sels[i]).attr("stock_sum");
			obj.image = $(sels[i]).attr("image");
			obj.spec = $(sels[i]).attr("spec");
			datas.push(obj);
			
			selected_goods_ids += "," + obj.id;
			$(sels[i]).closest("tr").remove();
		}
		$("#selected_goods_ids").val(selected_goods_ids);
		parent.<%=callback%>(datas);
		if ('<%=inputType%>' == 'radio') {
			return true;
		} else {
			return false;//若要关闭窗口，可返回true;
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
						<td class="label">商品名称：</td>
						<td class="field">
							<input type="text" name="name" class="input" maxlength="100" value="<c:out value="${param.name }"/>"/>
						</td>
						<td class="label">商品分类：</td>
						<td class="field">
							<dv:select name="category_id" dicKeyword="DIC_GOODS_CATEGORY" hasEmpty="true" defaultValue="${param.category_id }"/>
						</td>
					</tr>
				</table>
				<input type="hidden" id="selected_goods_ids" name="selected_goods_ids" value="<c:out value="${param.selected_goods_ids }"/>"/> 
				<input type="hidden" name="status" value="<c:out value="${param.status }"/>"/>
				<input type="hidden" name="has_stock" value="<c:out value="${param.has_stock }"/>"/>
				<input type="hidden" name="reference" value="<c:out value="${param.reference }"/>"/>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">商品列表&nbsp;</span>
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
						<th width="5%">店铺</th>
						<th width="40%">品名</th>
						<th width="10%">进货单价</th>
						<th width="10%">当前销量</th>
						<th width="10%">当前库存</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" origin_id="<c:out value="${bean.origin_id }"/>" shop_id="<c:out value="${bean.shop_id }"/>" shop_name="<c:out value="${bean.shop_name }"/>" goods_name="<c:out value="${bean.name }"/>" cost_price="<c:out value="${bean.cost_price }"/>" sale_price="<c:out value="${bean.sale_price }"/>" market_price="<c:out value="${bean.market_price }"/>" buy_rule="<c:out value="${bean.buy_rule }"/>" image="<c:out value="${bean.image }"/>" stock_sum="<c:out value="${bean.stock_sum }"/>" spec="<c:out value="${bean.spec }"/>" onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.id }"/>" origin_id="<c:out value="${bean.origin_id }"/>" shop_id="<c:out value="${bean.shop_id }"/>" shop_name="<c:out value="${bean.shop_name }"/>" goods_name="<c:out value="${bean.name }"/>" cost_price="<c:out value="${bean.cost_price }"/>" sale_price="<c:out value="${bean.sale_price }"/>" market_price="<c:out value="${bean.market_price }"/>" buy_rule="<c:out value="${bean.buy_rule }"/>" image="<c:out value="${bean.image }"/>" stock_sum="<c:out value="${bean.stock_sum }"/>" spec="<c:out value="${bean.spec }"/>" onclick="selectRadio(this)"/>
						<%}	%>		
						</td>
						<td>${status.count }</td>
						<td><img class="thumbnail" src="<c:out value="${bean.image }"/>?imageView2/0/w/50" rel="<c:out value="${bean.image }"/>?imageView2/0/w/200"/></td>
						<td><c:out value="${bean.shop_name }"/></td>
						<td><c:out value="${bean.name }"/></td>
						<td><c:out value="${bean.sale_price }"/></td>
						<td><c:out value="${bean.sold_volume }"/></td>
						<td><c:out value="${bean.stock_sum }"/></td>
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