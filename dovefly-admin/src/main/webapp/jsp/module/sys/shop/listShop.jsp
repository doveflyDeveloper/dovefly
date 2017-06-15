<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>店铺管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("shopController");
	
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
	
	/** 修改 */
	function find_onclick() {
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
		formHelper.jSubmit(formHelper.buildFindAction(id));
	}
	
	/** 查看 */
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
	
	/** 关停店 */
	function closeShop_onclick() {
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
		
		dvConfirm("店铺永久关闭后将不能再重新开店，您确定要关闭所选店铺吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildAction("shopController", "closeShop"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 店长趋势图 */
	function reportShop_onclick() {
		formHelper.jSubmit(formHelper.buildAction("shopReportController", "reportShop"));
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
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_shop"></div>
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
							<c:if test="${sessionScope.DV_USER_VO.superAdminRole || sessionScope.DV_USER_VO.headquartersRole }">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
							</c:if>
							<c:if test="${sessionScope.DV_USER_VO.cityManagerRole || sessionScope.DV_USER_VO.operationManagerRole }">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${sessionScope.DV_USER_VO.city_id}" ignoreValues="1" hasEmpty="true" attributes="disabled"/>
							</c:if>
						</td>
						<td class="label">学校：</td>
						<td class="field">
							<input type="text" name="school_name" class="input" maxlength="30" value="<c:out value="${param.school_name }"/>"/>
						</td>
						<td class="label">运营主管：</td>
						<td class="field">
							<input type="text" name="manager_name" class="input" maxlength="10" value="<c:out value="${param.manager_name }"/>"/>
						</td>
					</tr>
					<tr>
						<td class="label">店铺：</td>
						<td class="field">
							<input type="text" name="shop_name" class="input" maxlength="20" value="<c:out value="${param.shop_name }"/>"/>
						</td>
						<td class="label">店长：</td>
						<td class="field">
							<input type="text" name="shopkeeper_name" class="input" maxlength="20" value="<c:out value="${param.shopkeeper_name }"/>"/>
						</td>
						<td class="label">运营状态：</td>
						<td class="field">
							<dv:radio name="shop_status" hasEmpty="true" dicKeyword="DIC_SHOP_WORK_STATUS" defaultValue="${param.shop_status }"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">店铺列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="insert" value="新增" class="button" onclick="add_onclick();"/>
						<input type="button" name="update" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="closeShop" value="关停店" class="button" onclick="closeShop_onclick()"/>
						<input type="button" name="reportShop" value="店铺统计" class="button" onclick="reportShop_onclick()"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="5%">城市</th>
						<th width="10%">学校</th>
						<th width="10%">货仓</th>
						<th width="10%">运营主管</th>
						<th width="10%">店铺头像</th>
						<th width="10%">店铺</th>
						<th width="10%">店长</th>
						<th width="5%">起送价</th>
						<th width="5%">账号余额</th>
						<th width="5%">冻结资金</th>
						<th width="5%">待收金额</th>
						<th width="5%">运营状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.city_name }"/></td>
						<td><c:out value="${bean.school_name }"/></td>
						<td><c:out value="${bean.warehouse_name }"/></td>
						<td><c:out value="${bean.manager_name }"/></td>
						<td><img class="thumbnail" src="${bean.shop_logo }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.shop_name }"/></a></td>
						<td><c:out value="${bean.shopkeeper_name }"/></td>
						<td><c:out value="${bean.start_amount }"/></td>
						<td><c:out value="${bean.balance_amount }"/></td>
						<td><c:out value="${bean.locked_amount }"/></td>
						<td><c:out value="${bean.halfway_amount }"/></td>
						<td><dv:display dicKeyword="DIC_SHOP_WORK_STATUS" value="${bean.shop_status }" /></td>
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
