<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<%@ page import="com.deertt.module.order.bill.vo.OrderBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("orderBillController");
	
	/** 保存 */
	function save_onclick() {
		if(new Number($("#total_amount").val()) <= 0){
			dvAlert("商品订单列表不能为空，请先选择选购的商品！");
			return false;
		};
		
		if(new Number($("#total_amount").val()) < new Number("${start_amount }")){
			dvAlert("进货订单需满${start_amount }元起送！");
			return false;
		};
		
		if(validateForm()) {
			pay_onclick();
		}
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
		reference(context_path + "/goodsWController/reference?reference=order&has_stock=1&status=1&selected_goods_ids=" + selected_goods_ids, "选择商品", 900, 450, "referenceGoods_callback", "checkbox", "search", options);
	}
	
	/** 新增商品回调 */
	function referenceGoods_callback(datas) {
		//dvCloseDialog();// 关闭参照窗口
		// 枚举（循环）对象的所有属性
		var html = "";
		var currIndex = $("#detail_list_table tr").length - 1;
		for (i in datas) {
			var obj = datas[i];
			var df_quantity = (obj.buy_rule || "1").replace(/[^\d]/g, "");
			html += '<tr>' + 
			'	<td>' + 
			'		<input type="checkbox" name="dv_checkbox" value="0" onclick="selectCheckBox(this)"/>' + 
			'	</td>' + 
			'	<td>' + (++currIndex) + '</td>' + 
			'	<td><img class="thumbnail" src="' + obj.image + '?imageView2/0/w/50" rel="' + obj.image + '?imageView2/0/w/200"/></td>' + 
			'	<td>' + obj.name + '</td>' + 
			'	<td>' + obj.sale_price + '</td>' + 
			'	<td>' + 
			'		<input type="hidden" name="detail_id" value="0"/>' + 
			'		<input type="hidden" name="goods_id" value="' + obj.id + '"/>' + 
			'		<input type="hidden" name="goods_name" value="' + obj.name + '"/>' + 
			'		<input type="hidden" name="goods_image" value="' + obj.image + '"/>' + 
			'		<input type="hidden" name="goods_sale_price" value="' + obj.sale_price + '"/>' + 
			'		<span class="required_red">* </span><input type="text" class="half_input" name="goods_quantity" value="' + df_quantity + '" sale_price="' + 
					obj.sale_price + '" inputName="数量" validate="notNull;isNumber;gte(\'1\');lte(\'' + obj.stock_sum + '\');buy_rule(\'' + obj.buy_rule + '\')"/>' + 
			'		<br><span class="left_ts">(库存：' + obj.stock_sum + '，进货规则：' + obj.buy_rule.replace(/>/g, '&gt;').replace(/</g, '&lt;') + ')</span>' + 
			'	</td>' + 
			'	<td>' + new Number(obj.sale_price * df_quantity).toFixed(2) + '</td>' + 
			'</tr>'
		}
		
		$(html).appendTo("#detail_list_table").find("input[name='goods_quantity']")
		.bind("input propertychange", function(){
			var quantity = $(this).val();
			var sale_price = $(this).attr("sale_price");
			var sub_total = new Number(sale_price * quantity).toFixed(2);
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
		$("#total_amount").val(new Number(allAmount).toFixed(2));
	}
	
	/** 支付 */
	function pay_onclick() {
		dvPrompt(window.choosePayDivHtml, '选择支付方式', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts">目前仅支持 汀汀支付、微信钱包、支付宝 三种支付方式！</span>',
			loaded: function (h) {
				h.find("#choose_pay_amount").val($("#total_amount").val());
				if ('${shop.balance_amount + shop.loanable_amount - shop.loan_amount < bean.total_amount}' == 'true') {
					h.find("input[name='choose_pay_type'][value='<%=OrderBillVo.PAY_TYPE_TTPAY %>']").attr("disabled", "disabled").parent().append(' (<span class="left_ts">额度不足</span>)');
				} else if ('${shop.balance_amount < bean.total_amount}' == 'true' && '${shop.balance_amount + shop.loanable_amount - shop.loan_amount >= bean.total_amount}' == 'true'){
					h.find("input[name='choose_pay_type'][value='<%=OrderBillVo.PAY_TYPE_TTPAY %>']").parent().append(' (<span class="left_ts">贷款支付</span>)');
				}
				
			},
			submit: function(v, h, f) {
				if (!validateForm("choose_pay_table")) {return false;}
				//var choose_pay_type = f["choose_pay_type"];//("input[name='choose_pay_type']:checked")
				$("#pay_type").val(f["choose_pay_type"]);
				$("#use_coin_quantity").val(f["choose_use_coin_quantity"]);
				
				dvConfirm("订单提交后请在30分钟内完成支付，否则订单将自动取消，<br>您确认要提交吗？", 
					function() {
			        	formHelper.jSubmit(formHelper.buildAction("orderBillController", "saveAndSubmit"));
					}, 
					function() {
						//alert("干嘛要取消啊？");
					}
				);
				return true;
			}
		});
	}
	
	$(document).ready(function() {		
		$("input[name='goods_quantity']").bind("input propertychange", function(){
			var quantity = $(this).val();
			var sale_price = $(this).attr("sale_price");
			var sub_total = new Number(sale_price * quantity).toFixed(2);
			$(this).parent().next().html(sub_total);
			
			calculateAllAmount();
		});
		window.choosePayDivHtml = $("#choosePayDiv").html();
		$("#choosePayDiv").html("");
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">订单<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/purchase_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="提交订单" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<div>
					<table id="bill_base_info" class="insert_table">
						<tr>
							<td class="label">进货金额：</td>
							<td class="field">
								<input type="text" id="total_amount" name="total_amount" class="readonly" inputName="金额" value="<c:out value="${bean.total_amount }"/>" validate=""/>
							</td>
						</tr>
						<tr>
							<td class="label"><span class="required_red">* </span>收货人：</td>
							<td class="field">
								<input type="text" name="rcv_name" class="input" inputName="收货人" value="<c:out value="${bean.rcv_name }"/>" maxlength="50" validate="notNull"/>
							</td>
							<td class="label"><span class="required_red">* </span>收货人手机：</td>
							<td class="field">
								<input type="text" name="rcv_mobile" class="input" inputName="收货人手机" value="<c:out value="${bean.rcv_mobile }"/>" validate="notNull;isMobile"/>
							</td>
						</tr>
						<tr>
							<td class="label"><span class="required_red">* </span>收货地址：</td>
							<td class="field" colspan="3">
								<input type="text" name="rcv_address" class="full_input" inputName="收货地址" value="<c:out value="${bean.rcv_address }"/>" maxlength="100" validate="notNull"/>
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
						<tr>
							<td class="label"></td>
							<td class="field"></td>
							<td class="label"></td>
							<td class="field"></td>
						</tr>
					</table>
					<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
					<input type="hidden" name="bill_date" value="<c:out value="${bean.bill_date }"/>"/>
					<input type="hidden" name="bill_time" value="<c:out value="${bean.bill_time }"/>"/>
					<input type="hidden" id="limit_coin_quantity" name="limit_coin_quantity" value="<c:out value="${bean.limit_coin_quantity }"/>"/>
					<input type="hidden" id="use_coin_quantity" name="use_coin_quantity" value="<c:out value="${bean.use_coin_quantity }"/>"/>
					<input type="hidden" id="use_coin_amount" name="use_coin_amount" value="<c:out value="${bean.use_coin_amount }"/>"/>
					<input type="hidden" id="send_coin_quantity" name="send_coin_quantity" value="<c:out value="${bean.send_coin_quantity }"/>"/>
					<input type="hidden" id="pay_type" name="pay_type" value="">
					<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
				</div>
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
									<th width="40%">商品名称</th>
									<th width="10%">店长进货价</th>
									<th width="15%">进货数量</th>
									<th width="10%">进货小计</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>
										<input type="checkbox" name="dv_checkbox" value="<c:out value="${detail.id }"/>" onclick="selectCheckBox(this)"/>
									</td>
									<td>${status.count }</td>
									<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
									<td><c:out value="${detail.goods_name }"/></td>
									<td><c:out value="${detail.sale_price }"/></td>
									<td>
										<input type="hidden" name="detail_id" value="<c:out value="${detail.id }"/>"/>
										<input type="hidden" name="goods_id" value="<c:out value="${detail.goods_id }"/>"/>
										<input type="hidden" name="goods_name" value="<c:out value="${detail.goods_name }"/>"/>
										<input type="hidden" name="goods_image" value="<c:out value="${detail.goods_image }"/>"/>
										<input type="hidden" name="goods_sale_price" value="<c:out value="${detail.sale_price }"/>"/>
										<span class="required_red">* </span>
										<input type="text" class="half_input" name="goods_quantity" value="<c:out value="${detail.quantity }"/>" 
											sale_price="<c:out value="${detail.sale_price }"/>" inputName="进货数量" validate="notNull;isNumber;gte('1');lte('${detail.stock_sum }');buy_rule('${detail.buy_rule }')"/>
											<br><span class="left_ts">(库存：<c:out value="${detail.stock_sum }"/>，进货规则：<c:out value="${detail.buy_rule }"/>)</span>
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
								(进货订单满 <span class="left_ts">${start_amount }</span> 元起送)&nbsp;&nbsp;&nbsp;&nbsp;<span id="allAmount" style="font-weight:bold; line-height: 30px; padding-right: 20px;">合计：${bean.total_amount }（元）</span>
							</div>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
	<div id="choosePayDiv" style="display:none;">
		<table id="choose_pay_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>支付方式：</td>
				<td class="field">
					<dv:radio dicKeyword="DIC_PAY_TYPE" name="choose_pay_type" defaultValue="ttpay" ignoreValues="online,cod" attributes="inputName='支付方式' validate='notNull'"/>
				</td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>支付金额：</td>
				<td class="field">
					<input type="text" id="choose_pay_amount" name="choose_pay_amount" class="readonly" inputName="支付金额" value="" validate=""/>
				</td>
			</tr>
			<tr>
				<td class="label">汀豆代付(个)：</td>
				<td class="field">
					<input type="text" id="choose_use_coin_quantity" name="use_choose_coin_quantity" class="input" inputName="汀豆" value="${sessionScope.DV_USER_VO.coin_quantity }" validate="notNull;isNumber;gte('0');lte('${sessionScope.DV_USER_VO.coin_quantity }')"/>
					<span id="span_choose_use_coin_quantity">(可抵${sessionScope.DV_USER_VO.coin_quantity*0.1 }元)</span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
