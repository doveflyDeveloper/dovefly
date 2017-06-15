<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.trade.bill.vo.TradeBillVo" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>订单管理</title>
<style type="text/css">
<!--

-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("tradeBillController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 新增 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildCreateAction());
	}
	
	/** 复制 */	
	function copy_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildCopyAction(id));
	}
	
	/** 删除 */
	function deleteMulti_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "1")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
	
		var ids = findSelections("dv_checkbox", "value");
		
		dvConfirm("您确定要删除这些数据吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteMultiAction(ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 处理 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildDetailAction(id));
		} else {//勾选复选框查看
			var ids = findSelections("dv_checkbox", "value");
			if(!ids || !ids.length) {
				dvAlert("请选择一条记录！");
		  		return false;
			}
			if(ids.length > 1) {
				dvAlert("只能选择一条记录！");
		  		return false;
			}
			formHelper.jSubmit(formHelper.buildDetailAction(ids));
		}
	}
	
	/** 导出列表 */
	function exportList_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("tradeBillController", "exportList", ids));
	}
	
	/** 导出明细列表 */
	function exportListDetail_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("tradeBillController", "exportListDetail", ids));
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
							<c:if test="${sessionScope.DV_USER_VO.superAdminRole || sessionScope.DV_USER_VO.headquartersRole || sessionScope.DV_USER_VO.cashierRole }">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
							</c:if>
							<c:if test="${!(sessionScope.DV_USER_VO.superAdminRole || sessionScope.DV_USER_VO.headquartersRole || sessionScope.DV_USER_VO.cashierRole) }">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${sessionScope.DV_USER_VO.city_id}" ignoreValues="1" hasEmpty="true" attributes="disabled"/>
							</c:if>
						</td>
						<td class="label">学校：</td>
						<td class="field">
							<input type="text" name="school_name" class="input" maxlength="50" value="<c:out value="${param.school_name }"/>"/>
						</td>
						<td class="label">店铺ID：</td>
						<td class="field">
							<input type="text" name="shop_id" class="input" maxlength="50" value="<c:out value="${param.shop_id }"/>"/>
						</td>
					</tr>
					<tr>
						<td class="label">订单编号：</td>
						<td class="field">
							<input type="text" name="bill_code" class="input" maxlength="50" value="<c:out value="${param.bill_code }"/>"/>
						</td>
						<td class="label">订单日期：</td>
						<td class="field">
							<input type="text" name="bill_date_from" class="half_input Wdate" value="${param.bill_date_from == null ? bill_date_from : param.bill_date_from }" inputName="起始日期" validate="notNull"/>&nbsp;&nbsp;~&nbsp;
							<input type="text" name="bill_date_to" class="half_input Wdate" value="${param.bill_date_to == null ? bill_date_to : param.bill_date_to }" inputName="终止日期" validate="notNull"/>
						</td>
						<td class="label">买家：</td>
						<td class="field">
							<input type="text" name="buyer_name" class="input" maxlength="50" value="<c:out value="${param.buyer_name }"/>"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">订单列表&nbsp;</div>
					<dv:radioButton name="status" dicKeyword="DIC_TRADE_BILL_STATUS" defaultValue="${param.status }" hasEmpty="true"/>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="exportList" value="导出订单" class="button" onclick="exportList_onclick();"/>
						<input type="button" name="exportListDetail" value="导出明细" class="button" onclick="exportListDetail_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="4%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="3%">序号</th>
						<th width="3%">城市</th>
						<th width="5%">运营主管</th>
						<th width="10%">学校</th>
						<th width="5%">店铺ID</th>
						<th width="5%">店铺</th>
						<th width="5%">买家</th>
						<th width="12%">订单号</th>
						<th width="11%">下单时间</th>
						<th width="10%">订单金额 &nbsp;(实付)</th>
						<th width="10%">支付方式 / 支付状态</th>
						<th width="10%">收货人 / 手机 / 地址</th>
						<th width="7%">订单状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td>${bean.city_name }</td>
						<td>${bean.manager_name }</td>
						<td>${bean.school_name }</td>
						<td>${bean.shop_id }</td>
						<td>${bean.shop_name }</td>
						<td>${bean.buyer_name }</td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.bill_code }"/></a></td>
						<td>${fn:substring(bean.bill_time, 0, 19) }</td>
						<td><c:out value="${bean.total_amount }"/>&nbsp;&nbsp;&nbsp;&nbsp;(&nbsp;<c:out value="${bean.real_amount }"/>&nbsp;)</td>
						<td><dv:display dicKeyword="DIC_PAY_TYPE" value="${bean.pay_type }"/>&nbsp;|&nbsp;<dv:display dicKeyword="DIC_PAY_STATUS" value="${bean.pay_status }"/></td>
						<td><c:out value="${bean.ship_name }"/>&nbsp;|&nbsp;<c:out value="${bean.ship_mobile }"/>&nbsp;|&nbsp;<c:out value="${bean.ship_addr }"/></td>
						<td><dv:display dicKeyword="DIC_TRADE_BILL_STATUS" value="${bean.status }"/></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="page_div">
			<jsp:include page="/jsp/include/page.jsp"/>
		</div>
	</form>
</body>
</html>
