<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购订单信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("purchaseBillController");
	
	/** 确认入库 */
	function checkin_onclick() {
		dvConfirm("确认入库后，采购清单内所有商品将自动计入采购人的商品库存！<br><br>您确认要入库吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("purchaseBillController", "checkin", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 撤回 */
	function turnback_onclick() {
		dvConfirm("确认撤回此入库单后，清单内所有商品将自动从采购人的商品库存中扣除！<br><br>您确认要撤回吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("purchaseBillController", "turnback", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	/** 打印*/
	function print_onclick() {
		var options = {
			buttons : {
				'打印' : 'ok',
				'关闭' : false
			}
		};
		dvOpenDialog(context_path + '/purchaseBillController/print/${bean.id }', '订单打印', 320, 450, options);
	}
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">采购订单信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/purchase_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					    <input type="button" name="print" value="打印" class="button" onclick="print_onclick();" />
						<c:if test="${sessionScope.DV_USER_VO.cityManagerRole}">
						<c:if test="${bean.status == '0' }">
						<input type="button" name="checkin" value="确认入库" class="button" onclick="checkin_onclick();"/>
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						</c:if>
						<c:if test="${bean.status == '1' }">
						<input type="button" name="turnback" value="撤回" class="button" onclick="turnback_onclick();"/>
						</c:if>
						</c:if>
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
						<td class="label">供应商：</td>
						<td class="field"><c:out value="${bean.supplier_name }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label">订单编号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">订单日期：</td>
						<td class="field">${fn:substring(bean.bill_date, 0, 10) }</td>
					</tr>
					<tr>
						<td class="label">订单金额：</td>
						<td class="field"><c:out value="${bean.amount }"/></td>
						<td class="label">入库状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_PURCHASE_BILL_STATUS" value="${bean.status }"/></td>
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
									<th width="20%">商品</th>
                                    <th width="15%">单价<br>（以零售单位计）</th>
                                    <th width="15%">数量<br>（以零售单位计）</th>
                                    <th width="15%">包装规格<br>（单位包装包含的零售数）</th>
									<th width="15%">大包装数量</th>
									<th width="10%">小计</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>${status.count }</td>
									<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
									<td><c:out value="${detail.goods_name }"/></td>
									<td><c:out value="${detail.unit_price }"/></td>
									<td><c:out value="${detail.quantity }"/></td>
                                    <td><c:out value="${detail.spec }"/></td>
									<td><c:out value="${detail.spec_quantity }"/></td>
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
