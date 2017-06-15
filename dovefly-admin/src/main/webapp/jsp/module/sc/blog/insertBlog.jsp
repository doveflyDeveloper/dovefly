<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资讯文章<%=isModify ? "修改" : "新增"%></title>
<!-- 图片上传插件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/zyUpload/css/zyUpload.css" type="text/css">
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyFile.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyUpload.js"></script>
<link href="<%=request.getContextPath()%>/resources/js/umeditor/themes/default/css/umeditor.css" rel="stylesheet">
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.config.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/umeditor/umeditor.min.js"></script>
<style type="text/css">
<!--
	ul { margin:0px; padding:0px; margin-left:20px; }
	#goods_list { list-style-type:none; margin:0px; }
	#goods_list li { float:left; padding:10px; width:90px; height:90px; }
	#goods_list div { width:90px; height:90px; text-align:center; }
	img.thumbnail { width:90px; height:90px; max-width:90px; max-height:90px; }
	
	#gallery { width:90%; list-style-type:none; margin:0px; }
	#gallery li {width:120px;height:120px;float:left;margin:10px 0px 0px 10px;text-align:center;cursor:pointer;position:relative;overflow:hidden;}
	#gallery img.pic {width:120px;height:120px;}
	#gallery .control {width:120px;height:0px;background:rgba(0,0,0,0.4);position:absolute;left:0;bottom:0;color:#fff;font-family:"微软雅黑";font-size:14px;line-height:30px;}
	#gallery .control a {font-size:14px;}
	.placeHolder div { background-color:white !important; border:dashed 1px gray !important; }

