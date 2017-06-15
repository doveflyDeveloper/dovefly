<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<meta name="renderer" content="webkit">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/frame.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.7.min.js"></script>
<style type="text/css">
<!--

-->
</style>
<script type="text/javascript">
	var mainTabs = new Array(); //创建一个数组，记录其增加的顺序。
	var limitTabsNum = 10;
	var current_select_tab_id = "";
	
	mainTabs.push("welcome");
	current_select_tab_id = "welcome";
	
	function addTab(new_tab_id,new_tab_title,new_tab_url) {
		if(new_tab_title.length > 10) 
			new_tab_title = new_tab_title.substring(0, 10);
		var hasExist = false;
		for(var index in mainTabs) {//检查是否已经打开过
			var tab_id = mainTabs[index]; 
			if(new_tab_id == tab_id){//存在已显示的标签页
				hasExist = true;
				break;
			}
		}
		if(!hasExist){//新的请求页
			/*if(mainTabs.length >= limitTabsNum){
				alert("同时最多只能打开 " + limitTabsNum + " 个标签页。");
				return;
			}*/
			mainTabs.push(new_tab_id);
			$("#curTab").removeAttr("id");
			var tab_html = '<ul class="nav" tab_id="' + new_tab_id + '" onclick="selectTab(\'' + new_tab_id + '\')"><li class="left"></li><li class="center">' + new_tab_title + '<div class="closeTab" title="关闭此标签页"></div></li><li class="right"></li></ul>';
			$("#nav_contain_div").append(tab_html);
			var content_html = '<div class="" id="content_' + new_tab_id + '" style="display: none">' +
					'<iframe src="' + new_tab_url + '" id="frame_' + new_tab_id + '" name="frame_' + new_tab_id + '" frameborder="0" width="100%" height="' + getIframeHeight() + '" onload="doLoad(this);" onresize="doLoad(this);"></iframe>' +
					'</div>';
			$("#frame_content_td").append(content_html);
		} else {//已经打开此页，在原标签页重新打开
			$("#frame_" + new_tab_id).attr("src", new_tab_url);
		}
		selectTab(new_tab_id);
	}
	
	//删除标签页
	function delTab(imgObj) {
		var del_tab_id = $(imgObj).parent().parent().attr("tab_id");
		for(var index in mainTabs) {
			var tab_id = mainTabs[index]; 
			if(del_tab_id == tab_id){//存在已显示的标签页
				mainTabs.splice(index,1); 
				break;
			}
		}
		$(imgObj).parent().parent().remove();
		$("#content_" + del_tab_id).remove();
		if(current_select_tab_id == del_tab_id){
			selectTab(mainTabs[mainTabs.length-1]);
		}
	}
	
	//选中显示标签页
	function selectTab(select_tab_id) {
		if(current_select_tab_id != select_tab_id){
			$("#curTab").removeAttr("id");
			$("#content_" + current_select_tab_id).hide();
			$("#nav_contain_div > ul[tab_id='" + select_tab_id + "']").attr("id", "curTab");
			$("#content_" + select_tab_id).show();
			current_select_tab_id = select_tab_id;
		}
		resetTabPosition(select_tab_id);
		resetIframeHeight();
	}
	
	//获取标签页宽度
	function getTabWidth(tab_id){
		return $("#nav_contain_div > ul[tab_id='" + tab_id + "']").width();
	}
	
	//获得当前标签页（不包含本身）之前的标签页的总宽度
	function getAllBeforeTabsWidth(curr_tab_id){
		var all_width = 0;
		$("#nav_contain_div > ul").each(function() {
			var curr_width = $(this).width();
			if($(this).attr("tab_id") == curr_tab_id){
				return false;
			};
			all_width = all_width + curr_width + 10;
		});
		return all_width;
	}
	
	//获取所有标签页宽度
	function getAllTabsWidth() {
		var all_width = 0;
		$("#nav_contain_div > ul").each(function() {
			var curr_width = $(this).width();
			all_width = all_width + curr_width + 10;
		});
		return all_width;
	}
	
	//获取标签行容器的宽度
	function getNavDivWith() { 
		return $("#nav_width_limit_div").width();
	}
	
	//重设标签行容器的宽度
	function resetNavDivWith() { 
		$("#nav_width_limit_div").css("width", document.documentElement.clientWidth-120);
	}
	
	//获取iframe高度
	function getIframeHeight() {
		return document.documentElement.clientHeight-(31+17);
	}
	
	//重设iframe的高度
	function resetIframeHeight() { 
		$("iframe").attr("height", document.documentElement.clientHeight-(31+17));
	}
	//这个是框架自适应文档的
