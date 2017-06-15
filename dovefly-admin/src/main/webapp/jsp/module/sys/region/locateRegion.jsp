<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域定位</title>
<style type="text/css">
<!--
#l-map {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
#r-result {width:380px;height:340px;overflow:auto;}

-->
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=splMEg7d2hdwTAe1RlKkvEX3"></script>
<script type="text/javascript">
	
	/** 保存 */
	function ok_onclick() {
		if(validateForm()){
			$.ajax({
				type: "POST",
				async: false,
				dataType: "json",
				url: context_path + "/regionController/ajaxLocate/${bean.id }",
				data: $("form").serialize(),
				success: function(result){
					if(result){//存在
						if(result.success){
							dvTip(result.message, "success");
							//parent.resetBaiduAddr('${param.tId }');
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
			return true;
		} else {
			return false;
		}
	}
	
	function search_onclick() {
		var keyword = $("#search_keyword").val().trim();
		if (keyword != '') {
			local.search(keyword);
		}
	}
	
	var map;
	var local;
	var places = [];
	$(document).ready(function() {
		// 百度地图API功能
		map = new BMap.Map("l-map");// 创建Map实例
		map.centerAndZoom(new BMap.Point(121.480237, 31.236305), 12);// 初始化地图,设置中心点坐标和地图级别
		map.setCurrentCity("${bean.city_name}");// 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放

		local = new BMap.LocalSearch(map, {
			renderOptions: {map: map, panel: "r-result", selectFirstResult: false},
			onSearchComplete: function(results){
				if (local.getStatus() == BMAP_STATUS_SUCCESS){
					// 判断状态是否正确
					places = [];
					for (var i = 0; i < results.getCurrentNumPois(); i ++){
						var place = results.getPoi(i);
						places.push({"uid":place.uid, "title":place.title, "address":place.address, "lat":place.point.lat, "lng":place.point.lng});
					}
				} else {
					places = [];
				}
			}
		});
		
		$("#r-result li").live("click", function(){
			var idx = $(this).index();
			var p = places[idx];
			if (p) {
				$("#baidu_uid").val(p.uid);
				$("#baidu_title").val(p.title);
				$("#baidu_address").val(p.address);
				$("#baidu_longtitude").val(p.lng);
				$("#baidu_latitude").val(p.lat);
			}
		});
		
		$("#search_keyword").keyup(function(event){
			if (event.which == 13) {
				search_onclick();
			}
		});
		
		search_onclick();
		
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div>
			<table class="insert_table">
				<tr>
					<td class="field" style="width:50%" valign="top">
						<div style="line-height:30px;">
							当前区域：<input type="text" name="full_name" class="full_input readonly" style="width:70%;" inputName="区域全称" value="<c:out value="${bean.full_name }"/>" maxlength="100" validate=""/><br>
							百度地名：
							<input type="hidden" id="baidu_uid" name="baidu_uid" value="<c:out value="${bean.baidu_uid }"/>"/>
							<input type="text" id="baidu_title" name="baidu_title" class="readonly" style="width:70%;" inputName="百度地名" value="<c:out value="${bean.baidu_title }"/>" maxlength="100" validate=""/><br>
							详细地址：
							<input type="text" id="baidu_address" name="baidu_address" class="readonly" style="width:70%;" inputName="详细地址" value="<c:out value="${bean.baidu_address }"/>" maxlength="100" validate=""/><br>
							<input type="hidden" id="baidu_longtitude" name="baidu_longtitude" value="<c:out value="${bean.baidu_longtitude }"/>"/>
							<input type="hidden" id="baidu_latitude" name="baidu_latitude" value="<c:out value="${bean.baidu_latitude }"/>"/>
							关键词：
							<input type="text" id="search_keyword" name="search_keyword" class="full_input" style="width:70%;" inputName="关键词" value="<c:out value="${bean.name }"/>" maxlength="100" validate=""/>
							<input type="button" name="search" value="搜索" class="button" onclick="search_onclick();" />
							<div id="r-result"></div>
						</div>
					</td>
					<td class="field" style="width:50%" valign="top">
						<div style="margin:5px 5px 5px 0px;border:1px solid #ccc;width:450px;height:450px;">
							<div id="l-map"></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
	</form>
</body>
</html>
