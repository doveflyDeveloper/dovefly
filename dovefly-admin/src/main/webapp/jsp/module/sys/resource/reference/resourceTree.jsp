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
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<style type="text/css">
<!--
i.info {
  background:rgb(255,97,86);
  border-radius:25%;
  width:30px;
  height:15px;
  color:#FFF;
}

.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
-->
</style>
<script type="text/javascript"> 
	function showMenu(tab_id, tab_title, tab_url) { 
		$("#info_" + tab_id).html("");
		if(!tab_id) tab_id = "9999";
		if(!tab_title) tab_title = "无标题";
		if(!tab_url) tab_url = "/error_url.jsp";
		if(tab_url.charAt(0) == "/") tab_url = "<%=request.getContextPath()%>" + tab_url;
		top.rightFrame.addTab(tab_id, tab_title, tab_url);
	}
	
	function setInfoTip(id, num) {
		if (num > 0) {
			if (num < 10) {
				num = "&nbsp;" + num + "&nbsp;";
			}
			$("#info_" + id).html(num);
		}
	}
	
	//重设菜单栏的高度
	function resetHeight() { 
		document.getElementById("menu_frame_div").style.height = (document.documentElement.clientHeight-(31+17)) + "px"; 
	}
	
	window.onload = resetHeight;
	window.onresize = resetHeight;
	
	/** 展开全部 */
	function expandAll_onclick(id){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(true);
	}
	
	/** 折叠全部 */
	function collapseAll_onclick(id){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(false);
	}
	
	//切换菜单
	function changeMenu() { 
		window.location.href = "<%=request.getContextPath()%>/resourceController/resourceTreeNode2";
	}
	
	var setting = {
		data: {
			key: {
				title: "t"
			},
			simpleData: {
				enable: true
			}
		},
		view: {
			fontCss: getFont,
			nameIsHTML: true
		}
	};

	function getFont(treeId, node) {
		return node.font ? node.font : {};
	}
	
	var zNodes = [
<%
if(beans != null && beans.size() > 0) {
	for(int i = 0; i < beans.size(); i++){
		ResourceVo vo = beans.get(i);
%>
		{ id:"<%=vo.getId() %>", pId:"<%=vo.getParent_id() %>", t:"", name:"<%=vo.getName() %>&nbsp;<i id='info_<%=vo.getId() %>' class='info'></i>"<%=("1".equals(vo.getIs_leaf())) ? ", click:\"javascript:showMenu('" + vo.getId() + "', '" + vo.getName() + "', '" + vo.getUrl() +"')\"" : ", isParent:true, open:true, font:{'font-weight':'bold'}" %><%=("000".equals(vo.getCode())) ? ", iconOpen:'../resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/img/diy/1_open.png', iconClose:'../resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/img/diy/1_close.png'" : "" %>}<%= (i+1 == beans.size()) ? "" : "," %>
<%
	}
}
%>
	];
	
	//加载待办任务数量
	function loadTaskTag() {
		$.ajax({
			type: "GET",
			async: true,
			dataType: "json",
			url: "<%=request.getContextPath()%>/resourceController/loadTaskTag",
			data: {},
			success: function(result){
				if(result){//存在
					if(result.success){
						setInfoTip('25', result.data.message_count);//未读消息
						setInfoTip('75', result.data.coupon_count);//未用代金券
						setInfoTip('86', result.data.order_bill_count);//店长进货订单
						setInfoTip('77', result.data.trade_bill_count);//店长销售订单
						setInfoTip('58', result.data.order_back_bill_count);//店长退货单
						setInfoTip('88', result.data.user_apply_count);//用户申请
						setInfoTip('71', result.data.fund_apply_count);//提现申请
						setInfoTip('110', result.data.fund_refund_count);//退款申请
						
						window.audioPlayTimes = 0;
						if (result.data.trade_bill_count_submit > 0) {
						//if (1 > 0) {
							if (document.getElementById("autoSearch").checked){
								playAudio();
							}
						}
					}
				}
			}
		});
	}
	
	window.audioPlayTimes = 0;
	function playAudio() {
		var myAuto = document.getElementById('myaudio');
		if (window.audioPlayTimes >= 5) {
			myAuto.pause();
		} else {
			myAuto.play();
		}
		window.audioPlayTimes++;
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		
		loadTaskTag();
		
		setInterval(loadTaskTag, 1000*60*5);
		
		var myAuto = document.getElementById('myaudio');
 		if (myAuto.addEventListener) {//所有主流浏览器，除了 IE 8 及更早 IE版本
 			myAuto.addEventListener("ended", playAudio);
 		} else if (x.attachEvent) {// IE 8 及更早 IE 版本
 			myAuto.attachEvent("ended", playAudio);
 		}
		
	});
</script>
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
							&nbsp;<label style="vertical-align: top; line-height: 18px;" title="有新订单，铃声提醒"><input type="checkbox" name="autoSearchFlag" id="autoSearch" checked>新订单铃声提醒</label>&nbsp;&nbsp;&nbsp;
						</td>
						<td width="60" valign="top" class="frame_right_bg">
							<div class="nav_right"></div>
						</td>
					</tr>
					<!-- 以下是框架主显示区域 -->
					<tr>
						<td valign="middle" class="frame_left_bg"></td>
						<td valign="top" bgcolor="#F7F8F9">
							<!--显示区域-->
							<div id="menu_frame_div">
								<div>
									<div class="content_wrap">
										<ul id="treeDemo" class="ztree"></ul>
									</div>
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
	<audio id="myaudio" src="<%=request.getContextPath()%>/resources/media/remind.mp3" preload="auto"></audio>
</body>
</html>



