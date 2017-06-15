<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("");
	
	/** 确认支付 */
	function paySubmit_onclick() {
		if (validateForm()) {
			formHelper.jSubmit(formHelper.buildAction("pay/ttpay", "${bean.out_trade_no }"));
		}
	}
	
	$(document).ready(function() {
		
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">支付信息&nbsp;</div>
					<div class="table_title_tip" rel=""></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="pay" value="确认支付" class="button" onclick="paySubmit_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label">订单编号：</td>
						<td class="field"><c:out value="${bean.out_trade_no }"/></td>
						<td class="label">支付金额：</td>
						<td class="field"><c:out value="${bean.total_fee }"/></td>
					</tr>
					<tr>
						<td class="label">支付账号：</td>
						<td class="field">
							<input type="text" name="user_account" class="readonly" inputName="支付账号" value="<c:out value="${bean.user_account }"/>" autocomplete="off" validate=""/>
						</td>
						<td class="label"><span class="required_red">* </span>支付密码：</td>
						<td class="field">
							<input type="password" name="user_password" class="input" inputName="支付密码" value="" autocomplete="off" validate="notNull"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
