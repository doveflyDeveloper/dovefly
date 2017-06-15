<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特荐商品<%=isModify ? "修改" : "新增"%></title>
<!-- 图片上传插件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/zyUpload/css/zyUpload.css" type="text/css">
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyFile.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyUpload.js"></script>
<style type="text/css">
<!--
	ul { margin:0px; padding:0px; margin-left:20px; }
	#goods_list { list-style-type:none; margin:0px; }
	#goods_list li { float:left; padding:10px; width:90px; height:90px; }
	#goods_list div { width:90px; height:90px; text-align:center; }
	img.thumbnail { width:90px; height:90px; max-width:90px; max-height:90px; }

-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("goodsMHotController");
	
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

	/** 新增单品 */
	function addSingle_onclick() {
		var options = {
			buttons : {
				'确定' : 'ok',
				'关闭' : true
			}
		};
		reference(context_path + "/goodsMController/reference?reference=special&status=1", "选择商品", 900, 450, "referenceGoodsSingle_callback", "radio", "search", options);
	}
	
	/** 新增单品回调 */
	function referenceGoodsSingle_callback(datas) {
		for (i in datas) {
			var obj = datas[i];
			$("input[name='goods_id']").val(obj.id);
			$("input[name='goods_name']").val(obj.name);
			$("input[name='goods_image']").val(obj.image);
			$("#imgDiv").html('<img style="max-width:100px;max-height:100px;" src="' + obj.image + '?imageView2/0/w/100"/>');
		}
	}
	
	function changeImage(img) {
		$("input[name='goods_image']").val(img.path);
		var imgHtml = '<img style="max-width:100px;max-height:100px;" src="' + img.path + '?imageView2/0/w/100"/>';
		$("#imgDiv").html(imgHtml);
	}
	
	$(document).ready(function() {
		// 图片上传插件初始化
		$("#galleryUpload").zyUpload({
			width            :   "80%",                 // 宽度
			height           :   "auto",                 // 宽度
			itemWidth        :   "120px",                 // 文件项的宽度
			itemHeight       :   "100px",                 // 文件项的高度
			url              :   context_path + "/upload/image/goods",  // 上传文件的路径
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
					<div class="table_title">特荐商品<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_goods_special"></div>
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
						<td class="label"><span class="required_red">* </span>商品名称：</td>
						<td class="field" colspan="3">
							<input type="text" name="goods_name" class="full_input" inputName="商品名称" value="<c:out value="${bean.goods_name }"/>" maxlength="50" validate="notNull"/>
							<input type="button" id="referenceGoods" name="referenceGoods" value="关联商品" class="button" onclick="addSingle_onclick();"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>首图：</td>
						<td class="field">
							<input type="hidden" id="goods_image" name="goods_image" value="<c:out value="${bean.goods_image }"/>" inputName="首图" validate="notNull"/>
							<div id="imgDiv"><c:if test="${bean.goods_image != null && bean.goods_image != '' }"><img style="max-width:100px;max-height:100px;" src="<c:out value="${bean.goods_image }"/>?imageView2/0/w/100"/></c:if></div>
						</td>
						<td class="field" colspan="2" id="td_goods_image">
							<div id="galleryUpload" style="width:80%"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="city_id" value="<c:out value="${bean.city_id }"/>"/>
		<input type="hidden" name="city_name" value="<c:out value="${bean.city_name }"/>"/>
		<input type="hidden" name="market_id" value="<c:out value="${bean.market_id }"/>"/>
		<input type="hidden" name="market_name" value="<c:out value="${bean.market_name }"/>"/>
		<input type="hidden" name="goods_id" value="<c:out value="${bean.goods_id }"/>"/>
		<input type="hidden" name="cost_price" value="<c:out value="${bean.cost_price }"/>"/>
		<input type="hidden" name="sale_price" value="<c:out value="${bean.sale_price }"/>"/>
		<input type="hidden" name="market_price" value="<c:out value="${bean.market_price }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
