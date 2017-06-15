<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资讯文章信息</title>
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
	var formHelper = new DvFormHelper("blogController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 发表 */
	function issue_onclick() {
		formHelper.jSubmit(formHelper.buildAction("blogController", "issue", "${bean.id }"));
	}
	
	/** 取消发表 */
	function cancelIssue_onclick() {
		formHelper.jSubmit(formHelper.buildAction("blogController", "cancelIssue", "${bean.id }"));
	}
	
	function initImages() {
		var html = "";
		var goods = eval('${bean.link_goods}' || '[]');
		for (var i = 0; i < goods.length; i++) {
			html += '<li>' + 
						'<div>' + 
						'	<img class="pic" title="' + goods[i].name + '" src="' + goods[i].image + '?imageView2/0/w/200"/>' + 
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
					<div class="table_title">资讯文章信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/blog"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.issue_status == '0'}">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="issue" value="发表" class="button" onclick="issue_onclick();"/>
						</c:if>
						<c:if test="${bean.issue_status == '1'}">
						<input type="button" name="cancelIssue" value="撤回" class="button" onclick="cancelIssue_onclick();"/>
						</c:if>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_id }"/></td>
						<td class="label">学校：</td>
						<td class="field"><c:out value="${bean.school_id }"/></td>
					</tr>
					<tr>
						<td class="label">标题：</td>
						<td class="field" colspan="3"><c:out value="${bean.title }"/></td>
					</tr>
					<tr>
						<td class="label">类别：</td>
						<td class="field"><dv:display dicKeyword="DIC_BLOG_TYPE" value="${bean.type }"/></td>
						<td class="label">发布范围：</td>
						<td class="field"><dv:display dicKeyword="DIC_BLOG_ISSUE_TO" value="${bean.issue_to }"/></td>
					</tr>
					<tr>
						<td class="label">发表状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_BLOG_ISSUE_STATUS" value="${bean.issue_status }"/></td>
						<td class="label">发表时间：</td>
						<td class="field">${fn:substring(bean.issue_time, 0, 10) }</td>
					</tr>
					<tr>
						<td class="label">简述概要：</td>
						<td class="field" colspan="3"><c:out value="${bean.brief }"/></td>
					</tr>
					<tr>
						<td class="label">缩略图：</td>
						<td class="field" colspan="3">
							<div id="imgDiv"><c:if test="${bean.image != null && bean.image != '' }"><img style="max-width:100px;max-height:100px;" src="<c:out value="${bean.image }"/>?imageView2/0/w/100"/></c:if></div>
						</td>
					</tr>
					<tr>
						<td class="field" colspan="4"><c:out value="${bean.description }" escapeXml="false"/></td>
					</tr>
					<tr>
						<td class="label">关联商品：</td>
						<td class="field" style="height:100px;padding-bottom:10px;" colspan="3">
							<ul id="gallery"></ul>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
