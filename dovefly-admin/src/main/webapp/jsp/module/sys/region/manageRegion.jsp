<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>区域管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.exedit-3.5.min.js"></script>
<style type="text/css">
<!--

-->
</style>
<script type="text/javascript">
	var zNodes = ${nodes };

	var setting = {
		async: {
			enable: true,
			url:"<%=request.getContextPath()%>/regionController/loadRegions",
			autoParam:["id"],
			//autoParam:["id", "name=n", "level=lv"],
			otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter: filter
		},
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRenameBtn: true,
			renameTitle: "修改",
			showRemoveBtn: true,
			removeTitle: "删除"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeRemove: beforeRemove,
			beforeRename: beforeRename,
			onRemove: onRemove,
			onRename: onRename
		}
		
	};
	
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	
	function beforeRemove(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		var canDelete = false;
		var canNotDeleteMsg = "";
		$.ajax({
			type: "POST",
			async: false,
			dataType: "json",
			url: context_path + "/regionController/checkCanDelete/" + treeNode.id,
			data: {},
			success: function(result){
				if(result){//存在
					if(result.success){
						canDelete = true;
						//dvTip(result.message, "success");
					} else {
						//dvTip(result.message, "error");
						canNotDeleteMsg = result.message;
					}
				}
			}
		});
		if (canDelete) {
			return confirm("确认删除节点<" + treeNode.name + ">及其所有子节点吗？");
		} else {
			alert(canNotDeleteMsg);
			return false;
		}
	}
	
	function onRemove(event, treeId, treeNode) {
		//alert(treeNode.tId + ", " + treeNode.name + ", " + treeNode.id);
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: context_path + "/regionController/ajaxDelete/" + treeNode.id,
			data: {},
			success: function(result){
				if(result){//存在
					if(result.success){
						dvTip(result.message, "success");
					} else {
						dvTip(result.message, "error");
					}
				}
			}
		});
	}
	
	function beforeRename(treeId, treeNode, newName) {
		if (newName.length == 0) {
			alert("节点名称不能为空");
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		return true;
	}
	
	function onRename(event, treeId, treeNode, isCancel) {
		//alert(treeNode.tId + ", " + treeNode.name + ", " + treeNode.id);
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: context_path + "/regionController/ajaxUpdate/" + treeNode.id,
			data: {"name":treeNode.name},
			success: function(result){
				if(result){//存在
					if(result.success){
						dvTip(result.message, "success");
						
						//如果是学校的层级且未设置过百度地图信息，则弹出百度定位窗口，让用户选择百度地址
						if (result.data.level == 4 && !result.data.baidu_uid) {
							dvOpenDialog(context_path + "/regionController/find4Locate/" + result.data.id + "?tId=" + treeNode.tId, "百度地图定位", 800, 500, {top:'5%'});
						}
						
					} else {
						dvTip(result.message, "error");
					}
				}
			}
		});
		return false;
	}
	
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
		
		var btnStr = "";
/*		if (treeNode.region_level == 6) {//只有楼栋层级才会有查看店铺的按钮
			btnStr += "<span class='button shop' id='shopBtn_" + treeNode.tId + "' title='查看店铺' onfocus='this.blur();'></span>";
		}*/
		if (treeNode.region_level == 4) {//只有学校层级才会有百度定位的按钮
			btnStr += "<span class='button locate' id='locateBtn_" + treeNode.tId + "' title='百度定位' onfocus='this.blur();'></span>";
		}
		btnStr += "<span class='button sort' id='sortBtn_" + treeNode.tId + "' title='子节点排序' onfocus='this.blur();'></span>";
		btnStr += "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增' onfocus='this.blur();'></span>";
		sObj.after(btnStr);
		
		var btn_add = $("#addBtn_" + treeNode.tId);
		if (btn_add) {
			btn_add.bind("click", function(){
				//alert(treeNode.tId + ", " + treeNode.name + ", " + treeNode.id);
				$.ajax({
					type: "POST",
					async: true,
					dataType: "json",
					url: context_path + "/regionController/ajaxAdd/" + treeNode.id,
					data: {"name":"新节点"},
					success: function(result){
						if(result){//存在
							if(result.success){
								var zTree = $.fn.zTree.getZTreeObj("treeDemo");
								var node = {
									id:result.data.id, 
									pId:result.data.parent_id, 
									code:result.data.code, 
									name:result.data.name, 
									full_name:result.data.full_name, 
									region_level:result.data.level
								};
								var newNode = zTree.addNodes(treeNode, node);
								zTree.editName(newNode[0]);
							} else {
								dvTip(result.message, "error");
							}
						}
					}
				});
				return false;
			});
		}
		
		var btn_sort = $("#sortBtn_" + treeNode.tId);
		if (btn_sort) {
			btn_sort.bind("click", function(){
				dvOpenDialog(context_path + "/regionController/find4Sort/" + treeNode.id + "?tId=" + treeNode.tId, "子节点排序", 800, 400, {});
				return false;
			});
		}
		
		//只有学校层级才会有百度定位的按钮
		if (treeNode.region_level == 4) {
			var btn_locate = $("#locateBtn_" + treeNode.tId);
			if (btn_locate) {
				btn_locate.bind("click", function(){
					dvOpenDialog(context_path + "/regionController/find4Locate/" + treeNode.id + "?tId=" + treeNode.tId, "百度地图定位", 850, 550, {top:'5%'});
					return false;
				});
			}
		}
		
		//只有楼栋层级才会有查看店铺的按钮
		/*
		if (treeNode.region_level == 6) {
			var btn_shop = $("#shopBtn_" + treeNode.tId);
			if (btn_shop) {
				btn_shop.bind("click", function(){
					dvOpenDialog(context_path + "/shopController/reference?region_id=" + treeNode.id + "&region_code=" + treeNode.code + "&region_full_name=" + encodeURI(treeNode.full_name), 
							"查看店铺", 800, 500, {top:'5%', buttons:{'关闭' : true}});
					return false;
				});
			}
		}*/
		
	};
	
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
		$("#sortBtn_" + treeNode.tId).unbind().remove();
		$("#locateBtn_" + treeNode.tId).unbind().remove();
//		$("#shopBtn_" + treeNode.tId).unbind().remove();
	};
	
	function reloadChidrenAfterSort(tId) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var node = zTree.getNodeByTId(tId);
		zTree.reAsyncChildNodes(node, "refresh");
	}
	
	function changeListMode_onclick() {
		window.location.href = context_path + "/regionController/query?level=4";
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
</script> 
</head>
<body>
	<div class="border_div" style="margin:1px;">
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">区域&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
					<input type="button" name="mode" value="列表模式" class="button" onclick="changeListMode_onclick();"/>
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>