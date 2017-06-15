<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购退货单<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("purchaseBackBillController");
	
	/** 保存 */
	function save_onclick() {
		if(new Number($("#amount").val()) <= 0){
			dvAlert("退货单明细列表不能为空，请先选择需退货的商品！");
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
			'       <input type="text" class="half_input" style="width:50px;" name="spec_quantity" value="0" inputName="采购包装数量" validate="notNull;isPositiveInteger"/> × '+
			'		<input type="text" class="readonly half_input" style="width:50px;" name="spec" value="' + (obj.spec || '0') + '" inputName="商品规格" readonly="readonly" validate="notNull;isPositiveInteger"/> ' + 
			'       <img style="cursor:pointer;" src="' + context_path + '/resources/images/edit.gif" title="修改包装规格" onclick="changeSpec_onclick(this, ' + obj.id + ', \'' + obj.name + '\')"/>&nbsp;&nbsp; = &nbsp;&nbsp;'+ 
			'		<input type="text" class="readonly half_input" style="width:50px;" name="quantity" value="0" inputName="数量" readonly="readonly" validate="notNull;isPositiveInteger"/>' + 
			'	</td>' 
			+'	<td><span class="required_red">* </span><input type="text" class="half_input" name="sub_total" value="0" inputName="小计" validate="notNull;isPositive"/></td>' + 
			'</tr>';
		}
		
		$(html).appendTo("#detail_list_table").find("input[name='quantity']")
		.bind("input propertychange", function(){
            var quantity = $(this).val();
            var sub_total = $(this).closest("tr").find("input[name='sub_total']").val();
            var unit_price = new Number(sub_total / quantity).toFixed(2);
            $(this).closest("tr").find("td:eq(4)").html(unit_price);
            
            calculateAllAmount();
		}).end().find("input[name='sub_total']").bind("input propertychange", function(){
            var sub_total = $(this).val();
            var quantity = $(this).closest("tr").find("input[name='quantity']").val();
            var unit_price = new Number(sub_total / quantity).toFixed(2);
            $(this).closest("tr").find("td:eq(4)").html(unit_price);
            
            calculateAllAmount();
		}).end().find("input[name='spec_quantity']").bind("input propertychange", function(){
            var spec_quantity = $(this).val();
            var spec = $(this).closest("tr").find("input[name='spec']").val();
            var quantity = new Number(spec * spec_quantity);
            $(this).closest("tr").find("input[name='quantity']").val(quantity).trigger("input").trigger("propertychange");
            
            calculateAllAmount();
		});
		
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
	
	// 选择供应商
	function referenceSupplier() {
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/supplierController/reference", "选择供应商", 900, 450, "referenceSupplier_callback", "radio", "search", options);
	}

	/**
	 * 参照供应商
	 * 
	 * @param datas
	 */
	function referenceSupplier_callback(datas) {
	// 枚举（循环）对象的所有属性
		for (i in datas) {
			var obj = datas[i];
			$("input[name='supplier_id']").val(obj.supplier_id);
			$("input[name='supplier_name']").val(obj.supplier_name);
		}
	}
	
	/** 计算合计总金额 */
	function calculateAllAmount() {
		var allAmount = 0;
		$("input[name='sub_total']").each(function(i, e){
			var subTotal = $(this).val();
			allAmount += parseFloat(subTotal || "0");
		});
		
		$("#allAmount").html("合计：" + new Number(allAmount).toFixed(2) + "（元）");
		$("#amount").val(new Number(allAmount).toFixed(2));
	}
	
	
	$(document).ready(function() {
		
		$("input[name='quantity']").bind("input propertychange", function(){
			var quantity = $(this).val();
			var sub_total = $(this).closest("tr").find("input[name='sub_total']").val();
			var unit_price = new Number(sub_total / quantity).toFixed(2);
			$(this).closest("tr").find("td:eq(4)").html(unit_price);
			
			calculateAllAmount();
		});
		
		$("input[name='sub_total']").bind("input propertychange", function(){
			var sub_total = $(this).val();
			var quantity = $(this).closest("tr").find("input[name='quantity']").val();
			var unit_price = new Number(sub_total / quantity).toFixed(2);
			$(this).closest("tr").find("td:eq(4)").html(unit_price);
			
			calculateAllAmount();
		});
		
		$("input[name='spec_quantity']").bind("input propertychange", function(){
	        var spec_quantity = $(this).val();
	        var spec = $(this).closest("tr").find("input[name='spec']").val();
	        var quantity = new Number(spec * spec_quantity);
	        $(this).closest("tr").find("input[name='quantity']").val(quantity).trigger("input").trigger("propertychange");
	        
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
					<div class="table_title">退货单<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/purchase_back_bill"></div>
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
						<td class="label">退货单编号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="readonly" inputName="退货单编号" value="<c:out value="${bean.bill_code }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label"><span class="required_red">* </span>退货单日期：</td>
						<td class="field">
							<input type="text" name="bill_date" class="input Wdate" inputName="退货单日期" value="${fn:substring(bean.bill_date, 0, 10) }" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>退货单金额：</td>
						<td class="field">
							<input type="text" id="amount" name="amount" class="readonly" inputName="退货单金额" value="<c:out value="${bean.amount }"/>" validate=""/>
						</td>
						<td class="label"><span class="required_red">* </span>供应商：</td>
						<td class="field">
							<input type="hidden" name="supplier_id" value="<c:out value="${bean.supplier_id }"/>"/>
							<input type="text" name="supplier_name" class="reference" inputName="供应商" value="<c:out value="${bean.supplier_name }"/>" maxlength="50" validate="notNull"/><span class="referenceSpan" onclick="referenceSupplier();"></span>
						</td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
				<input type="hidden" name="bill_time" value="<c:out value="${bean.bill_time }"/>"/>
				<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
			</div>
				<DL id="sub_tab">
					<DT>退货单明细</DT>
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
									<th width="15%">单价<br>（以零售单位计）</th>
									<th width="25%">退货数量<br>（采购大包装数量 × 包装规格 = 零售单位数量）</th>
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
										<span class="required_red">*</span><input type="text" class="half_input" style="width:50px;" name="spec_quantity" value="<c:out value="${detail.spec_quantity }"/>" inputName="采购大包装数量" validate="notNull;isPositiveInteger" /> × 
										<input type="text" class="readonly half_input" style="width:50px;" name="spec" value="<c:out value="${detail.spec }"/>" inputName="包装规格" readonly="readonly" validate="notNull;isPositiveInteger"/>
										 <img style="cursor:pointer;" src="<%=request.getContextPath() %>/resources/images/edit.gif" title="修改包装规格" onclick="changeSpec_onclick(this, ${detail.goods_id }, '${detail.goods_name }')"/>&nbsp;&nbsp; = &nbsp;&nbsp;
										<input type="text" class="readonly half_input" style="width:50px;" name="quantity" value="<c:out value="${detail.quantity }"/>" inputName="数量" readonly="readonly" validate="notNull;isPositiveInteger"/>
									</td>
									<td><span class="required_red">* </span><input type="text" class="half_input" name="sub_total" value="<c:out value="${detail.sub_total }"/>" inputName="小计" validate="notNull;isPositive"/></td>
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
	</form>
</body>
</html>