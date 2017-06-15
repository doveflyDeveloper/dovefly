<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.market.bill.vo.MarketBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单信息</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery.printPage.js"></script>
<style type="text/css">
<!--

ul li{line-height:20px;color:#333333;margin-bottom:10px;}
ul li .progress-time{color:#999;}
ul li .progress-desc{color:#000;}

-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("marketBillController");
	
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
	
	/** 订单取消 */
	function cancel_onclick() {
		var msg = "${bean.pay_status }" == "<%=MarketBillVo.PAY_STATUS_SUCCESS%>" ? "本订单已在线付款，系统将自动线上退款给客户，您确定要取消此订单吗？" : "本订单尚未付款，系统将认为您已线下退款给客户，您确定要取消此订单吗？";
		dvConfirm(msg, 
			function() {
				formHelper.jSubmit(formHelper.buildAction("marketBillController", "cancel", "${bean.id }"));
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
				formHelper.jSubmit(context_path + "/marketBillController/agreeRefund/${bean.id}");
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
				formHelper.jSubmit(context_path + "/marketBillController/agreeReturnGoods/${bean.id}");
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
				formHelper.jSubmit(context_path + "/marketBillController/denyReturnGoods/${bean.id}");
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
		dvOpenDialog(context_path + '/marketBillController/print/${bean.id }', '订单打印', 320, 450, options);
	}
	
	
	
	/** 发货 */
	function send_onclick() {
	   var msg = "${bean.pay_status }" == "<%=MarketBillVo.PAY_STATUS_SUCCESS%>" ? "本订单已在线付款，您确定要发货吗？" : "本订单尚未付款，系统将认为客户已线下付款给您，您确定要发货吗？";
		dvConfirm(msg, 
			function() {
			
				if(validateForm("form")){//校验发货数量等信息
					dvPrompt(window.sendDivHtml, '填写发货信息', {
						top: '5%',
						width: 700,
						bottomText: '<span class="left_ts">如有发快递包裹，请务必认真填写物流信息！</span>',
						submit: function(v, h, f) {
							if ($("input[name='need_express']:checked").val() == "1") {
								if (!validateForm("exp_insert_table")) {return false;}
								var inputs = [
									'<input type="hidden" name="need_express" defaultValue="' + f['need_express'] + '"/>',
									'<input type="hidden" name="exp_company" value="' + f['express.exp_company'] + '"/>',
									'<input type="hidden" name="exp_tracking_no" value="' + f['express.exp_tracking_no'] + '"/>',
									'<input type="hidden" name="exp_date"   value="' + f['express.exp_date'] + '"/>',
									'<input type="hidden" name="exp_amount" value="' + f['express.exp_amount'] + '"/>',
									'<input type="hidden" name="sender_name" value="' + f['express.sender_name'] + '"/>',
									'<input type="hidden" name="sender_mobile" value="' + f['express.sender_mobile'] + '"/>',
									'<input type="hidden" name="sender_address" value="' + f['express.sender_address'] + '"/>',
									'<input type="hidden" name="receiver_name" value="' + f['express.receiver_name'] + '"/>',
									'<input type="hidden" name="receiver_mobile" value="' + f['express.receiver_mobile'] + '"/>',
									'<input type="hidden" name="receiver_address" value="' + f['express.receiver_address'] + '"/>',
									'<input type="hidden" name="remark" value="' + f['express.remark'] + '"/>'
								];
                                $("#sendInputDiv").html(inputs.join(""));
							}
							  /* var sender_name = f['express.sender_mobile'];
							  alert(sender_name);  */
							  alert($("form").length);
							 formHelper.jSubmit(context_path + "/marketBillController/send/${bean.id}"); 
							return true;
						}
					});
				}
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 发货/打印 */
	function sendAndPrint_onclick() {
		
		if(!("${bean.pay_status }" == "<%=MarketBillVo.PAY_STATUS_SUCCESS%>")){
			dvAlert("只有<span class='left_ts'>支付成功</span>状态的进货订单才可以发货！");
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
		
		dvOpenDialog(context_path + '/marketBillController/print/${bean.id }', '订单打印', 320, 450, options);
	}
			
	$(document).ready(function() {
		
		 $("input[name='need_express'").live("change", function(){
			if ("1" == this.value) {
				$("#exp_insert_table").show();
			} else {
				$("#exp_insert_table").hide();
			}
		}); 
		
        window.sendDivHtml = $("#sendDiv").html();
        $("#sendDiv").html("");
   		
    	var d_progress = JSON.parse('${bean.express.deliver_progress}' || "{}");
    	var html = "";
    	if (d_progress.Traces && d_progress.Traces.length > 0) {
    		for (var i = 0; i < d_progress.Traces.length; i++) {
	    		html += '<li><span class="progress-time">' + d_progress.Traces[i].AcceptTime + '</span><br>';
	    		html += '<span class="progress-desc">' + d_progress.Traces[i].AcceptStation + '</span></li>';
    		}
    	}
    	
    	$("#ul_deliver_progess").html(html);
		
    	var h = $(".about4_main ul li:first-child").height()/2;<!--第一个li高度的一半-->
    	var h1 = $(".about4_main ul li:last-child").height()/2;<!--最后一个li高度的一半-->
    	$(".line").css("top",h);
    	$(".line").height($(".about4_main").height()-h1-h);

	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">订单信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/market_bill"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
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
							<td class="label">实付金额：</td>
							<td class="field"><c:out value="${bean.real_amount }"/></td>
							<td class="label">订单总金额：</td>
							<td class="field"><c:out value="${bean.total_amount }"/></td>
						</tr>
						<tr>
							<td class="label">汀豆数量：</td>
							<td class="field"><c:out value="${bean.use_coin_quantity }"/></td>
							<td class="label">汀豆扣减金额：</td>
							<td class="field"><c:out value="${bean.use_coin_amount }"/></td>
						</tr>
						<tr>
							<td class="label">进货方：</td>
							<td class="field"><c:out value="${bean.buyer_name }"/></td>
							<td class="label">发货方：</td>
							<td class="field"><c:out value="${bean.market_name }"/></td>
						</tr>
						<tr>
							<td class="label">收货人：</td>
							<td class="field"><c:out value="${bean.ship_name }"/></td>
							<td class="label">收货人手机：</td>
							<td class="field"><c:out value="${bean.ship_mobile }"/></td>
						</tr>
						<tr>
							<td class="label">收货人地址：</td>
							<td class="field"><c:out value="${bean.ship_addr }"/></td>
							<td class="label"></td>
							<td class="field"></td>
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
									<th width="10%">购买价格</th>
									<th width="10%">购买数量</th>
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
								<span class="left_ts">(进货订单满${start_amount }元起送)</span>&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="font-weight:bold; line-height: 30px; padding-right: 20px;">进货合计：${bean.total_amount }（元）</span>
							</div>
						</div>
					</DD>
				    <c:if test="${bean.express != null}">
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
								<tr>
									<td class="label">快递进度：</td>
									<td class="field" colspan="3">
										<ul id="ul_deliver_progess">
										</ul>
									</td>
								</tr>
							</table>
						</div>
					</DD>
					</c:if> 
				</DL>
			</div>
		</div>
		<div id="sendInputDiv" style="display:none"></div>
	</form>
	
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
					<input type="text" name="express.receiver_name" class="input" inputName="收件人姓名" value="<c:out value="${bean.ship_name }"/>" maxlength="50" validate=""/>
				</td>
				<td class="label">收件人电话：</td>
				<td class="field">
					<input type="text" name="express.receiver_mobile" class="input" inputName="收件人电话" value="<c:out value="${bean.ship_mobile }"/>" maxlength="50" validate=""/>
				</td>
			</tr>
			<tr>
				<td class="label">收件人地址：</td>
				<td class="field" colspan="3">
					<input type="text" name="express.receiver_address" class="full_input" inputName="收件人地址" value="<c:out value="${bean.ship_addr }"/>" maxlength="50" validate=""/>
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
</body>
</html>
