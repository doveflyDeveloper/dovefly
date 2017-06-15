<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.trade.bill.vo.TradeBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/resources/js/jquery.printPage.js"></script>
<title>订单处理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("tradeBillController");
	
	/** 发货 */
	function send_onclick() {
		var msg = "${bean.pay_status }" == "<%=TradeBillVo.PAY_STATUS_SUCCESS%>" ? "本订单已在线付款，您确定要发货吗？" : "本订单尚未付款，系统将认为客户已线下付款给您，您确定要发货吗？";
		dvConfirm(msg, 
			function() {
				formHelper.jSubmit(formHelper.buildAction("tradeBillController", "send", "${bean.id }"));
			},
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 订单取消 */
	function cancel_onclick() {
		var msg = "${bean.pay_status }" == "<%=TradeBillVo.PAY_STATUS_SUCCESS%>" ? "本订单已在线付款，系统将自动线上退款给客户，您确定要取消此订单吗？" : "本订单尚未付款，系统将认为您已线下退款给客户，您确定要取消此订单吗？";
		dvConfirm(msg, 
			function() {
				formHelper.jSubmit(formHelper.buildAction("tradeBillController", "cancel", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 确认退款 */
	function agreeRefund_onclick() {
		dvConfirm('您确定要取消此订单并退款吗？', 
			function() {
				formHelper.jSubmit(context_path + "/tradeBillController/agreeRefund/${bean.id}");
			},
			function() {
				//alert("干嘛要取消啊？");
			}
		);
		dvAlert("暂未实现，敬请期待！");
	}
	
	/** 同意退货 */
	function agreeReturnGoods_onclick() {
		dvConfirm('此操作前请务必保证您确实已经收到被退回的商品，<br>确认退货操作后，系统将在一个工作日内自动退款给用户，<br>您确定要取消此订单并退款吗？', 
			function() {
				formHelper.jSubmit(context_path + "/tradeBillController/agreeReturnGoods/${bean.id}");
			},
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 拒绝退货 */
	function denyReturnGoods_onclick() {
		dvConfirm('您确定要拒绝此订单的退货申请吗？', 
			function() {
				formHelper.jSubmit(context_path + "/tradeBillController/denyReturnGoods/${bean.id}");
			},
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 打印 */
	function print_onclick() {
		var options = {
				buttons : {
					'打印' : 'ok',
					'关闭' : false
				}
			};
		dvOpenDialog(context_path + '/tradeBillController/print/${bean.id }', '订单打印', 320, 450, options);
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
					<div class="table_title">订单信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/trade_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					<c:if test="${sessionScope.DV_USER_VO.shopkeeperRole}"><!-- 店长 -->
						<c:if test="${bean.status == 'submit'}"><!-- 已提交 -->
						<input type="button" name="send" value="发货" class="button" onclick="send_onclick();"/>
						</c:if>
						<c:if test="${bean.status == 'apply_for_refund'}"><!-- 申请退款 -->
						<input type="button" name="agreeRefund" value="同意退款" class="button" onclick="agreeRefund_onclick();"/>
						<input type="button" name="send" value="拒绝退款，继续发货/打印" class="button" onclick="sendAndPrint_onclick();"/>
						</c:if>
						<c:if test="${bean.status == 'apply_for_return'}"><!-- 申请退货 -->
						<input type="button" name="agreeReturnGoods" value="同意退货" class="button" onclick="agreeReturnGoods_onclick();"/>
						<input type="button" name="denyReturnGoods" value="拒绝退货" class="button" onclick="denyReturnGoods_onclick();"/>
						</c:if>
					</c:if>
						<input type="button" name="print" value="打印" class="button" onclick="print_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<div>
					<table class="detail_table">
						<tr>
							<td class="label">订单编号：</td>
							<td class="field"><c:out value="${bean.bill_code }"/></td>
							<td class="label">订单类型：</td>
							<td class="field"><dv:display dicKeyword="DIC_BILL_TYPE" value="${bean.bill_type }"/></td>
						</tr>
						<tr>
							<td class="label">店铺：</td>
							<td class="field"><c:out value="${bean.shop_name }"/></td>
							<td class="label">买家：</td>
							<td class="field"><c:out value="${bean.buyer_name }"/></td>
						</tr>
						<tr>
							<td class="label">下单时间：</td>
							<td class="field">${fn:substring(bean.bill_time, 0, 19) }</td>
							<td class="label">商品数量：</td>
							<td class="field"><c:out value="${bean.quantity }"/></td>
						</tr>
						<tr>
							<td class="label">实付金额：</td>
							<td class="field"><c:out value="${bean.real_amount }"/></td>
							<td class="label">订单总金额：</td>
							<td class="field"><c:out value="${bean.total_amount }"/></td>
						</tr>
						<tr>
							<td class="label">使用汀豆：</td>
							<td class="field"><c:out value="${bean.use_coin_quantity }"/></td>
							<td class="label">汀豆抵扣金额：</td>
							<td class="field"><c:out value="${bean.use_coin_amount }"/></td>
						</tr>
						<tr>
							<td class="label">支付方式：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/></td>
							<td class="label">支付金额：</td>
							<td class="field"><c:out value="${bean.pay_amount }"/></td>
						</tr>
						<tr>
							<td class="label">支付回执：</td>
							<td class="field"><c:out value="${bean.pay_code }"/></td>
							<td class="label">支付时间：</td>
							<td class="field">${fn:substring(bean.pay_time, 0, 19) }</td>
						</tr>
						<tr>
							<td class="label">支付状态：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/></td>
							<td class="label">收货人：</td>
							<td class="field"><c:out value="${bean.ship_name }"/></td>
						</tr>
						<tr>
							<td class="label">联系电话：</td>
							<td class="field"><c:out value="${bean.ship_mobile }"/></td>
							<td class="label">收货地址：</td>
							<td class="field"><c:out value="${bean.ship_addr }"/></td>
						</tr>
						<tr>
							<td class="label">订单状态：</td>
							<td class="field"><span class='left_ts'><dv:display dicKeyword="DIC_TRADE_BILL_STATUS" value="${bean.status }"/></span></td>
							<td class="label">备注：</td>
							<td class="field"><c:out value="${bean.remark }"/></td>
						</tr>
					</table>
				</div>
				<DL id="sub_tab">
					<DT>订单明细</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="5%">缩略图</th>
									<th width="40%">商品名称</th>
									<th width="10%">店长零售价</th>
									<th width="10%">数量</th>
									<th width="10%">小计</th>
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
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
