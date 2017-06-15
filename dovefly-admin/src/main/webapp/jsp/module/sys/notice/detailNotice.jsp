<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统公告信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("noticeController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 发布 */
	function issue_onclick() {
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: formHelper.buildAction("noticeController", "issue", '${bean.id }'),
			data: null,
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
	
	/** 取消发布 */
	function cancelIssue_onclick() {
		$.ajax({
			type: "POST",
			async: true,
			dataType: "json",
			url: formHelper.buildAction("noticeController", "issue", '${bean.id }'),
			data: null,
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
	
	$(document).ready(function() {
	
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">系统公告信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_notice"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:choose>
						<c:when test="${bean.status == '1' }">
						<input type="button" name="find" value="取消发布" class="button" onclick="cancelIssue_onclick();"/>
						</c:when>
						<c:otherwise>
						<input type="button" name="find" value="发布" class="button" onclick="issue_onclick();"/>
						</c:otherwise>
						</c:choose>
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">标题：</td>
						<td class="field"><c:out value="${bean.title }"/></td>
						<td class="label">类型：</td>
						<td class="field"><dv:display dicKeyword="DIC_NOTICE_TYPE" value="${bean.type }"/></td>
					</tr>
					<tr>
						<td class="label">阅读次数：</td>
						<td class="field"><c:out value="${bean.read_times }"/></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					<tr>
						<td class="label">发布状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_NOTICE_ISSUE_STATUS" value="${bean.issue_status }"/></td>
						<td class="label">发布时间</td>
						<td class="field"><c:out value="${fn:substring(bean.issue_at, 0, 19) }"/></td>
					</tr>
					<tr>
						<td class="label">公告附件：</td>
						<td class="field">
							<jsp:include page="/jsp/include/upload/uploadifyDetail.jsp" flush="true">
								<jsp:param name="fileUploadName" value="common_file_upload"/>
								<jsp:param name="fileListName" value="commonFileList"/>
							</jsp:include>
						</td>
						<td class="label"></td>
						<td class="field">
						</td>
					</tr>
					<tr>
						<td class="label">新闻内容：</td>
						<td class="field" colspan="3"><c:out value="${bean.content }" escapeXml="false"/></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>
