<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典数据排序</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("dictDataController");
	
	/** 保存 */
	function save_onclick() {
		var ids = $("input[name='dv_checkbox']").map(function() {
			return $(this).val();
		}).get().join();
		$("input[name='sort_ids']").val(ids);
		formHelper.jSubmit(formHelper.buildAction("dictDataController", "sort", "${requestScope.type_id }"));
	}

	/** 刷新 */
	function refresh_onclick() {
		window.location.reload();
	}
	
	 //上移
	function up_onclick(upBtn) {
		var currTr = $(upBtn).parent().parent();
		var index = currTr.index();//当前行的id
		if (index > 1) {//不是表头，也不是第一行，则可以上移
			//$(currTr).prev().after(currTr);
			var tem0 = currTr.html();
			var tem1 = currTr.prev().html();
			currTr.prev().html(tem0);
			currTr.html(tem1);
		}
	}

	//下移
	function down_onclick(downBtn) {
		var lineNum = $("#dict_list_table tr").length;//总行数
		var currTr = $(downBtn).parent().parent();
		var index = currTr.index();//当前行的id
		if (index < lineNum - 1) {//不是最后一行，则可以下移
			//$(currTr).prev().before(currTr);
			var tem0 = currTr.html();
			var tem1 = currTr.next().html();
			currTr.next().html(tem0);
			currTr.html(tem1);
		}
	}

	$(document).ready(function() {
		$("#dict_list_table").dragsort({ dragSelector: "tr", dragSelectorExclude: "tr:first", dragBetween: false, dragEnd: saveOrder, placeHolderTemplate: "<tr></tr>" });
	});
	
	function saveOrder() {
		//alert("ssss");
	};
	
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div class="border_div" style="margin:1px;">
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">字典数据排序&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_dict_data"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="refresh" value="刷新" class="button" onclick="refresh_onclick();"/>
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table id="dict_list_table" class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="10%">字典key</th>
						<th width="10%">字典value</th>
						<th width="10%">备注</th>
						<th width="10%">状态</th>
						<th width="10%">操作</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.dic_key }"/></td>
						<td><c:out value="${bean.dic_value }"/></td>
						<td><c:out value="${bean.remark }"/></td>
						<td><dv:display dicKeyword="DIC_STATUS" value="${bean.status }"/></td>
						<td>
							<input type="button" name="up" value="上移" class="button" onclick="up_onclick(this);"/>
							<input type="button" name="down" value="下移" class="button" onclick="down_onclick(this);"/>
						</td>
					</tr>
					</c:forEach>  
				</table>
			</div>
		</div>
		<input type="hidden" name="sort_ids" value=""/>
	</form>
</body>
</html>
