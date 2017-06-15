<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
%>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>店长对账数据统计</title>
<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	var formHelper = new DvFormHelper("reportController");
	
	/** 查询 */
	function query_onclick() {
		if (validateForm()){
			formHelper.jSubmit(formHelper.buildAction("reportController", "reportShopkeeperCheckBill"));
		}
	}
	
	/** 批量导出 */
	function export_onclick() {
		formHelper.submit(formHelper.buildAction("reportController", "exportShopkeeperCheckBill"));
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
					<div class="table_title">查询条件&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/trade_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						<td class="label"><span class="required_red">* </span>统计周期（订单日期）：</td>
						<td class="field">
							<input type="text" name="bill_date_from" class="half_input Wdate" value="${bill_date_from }" inputName="起始日期" validate="notNull"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="bill_date_to" class="half_input Wdate" value="${bill_date_to }" inputName="终止日期" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">店铺ID：</td>
						<td class="field">
							<input type="text" name="shop_id" class="input" value="${param.shop_id }" inputName="店铺ID"/>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">统计列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<!-- <input type="button" name="detail" value="导出" class="button" onclick="export_onclick();"/> -->
						<input type="button" name="export" value="下载" class="button" onclick="export_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>店长销售订单</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="5%">城市</th>
									<th width="5%">店铺ID</th>
									<th width="7%">店铺</th>
									<th width="10%">订单号</th>
									<th width="10%">订单日期</th>
									<th width="8%">订单总金额</th>
									<th width="8%">买家实付金额</th>
									<th width="8%">汀豆抵扣金额</th>
									<th width="8%">卖家实收金额</th>
									<th width="10%">支付方式</th>
									<th width="8%">支付状态</th>
									<th width="8%">订单状态</th>
								</tr>
								<c:forEach var="bean" varStatus="status" items="${data.trades }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${bean.city_name }"/></td>
									<td><c:out value="${bean.shop_id }"/></td>
									<td><c:out value="${bean.shop_name }"/></td>
									<td><c:out value="${bean.bill_code }"/></td>
									<td><c:out value="${bean.bill_date }"/></td>
									<td><c:out value="${bean.total_amount }"/></td>
									<td><c:out value="${bean.real_amount }"/></td>
									<td><c:out value="${bean.use_coin_amount }"/></td>
									<td><c:out value="${bean.income_amount }"/></td>
									<td><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/></td>
									<td><dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/></td>
									<td><dv:display dicKeyword="DIC_TRADE_BILL_STATUS" value="${bean.status }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
					<DT>店铺提现记录</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="12%">提现单号</th>
									<th width="12%">申请时间</th>
									<th width="10%">店铺ID</th>
									<th width="10%">店铺</th>
									<th width="10%">店长ID</th>
									<th width="10%">店长</th>
									<th width="10%">提现金额</th>
									<th width="11%">支付时间</th>
									<th width="10%">支付状态</th>
								</tr>
								<c:forEach var="bean" varStatus="status" items="${data.applies }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${bean.bill_code }"/></td>
									<td>${fn:substring(bean.apply_time, 0, 19) }</td>
									<td><c:out value="${bean.store_id }"/></td>
									<td><c:out value="${bean.store_name }"/></td>
									<td><c:out value="${bean.user_id }"/></td>
									<td><c:out value="${bean.user_name }"/></td>
									<td><c:out value="${bean.apply_amount }"/></td>
									<td>${fn:substring(bean.pay_time, 0, 19) }</td>
									<td><dv:display dicKeyword="DIC_FUND_APPLY_PAY_STATUS" value="${bean.pay_status }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
					<DT>客户退款记录</DT>
					<DD>
						<div>
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="13%">退款单号</th>
									<th width="12%">申请时间</th>
									<th width="12%">关联订单号</th>
									<th width="5%">店铺ID</th>
									<th width="10%">店铺</th>
									<th width="5%">买家ID</th>
									<th width="10%">买家姓名</th>
									<th width="8%">退款金额</th>
									<th width="12%">退款时间</th>
									<th width="8%">退款状态</th>
								</tr>
								<c:forEach var="bean" varStatus="status" items="${data.refunds }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${bean.bill_code }"/></td>
									<td>${fn:substring(bean.refund_time, 0, 19) }</td>
									<td><c:out value="${bean.refer_bill_code }"/></td>
									<td><c:out value="${bean.seller_id }"/></td>
									<td><c:out value="${bean.seller_name }"/></td>
									<td><c:out value="${bean.buyer_id }"/></td>
									<td><c:out value="${bean.buyer_name }"/></td>
									<td><c:out value="${bean.refund_amount }"/></td>
									<td>${fn:substring(bean.pay_time, 0, 19) }</td>
									<td><dv:display dicKeyword="DIC_REFUND_PAY_STATUS" value="${bean.pay_status }"/></td>
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
