<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货品资料信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("mmItemController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	function initImages() {
		var html = "";
		var images = eval('${bean.images}' || '[]');
		for (var i = 0; i < images.length; i++) {
			html += '<li>' + 
						'<div>' + 
						'	<img class="pic" title="' + images[i].title + '" src="' + images[i].path + '?imageView2/0/w/200"/>' + 
						'</div>' + 
					'</li>';
		}
		
		$('#gallery').append(html);
	}
	
	$(document).ready(function() {
		
		//初始化相册历史图片
		initImages();
		
	});
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">货品资料信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_item"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>基本信息</DT>
					<DD>
						<div>
							<table class="detail_table">
								<tr>
									<td class="label">条形码：</td>
									<td class="field"><c:out value="${bean.barcode }"/></td>
									<td class="label">货品规格：</td>
									<td class="field"><c:out value="${bean.spec }"/></td>
								</tr>
								<tr>
									<td class="label">货品名称：</td>
									<td class="field"><c:out value="${bean.name }"/></td>
									<td class="label">重量（克）：</td>
									<td class="field"><c:out value="${bean.weight }"/></td>
								</tr>
								<tr>
									<td class="label">生产商：</td>
									<td class="field"><c:out value="${bean.producer }"/></td>
									<td class="label">保质期：</td>
									<td class="field"><c:out value="${bean.shelf_life }"/></td>
								</tr>
							</table>
						</div>
					</DD>
					<DT>图片</DT>
					<DD>
						<ul id="gallery"></ul>
					</DD>
					<DT>描述</DT>
					<DD>
						<div>
							<c:out value="${bean.description }" escapeXml="false"/>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