-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("blogController");
	
	/** 保存为草稿 */
	function save_onclick() {
		prepareImages();
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
	
	/** 预览 */
	function preview_onclick() {
		prepareImages();
		formHelper.jSubmit(formHelper.buildAction("blogController", "preview"));
	}
	
	/** 保存并发表 */
	function saveAndIssue_onclick() {
		prepareImages();
		if(validateForm()){
			formHelper.jSubmit(formHelper.buildAction("blogController", "saveAndIssue"));
		}
	}
	
	
	/** 新增商品 */
	function add_onclick() {
		var selected_goods_ids = $("input[name='goods_id']").map(function(){return this.value;}).get().join(",");
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/goodsController/reference?reference=special&status=1&selected_goods_ids=" + selected_goods_ids, "选择商品", 900, 450, "referenceGoods_callback", "checkbox", "search", options);
	}
	
	/** 新增商品回调 */
	function referenceGoods_callback(datas) {
		for (i in datas) {
			addNewImage(datas[i]);
		}
	}
	
	function addNewImage(goods) {
		var html =  '<li>' + 
						'<div>' + 
						'	<img class="pic" goods_id="' + goods.id + '" title="' + goods.name + '" image="' + goods.image + '" src="' + goods.image + '?imageView2/0/w/200"/>' + 
						'	<div class="control"><a onclick="deleteImage();">删除</a></div>' + 
						'</div>' + 
					'</li>';
		
		$(html).appendTo("#gallery").hover(function(){
			$(this).find(".control").stop().animate({height:"30px"},200);
		},function(){
			$(this).find(".control").stop().animate({height:"0px"},200);
		})
	}
	
	function deleteImage(e) {
		var theEvent = window.event || e;
		var theObj = theEvent.target || theEvent.srcElement;
		$(theObj).closest("li").remove();
	}
	
	function initImages() {
		var html = "";
		var goods = eval('${bean.link_goods}' || '[]');
		for (var i = 0; i < goods.length; i++) {
			html += '<li>' + 
						'<div>' + 
						'	<img class="pic" goods_id="' + goods[i].id + '" title="' + goods[i].name + '" image="' + goods[i].image + '" src="' + goods[i].image + '?imageView2/0/w/200"/>' + 
						'	<div class="control"><a onclick="deleteImage();">删除</a></div>' + 
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
			images.push({"id":$(this).attr("goods_id"), "name":$(this).attr("title"), "image":$(this).attr("image")});
		});
		$("input[name='link_goods']").val(JSON.stringify(images));
	}
	
	function changeImage(img) {
		$("input[name='image']").val(img.path);
		var imgHtml = '<img style="max-width:100px;max-height:100px;" src="' + img.path + '?imageView2/0/w/100"/>';
		$("#imgDiv").html(imgHtml);
	}
	
	$(document).ready(function() {
		var um = UM.getEditor('description');
		um.options.imageUrl = context_path + "/ueditor/image/blog";
		
		initImages();
		
		// 图片上传插件初始化
		$("#galleryUpload").zyUpload({
			width            :   "80%",                 // 宽度
			height           :   "auto",                 // 宽度
			itemWidth        :   "120px",                 // 文件项的宽度
			itemHeight       :   "100px",                 // 文件项的高度
			url              :   context_path + "/upload/image/blog",  // 上传文件的路径
			multiple         :   false,                    // 是否可以多个文件上传
			dragDrop         :   false,                    // 是否可以拖动上传文件
			del              :   true,                    // 是否可以删除文件
			finishDel        :   true,  				  // 是否在上传文件完成后删除预览
			
			onSelect: function(files, allFiles){                    // 选择文件的回调方法

			},
			onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法

			},
			onSuccess: function(file, response){                    // 文件上传成功的回调方法
				var image = JSON.parse(response);
				changeImage(image);
			},
			onFailure: function(file){                    // 文件上传失败的回调方法

			},
			onComplete: function(responseInfo){           // 上传完成的回调方法

			}
		});
		
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">资讯文章<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/blog"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="preview" value="预览" class="button" onclick="preview_onclick();"/>
						<input type="button" name="save" value="保存为草稿" class="button" onclick="save_onclick();"/>
						<input type="button" name="saveAndIssue" value="保存并发表" class="button" onclick="saveAndIssue_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>标题：</td>
						<td class="field" colspan="3">
							<input type="text" name="title" class="full_input" inputName="标题" value="<c:out value="${bean.title }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>类别：</td>
						<td class="field">
							<dv:radio name="type" dicKeyword="DIC_BLOG_TYPE" defaultValue="${bean.type }" attributes=" inputName='类别' validate='notNull'"/>
						</td>
						<td class="label"><span class="required_red">* </span>发布时间：</td>
						<td class="field">
							<input type="text" name="issue_time" class="input Wdate" inputName="发布时间" value="<c:out value="${fn:substring(bean.issue_time, 0, 19) }"/>" validate="notNull" format="both"/>
						</td>
					</tr>
					<!-- 
					<tr>
						<td class="label"><span class="required_red">* </span>发布范围：</td>
						<td class="field">
							<dv:radio name="issue_to" dicKeyword="DIC_BLOG_ISSUE_TO" defaultValue="${bean.issue_to }" attributes=" inputName='发布范围' validate='notNull'"/>
						</td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					 -->
					<tr>
						<td class="label"><span class="required_red">* </span>简述概要：</td>
						<td class="field" colspan="3">
							<textarea name="brief" class="full_input" style="height:90px;"cols="30" rows="5" validate="notNull">${bean.brief }</textarea>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>缩略图：</td>
						<td class="field" colspan="3">
							<input type="hidden" name="image" value="<c:out value="${bean.image }"/>" inputName="缩略图" validate="notNull"/>
							<div id="imgDiv"><c:if test="${bean.image != null && bean.image != '' }"><img style="max-width:100px;max-height:100px;" src="<c:out value="${bean.image }"/>?imageView2/0/w/100"/></c:if></div>
							<div id="galleryUpload" style="width:80%"></div>
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field" colspan="3">
							为了方便用户编辑出更加规范美观的文章布局，推荐使用<span class="left_ts">135在线微信编辑器</span><br>
							使用步骤：<br><br>
							1、点击<a href="http://www.135editor.com/" target="_blank" >135在线微信编辑器</a>，进入135微信编辑器官网并登录（账号：18621746267，密码：deertt）。<br>
							2、将本页面内的文章内容编辑器内的内容全部复制（快捷键：CTRL+A,CTRL+C,CTRL+V）到135编辑器的编辑框内进行编辑。<br>
							3、将135编辑器内编辑好的内容复制回本页面编辑器内，单击“保存”保存修改后内容。<br><br>
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field" colspan="3">
							<script id="description" name="description" type="text/plain" inputName="描述">${bean.description }</script>
						</td>
					</tr>
					<tr>
						<td class="label">关联商品：</td>
						<td class="field" style="height:100px;padding-bottom:10px;" colspan="3">
							<ul id="gallery"></ul>
							<img style="vertical-align: middle;width:50px;height:50px;margin: 30px 20px 20px 20px" title="添加商品" src="<%=request.getContextPath() %>/resources/images/add-circular.png" onclick="add_onclick();">
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="city_id" value="<c:out value="${bean.city_id }"/>"/>
		<input type="hidden" name="school_id" value="<c:out value="${bean.school_id }"/>"/>
		<input type="hidden" name="issue_status" value="<c:out value="${bean.issue_status }"/>"/>
		<input type="hidden" name="link_goods" value="<c:out value="${bean.link_goods }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
