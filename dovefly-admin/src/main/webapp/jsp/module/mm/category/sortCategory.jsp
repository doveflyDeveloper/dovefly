<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	boolean refreshCategoryTree = (request.getAttribute("refreshCategoryTree") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品分类排序</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("categoryController");
	
	/** 保存 */
	function save_onclick() {
		var ids = $("input[name='dv_checkbox']").map(function() {
			return $(this).val();
		}).get().join();
		$("input[name='sort_ids']").val(ids);
		formHelper.jSubmit(formHelper.buildAction("categoryController", "sort", "<c:out value="${bean.id }"/>"));
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
		var lineNum = $("#children_list_table tr").length;//总行数
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
		$("#children_list_table").dragsort({ dragSelector: "tr", dragSelectorExclude: "tr:first", dragBetween: false, dragEnd: saveOrder, placeHolderTemplate: "<tr></tr>" });
		
		if(<%=refreshCategoryTree%>){
			parent.categoryTree.window.location.reload();//刷新左侧树
		}
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
					<div class="table_title">商品分类信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_category"></div>
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
				<table class="detail_table">
					<tr>
						<td class="label">分类编号：</td>
						<td class="field"><c:out value="${bean.code }"/></td>
						<td class="label">分类名称：</td>
						<td class="field"><c:out value="${bean.name }"/></td>
					</tr>
					<tr>
						<td class="label">父级分类编号：</td>
						<td class="field"><c:out value="${bean.parent_code }"/></td>
						<td class="label">父级分类名称：</td>
						<td class="field"><c:out value="${bean.parent_name }"/></td>
					</tr>
					<tr>
						<td class="label">层级深度：</td>
						<td class="field"><c:out value="${bean.level }"/></td>
						<td class="label">是否叶子节点：</td>
						<td class="field"><dv:display dicKeyword="DIC_YES_NO" value="${bean.is_leaf }"/></td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field" colspan="3"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			</div>
			<div class="padding_2_div">
				<table id="children_list_table" class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="10%">分类编号</th>
						<th width="10%">分类名称</th>
						<th width="10%">操作</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.code }"/></td>
						<td><c:out value="${bean.name }"/></td>
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