/* 	function resetIFrameHeight(iframe) { 
		var bHeight = iframe.contentWindow.document.body.scrollHeight;
		var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		var height = Math.max(bHeight, dHeight);
		iframe.height =  height;
		alert(height);
	}  */
	
	//刷新，重新加载当前iframe的内容
	function refresh() {
		window.frames["frame_" + current_select_tab_id].window.location.reload();
	}

	function doLoad(iframe){

		var load_tab_id = $(iframe).attr("id").replace(/frame_/, "");
		if(load_tab_id != current_select_tab_id){
			selectTab(load_tab_id);
		} else {
			resetTabPosition(current_select_tab_id);
			resetIframeHeight();
		}
	}
	
	function resetTabPosition(select_tab_id){
		var select_width = getTabWidth(select_tab_id);
		var all_width = getAllTabsWidth();
		var nav_div_width = getNavDivWith();
		//alert($("#nav_contain_div").offset().left);
		
		if(getAllBeforeTabsWidth(select_tab_id) < $("#nav_width_limit_div").scrollLeft()){//被左边隐藏
			$("#nav_width_limit_div").scrollLeft(getAllBeforeTabsWidth(select_tab_id));
		}
		if(getAllBeforeTabsWidth(select_tab_id) + getTabWidth(select_tab_id) > $("#nav_width_limit_div").scrollLeft() + nav_div_width){//被右边隐藏
			$("#nav_width_limit_div").scrollLeft(getAllBeforeTabsWidth(select_tab_id) + getTabWidth(select_tab_id) - nav_div_width + 90);
		}
		
//		if(all_width > (nav_div_width - 90)){
//			$("#nav_width_limit_div").scrollLeft((all_width - nav_div_width) + 90);
//		}
		//alert($("#nav_width_limit_div").scrollLeft());
		
	}
	
	function moveTabPosition(to){
		//var select_width = getTabWidth(select_tab_id);
		var all_width = getAllTabsWidth();
		var nav_div_width = getNavDivWith();
		//alert($("#nav_contain_div").offset().left);
		/* var all_width = 0;
		$("#nav_contain_div > ul").each(function() {
			var curr_width = $(this).width();
			all_width = all_width + curr_width + 10;
		});
		return all_width; */
		
		
		
		if(all_width > (nav_div_width - 90)){
			if(to == 'to_right'){
				$("#nav_width_limit_div").scrollLeft($("#nav_width_limit_div").scrollLeft() + 90);
			}else if(to == 'to_left'){
				$("#nav_width_limit_div").scrollLeft($("#nav_width_limit_div").scrollLeft() - 90);
			}
			
		}
		//alert($("#nav_width_limit_div").scrollLeft());
		
	}
	
	$(document).ready(function() {
		resetNavDivWith();
		$(window).bind("resize", resetNavDivWith);
		$(window).bind("resize", resetIframeHeight);
		
		//“刷新图标”单击事件
		$("#refreshTab").live("click", function() {
			refresh();
		});
		
		//“标签关闭”单击事件
		$(".closeTab").live("click", function() {
			delTab(this);
		});
		
		var interval = null;
		//“标签移动”单击事件
		$("#scrollTabLeft").live({
			mousedown: function() {
				moveTabPosition("to_left");
				interval = setInterval("moveTabPosition('to_left')", 200);
			},
			mouseup: function() {
				clearInterval(interval);
			}
		});
		
		//“标签移动”单击事件
		$("#scrollTabRight").live({
			mousedown: function() {
				moveTabPosition("to_right");
				interval = setInterval("moveTabPosition('to_right');", 200);
			},
			mouseup: function() {
				clearInterval(interval);
			}
		});
		
		window.document.oncontextmenu = function() { return false;};//屏蔽标签页的右键功能
		window.document.onselectstart = function() { return false;};//屏蔽标签页的右键功能
		
	});

</script>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<!-- 以下是框架主头部标签页区域 -->
		<tr height="31">
			<td width="17" valign="top" class="frame_left_bg">
				<div class="nav_left"></div>
			</td>
			<td class="nav_bg" align="left">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="31">
						<td valign="top" >
							<div id="nav_width_limit_div">
								<div id="nav_contain_div">
									<ul id="curTab" class="nav" tab_id="welcome" onclick="selectTab('welcome')"><li class="left"></li><li class="center">&nbsp;首页&nbsp;</li><li class="right"></li></ul>
								</div>
							</div>
						</td>
						<td valign="middle" align="left" width="28">
							<div id="scrollTabLeft" class="scroll_left" title="左移标签页"></div>
						</td>
						<td valign="middle" align="left" width="28">
							<div id="scrollTabRight" class="scroll_right" title="右移标签页"></div>
						</td>
						<td valign="middle" width="20">
							<div id="refreshTab" class="refresh" title="刷新当前页"></div>
						</td>
					</tr>
				</table>
			</td>
			<td width="16" valign="top" class="frame_right_bg">
				<div class="nav_right"></div>
			</td>
		</tr>
		<!-- 以下是框架主显示区域 -->
		<tr>
			<td valign="middle" class="frame_left_bg"></td>
			<td id="frame_content_td" valign="top" bgcolor="#F7F8F9">
				<!--显示区域-->
				<div class="" id="content_welcome" style="display: block">
					<iframe id="frame_welcome" name="frame_welcome" src="<%=request.getContextPath() %>/userController/welcome" width="100%" height="500" frameborder="0" onload="doLoad(this);" onresize="doLoad(this);"></iframe>
				</div>
			</td>
			<td class="frame_right_bg"></td>
		</tr>
		<!-- 以下是框架底部边框 -->
		<tr>
			<td valign="bottom" class="frame_left_bg">
				<div class="bottom_left_corner"></div>
			</td>
			<td class="frame_bottom_bg">	
						
			</td>
			<td valign="bottom" class="frame_right_bg">
				<div class="bottom_right_corner"></div>
			</td>
		</tr>
	</table>
</body>
</html>