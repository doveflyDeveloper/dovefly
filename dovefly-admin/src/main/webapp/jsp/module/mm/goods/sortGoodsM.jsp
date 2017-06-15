<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>库存商品排序</title>
<style type="text/css">
<!--
	ul { margin:0px; padding:0px; margin-left:20px; }
	#goods_list { list-style-type:none; margin:0px; }
	#goods_list li { float:left; padding:10px; width:90px; height:90px; }
	#goods_list div { width:90px; height:90px; border:solid 1px gray; text-align:center; }
	.placeHolder div { background-color:white !important; border:dashed 1px gray !important; }
	img.thumbnail { width:90px; height:90px; max-width:90px; max-height:90px; }
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("goodsMController");
	
	/** 确定 */
	function ok_onclick() {
		var sort_ids = $("#goods_list img").map(function() {
			return $(this).attr("goods_id"); 
		}).get().join();
		
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: context_path + "/goodsMController/sort",
			data: {"sort_ids":sort_ids},
			success: function(result){
				if(result){//存在
					if(result.success){
						dvTip(result.message, "success");
						parent.location.reload();
					} else {
						dvTip(result.message, "error");
					}
				}
			}
		});
		
		return false;
	}
	
	function saveOrder() {
		//alert("ssss");
	};
	
	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
		$("#goods_list").dragsort({ dragSelector: "div", dragBetween: false, dragEnd: saveOrder, placeHolderTemplate: "<li class='placeHolder'><div></div></li>" });
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<ul id="goods_list">
			<c:forEach var="bean" varStatus="status" items="${beans }">
			<li><div><img class="thumbnail" goods_id="${bean.id }" src="<c:out value="${bean.image }"/>?imageView2/0/w/90"/></div></li>
			</c:forEach>
		</ul>
	</form>
</body>
</html>
