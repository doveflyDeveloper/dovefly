<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	boolean refreshResourceTree = (request.getAttribute("refreshResourceTree") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统功能信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("resourceController");
	
	/** 新增子资源 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildAction("resourceController", "create", "<c:out value="${bean.id }"/>"));
	}
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("<c:out value="${bean.id }"/>"));
	}
	
	/** 删除 */
	function delete_onclick() {
		dvConfirm("您确定要删除此菜单及其所有子菜单吗？", 
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
		formHelper.jSubmit(formHelper.buildAction("resourceController", "find4Sort", "<c:out value="${bean.id }"/>"));
	}

	$(document).ready(function() {
		if(<%=refreshResourceTree%>){
			parent.resourceTree.window.location.reload();//刷新左侧树
		}
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div class="border_div" style="margin:1px;">
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">系统功能信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_resource"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="add" value="添加子资源" class="button" onclick="add_onclick();"/>
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="delete" value="删除" class="button" onclick="delete_onclick();"/>
						<input type="button" name="sort" value="排序子资源" class="button" onclick="find4Sort_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">资源编号：</td>
						<td class="field"><c:out value="${bean.code }"/></td>
						<td class="label">资源名称：</td>
						<td class="field"><c:out value="${bean.name }"/></td>
					</tr>
					<tr>
						<td class="label">父级资源编号：</td>
						<td class="field"><c:out value="${bean.parent_code }"/></td>
						<td class="label">父级资源名称：</td>
						<td class="field"><c:out value="${bean.parent_name }"/></td>
					</tr>
					<tr>
						<td class="label">资源类型：</td>
						<td class="field"><dv:display dicKeyword="DIC_RESOURCE_TYPE" value="${bean.type }"/></td>
						<td class="label">资源权限：</td>
						<td class="field"><c:out value="${bean.permission }"/></td>
					</tr>
					<tr>
						<td class="label">资源地址：</td>
						<td class="field" colspan="3"><c:out value="${bean.url }"/></td>
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
