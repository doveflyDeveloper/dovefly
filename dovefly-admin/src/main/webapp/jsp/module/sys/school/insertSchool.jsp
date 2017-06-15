<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学校<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
#l-map {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
#r-result {width:380px;height:340px;overflow:auto;}

-->
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=splMEg7d2hdwTAe1RlKkvEX3"></script>
<script type="text/javascript">
	var formHelper = new DvFormHelper("schoolController");
	
	/** 保存 */
	function save_onclick() {
		if(validateForm()){
			if(<%=isModify%>){
				update_onclick();
			} else {
				insert_onclick();
			}
		}
	}
	
	/** 新增保存 */
	function insert_onclick() {
		formHelper.jSubmit(formHelper.buildInsertAction());
	}
	
	/** 修改保存 */
	function update_onclick() {
		formHelper.jSubmit(formHelper.buildUpdateAction());
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
		map.setCurrentCity("${bean.city_name}" || "上海");// 设置地图显示的城市 此项是必须设置的
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
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">学校<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_school"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${bean.city_id }" hasEmpty="true" attributes="inputName='城市' validate='notNull'"/>
						</td>
						<td class="field" rowspan="4" colspan="2">
							<div style="margin:5px 5px 5px 0px;border:1px solid #ccc;width:450px;height:450px;">
								<div id="l-map"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>货仓：</td>
						<td class="field">
							<dv:select name="warehouse_id" dicKeyword="DIC_SYS_WAREHOUSE" defaultValue="${bean.warehouse_id }" hasEmpty="true" attributes="inputName='货仓' validate='notNull'"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>学校：</td>
						<td class="field">
							<input type="text" name="school_name" class="input" inputName="学校" value="<c:out value="${bean.school_name }"/>" maxlength="20" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>百度地址：</td>
						<td class="field">
							<div style="line-height:30px;">
								百度地名：
								<input type="hidden" id="baidu_uid" name="baidu_uid" value="<c:out value="${bean.baidu_uid }"/>"/>
								<input type="text" id="baidu_title" name="baidu_title" class="readonly" style="width:70%;" inputName="百度地名" value="<c:out value="${bean.baidu_title }"/>" maxlength="100" validate=""/><br>
								详细地址：
								<input type="text" id="baidu_address" name="baidu_address" class="readonly" style="width:70%;" inputName="详细地址" value="<c:out value="${bean.baidu_address }"/>" maxlength="100" validate=""/><br>
								<input type="hidden" id="baidu_longtitude" name="baidu_longtitude" value="<c:out value="${bean.baidu_longtitude }"/>"/>
								<input type="hidden" id="baidu_latitude" name="baidu_latitude" value="<c:out value="${bean.baidu_latitude }"/>"/>
								关键词：
								<input type="text" id="search_keyword" name="search_keyword" class="full_input" style="width:70%;" inputName="关键词" value="<c:out value="${bean.school_name }"/>" maxlength="100" validate=""/>
								<input type="button" name="search" value="搜索" class="button" onclick="search_onclick();" />
								<div id="r-result"></div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="warehouse_name" value="<c:out value="${bean.warehouse_name }"/>"/>
		<input type="hidden" name="manager_id" value="<c:out value="${bean.manager_id }"/>"/>
		<input type="hidden" name="manager_name" value="<c:out value="${bean.manager_name }"/>"/>
		<input type="hidden" name="search_times" value="<c:out value="${bean.search_times }"/>"/>
		<input type="hidden" name="shop_count" value="<c:out value="${bean.shop_count }"/>"/>
		<input type="hidden" name="user_count" value="<c:out value="${bean.user_count }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
