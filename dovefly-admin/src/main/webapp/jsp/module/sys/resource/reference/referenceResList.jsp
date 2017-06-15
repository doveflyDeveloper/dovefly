<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.sys.resource.vo.ResourceVo" %>
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
<title>系统功能参照</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("resourceController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildReferenceAction());
	}
	
	/** 查看 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildDetailAction(id));
		} else {//勾选复选框查看
			var ids = findSelections("dv_checkbox", "value");
			if(!ids || !ids.length) {
				dvTip("请选择一条记录！");
		  		return false;
			}
			if(ids.length > 1) {
				dvTip("只能选择一条记录！");
		  		return false;
			}
			formHelper.jSubmit(formHelper.buildDetailAction(ids));
		}
	}
	
	/** 确定 */
	function ok_onclick() {
		var sels = findSelections("dv_<%=inputType%>");
		if(!sels || !sels.length) {
			dvTip("请先选择记录！");
	  		return false;
		}
		var datas = new Array();
		for (var i = 0; i < sels.length; i++) {
			var obj = new Object();
			obj.res_id = $(sels[i]).val();
			obj.res_code = $(sels[i]).attr("code");
			obj.res_name = $(sels[i]).attr("name");
			datas.push(obj);
		}	
		parent.parent.<%=callback%>(datas);
		return true;//若要关闭窗口，可返回true;
	}
	
	/** 清空 */
	function clear_onclick() {
		var datas = new Array();
		var obj = new Object();
		obj.res_id = "";
		obj.res_code = "";
		obj.res_name = "";
		datas.push(obj);
		parent.parent.<%=callback%>(datas);
		return true;//若要关闭窗口，可返回true;
	}

	/** 页面加载后需绑定的事件 */
	$(document).ready(function() {
	
	});
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div class="border_div" style="margin: 1px;">
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
						<td class="label">${TABLE_COLUMN_CHINESE.org_code }：</td>
						<td class="field">
							<input type="text" name="org_code" class="input" maxlength="100" inputName="${TABLE_COLUMN_CHINESE.org_code }" value="<c:out value="${param.org_code }"/>"/>	
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.org_name }：</td>
						<td class="field">
							<input type="text" name="org_name" class="input" maxlength="100" inputName="${TABLE_COLUMN_CHINESE.org_name }" value="<c:out value="${param.org_name }"/>"/>	
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<span class="table_title">系统功能列表&nbsp;</span>
				</div>
				<div class="right_div">
					<span class="right_menu">
						<input type="button" name="clear" value="清空" class="button" onclick="clear_onclick();"/>
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
						<th width="10%">${TABLE_COLUMN_CHINESE.org_code }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.org_name }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.parent_code }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.parent_name }</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
						<%if("checkbox".equalsIgnoreCase(inputType)){ %>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.org_id }"/>" org_code="<c:out value="${bean.org_code }"/>" org_name="<c:out value="${bean.org_name }"/>" onclick="selectCheckBox(this)"/>
						<%} else {%>	
							<input type="radio" name="dv_radio" value="<c:out value="${bean.org_id }"/>" org_code="<c:out value="${bean.org_code }"/>" org_name="<c:out value="${bean.org_name }"/>" onclick="selectRadio(this)"/>
						<%}	%>		
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.org_code }"/></td>
						<td><c:out value="${bean.org_name }"/></td>
						<td><c:out value="${bean.parent_code }"/></td>
						<td><c:out value="${bean.parent_name }"/></td>
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