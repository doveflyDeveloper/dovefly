<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.order.back.vo.OrderBackBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退货单信息</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery.printPage.js"></script>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("orderBackBillController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 提交 */
	function submit_onclick() {
		if("${bean.status }" != "<%=OrderBackBillVo.STATUS_EDIT%>"){
			dvAlert("只有<span class='left_ts'>编辑中</span>状态的采购退货单才可以提交！");
	  		return false;
		}
		dvPrompt(window.sendDivHtml, '填写发货信息', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts">如有发快递包裹，请务必认真填写物流信息！</span>',
			submit: function(v, h, f) {
				if ($("input[name='need_express']:checked").val() == "1") {
					if (!validateForm()) {return false;}
					var inputs = [
						'<input type="hidden" name="need_express" value="' + f['need_express'] + '"/>',
						'<input type="hidden" name="express.exp_company" value="' + f['express.exp_company'] + '"/>',
						'<input type="hidden" name="express.exp_tracking_no" value="' + f['express.exp_tracking_no'] + '"/>',
						'<input type="hidden" name="express.exp_date" value="' + f['express.exp_date'] + '"/>',
						'<input type="hidden" name="express.exp_amount" value="' + f['express.exp_amount'] + '"/>',
						'<input type="hidden" name="express.sender_name" value="' + f['express.sender_name'] + '"/>',
						'<input type="hidden" name="express.sender_mobile" value="' + f['express.sender_mobile'] + '"/>',
						'<input type="hidden" name="express.sender_address" value="' + f['express.sender_address'] + '"/>',
						'<input type="hidden" name="express.receiver_name" value="' + f['express.receiver_name'] + '"/>',
						'<input type="hidden" name="express.receiver_mobile" value="' + f['express.receiver_mobile'] + '"/>',
						'<input type="hidden" name="express.receiver_address" value="' + f['express.receiver_address'] + '"/>',
						'<input type="hidden" name="express.remark" value="' + f['express.remark'] + '"/>'
					];
					$("#sendDiv").html(inputs.join(""));
				}
				formHelper.jSubmit(context_path + "/orderBackBillController/submit/${bean.id}");
				return true;
			}
		});
	}
	
	//确认收货
	function receive_onclick() {
		dvConfirm("确认收货前，请务必确认您收到的商品品类及数量正确。确认收货后，将自动完成退款流程，您现在要确认收货吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("orderBackBillController", "receive", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 退单 */
	function reject_onclick() {
		dvConfirm("退单后，将无法完成退货流程和退款流程。您确定要退单吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("orderBackBillController", "reject", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 打印 */
	function print_onclick() {
		$.printPage(formHelper.buildAction("orderBackBillController", "print", "${bean.id }"));
	}
	
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
					<div class="table_title">退货单信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/order_back_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					<c:if test="${sessionScope.DV_USER_VO.cityManagerRole}">
						<c:if test="${bean.status == 'submit'}"><!-- 已提交 -->
						<input type="button" name="receive" value="确认收货" class="button" onclick="receive_onclick();"/>
						<input type="button" name="reject" value="退回" class="button" onclick="reject_onclick();"/>
						</c:if>
					</c:if>
						<input type="button" name="find" value="打印" class="button" onclick="print_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<div>
					<table class="detail_table">
						<tr>
							<td class="label">城市：</td>
							<td class="field"><c:out value="${bean.city_name }"/></td>
							<td class="label">退货单编号：</td>
							<td class="field"><c:out value="${bean.bill_code }"/></td>
						</tr>
						<tr>
							<td class="label">退货单日期：</td>
							<td class="field">${fn:substring(bean.bill_date, 0, 10) }</td>
							<td class="label">金额：</td>
							<td class="field"><c:out value="${bean.amount }"/></td>
						</tr>
						<tr>
							<td class="label">退款方式：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/></td>
							<td class="label">退款回执编号：</td>
							<td class="field"><c:out value="${bean.pay_code }"/></td>
						</tr>
						<tr>
							<td class="label">退款金额：</td>
							<td class="field"><c:out value="${bean.pay_amount }"/></td>
							<td class="label">退款时间：</td>
							<td class="field"><c:out value="${bean.pay_time }"/></td>
						</tr>
						<tr>
							<td class="label">退款状态：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/></td>
							<td class="label">退货单状态：</td>
							<td class="field"><dv:display dicKeyword="DIC_ORDER_BACK_BILL_STATUS" value="${bean.status }"/></td>
						</tr>
						<tr>
							<td class="label">备注：</td>
							<td class="field"><c:out value="${bean.remark }"/></td>
							<td class="label"></td>
							<td class="field"></td>
						</tr>
					</table>
				</div>
				<DL id="sub_tab">
					<DT>退货单明细</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="5%">缩略图</th>
									<th width="20%">商品名称</th>
									<th width="10%">采购价</th>
									<th width="10%">退货数量</th>
									<th width="10%">金额小计</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>${status.count }</td>
									<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
									<td><c:out value="${detail.goods_name }"/></td>
									<td><c:out value="${detail.unit_price }"/></td>
									<td><c:out value="${detail.quantity }"/></td>
									<td><c:out value="${detail.sub_total }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
						<div>
							<div style="display:inline;float:left;">
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
