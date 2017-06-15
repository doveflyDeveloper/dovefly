<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付申请信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("applyController");
	
	/** 支付处理 */
	function pay_onclick() {
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: context_path + "/applyController/checkAccount/${bean.id }",
			data: {},
			success: function(result){
				if(result.success){
					dvConfirm(result.message, 
						function() {
							formHelper.jSubmit(formHelper.buildAction("applyController", "deal", "${bean.id }"));
						}, 
						function() {
							//alert("干嘛要取消啊？");
						}
					);
				} else {
					dvAlert(result.message);
				}
			}
		});
	}
	
	/** 拒绝 */
	function deny_onclick() {
		dvPrompt(window.denyDivHtml, '填写拒绝信息', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts"></span>',
			submit: function(v, h, f) {
				if (!validateForm("deny_table")) {return false;}
				$("#reason").val(f['deny_reason']);
				formHelper.jSubmit(formHelper.buildAction("applyController", "deny", "${bean.id }"));
				return true;
			}
		});
	}
	
	/** 手动处理 */
	function manual_deal_onclick(id){
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: context_path + "/applyController/checkAccount/${bean.id }",
			data: {},
			success: function(result){
				if(result.success){
					dvConfirm(result.message, 
						function() {
							dvPrompt(window.denyDivHtml, '填写支付信息', {
								top: '5%',
								width: 700,
								bottomText: '<span class="left_ts"></span>',
								submit: function(v, h, f) {
									if (!validateForm("manual_deal_table")) {return false;}
									$("#payCode").val(f['pay_code']);
									formHelper.jSubmit(formHelper.buildAction("applyController", "manualDeal", "${bean.id }"));
									return true;
								}
							});
						}, 
						function() {
							//alert("干嘛要取消啊？");
						}
					);
				} else {
					dvAlert(result.message);
				}
			}
		});
	}
	
	$(document).ready(function() {
		window.denyDivHtml = $("#denyDiv").html();
		$("#denyDiv").html("");
		
		window.manualDealDivHtml = $("#manualDealDiv").html();
		$("#manualDealDiv").html("");
	});

</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">支付申请信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/fund_apply"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.status == '0'}">
						<!-- <input type="button" name="deal" value="手动处理" class="button" onclick="manual_deal_onclick();"/> -->
						<input type="button" name="pay" value="支付" class="button" onclick="pay_onclick();"/>
						<input type="button" name="deny" value="拒绝" class="button" onclick="deny_onclick();"/>
						</c:if>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">申请单号：</td>
						<td class="field"><c:out value="${bean.bill_code }"/></td>
						<td class="label">申请用户：</td>
						<td class="field"><c:out value="${bean.user_name }"/></td>
					</tr>
					<tr>
						<td class="label">申请时间：</td>
						<td class="field">${fn:substring(bean.apply_time, 0, 19) }</td>
						<td class="label">提现金额：</td>
						<td class="field"><c:out value="${bean.apply_amount }"/>（元）</td>
					</tr>
					<tr>
						<td class="label">提现渠道：</td>
						<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.apply_to }"/></td>
						<td class="label">交易概述：</td>
						<td class="field"><c:out value="${bean.brief }"/></td>
					</tr>
					<tr>
						<td class="label">收款人姓名：</td>
						<td class="field"><c:out value="${bean.receive_real_name }"/></td>
						<td class="label">收款账号：</td>
						<td class="field"><c:out value="${bean.receive_account }"/></td>
					</tr>
					<tr>
						<td class="label">支付交易码：</td>
						<td class="field"><c:out value="${bean.pay_code }"/></td>
						<td class="label">支付时间：</td>
						<td class="field">${fn:substring(bean.pay_time, 0, 19) }</td>
					</tr>
					<tr>
						<td class="label">支付状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_FUND_APPLY_PAY_STATUS" value="${bean.pay_status }"/></td>
						<td class="label">支付信息：</td>
						<td class="field"><c:out value="${bean.pay_msg }"/></td>
					</tr>
					<tr>
						<td class="label">处理状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_FUND_APPLY_STATUS" value="${bean.status }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
				<input type="hidden" id="reason" name="reason" value=""/>
				<input type="hidden" id="payCode" name="payCode" value=""/>
			</div>
		</div>
	</form>
	<div id="denyDiv" style="display:none;">
		<table id="deny_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>拒绝原因：</td>
				<td class="field">
					<textarea id="deny_reason" name="deny_reason" class="textarea" inputName="拒绝原因" validate="notNull"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="manualDealDiv" style="display:none;">
		<table id="manual_deal_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>支付单号：</td>
				<td class="field">
					<input id="pay_code" name="pay_code" class="input" inputName="支付单号" validate="notNull" />
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
