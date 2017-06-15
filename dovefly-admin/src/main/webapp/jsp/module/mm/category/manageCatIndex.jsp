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
				</div>
			</div>
			<div class="padding_2_div">
				<div class="tip_title">关于商品分类的说明</div>
				<div class="tip_list">
					<div class="left_txt">
						1、您现在使用的是***开发的一套用于构建商务信息类门户型网站的专业系统！如果您有任何疑问请点左下解的<br/>
						<span class="left_ts">在线客服</span>
						<span class="left_txt">进行咨询！</span>
					</div>
					<div class="left_txt">
						2、七大栏目完美整合，一站通使用方式，功能强大，操作简单，后台操作易如反掌，只需会打字，会上网，就会管理网站！<br>
						此程序是您建立地区级商家信息门户的首选方案！ <br>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
