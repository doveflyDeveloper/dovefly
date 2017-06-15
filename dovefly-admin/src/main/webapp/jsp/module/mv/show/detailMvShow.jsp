<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电影剧本信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("mvShowController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 审核通过 */
	function  agree_onclick(){
		dvPrompt(window.agreeDivHtml, '通过告知信息', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts"></span>',
			submit: function(v, h, f) {
				if (!validateForm("agree_table")) {return false;}
				$("#agreereason").val(f['agree_reason']);
				formHelper.jSubmit(formHelper.buildAction("mvShowController", "agree", "${bean.id }"));
				return true;
			}
		});
		
	}
	
	/** 审核拒绝 */
	function  deny_onclick(){
		dvPrompt(window.denyDivHtml, '填写拒绝信息', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts"></span>',
			submit: function(v, h, f) {
				if (!validateForm("deny_table")) {return false;}
				$("#denyreason").val(f['deny_reason']);
				formHelper.jSubmit(formHelper.buildAction("mvShowController", "deny", "${bean.id }"));
				return true;
			}
		});
		
	}
	
	
	$(document).ready(function() {
		var imgHtml = "";
		var mv_images = JSON.parse('${bean.mv_images }');
		for (var i in mv_images) {
			imgHtml += '<img src="https://ods5st3df.qnssl.com/' + mv_images[i] + '" />';
		}
		$("#td_mv_images").html(imgHtml);
		window.denyDivHtml = $("#denyDiv").html();
		$("#denyDiv").html("");
		window.agreeDivHtml = $("#agreeDiv").html();
		$("#agreeDiv").html("");
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">电影剧本信息&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/mv_show"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${bean.status == '0' }">
						<input type="button" name="agree" value="通过" class="button" onclick="agree_onclick();" />
						<input type="button" name="deny" value="拒绝" class="button" onclick="deny_onclick();" />
						</c:if>
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">城市：</td>
						<td class="field"><c:out value="${bean.city_name }"/></td>
						<td class="label">学校：</td>
						<td class="field"><c:out value="${bean.school_name }"/></td>
					</tr>
					<tr>
						<td class="label">参赛人：</td>
						<td class="field"><c:out value="${bean.user_name }"/></td>
						<td class="label">推荐人：</td>
						<td class="field"><c:out value="${bean.recommend_name }"/></td>
					</tr>
					<tr>
						<td class="label">姓名：</td>
						<td class="field"><c:out value="${bean.name }"/></td>
						<td class="label">手机号：</td>
						<td class="field"><c:out value="${bean.mobile }"/></td>
					</tr>
					<tr>
						<td class="label">邮箱：</td>
						<td class="field"><c:out value="${bean.email }"/></td>
						<td class="label">院系专业：</td>
						<td class="field"><c:out value="${bean.major }"/></td>
					</tr>
					<tr>
						<td class="label">剧本名称：</td>
						<td class="field"><c:out value="${bean.mv_name }"/></td>
						<td class="label">剧本类型：</td>
						<td class="field"><c:out value="${bean.mv_type }"/></td>
					</tr>
					<tr>
						<td class="label">剧本梗概：</td>
						<td class="field" colspan="3"><c:out value="${bean.mv_brief }"/></td>
					</tr>
					<tr>
						<td class="label">剧照图：</td>
						<td class="field" colspan="3" id="td_mv_images"></td>
					</tr>
					<tr>
						<td class="label">汀豆：</td>
						<td class="field"><c:out value="${bean.mv_coin }"/></td>
					</tr>
					<tr>
					<td class="label">剧本大纲：</td>
						<td class="field" colspan="3"><c:out value="${bean.mv_desc }" escapeXml="false"/></td>
					</tr>
					<tr>
						<td class="label">剧本附件：</td>
						<td class="field"><a href="https://ods5st3df.qnssl.com/${bean.mv_file }"><c:out value="${bean.mv_file }"/></a></td>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
					</tr>
				</table>
			<input type="hidden" id="agreereason" name="agreereason" value=""/>
			<input type="hidden" id="denyreason" name="denyreason" value=""/>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>投票明细</DT>
					<DD>
						<div>
							<table id="detail_list_table" class="list_table">
								<tr>
									<th width="25%">序号</th>
									<th width="25%">投票人</th>
									<th width="25%">获得汀豆</th>
								    <th width="25%">投票时间</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${detail.user_name }"/></td>
									<td><c:out value="${detail.coin_quantity }"/></td>
									<td><c:out value="${detail.create_at}"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
		<div id="denyDiv" style="display:none;">
		<table id="deny_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>拒绝原因：</td>
				<td class="field">
					<textarea id="deny_reason" name="deny_reason" class="textarea" inputName="拒绝原因" validate="notNull"></textarea>
				</td>
			</tr>
		</table>
	</div>
	<div id="agreeDiv" style="display:none;">
		<table id="agree_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>通过：</td>
				<td class="field">
					<textarea id="agree_reason" name="agree_reason" class="textarea" inputName="通过告知" validate="notNull"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
