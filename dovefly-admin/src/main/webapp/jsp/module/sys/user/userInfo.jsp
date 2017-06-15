<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.alipay.config.AlipayConfig" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var g_wechat_account = "${bean.wechat_account }";
	var g_alipay_account = "${bean.alipay_account }";
	var g_balance_amount = new Number("${bean.store.balance_amount }");
	var g_apply_amount_validate = "notNull;isNumber;gte('0');lte('${bean.store.balance_amount }')";

	var formHelper = new DvFormHelper("userController");
	
	/** 修改个人信息 */
	function changeUser_onclick() {
		if (validateForm("form_user")) {
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/userController/change",
				data: $("#form_user").serialize(),
				success: function(result){
					if(result){//存在
						if(result.success){
							dvTip(result.message, "success");
							g_alipay_account = $("#alipay_account").val();
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
		}
	}
	
	/** 修改货仓信息 */
	function changeWarehouseInfo_onclick() {
		if (validateForm("form_warehouse")) {
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/warehouseController/changeWarehouseInfo",
				data: $("#form_warehouse").serialize(),
				success: function(result){
					if(result){//存在
						if(result.success){
							dvTip(result.message, "success");
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
		}
	}
	
	/** 修改店铺信息 */
	function changeShopInfo_onclick() {
		if (validateForm("form_shop")) {
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/shopController/changeShopInfo",
				data: $("#form_shop").serialize(),
				success: function(result){
					if(result){//存在
						if(result.success){
							dvTip(result.message, "success");
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
		}
	}
	
	/** 修改超市信息 */
	function changeMarketInfo_onclick() {
		if (validateForm("form_market")) {
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/marketController/changeMarketInfo",
				data: $("#form_market").serialize(),
				success: function(result){
					if(result){//存在
						if(result.success){
							dvTip(result.message, "success");
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
		}
	}
	
	/** 提现 */
	function withdraw_onclick() {
		
		if (g_balance_amount <= 0) {
			dvAlert("可用余额不足，无法提现！"); 
			return false;
		}
		
		if (g_wechat_account == '' && g_alipay_account == '') {
			dvAlert("目前仅支持提现到预先绑定的微信钱包或支付宝，<br/>检测到您暂未绑定支付宝或微信钱包的任何一种方式，<br/>请先完成绑定后再申请提现！"); 
			return false;
		}
		
		dvPrompt(window.withdrawDivHtml, '提现申请', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts">目前仅支持提现到预先绑定到账号的微信钱包或支付宝！</span>',
			loaded: function (h) {
				$("#td_wechat_account").html(g_wechat_account);
				$("#td_alipay_account").html(g_alipay_account);
				/*
				if (g_wechat_account == '') {
					$("input[name='apply_to'][value='weixin']").attr("disabled", "disabled");
				}
				if (g_alipay_account == '') {
					$("input[name='apply_to'][value='alipay']").attr("disabled", "disabled");
				}
				*/
				$("#apply_amount").attr("validate", g_apply_amount_validate);
			},
			submit: function(v, h, f) {
				if (validateForm("withdraw_insert_table")) {
					$.ajax({
						type: "POST",
						async: true,
						dataType: "json",
						url: context_path + "/applyController/add",
						data: {
							"apply_to":$("input[name='apply_to']:checked").val(),
							"apply_amount":$("input[name='apply_amount']").val(),
							"receive_account":$("input[name='receive_account']").val(),
							"receive_real_name":$("input[name='receive_real_name']").val()
						},
						success: function(result){
							if(result){//存在
								if(result.success){
									var user = result.data;
									$("#span_balance_amount").html(new Number(user.store.balance_amount).toFixed(2));
									$("#span_locked_amount").html(new Number(user.store.locked_amount).toFixed(2));
									g_apply_amount_validate = "notNull;isNumber;gte('1');lte('" + user.store.balance_amount + "')";
									g_balance_amount = new Number(user.store.balance_amount);
									dvTip(result.message, "success");
								} else {
									dvTip(result.message, "error");
								}
							}
						}
					});
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	/** 充值 */
	function recharge_onclick() {
		dvPrompt(window.rechargeDivHtml, '充值', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts">目前仅支持微信钱包或支付宝充值！</span>',
			submit: function(v, h, f) {
				if (validateForm("recharge_insert_table")) {
					var params = "pay_type=" + $("input[name='pay_type']:checked").val() + "&recharge_amount=" + $("#recharge_amount").val();
					window.location.href = context_path + "/rechargeController/insert?" + params;
					return true;
				}
				return false;
			}
		});
	}
	
	/** 我的交易明细 */
	function transition_list_onclick() {
		dvOpenDialog(context_path + "/transitionController/queryAll", "我的交易明细", 900, 400, null);
	}
	
	/** 我的充值记录 */
	function recharge_list_onclick() {
		dvOpenDialog(context_path + "/rechargeController/queryAll", "我的充值记录", 900, 400, null);
	}
	
	/** 我的提现记录 */
	function withdraw_list_onclick() {
		dvOpenDialog(context_path + "/applyController/queryAll", "我的提现记录", 900, 400, null);
	}
	
	/** 修改密码 */
	function changePwd_onclick() {
		if (validateForm("form_pwd")) {
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/userController/resetPwd",
				data: $("#form_pwd").serialize(),
				success: function(result){
					if(result){//存在
						if(result.success){
							dvTip(result.message, "success");
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
		}
	}
	
	function changeWorkStatus_onclick() {
		var work_status = $("#work_status_img").attr("work_status");
		work_status = (work_status == 'w' ? 's' : 'w');
		$.ajax({
			type : "POST",
			async : true,
			dataType : "json",
			url : context_path + "/marketController/changeWorkStatus",
			data : {
				"work_status" : work_status
			},
			success : function(result) {
				if (result) {//存在
					if (result.success) {
						dvTip(result.message, "success");
						if (work_status == 'w') {
							$("#user_work_status").html('本店营业啦！');
							$("#work_status_img").attr("work_status", work_status).attr("src", "<%=request.getContextPath() %>/resources/images/open.jpg");
						} else if (work_status == 's') {
							$("#user_work_status").html('本店打烊啦！');
							$("#work_status_img").attr("work_status", work_status).attr("src", "<%=request.getContextPath() %>/resources/images/closed.jpg");
						}
					} else {
						dvTip(result.message, "error");
					}
				}
			}
		});
	}
	
	$(document).ready(function() {
		
		window.withdrawDivHtml = $("#withdrawDiv").html();
		$("#withdrawDiv").html("");
		
		window.rechargeDivHtml = $("#rechargeDiv").html();
		$("#rechargeDiv").html("");
		
		if ('${bean.headquartersRole }' == 'true') {//总部管理员
			$('#user_qrcode').load(context_path + "/userController/qrcode/shop");
		} else if ('${bean.cityManagerRole }' == 'true') {//用户是城市经理
			$('#user_qrcode').load(context_path + "/userController/qrcode/shop");
		} else if ('${bean.shopkeeperRole }' == 'true') {//用户是店长
			$('#user_qrcode').load(context_path + "/userController/qrcode/shop");
		} else if ('${bean.operationManagerRole }' == 'true') {//运营主管
			$('#user_qrcode').load(context_path + "/userController/qrcode/manager");
		}
		
	});
	
</script>
</head>
<body>
	<div style="height: 4px"></div>
	<div class="border_div" >
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">个人信息&nbsp;</div>
				<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_user"></div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="find" value="保存个人信息" class="button" onclick="changeUser_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<form id="form_user" name="form_user" method="post">
				<table class="insert_table">
					<tr>
						<td class="label">微信：</td>
						<td class="field">
							（<span id="span_wechat_account"><c:out value="${bean.wechat_account }"/></span>）
							<img id="img_wechat_avatar" src="${bean.wechat_avatar }" style="width:50px;height:50px;vertical-align: middle;"/>
						</td>
						<td class="label">手机号码：</td>
						<td class="field"><c:out value="${bean.mobile }"/></td>
					</tr>
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
						<td class="label">学校：</td>
						<td class="field"><c:out value="${bean.school_name }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>真实姓名：</td>
						<td class="field">
							<input type="text" name="real_name" class="input" inputName="真实姓名" value="<c:out value="${bean.real_name }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="label">支付宝账号：</td>
						<td class="field">
							<input type="text" id="alipay_account" name="alipay_account" class="input" inputName="支付宝账号" value="<c:out value="${bean.alipay_account }"/>" maxlength="50" validate="notNull"/>
							<a href="#alipay_guide" title="什么是支付宝账号？"><img align="middle" src="<%=request.getContextPath() %>/resources/images/help-blue20.png"/></a>
						</td>
					</tr>
					<tr>
						<td class="label">邮箱：</td>
						<td class="field">
							<input type="text" name="email" class="input" inputName="邮箱" value="<c:out value="${bean.email }"/>" maxlength="50" validate="isEmail"/>
						</td>
						<td class="label"><span class="required_red">* </span>联系地址：</td>
						<td class="field">
							<input type="text" name="address" class="input" inputName="联系地址" value="<c:out value="${bean.address }"/>" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">汀豆：</td>
						<td class="field"><c:out value="${bean.coin_quantity }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
			</form>
		</div>
	</div>
	<div class="space_h15_div"></div>
	<div class="border_div" >
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">修改密码&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="find" value="保存新密码" class="button" onclick="changePwd_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<form id="form_pwd" name="form_pwd" method="post">
				<table class="insert_table">
					<tr>
						<td class="label">账号：</td>
						<td class="field">
							<c:out value="${bean.account }"/>
						</td>
						<td class="label"><span class="required_red">* </span>原密码：</td>
						<td class="field">
							<input type="password" name="old_password" class="input" inputName="密码" value="" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>新密码：</td>
						<td class="field">
							<input type="password" name="password" class="input" inputName="新密码" value="" maxlength="50" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>确认新密码：</td>
						<td class="field">
							<input type="password" name="confirm_password" class="input" inputName="确认新密码" value="" maxlength="50" validate="notNull;equalsTo('password')"/>
						</td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
				<input type="hidden" name="account" value="<c:out value="${bean.account }"/>"/>
			</form>
		</div>
	</div>
	<c:if test="${bean.cityManagerRole }">
	<div class="space_h15_div"></div>
	<div class="border_div" >
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">我的仓库&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="find" value="保存仓库信息" class="button" onclick="changeWarehouseInfo_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<form id="form_warehouse" name="form_warehouse" method="post">
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>货仓：</td>
						<td class="field">
							<input type="text" name="warehouse_name" class="input" inputName="货仓" value="<c:out value="${bean.warehouse.warehouse_name }"/>" maxlength="10" validate="notNull"/>
						</td>
						<td class="label">货仓描述：</td>
						<td class="field">
							<input type="text" name="warehouse_desc" class="input" inputName="货仓描述" value="<c:out value="${bean.warehouse.warehouse_desc }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">配送区域：</td>
						<td class="field">
							<input type="text" name="warehouse_area" class="input" inputName="配送区域" value="<c:out value="${bean.warehouse.warehouse_area }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label"><span class="required_red">* </span>起送价：</td>
						<td class="field">
							<input type="text" name="start_amount" class="input" inputName="起送价" value="<c:out value="${bean.warehouse.start_amount }"/>" maxlength="50" validate="notNull;isPositive"/>
						</td>
					</tr>
					<tr>
						<td class="label">账户金额：</td>
						<td class="field">
							<span style="color:red;font-size:24px;font-weight:bold;" id="span_balance_amount"><c:out value="${bean.warehouse.balance_amount }"/></span>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="transition_list_onclick();">我的账单记录</a>
						</td>
						<td class="label"></td>
						<td class="field">
							<input type="button" name="recharge" value="我要充值" class="button" onclick="recharge_onclick();"/>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="recharge_list_onclick();">我的充值记录</a>
						</td>
					</tr>
					<tr>
						<td class="label">待收款金额：</td>
						<td class="field">
							<span style="color:red;font-size:24px;font-weight:bold;" id="span_halfway_amount"><c:out value="${bean.warehouse.halfway_amount }"/></span>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label">提现中金额：</td>
						<td class="field">
							<span style="color:red;font-size:24px;font-weight:bold;" id="span_locked_amount"><c:out value="${bean.warehouse.locked_amount }"/></span>
						</td>
						<td class="label"></td>
						<td class="field">
							<input type="button" name="withdraw" value="我要提现" class="button" onclick="withdraw_onclick();"/>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="withdraw_list_onclick();">我的提现记录</a>
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field"></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="field" colspan="4">
							<fieldset style="padding:10px;margin:10px;color:#333;border:#06c dashed 1px;line-height:25px;">
								<legend style="color:#06c;background:#fff;">注意：</legend>
								<span class="left_ts">支付宝提现一定要填写您自己正确的真实姓名和支付宝账号</span><br/>
								目前提现仅支持提现到预先绑定到账号的微信钱包或支付宝。<br/>
								所以，在申请提现之前，请务必先绑定您自己的支付宝和微信钱包，<br/>
								如果已经绑定这些信息，也请您务必再次检查是否是您自己的支付宝账号或微信账号<br/>
								
								微信绑定流程：<br/>
									1、鼠标点击本页面上的“扫码绑定”超链接，将显示“小鹿汀汀”公众号的微信二维码。<br/>
									2、登录手机微信，通过“扫一扫”功能，对此二维码进行扫码，如果此时本页面已成功回显您的微信头像及昵称，即表示已绑定成功<br/>
									3、如果微信更换，或绑定错误，可重新扫码进行绑定<br/>
									<a name="wxpay_guide"><span class="left_ts">微信提现时如何知道自己的微信账号的真实姓名？？</span></a>，请参考手机微信截图：<br/>
									<img width="100%" height="100%" src="<%=request.getContextPath() %>/jsp/module/sys/user/wxpay_guide.png"/>
								<br/>
								支付宝绑定流程：<br/>
									输入支付宝账号，点击保存即可。<br/>
									<a name="alipay_guide"><span class="left_ts">什么是支付宝账号？</span></a>，支付宝账号可能是您的邮箱账号，也可能是您的手机号，具体可参考手机支付宝截图：<br/>
									<img width="100%" height="100%" src="<%=request.getContextPath() %>/jsp/module/sys/user/alipay_guide.png"/>
							</fieldset>
						</td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.manage_warehouse_id }"/>"/>
			</form>
		</div>
	</div>
	</c:if>
	<c:if test="${bean.shopkeeperRole }">
	<div class="space_h15_div"></div>
	<div class="border_div" >
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">我的店铺&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="find" value="保存店铺信息" class="button" onclick="changeShopInfo_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<form id="form_shop" name="form_shop" method="post">
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>我的店名：</td>
						<td class="field">
							<input type="text" name="shop_name" class="input" inputName="我的店名" value="<c:out value="${bean.shop.shop_name }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="field" rowspan="4">
							<img id="work_status_img" style="cursor: pointer;" work_status="${bean.shop.shop_status }" title="点击图标，切换营业状态" src="<%=request.getContextPath() %>/resources/images/${bean.shop.shop_status == 'w' ? 'open.jpg' : 'closed.jpg'}" onclick="changeWorkStatus_onclick();"/><br>
							<span id="user_work_status" style="padding-left:50px;" class="left_ts"><dv:display dicKeyword="DIC_SHOP_WORK_STATUS" value="${bean.shop.shop_status }"/></span>
						</td>
						<td class="label" rowspan="4">
							<span style="line-height:20px;padding-left:30px;">微信扫一扫，关注本店！</span><br>
							<div id="user_qrcode"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>店铺描述：</td>
						<td class="field">
							<input type="text" name="shop_desc" class="input" inputName="店铺描述" value="<c:out value="${bean.shop.shop_desc }"/>" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>送货范围：</td>
						<td class="field">
							<input type="text" name="shop_area" class="input" inputName="送货范围" value="<c:out value="${bean.shop.shop_area }"/>" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>起送价：</td>
						<td class="field">
							<input type="text" name="start_amount" class="input" inputName="起送价" value="<c:out value="${bean.shop.start_amount }"/>" maxlength="50" validate="notNull;isPositive"/>
						</td>
					</tr>
					<tr>
						<td class="label">店铺余额：</td>
						<td class="field">
							<span style="color:red;font-size:24px;font-weight:bold;" id="span_balance_amount"><c:out value="${bean.shop.balance_amount }"/></span>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="transition_list_onclick();">我的账单记录</a>
						</td>
						<td class="label"></td>
						<td class="field">
							<input type="button" name="recharge" value="我要充值" class="button" onclick="recharge_onclick();"/>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="recharge_list_onclick();">我的充值记录</a>
						</td>
					</tr>
					<tr>
						<td class="label">待收款金额：</td>
						<td class="field">
							<span style="color:red;font-size:24px;font-weight:bold;" id="span_halfway_amount"><c:out value="${bean.shop.halfway_amount }"/></span>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label">提现中金额：</td>
						<td class="field">
							<span style="color:red;font-size:24px;font-weight:bold;" id="span_locked_amount"><c:out value="${bean.shop.locked_amount }"/></span>
						</td>
						<td class="label"></td>
						<td class="field">
							<input type="button" name="withdraw" value="我要提现" class="button" onclick="withdraw_onclick();"/>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="withdraw_list_onclick();">我的提现记录</a>
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field"></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.manage_shop_id }"/>"/>
			</form>
		</div>
	</div>
	</c:if>
	<c:if test="${bean.marketSellerRole }">
	<div class="space_h15_div"></div>
	<div class="border_div" >
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">我的超市&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="find" value="保存超市信息" class="button" onclick="changeMarketInfo_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<form id="form_market" name="form_market" method="post">
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>我的超市：</td>
						<td class="field">
							<input type="text" name="market_name" class="input" inputName="我的超市" value="<c:out value="${bean.market.market_name }"/>" maxlength="50" validate="notNull"/>
						</td>
						<td class="field" rowspan="4">
							<img id="work_status_img" style="cursor: pointer;" work_status="${bean.market.market_status }" title="点击图标，切换营业状态" src="<%=request.getContextPath() %>/resources/images/${bean.market.market_status == 'w' ? 'open.jpg' : 'closed.jpg'}" onclick="changeWorkStatus_onclick();"/><br>
							<span id="user_work_status" style="padding-left:50px;" class="left_ts"><dv:display dicKeyword="DIC_SHOP_WORK_STATUS" value="${bean.market.market_status }"/></span>
						</td>
						<td class="label" rowspan="4">
							<span style="line-height:20px;padding-left:30px;">微信扫一扫，关注本店！</span><br>
							<div id="user_qrcode"></div>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>超市描述：</td>
						<td class="field">
							<input type="text" name="market_desc" class="input" inputName="超市描述" value="<c:out value="${bean.market.market_desc }"/>" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>送货范围：</td>
						<td class="field">
							<input type="text" name="market_area" class="input" inputName="送货范围" value="<c:out value="${bean.market.market_area }"/>" maxlength="50" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>起送价：</td>
						<td class="field">
							<input type="text" name="start_amount" class="input" inputName="起送价" value="<c:out value="${bean.market.start_amount }"/>" maxlength="50" validate="notNull;isPositive"/>
						</td>
					</tr>
				</table>
				<input type="hidden" name="id" value="<c:out value="${bean.manage_market_id }"/>"/>
			</form>
		</div>
	</div>
	</c:if>
	<div id="withdrawDiv" style="display:none">
		<table id="withdraw_insert_table" class="insert_table">
			<tr>
				<td class="label">已绑定支付宝：</td>
				<td class="field" id="td_alipay_account"><c:out value="${bean.alipay_account }"/></td>
			</tr>
			<tr>
				<td class="label">已绑定微信：</td>
				<td class="field" id="td_wechat_account"><c:out value="${bean.wechat_account }"/></td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>提现到：</td>
				<td class="field"><dv:radio name="apply_to" dicKeyword="DIC_PAY_TYPE" ignoreValues="online,cod,ttpay" attributes="inputName='提现渠道' validate='notNull'"/></td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>支付宝账号：</td>
				<td class="field"><input type="text" id="receive_account" name="receive_account" class="input" inputName="支付宝账号" value="<c:out value="${bean.alipay_account }"/>" maxlength="50" validate="notNull"/></td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>微信或支付宝账号真实姓名：</td>
				<td class="field"><input type="text" id="receive_real_name" name="receive_real_name" class="input" inputName="真实姓名" value="<c:out value="${bean.real_name }"/>" maxlength="20" validate="notNull"/></td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>提现金额：</td>
				<td class="field"><input type="text" id="apply_amount" name="apply_amount" class="input" inputName="提现金额" value="" maxlength="50" validate="notNull;isNumber;gte('1');lte('${bean.store.balance_amount }')"/></td>
			</tr>
			<tr>
				<td class="field" colspan="2"><span class="left_ts">“微信或支付宝账号真实姓名”是您在注册微信或支付宝账号时填写的您的真实姓名（非昵称），<br>请务必要填写一致，否则将会导致转账失败！</span></td>
			</tr>
		</table>
	</div>
	<div id="rechargeDiv" style="display:none">
		<table id="recharge_insert_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>充值账号：</td>
				<td class="field"><c:out value="${bean.account }"/></td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>支付方式：</td>
				<td class="field"><dv:radio dicKeyword="DIC_PAY_TYPE" name="pay_type" ignoreValues="online,cod,ttpay" attributes="inputName='支付方式' validate='notNull'"/></td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>充值金额：</td>
				<td class="field"><input type="text" id="recharge_amount" name="recharge_amount" class="input" inputName="充值金额" value="" maxlength="50" validate="notNull;isNumber"/></td>
			</tr>
			<tr>
				<td class="label"></td>
				<td class="field"></td>
			</tr>
		</table>
	</div>
</body>
</html>
