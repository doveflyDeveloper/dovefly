<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典类型信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("dictTypeController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
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
					<div class="table_title">字典类型信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sys_dict_type"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">标识关键词：</td>
						<td class="field"><c:out value="${bean.keyword }"/></td>
						<td class="label">字典名称：</td>
						<td class="field"><c:out value="${bean.name }"/></td>
					</tr>
					<tr>
						<td class="label">备注：</td>
						<td class="field"><c:out value="${bean.remark }"/></td>
						<td class="label">状态：</td>
						<td class="field"><dv:display dicKeyword="DIC_STATUS" value="${bean.status }"/></td>
					</tr>
				</table>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>字典数据值</DT>
					<DD>
						<div>
							<iframe	rel="<%=request.getContextPath()%>/dictDataController/queryAll/<c:out value="${bean.id }"/>" width="100%" height="300px" frameborder="0" scrolling="auto"></iframe>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
</body>
</html>
