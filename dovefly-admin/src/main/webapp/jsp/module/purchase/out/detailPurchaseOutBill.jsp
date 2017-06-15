<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出库单信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("purchaseOutBillController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 确认出库 */
	function checkout_onclick() {
		dvConfirm("确人出库后，出库清单内所有商品的数量将自动从货仓库存扣除，且不可撤销！<br><br>您确认要出库吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("purchaseOutBillController", "checkout", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 撤回 */
	function turnback_onclick() {
		dvConfirm("确认撤回此出库单后，清单内所有商品将自动返回此货仓的商品库存中！<br><br>您确认要撤回吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("purchaseOutBillController", "turnback", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 打印*/
	function print_onclick() {
		var options = {
			buttons : {
				'打印' : 'ok',
				'关闭' : false
			}
		};
		dvOpenDialog(context_path + '/purchaseOutBillController/print/${bean.id }', '订单打印', 310, 450, options);
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
					<div class="table_title">出库单信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/purchase_out_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${sessionScope.DV_USER_VO.cityManagerRole}">
						<c:if test="${bean.status == '0' }">
						<input type="button" name="checkout" value="确认出库" class="button" onclick="checkout_onclick();"/>
						</c:if>
						<c:if test="${bean.status == '1' }">
						<input type="button" name="turnback" value="撤回" class="button" onclick="turnback_onclick();"/>
						</c:if>
						</c:if>
					    <input type="button" name="print" value="打印" class="button" onclick="print_onclick();" />
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
						<td class="label">货仓：</td>
						<td class="field"><c:out value="${bean.warehouse_name }"/></td>
					</tr>
					<tr>
						<td class="label">出库单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">订单日期：</td>
						<td class="field">${fn:substring(bean.bill_date, 0, 10) }</td>
					</tr>
					<tr>
						<td class="label">订单时间：</td>
						<td class="field">${fn:substring(bean.bill_time, 0, 19) }</td>
						<td class="label">订单金额：</td>
						<td class="field"><c:out value="${bean.amount }"/></td>
					</tr>
					<tr>
						<td class="label">出库状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_PURCHASE_OUT_BILL_STATUS" value="${bean.status }"/></td>
						<td class="label">出库说明：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
						<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>订单明细</DT>
					<DD>
						<div>
							<table id="detail_list_table" class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="5%">缩略图</th>
									<th width="40%">商品</th>
									<th width="20%">单价（以零售单位计）</th>
									<th width="20%">数量（以零售单位计）</th>
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
						<div>
							<div style="display:inline;float:left;">
							</div>
							<div style="display:inline;float:right;">
								<span style="font-weight:bold; line-height: 30px; padding-right: 20px;">合计：${bean.amount }（元）</span>
							</div>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
