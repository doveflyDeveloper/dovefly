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
	var formHelper = new DvFormHelper("goodsSController");
	
	/** 保存 */
	function save_onclick() {
		prepareImages();
		if(checkImagesAndDescription()){
			if(<%=isModify%>){
				update_onclick();
			} else {
				insert_onclick();
			}
		}
	}
	
	/** 关联货品 */
	function referenceItem() {
		var options = {
			buttons : {
				'确定' : 'ok',
				'新增货品' : 'addItem',
				'关闭' : true
			},
			submit: function (v, h, f) {
				/* 点击窗口按钮后的回调函数，返回true时表示关闭窗口，参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
			    if (v == 'ok') {
			    	var ifr = h.find("iframe").get(0);
			    	var win = ifr.window || ifr.contentWindow;
			    	if (win.ok_onclick) {
			    		return win.ok_onclick();
			    	}
			    } else if (v == 'clear') {
			    	var ifr = h.find("iframe").get(0);
			    	var win = ifr.window || ifr.contentWindow;
			    	if (win.clear_onclick) {
			    		return win.clear_onclick();
			    	}
			    } else if (v == 'addItem') {
			    	top.rightFrame.addTab('119', '货品管理', '<%=request.getContextPath()%>/mmItemController/create');
			    }
				return true; 
			}
		};
		reference(context_path + "/mmItemController/reference", "选择货品", 900, 450, "referenceItem_callback", "radio", "search", options);
	}

	/**
	 * 参照货品
	 * 
	 * @param datas
	 */
	function referenceItem_callback(datas) {
	// 枚举（循环）对象的所有属性
		for (i in datas) {
			var obj = datas[i];
			$("[name=item_id]").val(obj.id);
			$("[name=barcode]").val(obj.barcode);
			$("[name=name]").val(obj.name);
			$("[name=title]").val(obj.name);
			$("[name=tips]").val(obj.name);
			$("[name=weight]").val(obj.weight);
			$("[name=spec]").val(obj.spec);
			$("[name=storage_type]").val(obj.storage_type);
			$("[name=producer]").val(obj.producer);
			$("[name=shelf_life]").val(obj.shelf_life);
			$("[name=description]").val(obj.description);
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
						'	<img class="pic" title="' + (images[i].title || '') + '" path="' + images[i].path + '" src="' + images[i].path + '?imageView2/0/w/120" />' + 
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
						'	<img class="pic" title="" path="' + img.path + '" src="' + img.path + '?imageView2/0/w/120" />' + 
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

</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">商品<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_goods_s"></div>
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
									<td class="label"><span class="required_red">* </span>条形码：</td>
									<td class="field">
										<%-- <input type="text" name="barcode" class="readonly input" inputName="条形码" value="<c:out value="${bean.barcode }"/>" maxlength="20" validate="notNull"/>
										<c:if test="${isModify == null}"><input type="button" value="关联货品" onclick="referenceItem()"/></c:if> --%>
										<input type="text" name="barcode" class="full_input" inputName="条形码" value="<c:out value="${bean.barcode }"/>" maxlength="20" validate="notNull"/>
									</td>
									<td class="label"><span class="required_red">* </span>所属分类：</td>
									<td class="field">
										<dv:select name="category_id" dicKeyword="DIC_GOODS_CATEGORY" hasEmpty="true" defaultValue="${bean.category_id }" attributes="inputName='所属分类' validate='notNull'"/>
										<input type="hidden" name="category_code" value="<c:out value="${bean.category_code }"/>"/>
									</td>
								</tr>
								<tr>
									<td class="label"><span class="required_red">* </span>商品名称：</td>
									<td class="field" colspan="3">
										<input type="text" name="name" class="full_input" inputName="商品名称" value="<c:out value="${bean.name }"/>" maxlength="50" validate="notNull"/>
									</td>
								</tr>
								<tr>
									<td class="label"><span class="required_red">* </span>标题：</td>
									<td class="field" colspan="3">
										<input type="text" name="title" class="full_input" inputName="标题" value="<c:out value="${bean.title }"/>" maxlength="100" validate="notNull"/>
									</td>
								</tr>
								<tr>
									<td class="label">摘要：</td>
									<td class="field" colspan="3">
										<input type="text" name="tips" class="full_input" inputName="摘要" value="<c:out value="${bean.tips }"/>" maxlength="100" validate=""/>
									</td>
								</tr>
								<tr>
									<td class="label">商品属性：</td>
									<td class="field">
										<dv:radio name="type" dicKeyword="DIC_GOODS_TYPE" defaultValue="${bean.type }" attributes="inputName='商品属性' validate=''"/>
									</td>
									<td class="label">标签：</td>
									<td class="field">
										<dv:radio name="tag" dicKeyword="DIC_GOODS_TAG" defaultValue="${bean.tag }" attributes="inputName='标签' validate=''"/>
									</td>
								</tr>
								<tr>
									<td class="label">重量（克）：</td>
									<td class="field">
										<input type="text" name="weight" class="input" inputName="重量" value="<c:out value="${bean.weight }"/>" validate=""/>
									</td>
									<td class="label">保质期：</td>
									<td class="field">
										<input type="text" name="shelf_life" class="input" inputName="保质期" value="<c:out value="${bean.shelf_life }"/>" maxlength="10" validate=""/>
									</td>
								</tr>
								<tr>
									<td class="label">储藏方法：</td>
									<td class="field">
										<dv:radio name="storage_type" dicKeyword="DIC_GOODS_STORAGE_TYPE" defaultValue="${bean.storage_type }" attributes="inputName='储藏方法' validate=''"/>
									</td>
									<td class="label">生产商：</td>
									<td class="field">
										<input type="text" name="producer" class="input" inputName="生产商" value="<c:out value="${bean.producer }"/>" maxlength="50" validate=""/>
									</td>
								</tr>
								<tr>
									<td class="label"><span class="required_red">* </span>店长进货价：</td>
									<td class="field">
										<input type="text" name="sale_price" class="input" inputName="店长进货价" value="<c:out value="${bean.sale_price }"/>" validate="notNull"/>
										<input type="hidden" name="cost_price" value="<c:out value="${bean.cost_price }"/>" />
									</td>
									<td class="label"></td>
									<td class="field"></td>
								</tr>
								<tr>
									<td class="label"><span class="required_red">* </span>店长零售价：</td>
									<td class="field">
										<input type="text" name="market_price" class="input" inputName="店长零售价" value="<c:out value="${bean.market_price }"/>" validate="notNull"/>
									</td>
									<td class="label">库存安全线：</td>
									<td class="field">
										<input type="text" name="safe_line" class="input" inputName="库存安全线" value="<c:out value="${bean.safe_line }"/>" validate=""/>
									</td>
								</tr>
								<tr>
									<td class="label">单笔限购数量：</td>
									<td class="field">
										<input type="text" name="max_limit" class="input" inputName="单笔限购数量" value="<c:out value="${bean.max_limit }"/>" validate=""/>
									</td>
									<td class="label"><span class="left_ts">店长进货限购规则：</span></td>
									<td class="field">
										<input type="text" name="buy_rule" class="input" inputName="店长进货限购规则" value="<c:out value="${bean.buy_rule }"/>" validate="checkBuyRule"/>
									</td>
								</tr>
								<tr>
									<td class="label">单品限用汀豆数量：</td>
									<td class="field">
										<input type="text" name="limit_coin_quantity" class="input" inputName="单品限用汀豆数量" value="<c:out value="${bean.limit_coin_quantity }"/>" validate=""/>
									</td>
									<td class="label">单品返还汀豆数量：</td>
									<td class="field">
										<input type="text" name="send_coin_quantity" class="input" inputName="单品返还汀豆数量" value="<c:out value="${bean.send_coin_quantity }"/>" validate=""/>
									</td>
								</tr>
								<tr>
									<td class="label">商品数量规格：</td>
									<td class="field">
										<input type="text" name="spec" class="input" inputName="商品数量规格" value="<c:out value="${bean.spec }"/>" validate=""/>
									</td>
								</tr>
								<tr>
									<td class="label"></td>
									<td class="field"></td>
									<td class="label"></td>
									<td class="field"></td>
								</tr>
								<tr>
									<td class="field" colspan="4">
										<fieldset style="padding:10px;margin:10px;color:#333;border:#06c dashed 1px;line-height:25px;">
											<legend style="color:#06c;background:#fff;">注意：</legend>
											<span class="left_ts">店长进货限购规则：</span><br/>
											店长进货限购规则：是用于店长在从城市经理进货商品时，对进货数量的一种规则约束，如：起售数量是多少，限购数量是多少，是否按照大包装（整箱/整袋）数量购买等规则。<br/>
											目前支持的规则有三类：<br/>
											1、设定起售数量（>=?）：如果想设定起购数量，如：想10件商品起售，则输入：“<span class="left_ts">>=10</span>”（不包含引号）<br/>
											2、设定限购数量（<=?）：如果想设定限购数量，如：想限制某商品一次最多可购买20件，则输入：“<span class="left_ts"><=20</span>”（不包含引号）<br/>
											3、设定整倍数销售（=?*n）：如果想设定数量的倍数，如：某商品12件为一箱，限定购买时以整箱方式购买，则输入：“<span class="left_ts">=12*n</span>”（不包含引号）<br/>
											<br/>
										</fieldset>
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
		<input type="hidden" name="origin_id" value="<c:out value="${bean.origin_id }"/>"/>
		<input type="hidden" name="item_id" value="<c:out value="${bean.item_id }"/>"/>
		<input type="hidden" name="city_id" value="<c:out value="${bean.city_id }"/>"/>
		<input type="hidden" name="city_name" value="<c:out value="${bean.city_name }"/>"/>
		<input type="hidden" name="shop_id" value="<c:out value="${bean.shop_id }"/>"/>
		<input type="hidden" name="shop_name" value="<c:out value="${bean.shop_name }"/>"/>
		<input type="hidden" name="sold_volume" value="<c:out value="${bean.sold_volume }"/>"/>
		<input type="hidden" name="stock_sum" value="<c:out value="${bean.stock_sum }"/>"/>
		<input type="hidden" name="stock_locked" value="<c:out value="${bean.stock_locked }"/>"/>
		<input type="hidden" name="cmt_point" value="<c:out value="${bean.cmt_point }"/>"/>
		<input type="hidden" name="cmt_times" value="<c:out value="${bean.cmt_times }"/>"/>
		<input type="hidden" name="image" value="<c:out value="${bean.image }"/>"/>
		<input type="hidden" name="images" value="<c:out value="${bean.images }"/>"/>
		<input type="hidden" name="remark" value="<c:out value="${bean.remark }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
