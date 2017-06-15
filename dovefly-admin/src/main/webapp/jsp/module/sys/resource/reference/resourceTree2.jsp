<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.resource.vo.ResourceVo" %>
<%@ page import="java.util.List" %>
<%
	List<ResourceVo> beans = (List<ResourceVo>) request.getAttribute("beans");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/frame.css"/>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/menu.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/prototype.lite.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/moo.fx.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/moo.fx.pack.js"></script>
<script type="text/javascript"> 
	function showMenu(tab_id, tab_title, tab_url) { 
		
		if(!tab_id) tab_id = "9999";
		if(!tab_title) tab_title = "无标题";
		if(!tab_url) tab_url = "/error_url.jsp";
		if(tab_url.charAt(0) == "/") tab_url = "<%=request.getContextPath()%>" + tab_url;
		top.rightFrame.addTab(tab_id, tab_title, tab_url);
	}
	
	//重设菜单栏的高度
	function resetHeight() { 
		document.getElementById("menu_frame_div").style.height = document.documentElement.clientHeight-(31+17) + "px"; 
	}
	
	//切换菜单
	function changeMenu() { 
		window.location.href = "<%=request.getContextPath()%>/resourceController/resourceTreeNode";
	}
	
	window.onload = resetHeight;
	window.onresize = resetHeight;
	
</script> 
<style type="text/css">
<!--

-->
</style>
</head>
<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB">
		<tr>
			<td width="215" valign="top">

				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<!-- 以下是框架主头部标签页区域 -->
					<tr height="31">
						<td width="17" valign="top" class="frame_left_bg">
							<div class="nav_left"></div>
						</td>
						<td class="nav_bg" align="left">
							菜单功能区域
						</td>
						<td width="60" valign="top" class="frame_right_bg">
							<div class="nav_right">[<a href="#" onclick="javascript:changeMenu();">切换</a>]</div>
						</td>
					</tr>
					<!-- 以下是框架主显示区域 -->
					<tr>
						<td valign="middle" class="frame_left_bg"></td>
						<td valign="top" bgcolor="#F7F8F9">
							<!--显示区域-->
							<div id="menu_frame_div">
								<div>
								<%
								if(beans != null && beans.size() > 0) {
									for(int i = 0; i < beans.size(); i++){
										ResourceVo vo = beans.get(i);
										if(!"1".equals(vo.getIs_leaf())){
								%>			
									<h1 class="type">
										<a href="javascript:void(0)"><%=vo.getName() %></a>
									</h1>
									<div class="content">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td>
													<img src="resources/images/menu_topline.gif" width="182" height="5" />
												</td>
											</tr>
										</table>
										<ul class="MM">
										<%
											for(int j = 0; j < beans.size(); j++){
												ResourceVo vo1 = beans.get(j);
												if("1".equals(vo1.getIs_leaf()) && vo1.getParent_id().equals(vo.getId())){
										%>
											<li><a href="javascript:void(0);" onclick="showMenu('<%=vo1.getId() %>','<%=vo1.getName() %>','<%=vo1.getUrl() %>')"><%=vo1.getName() %></a></li>
										<%
												}
											}
										%>
										</ul>
									</div>	
								<%
										}
									}
								}
								%>
								</div>
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
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	var contents = document.getElementsByClassName('content');
	var toggles = document.getElementsByClassName('type');

	var myAccordion = new fx.Accordion(
		toggles, contents, {opacity: true, duration: 400}
	);
	myAccordion.showThisHideOpen(contents[0]);
</script>
</html>

