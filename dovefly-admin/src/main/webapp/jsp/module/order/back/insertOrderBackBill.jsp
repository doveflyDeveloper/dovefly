<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退货单<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("orderBackBillController");
	
	/** 保存 */
	function save_onclick() {
		if($("#amount").val() <= 0){
			dvAlert("商品订单列表不能为空，请先选择需要退货的商品！");
			return false;
		};
		
		if(!validateForm()){
			return false;
		}
		
		dvConfirm("退款订单提交后，将由城市经理处理，<br>退货成功后，退货款项将支付到您的汀鹿账户内，<br>您确认要提交吗？", 
			function() {
	        	formHelper.jSubmit(formHelper.buildAction("orderBackBillController", "saveAndSubmit"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 新增商品 */
	function add_onclick() {
		var selected_goods_ids = $("input[name='goods_id']").map(function(){return this.value;}).get().join(",");
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/goodsSController/reference?reference=back&has_stock=1&status=1&selected_goods_ids=" + selected_goods_ids, "选择商品", 900, 450, "referenceGoods_callback", "checkbox", "search", options);
	}
	
	/** 新增商品回调 */
	function referenceGoods_callback(datas) {
		dvCloseDialog();// 关闭参照窗口
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
			'	<td><img class="thumbnail" src="' + obj.image + '" rel="' + obj.image + '"/></td>' + 
			'	<td>' + obj.name + '</td>' + 
			'	<td>' + obj.sale_price + '</td>' + 
			'	<td>' + 
			'		<input type="hidden" name="detail_id" value="0"/>' + 
			'		<input type="hidden" name="origin_id" value="' + obj.origin_id + '"/>' + 
			'		<input type="hidden" name="goods_id" value="' + obj.id + '"/>' + 
			'		<input type="hidden" name="goods_name" value="' + obj.name + '"/>' + 
			'		<input type="hidden" name="goods_image" value="' + obj.image + '"/>' + 
			'		<input type="hidden" name="goods_unit_price" value="' + obj.sale_price + '"/>' + 
			'		<span class="required_red">* </span><input type="text" class="half_input" name="goods_quantity" value="' + obj.stock_sum + '" unit_price="' + obj.sale_price + '" stock_sum="' + obj.stock_sum + '" inputName="数量" validate="notNull;isNumber"/>' + 
			'	</td>' + 
			'	<td>' + new Number(obj.sale_price * obj.stock_sum).toFixed(2) + '</td>' + 
			'</tr>'
		}
		
		$(html).appendTo("#detail_list_table").find("input[name='goods_quantity']")
		.bind("input propertychange", function(){
			var quantity = $(this).val();
			var unit_price = $(this).attr("unit_price");
			var sub_total = new Number(unit_price * quantity).toFixed(2);
			$(this).parent().next().html(sub_total);
			
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
		
		$("input[name='goods_quantity'").bind("input propertychange", function(){
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
					<div class="table_title">退货单<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>//helpController/getHelp/order_back_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="提交" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<div>
					<table class="insert_table">
						<tr>
							<td class="label">退货单编号：</td>
							<td class="field">
								<input type="text" name="bill_code" class="readonly" inputName="退货单编号" value="<c:out value="${bean.bill_code }"/>" maxlength="50" validate=""/>
							</td>
							<td class="label">退货金额：</td>
							<td class="field">
								<input type="text" id="amount" name="amount" class="readonly" inputName="退货金额" value="<c:out value="${bean.amount }"/>" validate=""/>
							</td>
						</tr>
						<tr>
							<td class="label">备注：</td>
							<td class="field">
								<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="200" validate=""/>
							</td>
							<td class="label"></td>
							<td class="field"></td>
						</tr>
					</table>
					<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
					<input type="hidden" name="bill_date" value="<c:out value="${bean.bill_date }"/>"/>
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
									<th width="40%">商品名称</th>
									<th width="10%">店长采购价</th>
									<th width="10%">退货数量</th>
									<th width="10%">退货小计</th>
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
										<input type="hidden" name="origin_id" value="<c:out value="${detail.origin_id }"/>"/>
										<input type="hidden" name="goods_id" value="<c:out value="${detail.goods_id }"/>"/>
										<input type="hidden" name="goods_name" value="<c:out value="${detail.goods_name }"/>"/>
										<input type="hidden" name="goods_image" value="<c:out value="${detail.goods_image }"/>"/>
										<input type="hidden" name="goods_unit_price" value="<c:out value="${detail.unit_price }"/>"/>
										<span class="required_red">* </span><input type="text" class="half_input" name="goods_quantity" value="<c:out value="${detail.quantity }"/>" unit_price="<c:out value="${detail.unit_price }"/>" inputName="退货数量" validate="notNull;isNumber"/>
									</td>
									<td><c:out value="${detail.sub_total }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
						<div>
							<div style="display:inline;float:left;">
								<input type="button" name="save" value="添加商品" class="button" onclick="add_onclick();"/>
								<input type="button" name="back" value="移除商品" class="button" onclick="remove_onclick();" />
							</div>
							<div style="display:inline;float:right;">
								<span id="allAmount" style="font-weight:bold; line-height: 30px; padding-right: 20px;">合计：${bean.amount }（元）</span>
							</div>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
