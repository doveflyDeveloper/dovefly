<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品信息</title>
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
						'	<img class="pic" title="' + images[i].title + '" src="' + images[i].path + '?imageView2/0/w/120" />' + 
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
					<div class="table_title">商品信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_goods_s"></div>
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
									<td class="label">所属分类：</td>
									<td class="field"><dv:display dicKeyword="DIC_GOODS_CATEGORY" value="${bean.category_id }"/></td>
								</tr>
								<tr>
									<td class="label">商品名称：</td>
									<td class="field" colspan="3"><c:out value="${bean.name }"/></td>
								</tr>
								<tr>
									<td class="label">标题：</td>
									<td class="field" colspan="3"><c:out value="${bean.title }"/></td>
								</tr>
								<tr>
									<td class="label">摘要：</td>
									<td class="field" colspan="3"><c:out value="${bean.tips }"/></td>
								</tr>
								<tr>
									<td class="label">商品属性：</td>
									<td class="field"><dv:display dicKeyword="DIC_GOODS_TYPE" value="${bean.type }"/></td>
									<td class="label">标签：</td>
									<td class="field"><dv:display dicKeyword="DIC_GOODS_TAG" value="${bean.tag }"/></td>
								</tr>
								<tr>
									<td class="label">重量（克）：</td>
									<td class="field"><c:out value="${bean.weight }"/></td>
									<td class="label">保质期：</td>
									<td class="field"><c:out value="${bean.shelf_life }"/></td>
								</tr>
								<tr>
									<td class="label">储藏方法：</td>
									<td class="field"><dv:display dicKeyword="DIC_GOODS_STORAGE_TYPE" value="${bean.storage_type }"/></td>
									<td class="label">生产商：</td>
									<td class="field"><c:out value="${bean.producer }"/></td>
								</tr>
								<tr>
									<td class="label">店长进货价：</td>
									<td class="field"><c:out value="${bean.sale_price }"/></td>
									<td class="label">店长零售价：</td>
									<td class="field"><c:out value="${bean.market_price }"/></td>
								</tr>
								<tr>
									<td class="label">库存量：</td>
									<td class="field"><c:out value="${bean.stock_sum }"/></td>
									<td class="label">锁定库存量：</td>
									<td class="field"><c:out value="${bean.stock_locked }"/></td>
								</tr>
								<tr>
									<td class="label">库存安全线：</td>
									<td class="field"><c:out value="${bean.safe_line }"/></td>
									<td class="label">销量：</td>
									<td class="field"><c:out value="${bean.sold_volume }"/></td>
								</tr>
								<tr>
									<td class="label">评价分数：</td>
									<td class="field"><c:out value="${bean.cmt_point }"/></td>
									<td class="label">评价次数：</td>
									<td class="field"><c:out value="${bean.cmt_times }"/></td>
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
