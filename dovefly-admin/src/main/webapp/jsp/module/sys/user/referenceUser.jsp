<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//默认是radio
	String inputType = request.getParameter("inputType");
	if(!"checkbox".equalsIgnoreCase(inputType)){
		inputType = "radio";
	}
	//回调函数
	String callback = request.getParameter("callback");

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>用户参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("userController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildReferenceAction());
	}
	
	/** 确定 */
	function ok_onclick() {
		var sels = findSelections("dv_<%=inputType%>");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		var selected_user_ids = $("#selected_user_ids").val() || "0";
		var datas = new Array();
		for (var i = 0; i < sels.length; i++) {
			var obj = new Object();
			obj.user_id = $(sels[i]).val();
			obj.user_name = $(sels[i]).attr("user_name");
			datas.push(obj);
			
			selected_user_ids += "," + obj.id;
			$(sels[i]).closest("tr").remove();
		}
		$("#selected_user_ids").val(selected_user_ids);
		parent.<%=callback%>(datas);
		if ('<%=inputType%>' == 'radio') {
			return true;
		} else {
			return false;//若要关闭窗口，可返回true;
		}
	}

	/** 页面加载后需绑定的事件 */
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
					<span class="table_title">查询条件&nbsp;</span>
				</div>
				<div class="right_div">
					<span class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
					</span>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<c:if test="${sessionScope.DV_USER_VO.headquartersRole}">
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						</c:if>
					</tr>
					<tr>
						<td class="label">姓名：</td>
						<td class="field">
							<input type="text" name="real_name" class="input" maxlength="20" value="<c:out value="${param.real_name }"/>"/>	
						</td>
						<td class="label">联系电话：</td>
						<td class="field">
							<input type="text" name="mobile" class="input" maxlength="11" value="<c:out value="${param.mobile }"/>"/>	
						</td>
					</tr>
				</table>
				<input type="hidden" id="selected_user_ids" name="selected_user_ids" value="<c:out value="${param.selected_user_ids }"/>"/> 
				<input type="hidden" name="status" value="<c:out value="${param.status }"/>"/>
				<input type="hidden" name="reference" value="<c:out value="${param.reference }"/>"/>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">处理人列表&nbsp;</span>
				</div>
				<div class="right_div">
					<span class="right_menu">
					</span>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
							<%} %>
						</th>
						<th width="5%">序号</th>
						<th width="5%">微信头像</th>
						<th width="10%">微信昵称</th>
						<th width="10%">用户姓名</th>
						<th width="10%">联系电话</th>
						<th width="5%">城市</th>
						<th width="20%">学校</th>
						<th width="5%">用户状态</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" user_id="<c:out value="${bean.id }"/>" user_name="<c:out value="${bean.real_name }"/>" onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.id }"/>" user_id="<c:out value="${bean.id }"/>" user_name="<c:out value="${bean.real_name }"/>" onclick="selectRadio(this)"/>
						<%}	%>
						</td>
						<td>${status.count }</td>
						<td><img class="thumbnail" src="${bean.wechat_avatar }"/></td>
						<td><c:out value="${bean.wechat_account }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.real_name }"/></a></td>
						<td><c:out value="${bean.mobile }"/></td>
						<td><c:out value="${bean.city_name }"/></td>
						<td><c:out value="${bean.school_name }"/></td>
						<td><dv:display dicKeyword="DIC_STATUS" value="${bean.status }"/></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="page_div">
			<jsp:include page="/jsp/include/page.jsp"/>
		</div>
		<input type="hidden" name="inputType" value="<%=inputType %>"/>
		<input type="hidden" name="callback" value="<%=callback %>"/>
	</form>
</body>
</html>
