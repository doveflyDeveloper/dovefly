<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商品分类管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {

	});
</script>
</head>
<body>
	<div class="border_div">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="250px">
					<iframe id="categoryTree" name="categoryTree" src="<%=request.getContextPath()%>/categoryController/manageCatTree" width="100%" height="475px" marginwidth="1px" frameborder="0"></iframe>
				</td>
				<td>
					<iframe id="categoryMain" name="categoryMain" src="<%=request.getContextPath()%>/jsp/module/mm/category/manageCatIndex.jsp" width="100%" height="475px" marginwidth="1px" frameborder="0"></iframe>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
