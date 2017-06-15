<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品<%=isModify ? "修改" : "新增"%></title>
<link href="<%=request.getContextPath()%>/resources/js/umeditor/themes/default/css/umeditor.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.config.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.min.js"></script>
<!-- 图片上传插件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/zyUpload/css/zyUpload.css" type="text/css">
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyFile.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyUpload.js"></script>

<!-- 
<script src="<%=request.getContextPath()%>/resources/js/lightbox/jquery.lightbox.js"></script>
 -->

<style type="text/css">
<!--
#gallery { width:90%; list-style-type:none; margin:0px; }
#gallery li {width:120px;height:120px;float:left;margin:10px 0px 0px 10px;text-align:center;cursor:pointer;position:relative;overflow:hidden;}
#gallery img.pic {width:120px;height:120px;}
#gallery .control {width:120px;height:0px;background:rgba(0,0,0,0.4);position:absolute;left:0;bottom:0;color:#fff;font-family:"微软雅黑";font-size:14px;line-height:30px;}
#gallery .control a {font-size:14px;}
.placeHolder div { background-color:white !important; border:dashed 1px gray !important; }
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("mmItemController");
	
	/** 保存 */
	function save_onclick() {
		prepareImages();
		if(checkImagesAndDescription()){
			if (!checkInternetBarcode($("#barcode").val())) {
				dvConfirm("联网查询此条形码不存在，<br>是否仍使用此条形码作为本商品条形码？", 
					function() {
						if(<%=isModify%>){
							update_onclick();
						} else {
							insert_onclick();
						}
					}, 
					function() {
						//alert("干嘛要取消啊？");
					}
				);
			} else {
				if(<%=isModify%>){
					update_onclick();
				} else {
					insert_onclick();
				}
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
	
	$(document).ready(function() {
		
		var um = UM.getEditor('description');
		um.options.imageUrl = context_path + "/ueditor/image/goods";
		
		// 图片上传插件初始化
		$("#galleryUpload").zyUpload({
			width            :   "100%",                 // 宽度
			height           :   "auto",                 // 宽度
			itemWidth        :   "120px",                 // 文件项的宽度
			itemHeight       :   "100px",                 // 文件项的高度
			url              :   context_path + "/upload/image/goods",  // 上传文件的路径
			multiple         :   true,                    // 是否可以多个文件上传
			dragDrop         :   false,                    // 是否可以拖动上传文件
			del              :   true,                    // 是否可以删除文件
			finishDel        :   true,  				  // 是否在上传文件完成后删除预览
			
			onSelect: function(files, allFiles){                    // 选择文件的回调方法

			},
			onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法

			},
			onSuccess: function(file, response){                    // 文件上传成功的回调方法
				var image = JSON.parse(response);
				addNewImage(image);
			},
			onFailure: function(file){                    // 文件上传失败的回调方法

			},
			onComplete: function(responseInfo){           // 上传完成的回调方法

			}
		});
		
		//初始化相册历史图片
		initImages();
		
		//初始化相册，使图片可拖拉排序
		$("#gallery").dragsort({ dragSelector: "div", dragBetween: false, dragEnd: saveOrder, placeHolderTemplate: "<li class='placeHolder'><div></div></li>" });
		
		//图片放大
		//$("#manualGroup a").lightbox();
		
	});
	
	function saveOrder() {
		//var data = $("#gallery li").map(function() { return $(this).children().html(); }).get();
		//$("input[name=list1SortOrder]").val(data.join("|"));
	};
	
	function initImages() {
		var html = "";
		var images = eval('${bean.images}' || '[]');
		for (var i = 0; i < images.length; i++) {
			html += '<li>' + 
						'<div>' + 
						'	<img class="pic" title="' + (images[i].title || '') + '" path="' + images[i].path + '" src="' + images[i].path + '?imageView2/0/w/200"/>' + 
						'	<div class="control"><a onclick="zoomImage();">放大</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="deleteImage();">删除</a></div>' + 
						'</div>' + 
					'</li>';
		}
		
		$('#gallery').append(html);
		
		//相册图片，悬停效果（放大，删除）
		$("#gallery li").hover(function(){
			$(this).find(".control").stop().animate({height:"30px"},200);
		},function(){
			$(this).find(".control").stop().animate({height:"0px"},200);
		})
	}
	
	function prepareImages() {
		var images = [];
		$('#gallery').find('img.pic').each(function () {
			images.push({"title":$(this).attr("title"),"path":$(this).attr("path")});
		});
		$("input[name='images']").val(JSON.stringify(images));
		if (images.length > 0) {
			$("input[name='image']").val(images[0].path);
		}
	}
	
	function checkImagesAndDescription() {
		if (!validateForm()) {
			mytab.tab(1);
			return false;
		} else if ("[]" == $("input[name='images'").val()) {
			dvAlert("必须上传至少一张商品相关的图片！");
			mytab.tab(2);
			return false;
		} else if ("" == UM.getEditor('description').getContent()) {
			dvAlert("必须填写商品相关描述信息！");
			mytab.tab(3);
			return false;
		}
		return true;
	}
	
	function addNewImage(img) {
		var html =  '<li>' + 
						'<div>' + 
						'	<img class="pic" title="" path="' + img.path + '" src="' + img.path + '?imageView2/0/w/200"/>' + 
						'	<div class="control"><a onclick="zoomImage();">放大</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="deleteImage();">删除</a></div>' + 
						'</div>' + 
					'</li>';
		
		$(html).appendTo("#gallery").hover(function(){
			$(this).find(".control").stop().animate({height:"30px"},200);
		},function(){
			$(this).find(".control").stop().animate({height:"0px"},200);
		})
	}
	
	function zoomImage(e) {
		alert("放大图片");
	}
	
	function deleteImage(e) {
		alert("删除图片");
		var theEvent = window.event || e;
		var theObj = theEvent.target || theEvent.srcElement;
		$(theObj).closest("li").remove();
	}
	
	function checkInternetBarcode(barcode) {
		var ret = false;
		
		$.ajax({
			type: "GET",
			async: false,
			dataType: "json",
			url: context_path + "/mmItemController/checkInternetBarcode",
			data: {"barcode":barcode},
			success: function(result){
				if(result && result.success && result.data){//已存在
					ret = true;
				}
			}
		});
		
		return ret;
	}
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">商品<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_item"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>基本信息</DT>
					<DD>
						<div>
							<table class="insert_table">
								<tr>
									<td class="label"><span class="required_red">*</span>条形码：</td>
									<td class="field">
										<input type="text" id="barcode" name="barcode" class="input" inputName="条形码" value="<c:out value="${bean.barcode }"/>" maxlength="20" validate="notNull;existsBarcode"/>
									</td>
									<td class="label"><span class="required_red">*</span>商品名称：</td>
									<td class="field">
										<input type="text" name="name" class="input" inputName="商品名称" value="<c:out value="${bean.name }"/>" maxlength="50" validate="notNull"/>
									</td>
								</tr>
								<tr>
									<td class="label"><span class="required_red">*</span>重量（克）：</td>
									<td class="field">
										<input type="text" name="weight" class="input" inputName="重量" value="<c:out value="${bean.weight }"/>" validate="notNull"/>
									</td>
									<td class="label"><span class="required_red">*</span>保质期：</td>
									<td class="field">
										<input type="text" name="shelf_life" class="input" inputName="保质期" value="<c:out value="${bean.shelf_life }"/>" maxlength="10" validate="notNull"/>
									</td>
								</tr>
								<tr>
									<td class="label"><span class="required_red">*</span>生产商：</td>
									<td class="field">
										<input type="text" name="producer" class="input" inputName="生产商" value="<c:out value="${bean.producer }"/>" maxlength="50" validate="notNull"/>
									</td>
									<td class="label"><span class="required_red">*</span>商品规格：</td>
									<td class="field">
										<input type="text" name="spec" class="input" inputName="商品规格" value="<c:out value="${bean.spec }"/>" maxlength="50" validate="notNull"/>
									</td>
								</tr>
							</table>
						</div>
					</DD>
					<DT>图片</DT>
					<DD>
						<div id="galleryUpload"></div>
						<ul id="gallery"></ul>
					</DD>
					<DT>描述</DT>
					<DD>
						<div>
							<script id="description" name="description" type="text/plain" inputName="描述">${bean.description }</script>
						</div>
					</DD>
				</DL>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="image" value="<c:out value="${bean.image }"/>"/>
		<input type="hidden" name="images" value="<c:out value="${bean.images }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
