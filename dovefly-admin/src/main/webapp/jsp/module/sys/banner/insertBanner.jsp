<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网站插页<%=isModify ? "修改" : "新增"%></title>
<!-- 图片上传插件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/zyUpload/css/zyUpload.css" type="text/css">
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyFile.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/zyUpload/js/zyUpload.js"></script>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("bannerController");
	
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
	
	function changeImage(img) {
		$("input[name='image']").val(img.path);
		var imgHtml = '<img style="max-width:500px;max-height:500px;" src="' + img.path + '"/>';
		$("#imgDiv").html(imgHtml);
	}
	
	$(document).ready(function() {
		
		// 图片上传插件初始化
		$("#galleryUpload").zyUpload({
			width            :   "80%",                 // 宽度
			height           :   "auto",                 // 宽度
			itemWidth        :   "120px",                 // 文件项的宽度
			itemHeight       :   "100px",                 // 文件项的高度
			url              :   context_path + "/upload/image/banner",  // 上传文件的路径
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
					<div class="table_title">网站插页<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_banner"></div>
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
						<td class="label"><span class="required_red">* </span>标题：</td>
						<td class="field">
							<input type="text" name="title" class="input" inputName="标题" value="<c:out value="${bean.title }"/>" maxlength="100" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>设备类型：</td>
						<td class="field">
							<dv:select name="device" dicKeyword="DIC_BANNER_DEVICE" hasEmpty="true" defaultValue="${bean.device }" ignoreValues="${ignoreDevice }" attributes="inputName='设备类型' validate='notNull'"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>跳转地址：</td>
						<td class="field" colspan="3">
							<input type="text" name="url" class="full_input" inputName="跳转地址" value="<c:out value="${bean.url }"/>" maxlength="100" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>有效起始日期：</td>
						<td class="field">
							<input type="text" name="start_time" class="input Wdate" inputName="起始日期" value="${fn:substring(bean.start_time, 0, 10) }" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>有效结束日期：</td>
						<td class="field">
							<input type="text" name="end_time" class="input Wdate" inputName="结束日期" value="${fn:substring(bean.end_time, 0, 10) }" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>图片：</td>
						<td class="field" colspan="3">
							<input type="hidden" name="image" value="<c:out value="${bean.image }"/>" inputName="图片" validate="notNull"/>
							<div id="imgDiv" ><c:if test="${bean.image != null && bean.image != '' }"><img style="max-width:100px;max-height:100px;" src="<c:out value="${bean.image }"/>"/></c:if></div>
							<div id="galleryUpload"></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
