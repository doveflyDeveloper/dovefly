<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	boolean refreshCategoryTree = (request.getAttribute("refreshCategoryTree") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品分类信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("categoryController");
	
	/** 新增子分类 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildAction("categoryController", "create", "<c:out value="${bean.id }"/>"));
	}
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("<c:out value="${bean.id }"/>"));
	}
	
	/** 删除 */
	function delete_onclick() {
		dvConfirm("您确定要删除此分类及其所有子分类吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteAction("<c:out value="${bean.id }"/>"));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 排序 */
	function find4Sort_onclick() {
		formHelper.jSubmit(formHelper.buildAction("categoryController", "find4Sort", "<c:out value="${bean.id }"/>"));
	}

	$(document).ready(function() {
		if(<%=refreshCategoryTree%>){
			parent.categoryTree.window.location.reload();//刷新左侧树
		}
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div class="border_div" style="margin:1px;">
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">商品分类信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_category"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="add" value="添加子分类" class="button" onclick="add_onclick();"/>
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="delete" value="删除" class="button" onclick="delete_onclick();"/>
						<input type="button" name="sort" value="排序子分类" class="button" onclick="find4Sort_onclick();"/>
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
		</div>
	</form>
</body>
</html>
