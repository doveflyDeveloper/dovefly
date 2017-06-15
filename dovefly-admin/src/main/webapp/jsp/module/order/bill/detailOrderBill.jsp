<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.order.bill.vo.OrderBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单信息</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery.printPage.js"></script>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("orderBillController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	function dealNext_onclick() {
		if ("${nextBean.id }" == "") {
			dvAlert("已经没有下一笔要处理了！");
			return false;
		}
		formHelper.jSubmit(formHelper.buildDetailAction("${nextBean.id }"));
	}
	
	/** 支付 */
	function pay_onclick(pay_type) {
		if (pay_type == '<%=OrderBillVo.PAY_TYPE_TTPAY %>') {
			url = context_path + "/pay/toTtpay/${bean.bill_code }";
		} else if (pay_type == '<%=OrderBillVo.PAY_TYPE_ALIPAY %>') {
			url = context_path + "/pay/toAlipay/${bean.bill_code }";
		} else if (pay_type == '<%=OrderBillVo.PAY_TYPE_WXPAY %>') {
			url= context_path + "/pay/toWxpay/${bean.bill_code }";
		}
		    window.location.href = url;
	}
	
	/** 发货 */
	function send_onclick() {
		if(!("${bean.status }" == "<%=OrderBillVo.STATUS_SUBMIT %>" || "${bean.status }" == "<%=OrderBillVo.STATUS_APPLY_FOR_REFUND %>")){
			dvAlert("只有<span class='left_ts'>未发货</span>或<span class='left_ts'>申请退款</span>状态的进货订单才可以发货！");
	  		return false;
		}
		
		dvConfirm("您确定要发货吗？", 
			function() {
			/*
				if(validateForm("form")){//校验发货数量等信息
					dvPrompt(window.sendDivHtml, '填写发货信息', {
						top: '5%',
						width: 700,
						bottomText: '<span class="left_ts">如有发快递包裹，请务必认真填写物流信息！</span>',
						submit: function(v, h, f) {
							if ($("input[name='need_express']:checked").val() == "2") {
								if (!validateForm("exp_insert_table")) {return false;}
								var inputs = [
									'<input type="hidden" name="need_express" defaultValue="' + f['need_express'] + '"/>',
									'<input type="hidden" name="express.exp_company" value="' + f['express.exp_company'] + '"/>',
									'<input type="hidden" name="express.exp_tracking_no" value="' + f['express.exp_tracking_no'] + '"/>',
									'<input type="hidden" name="express.exp_date"   value="' + f['express.exp_date'] + '"/>',
									'<input type="hidden" name="express.exp_amount" value="' + f['express.exp_amount'] + '"/>',
									'<input type="hidden" name="express.sender_name" value="' + f['express.sender_name'] + '"/>',
									'<input type="hidden" name="express.sender_mobile" value="' + f['express.sender_mobile'] + '"/>',
									'<input type="hidden" name="express.sender_address" value="' + f['express.sender_address'] + '"/>',
									'<input type="hidden" name="express.receiver_name" value="' + f['express.receiver_name'] + '"/>',
									'<input type="hidden" name="express.receiver_mobile" value="' + f['express.receiver_mobile'] + '"/>',
									'<input type="hidden" name="express.receiver_address" value="' + f['express.receiver_address'] + '"/>',
									'<input type="hidden" name="express.remark" value="' + f['express.remark'] + '"/>'
								];
                                $("#sendInputDiv").html(inputs.join(""));
							}
							//formHelper.jSubmit(context_path + "/orderBillController/send/${bean.id}");
							return true;
						}
					});
				}
			*/
				formHelper.jSubmit(context_path + "/orderBillController/send/${bean.id}");
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 发货/打印 */
	function sendAndPrint_onclick() {
		
		if(!("${bean.status }" == "<%=OrderBillVo.STATUS_SUBMIT %>" || "${bean.status }" == "<%=OrderBillVo.STATUS_APPLY_FOR_REFUND %>")){
			dvAlert("只有<span class='left_ts'>未发货</span>或<span class='left_ts'>申请退款</span>状态的进货订单才可以发货！");
	  		return false;
		}
		
		var options = {
			buttons : {
				'发货' : 'send',
				'打印' : 'ok',
				'关闭' : false
			},
			submit: function (v, h, f) {
				/* 点击窗口按钮后的回调函数，返回true时表示关闭窗口，参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
			    if (v == 'ok') {
			    	var ifr = h.find("iframe").get(0);
			    	var win = ifr.window || ifr.contentWindow;
			    	if (win.ok_onclick) {
			    		return win.ok_onclick();
			    	}
			    } else if (v == 'send') {
			    	send_onclick();
                    return false;
			    }
				return true; 
			}
		};
		
		dvOpenDialog(context_path + '/orderBillController/print/${bean.id }', '订单打印', 320, 450, options);
	}
	
	//确认收货
	function receive_onclick() {
		dvConfirm("确认收货前，请务必确认您收到的商品品类及数量正确。您现在要确认收货吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("orderBillController", "receive", "${bean.id }"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 同意退款 */
	function agreeRefund_onclick() {
		dvConfirm('<br>确认退歀操作后，退款将立即退至店长汀汀账户（不再原路退回），<br>您确定要取消此订单并立即退款吗？', 
			function() {
				formHelper.jSubmit(context_path + "/orderBillController/agreeRefund/${bean.id}");
			},
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 同意退货 */
	function agreeReturnGoods_onclick() {
		dvConfirm('此操作前请务必保证您确实已经收到被退回的商品，<br>确认退歀操作后，退款将立即退至店长汀汀账户（不再原路退回），<br>您确定要取消此订单并立即退款吗？', 
			function() {
				formHelper.jSubmit(context_path + "/orderBillController/agreeReturnGoods/${bean.id}");
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
				formHelper.jSubmit(context_path + "/orderBillController/denyReturnGoods/${bean.id}");
			},
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 打印 */
	function print_onclick() {
		//$.printPage(formHelper.buildAction("orderBillController", "print", "${bean.id }"));
		var options = {
			buttons : {
				'打印' : 'ok',
				'关闭' : false
			}
		};
		dvOpenDialog(context_path + '/orderBillController/print/${bean.id }', '订单打印', 320, 450, options);
	}
	
	$(document).ready(function() {
		
		/* $("input[name='need_express'").live("change", function(){
			if ("1" == this.value) {
				$("#exp_insert_table").show();
			} else {
				$("#exp_insert_table").hide();
			}
		}); */
		
        //window.sendDivHtml = $("#sendDiv").html();
        //$("#sendDiv").html("");
		
		//window.choosePayDivHtml = $("#choosePayDiv").html();
		//$("#choosePayDiv").html("");
		//alert(window.choosePayDivHtml);
		
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/order_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
					<c:if test="${sessionScope.DV_USER_VO.shopkeeperRole}">
						<c:if test="${bean.status == 'edit'}"><!-- 未提交 -->
						（提交后 <span class='left_ts'>30分钟</span> 内未支付成功的订单将自动关闭）
						<input type="button" name="pay" value="支付" class="button" onclick="pay_onclick('${bean.pay_type}');"/>
						</c:if>
						<c:if test="${bean.status == 'delivered'}"><!-- 已发货 -->
						<input type="button" name="receive" value="确认收货" class="button" onclick="receive_onclick();"/>
						</c:if>
					</c:if>
					<c:if test="${sessionScope.DV_USER_VO.cityManagerRole}">
						<c:if test="${bean.status == 'submit'}"><!-- 已提交 -->
						<input type="button" name="send" value="发货/打印" class="button" onclick="sendAndPrint_onclick();"/>
						</c:if>
						<c:if test="${bean.status == 'apply_for_refund'}"><!-- 申请退款 -->
						<input type="button" name="agreeRefund" value="同意退款" class="button" onclick="agreeRefund_onclick();"/>
						<input type="button" name="send" value="拒绝退款，继续发货/打印" class="button" onclick="sendAndPrint_onclick();"/>
						</c:if>
						<c:if test="${bean.status == 'apply_for_return'}"><!-- 申请退货 -->
						<input type="button" name="agreeReturnGoods" value="同意退货" class="button" onclick="agreeReturnGoods_onclick();"/>
						<input type="button" name="denyReturnGoods" value="拒绝退货" class="button" onclick="denyReturnGoods_onclick();"/>
						</c:if>
						<input type="button" name="dealNext" value="处理下一笔" class="button" onclick="dealNext_onclick();"/>
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
							<td class="label">下单时间：</td>
							<td class="field">${fn:substring(bean.bill_time, 0, 19) }</td>
						</tr>
						<tr>
							<td class="label">订单金额：</td>
							<td class="field"><c:out value="${bean.total_amount }"/></td>
							<td class="label">实付金额：</td>
							<td class="field">
								<c:out value="${bean.real_amount }"/>
								<c:if test="${bean.use_coin_amount != null && bean.use_coin_amount > 0}">
								(汀豆代付：<c:out value="${bean.use_coin_amount }"/>)
								</c:if>
							</td>
							
						</tr>
						<tr>
							<td class="label">进货方：</td>
							<td class="field"><c:out value="${bean.shop_name }"/></td>
							<td class="label">发货方：</td>
							<td class="field"><c:out value="${bean.warehouse_name }"/></td>
						</tr>
						<tr>
							<td class="label">收货人：</td>
							<td class="field"><c:out value="${bean.rcv_name }"/></td>
							<td class="label">收货人手机：</td>
							<td class="field"><c:out value="${bean.rcv_mobile }"/></td>
						</tr>
						<tr>
							<td class="label">收货人地址：</td>
							<td class="field"><c:out value="${bean.rcv_address }"/></td>
							<td class="label">汀豆代金：</td>
							<td class="field"><c:out value="${bean.use_coin_amount }"/></td>
						</tr>
						<tr>
							<td class="label">支付方式：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/></td>
							<td class="label">支付回执编号：</td>
							<td class="field"><c:out value="${bean.pay_code }"/></td>
						</tr>
						<tr>
							<td class="label">支付金额：</td>
							<td class="field"><c:out value="${bean.pay_amount }"/></td>
							<td class="label">支付时间：</td>
							<td class="field"><c:out value="${bean.pay_time }"/></td>
						</tr>
						<tr>
							<td class="label">支付状态：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/></td>
							<td class="label">订单状态：</td>
							<td class="field"><dv:display dicKeyword="DIC_ORDER_BILL_STATUS" value="${bean.status }"/></td>
						</tr>
						<tr>
							<td class="label">退款渠道：</td>
							<td class="field"><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.refund_type }"/></td>
							<td class="label">退款状态：</td>
							<td class="field"><dv:display dicKeyword="DIC_ORDER_BILL_REFUND_STATUS" value="${bean.refund_status }"/></td>
						</tr>
						<tr>
							<td class="label">备注：</td>
							<td class="field" colspan="3"><c:out value="${bean.remark }"/></td>
						</tr>
					</table>
				</div>
				<DL id="sub_tab">
					<DT>订单明细</DT>
					<DD>
						<div>
							<table id="detail_list_table" class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="5%">缩略图</th>
									<th width="20%">商品名称</th>
									<th width="10%">店长进货价</th>
									<th width="10%">进货数量</th>
									<th width="10%">进货小计</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>${status.count }</td>
									<td><img class="thumbnail" src="<c:out value="${detail.goods_image }"/>?imageView2/0/w/50" rel="<c:out value="${detail.goods_image }"/>?imageView2/0/w/200"/></td>
									<td><c:out value="${detail.goods_name }"/></td>
									<td><c:out value="${detail.sale_price }"/></td>
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
								<span class="left_ts">(进货订单满${start_amount }元起送)</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="font-weight:bold; line-height: 30px; padding-right: 20px;">进货合计：${bean.total_amount }（元）</span>
							</div>
						</div>
					</DD>
					<%-- <c:if test="${bean.express != null}">
					<DT>物流信息</DT>
					<DD>
						<div>
							<table class="detail_table">
								<tr>
									<td class="label">快递公司：</td>
									<td class="field"><dv:display dicKeyword="DIC_EXPRESS_COMPANY" value="${bean.express.exp_company }" /></td>
									<td class="label">快递单号：</td>
									<td class="field"><c:out value="${bean.express.exp_tracking_no }"/></td>
								</tr>
								<tr>
									<td class="label">快递日期：</td>
									<td class="field"><c:out value="${bean.express.exp_date }"/></td>
									<td class="label">快递费用：</td>
									<td class="field"><c:out value="${bean.express.exp_amount }"/></td>
								</tr>
								<tr>
									<td class="label">发件人姓名：</td>
									<td class="field"><c:out value="${bean.express.sender_name }"/></td>
									<td class="label">发件人电话：</td>
									<td class="field"><c:out value="${bean.express.sender_mobile }"/></td>
								</tr>
								<tr>
									<td class="label">发件人地址：</td>
									<td class="field" colspan="3"><c:out value="${bean.express.sender_address }"/></td>
								</tr>
								<tr>
									<td class="label">收件人姓名：</td>
									<td class="field"><c:out value="${bean.express.receiver_name }"/></td>
									<td class="label">收件人电话：</td>
									<td class="field"><c:out value="${bean.express.receiver_mobile }"/></td>
								</tr>
								<tr>
									<td class="label">收件人地址：</td>
									<td class="field" colspan="3"><c:out value="${bean.express.receiver_address }"/></td>
								</tr>
							</table>
						</div>
					</DD>
					</c:if> --%>
				</DL>
			</div>
		</div>
		<div id="sendInputDiv" style="display:none"></div>
	</form>
	
	<!-- 
	<div id="sendDiv" style="display:none">
		<div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="required_red">* </span>是否使用快递：
			<dv:radio name="need_express" dicKeyword="DIC_YES_NO" defaultValue="1" attributes="inputName='是否使用快递' validate='notNull'" />
		</div>
		<br><br>
		<table id="exp_insert_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>快递公司：</td>
				<td class="field">
					<dv:select name="express.exp_company" dicKeyword="DIC_EXPRESS_COMPANY" hasEmpty="true" defaultValue="" attributes="inputName='快递公司' validate='notNull'" />
				</td>
				<td class="label"><span class="required_red">* </span>快递单号：</td>
				<td class="field">
					<input type="text" name="express.exp_tracking_no" class="input" inputName="快递单号" value="" maxlength="50" validate="notNull"/>
				</td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>快递日期：</td>
				<td class="field">
					<input type="text" name="express.exp_date" class="input Wdate" inputName="快递日期" value="" validate="notNull"/>	
				</td>
				<td class="label"><span class="required_red">* </span>快递费用：</td>
				<td class="field">
					<input type="text" name="express.exp_amount" class="input" inputName="快递费用" value="" validate="notNull"/>
				</td>
			</tr>
			<tr>
				<td class="label">寄件人姓名：</td>
				<td class="field">
					<input type="text" name="express.sender_name" class="input" inputName="寄件人姓名" value="${sessionScope.DV_USER_VO.real_name}" maxlength="50" validate=""/>
				</td>
				<td class="label">寄件人电话：</td>
				<td class="field">
					<input type="text" name="express.sender_mobile" class="input" inputName="寄件人电话" value="${sessionScope.DV_USER_VO.mobile}" maxlength="50" validate=""/>
				</td>
			</tr>
			<tr>
				<td class="label">寄件人地址：</td>
				<td class="field" colspan="3">
					<input type="text" name="express.sender_address" class="full_input" inputName="寄件人地址" value="${sessionScope.DV_USER_VO.address}" maxlength="50" validate=""/>
				</td>
			</tr>
			<tr>
				<td class="label">收件人姓名：</td>
				<td class="field">
					<input type="text" name="express.receiver_name" class="input" inputName="收件人姓名" value="<c:out value="${bean.rcv_name }"/>" maxlength="50" validate=""/>
				</td>
				<td class="label">收件人电话：</td>
				<td class="field">
					<input type="text" name="express.receiver_mobile" class="input" inputName="收件人电话" value="<c:out value="${bean.rcv_mobile }"/>" maxlength="50" validate=""/>
				</td>
			</tr>
			<tr>
				<td class="label">收件人地址：</td>
				<td class="field" colspan="3">
					<input type="text" name="express.receiver_address" class="full_input" inputName="收件人地址" value="<c:out value="${bean.rcv_address }"/>" maxlength="50" validate=""/>
				</td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td class="field" colspan="3">
					<input type="text" name="express.remark" class="input" inputName="备注" value="" maxlength="100" validate=""/>
				</td>
			</tr>
		</table>
	</div>
	<div id="choosePayDiv" style="display:none;">
		<table id="choose_pay_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>支付方式：</td>
				<td class="field">
					<dv:radio dicKeyword="DIC_PAY_TYPE" name="pay_type" defaultValue="ttpay" ignoreValues="online,cod" attributes="inputName='支付方式' validate='notNull'"/>
				</td>
			</tr>
			<tr>
				<td class="label"><span class="required_red">* </span>支付金额：</td>
				<td class="field">
					<input type="text" id="pay_amount" name="pay_amount" class="readonly" inputName="支付金额" value="<c:out value="${bean.real_amount }"/>" validate=""/>
				</td>
			</tr>
			<tr id="tr_coupon" class="hidden">
				<td class="label"><span class="required_red">* </span>可用优惠券：</td>
				<td class="field" id="td_coupon">
				
				</td>
			</tr>
		</table>
	</div>
	-->
</body>
</html>
