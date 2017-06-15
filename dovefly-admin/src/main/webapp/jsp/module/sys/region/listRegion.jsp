<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>区域管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("regionController");
	
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
	
	function ajaxQuery() {
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: context_path + "/regionController/ajaxQuery",
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
	
	function setWarehouse_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || !ids.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		var region_ids = ids.toString();
		$.ajax({
			type: "GET",
			async: true,
			dataType: "json",
			url: context_path + "/warehouseController/loadWarehouse",
			data: {},
			success: function(result){
				if(result && result.success){
					var warehouses = result.data;
					if (warehouses && warehouses.length > 0) {
						var warehouseHtml = '<div id="choose_warehouse">';
						for (var i = 0; i < warehouses.length; i++) {
							warehouseHtml += '<label><input type="radio" class="radio" name="warehouse" value="' + warehouses[i].id + '" inputname="仓库" validate="notNull">' + warehouses[i].warehouse_name + '</label><br>';
						}
						warehouseHtml += '<label><input type="radio" class="radio" name="warehouse" value="0" inputname="仓库" validate="notNull">清除仓库</label><br>';
						warehouseHtml += "</div>";
						dvPrompt(warehouseHtml, '分配仓库', {
							top: '5%',
							width: 300,
							bottomText: '',
							submit: function(v, h, f) {
								if (validateForm("choose_warehouse")) {
									var warehouse_id = f['warehouse'];
									//alert(warehouse_id);
									//alert($("input[name='warehouse']:checked").val());
									$.ajax({
										type: "POST",
										async: false,
										dataType: "json",
										url: context_path + "/regionController/setWarehouse",
										data: {"warehouse_id":warehouse_id, "region_ids":region_ids},
										success: function(result){
											if(result && result.success){
												window.location.reload();
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
				}
			}
		});
	}
	
	function changeTreeMode_onclick() {
		window.location.href = context_path + "/regionController/manageRegion";
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_region"></div>
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
						<c:if test="${sessionScope.DV_USER_VO.headquartersRole}">
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						</c:if>
						<td class="label">学校：</td>
						<td class="field">
							<input type="text" name="full_name" class="input" maxlength="100" value="<c:out value="${param.full_name }"/>"/>
						</td>
						<td class="label">主管：</td>
						<td class="field">
							<input type="text" name="manager_name" class="input" maxlength="20" value="<c:out value="${param.manager_name }"/>"/>
						</td>
					</tr>
					<tr>
						<td class="label">是否已分配主管：</td>
						<td class="field">
							<label><input type="radio" class="radio" name="has_manager" value="" checked="checked">全部</label>&nbsp;&nbsp;
							<label><input type="radio" class="radio" name="has_manager" value="1" <c:if test="${param.has_manager == '1'}">checked</c:if>>已分配</label>&nbsp;&nbsp;
							<label><input type="radio" class="radio" name="has_manager" value="0" <c:if test="${param.has_manager == '0'}">checked</c:if>>未分配</label>&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				<input name="level" type="hidden" value="${param.level }"/>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">区域列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="setWarehouse" value="分配仓库" class="button" onclick="setWarehouse_onclick();"/>
						<input type="button" name="mode" value="层级模式" class="button" onclick="changeTreeMode_onclick();"/>
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
						<th width="10%">仓库 </th>
						<th width="10%">区域名称</th>
						<th width="10%">主管人员</th>
						<th width="10%">区域层级</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.warehouse_name }"/></td>
						<td style="text-align:left;"><c:out value="${bean.full_name }"/></td>
						<td><c:out value="${bean.manager_name }"/></td>
						<td><c:out value="${bean.level }"/></td>
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
