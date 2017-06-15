<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>分类管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.exedit-3.5.min.js"></script>
<style type="text/css">
<!--
//.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
//.ztree li ul.level0 {padding:0; background:none;}
//.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}

-->
</style>
<script type="text/javascript">
	var setting = {
		async: {
			enable: true,
			url:"<%=request.getContextPath()%>/categoryController/loadCategorys",
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
			url: context_path + "/categoryController/checkCanDelete/" + treeNode.id,
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
			url: context_path + "/categoryController/ajaxDelete/" + treeNode.id,
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
			url: context_path + "/categoryController/ajaxUpdate/" + treeNode.id,
			data: {"name":treeNode.name},
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
		return false;
	}
	
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='新增' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn) btn.bind("click", function(){
			//alert(treeNode.tId + ", " + treeNode.name + ", " + treeNode.id);
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/categoryController/ajaxAdd/" + treeNode.id,
				data: {"name":treeNode.name},
				success: function(result){
					if(result){//存在
						if(result.success){
							var zTree = $.fn.zTree.getZTreeObj("treeDemo");
							var newNode = zTree.addNodes(treeNode, {id:result.data.id, pId:treeNode.id, name:"新节点"});
							zTree.editName(newNode[0]);
						} else {
							dvTip(result.message, "error");
						}
					}
				}
			});
			return false;
		});
	};
	
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting);
	});
</script> 
</head>
<body>
	<div class="border_div" style="margin:1px;">
		<div class="header_div">
			<div class="left_div">
				<div class="table_title">分类&nbsp;</div>
			</div>
			<div class="right_div">
				<div class="right_menu">
				</div>
			</div>
		</div>
		<div class="padding_2_div">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</body>
</html>