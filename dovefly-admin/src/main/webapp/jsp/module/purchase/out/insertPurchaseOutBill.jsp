<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("purchaseOutBillController");
	
	/** 保存 */
	function save_onclick() {
		if(new Number($("#amount").val()) <= 0){
			dvAlert("订单明细列表不能为空，请先选择需出库的商品！");
			return false;
		};
		
		if (!validateForm()) {
			return false;
		}
		
		if(<%=isModify%>){
			update_onclick();
		} else {
			insert_onclick();
		}
	}
	
	/** 新增保存 */
	function insert_onclick() {
		formHelper.jSubmit(formHelper.buildInsertAction());
	}
	
	/** 修改保存 */
	function update_onclick() {
		formHelper.jSubmit(formHelper.buildUpdateAction());
	}
	
	/** 新增商品 */
	function add_onclick() {
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		var selected_goods_ids = $("input[name='goods_id']").map(function(){return this.value;}).get().join(",");
		reference(context_path + "/goodsWController/reference?reference=purchase&status=1&selected_goods_ids=" + selected_goods_ids, "选择商品", 900, 450, "referenceGoods_callback", "checkbox", "search", options);
	}
	
	/** 新增商品回调 */
	function referenceGoods_callback(datas) {
		//dvCloseDialog();// 关闭参照窗口
		// 枚举（循环）对象的所有属性
		var html = "";
		var currIndex = $("#detail_list_table tr").length - 1;
		for (i in datas) {
			var obj = datas[i];
			html += '<tr>' + 
			'	<td>' + 
			'		<input type="checkbox" name="dv_checkbox" value="0" onclick="selectCheckBox(this)"/>' + 
			'	</td>' + 
			'	<td>' + (++currIndex) + '</td>' + 
			'	<td><img class="thumbnail" src="' + obj.image + '?imageView2/0/w/50" rel="' + obj.image + '?imageView2/0/w/200"/></td>' + 
			'	<td>' + obj.name + '</td>' + 
			'	<td>' + obj.cost_price + '</td>' + 
			'	<td>' + 
			'		<input type="hidden" name="detail_id" value="0"/>' + 
			'		<input type="hidden" name="goods_id" value="' + obj.id + '"/>' + 
			'		<input type="hidden" name="goods_name" value="' + obj.name + '"/>' + 
			'		<input type="hidden" name="goods_image" value="' + obj.image + '"/>' + 
			'		<input type="hidden" name="goods_unit_price" value="' + obj.cost_price + '"/>' + 
			'		<span class="required_red">* </span><input type="text" class="half_input" name="quantity" value="1" unit_price="' + obj.cost_price + '" inputName="数量" validate="notNull;isPositiveInteger"/>' + 
			'	</td>' + 
			'	<td>' + obj.cost_price + '</td>' + 
			'</tr>';
		}
		
		$(html).appendTo("#detail_list_table").find("input[name='quantity']")
		.bind("input propertychange", function(){
			var quantity = $(this).val();
			var unit_price = $(this).attr("unit_price");
			var sub_total = new Number(unit_price * quantity).toFixed(2);
			$(this).parent().next().html(sub_total);

			calculateAllAmount();
		})
		
		calculateAllAmount();
	}
	
	/** 移除商品 */
	function remove_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择商品！");
	  		return false;
		}
		
		for (var i = 0; i < sels.length; i++) {
			$(sels[i]).closest("tr").remove();
		}
		
		$("#detail_list_table tr").each(function(i, e){
			$(this).find("td:eq(1)").html(i);
		});
		
		calculateAllAmount();
	}
	
	/** 计算合计总金额 */
	function calculateAllAmount() {
		var allAmount = 0;
		$("#detail_list_table tr").each(function(i, e){
			if (i == 0) {return true;}//忽略题头
			var subTotal = $(this).find("td:last").html();
			allAmount += parseFloat(subTotal);
		});
		$("#allAmount").html("合计：" + new Number(allAmount).toFixed(2) + "（元）");
		$("#amount").val(new Number(allAmount).toFixed(2));
	}
	
	$(document).ready(function() {
		
		$("input[name='quantity']").bind("input propertychange", function(){
			var quantity = $(this).val();
			var unit_price = $(this).attr("unit_price");
			var sub_total = new Number(unit_price * quantity).toFixed(2);
			$(this).parent().next().html(sub_total);
			
			calculateAllAmount();
		});

	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">出库单<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/purchase_out_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="insert_table">
					<tr>
						<td class="label">出库单编号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="readonly" inputName="出库单编号" value="<c:out value="${bean.bill_code }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label" rowspan="3"><span class="required_red">* </span>领料出库说明：</td>
						<td class="field" rowspan="3">
							<textarea name="remark" class="input" style="width:80%; height:60px;" inputName="领料出库说明" validate="notNull"><c:out value="${bean.remark }"/></textarea>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>出库日期：</td>
						<td class="field">
							<input type="text" name="bill_date" class="input Wdate" inputName="出库单日期" value="${fn:substring(bean.bill_time, 0, 10) }" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>出库单金额：</td>
						<td class="field">
							<input type="text" id="amount" name="amount" class="readonly" inputName="出库单金额" value="<c:out value="${bean.amount }"/>" validate=""/>
						</td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
				<input type="hidden" name="bill_time" value="<c:out value="${bean.bill_time }"/>"/>
				<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>订单明细</DT>
					<DD>
						<div>
							<table id="detail_list_table" class="list_table">
								<tr>
									<th width="5%">
										<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
									</th>
									<th width="5%">序号</th>
									<th width="5%">缩略图</th>
									<th width="35%">品名</th>
									<th width="20%">单价（以零售单位计）</th>
									<th width="20%">数量（以零售单位计）</th>
									<th width="10%">小计</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>
										<input type="checkbox" name="dv_checkbox" value="<c:out value="${detail.id }"/>" onclick="selectCheckBox(this)"/>
									</td>
									<td>${status.count }</td>
									<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
									<td><c:out value="${detail.goods_name }"/></td>
									<td><c:out value="${detail.unit_price }"/></td>
									<td>
										<input type="hidden" name="detail_id" value="<c:out value="${detail.id }"/>"/>
										<input type="hidden" name="goods_id" value="<c:out value="${detail.goods_id }"/>"/>
										<input type="hidden" name="goods_name" value="<c:out value="${detail.goods_name }"/>"/>
										<input type="hidden" name="goods_image" value="<c:out value="${detail.goods_image }"/>"/>
										<input type="hidden" name="goods_unit_price" value="<c:out value="${detail.unit_price }"/>"/>
										<span class="required_red">* </span>
										<input type="text" class="half_input" name="quantity" value="<c:out value="${detail.quantity }"/>" unit_price="${detail.unit_price }" inputName="数量" validate="notNull;isPositiveInteger"/>
									</td>
									<td><c:out value="${detail.sub_total }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
						<div>
							<div style="display:inline;float:left;">
								<input type="button" name="add" value="添加商品" class="button" onclick="add_onclick();"/>
								<input type="button" name="remove" value="移除商品" class="button" onclick="remove_onclick();" />
							</div>
							<div style="display:inline;float:right;">
								<span class="left_ts"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span id="allAmount" style="font-weight:bold; line-height: 30px; padding-right: 20px;">合计：${bean.amount }（元）</span>
							</div>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
