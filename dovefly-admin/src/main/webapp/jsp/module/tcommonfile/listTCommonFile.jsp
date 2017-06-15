<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.module.tcommonfile.vo.TCommonFileVo" %>
<%@ page import="com.deertt.module.tcommonfile.util.ITCommonFileConstants" %>
<%
	
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title><%=ITCommonFileConstants.TABLE_NAME_CHINESE %>管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("tCommonFileController");
	
	/** 查询 */
	function query_onclick() {
		formHelper.jSubmit(formHelper.buildQueryAction());
	}
	
	/** 新增 */
	function add_onclick() {
		formHelper.jSubmit(formHelper.buildCreateAction());
	}
	
	/** 复制 */	
	function copy_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildCopyAction(id));
	}
	
	/** 删除 */
	function deleteMulti_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "1")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
	
		var ids = findSelections("dv_checkbox", "value");
		
		dvConfirm("您确定要删除这些数据吗？", 
			function() {
				formHelper.jSubmit(formHelper.buildDeleteMultiAction(ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 修改 */
	function find_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		
		//其他一些限制操作的校验：如审批状态等。
		//if(!validateSelections(sels, "issue_state", "0")){
		//	dvAlert("只有发布状态为1的单据才可以删除！");
	  	//	return false;
		//}
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildFindAction(id));
	}
	
	/** 查看 */
	function detail_onclick(id){
		if(id) {//点击单据名称超链接查看
			formHelper.jSubmit(formHelper.buildDetailAction(id));
		} else {//勾选复选框查看
			var ids = findSelections("dv_checkbox", "value");
			if(!ids || !ids.length) {
				dvAlert("请选择一条记录！");
		  		return false;
			}
			if(ids.length > 1) {
				dvAlert("只能选择一条记录！");
		  		return false;
			}
			formHelper.jSubmit(formHelper.buildDetailAction(ids));
		}
	}
	
	/** 导入 */
	function importExcel_onclick() {
		var redirectUrl = "tCommonFileController/importExcel";
		var downloadUrl = "tCommonFileController/downloadTemplate";
		var title = "<%=ITCommonFileConstants.TABLE_NAME_CHINESE %>导入";
		dvOpenImportDialog(redirectUrl, downloadUrl, title);
	}
	
	/** 导出 */
	function exportDetail_onclick() {
		var sels = findSelections("dv_checkbox");
		if(!sels || !sels.length) {
	  		dvAlert("请选择一条记录！");
	  		return false;
		}
		if(sels.length > 1) {
			dvAlert("只能选择一条记录！");
	  		return false;
		}
		var id = $(sels[0]).val();
		formHelper.submit(formHelper.buildAction("tCommonFileController", "exportDetail", id));
	}
	
	/** 批量导出 */
	function exportList_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("tCommonFileController", "exportList", ids));
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
					<div class="table_title">查询条件&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%><%=ITCommonFileConstants.TIP_JSP_URL%>"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<label style="color: #CCC"><input type="checkbox" id="query_state" name="query_state" class="checkbox" value="1" <c:if test='${param.query_state == 1 }'>checked="checked"</c:if>/>更多</label>&nbsp;&nbsp;
						<input type="button" name="query" value="查询" class="button" onclick="query_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="query_table">
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_table_name }：</td>
						<td class="field">
							<input type="text" name="owner_table_name" class="input" maxlength="100" inputName="${TABLE_COLUMN_CHINESE.owner_table_name }" value="<c:out value="${param.owner_table_name }"/>"/>	
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_bill_id }：</td>
						<td class="field">
							<input type="text" name="owner_bill_id" class="input" maxlength="50" inputName="${TABLE_COLUMN_CHINESE.owner_bill_id }" value="<c:out value="${param.owner_bill_id }"/>"/>	
						</td>
					</tr>
					<tr>
						<td class="label">${TABLE_COLUMN_CHINESE.owner_column_name }：</td>
						<td class="field">
							<input type="text" name="owner_column_name" class="input" maxlength="50" inputName="${TABLE_COLUMN_CHINESE.owner_column_name }" value="<c:out value="${param.owner_column_name }"/>"/>	
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.file_name }：</td>
						<td class="field">
							<input type="text" name="file_name" class="input" maxlength="100" inputName="${TABLE_COLUMN_CHINESE.file_name }" value="<c:out value="${param.file_name }"/>"/>	
						</td>
					</tr>
					<tr class="hidden">
						<td class="label">${TABLE_COLUMN_CHINESE.file_url }：</td>
						<td class="field">
							<input type="text" name="file_url" class="input" maxlength="200" inputName="${TABLE_COLUMN_CHINESE.file_url }" value="<c:out value="${param.file_url }"/>"/>	
						</td>
						<td class="label">${TABLE_COLUMN_CHINESE.file_type }：</td>
						<td class="field">
							<input type="text" name="file_type" class="input" maxlength="50" inputName="${TABLE_COLUMN_CHINESE.file_type }" value="<c:out value="${param.file_type }"/>"/>	
						</td>
					</tr>
					<tr class="hidden">
						<td class="label">${TABLE_COLUMN_CHINESE.file_size }：</td>
						<td class="field">
							<input type="text" name="file_size" class="input" maxlength="11" inputName="${TABLE_COLUMN_CHINESE.file_size }" value="<c:out value="${param.file_size }"/>"/>	
						</td>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" maxlength="100" inputName="备注" value="<c:out value="${param.remark }"/>"/>	
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title"><%=ITCommonFileConstants.TABLE_NAME_CHINESE %>列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="insert" value="新增" class="button" onclick="add_onclick();"/>
						<input type="button" name="update" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="importExcel" value="导入" class="button" onclick="importExcel_onclick();"/>
						<input type="button" name="exportList" value="批量导出" class="button" onclick="exportList_onclick();"/>
						<input type="button" name="exportDetail" value="导出" class="button" onclick="exportDetail_onclick();"/>
						<input type="button" name="delete" value="删除" class="button" onclick="deleteMulti_onclick();"/>
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="list_table">
					<tr>
						<th width="5%">
							<input type="checkbox" name="dv_checkbox_all" value="" onclick="selectAll(this)"/>	
						</th>
						<th width="5%">序号</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.owner_table_name }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.owner_bill_id }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.owner_column_name }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.file_name }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.file_url }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.file_type }</th>
						<th width="10%">${TABLE_COLUMN_CHINESE.file_size }</th>
						<th width="10%">备注</th>
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.file_id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.owner_table_name }"/></td>
						<td><c:out value="${bean.owner_bill_id }"/></td>
						<td><c:out value="${bean.owner_column_name }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.file_id }"/>')"><c:out value="${bean.file_name }"/></a></td>
						<td><c:out value="${bean.file_url }"/></td>
						<td><c:out value="${bean.file_type }"/></td>
						<td><c:out value="${bean.file_size }"/></td>
						<td><c:out value="${bean.remark }"/></td>
					</tr>
					</c:forEach>  
				</table>
			</div>
		</div>
		<div class="page_div">
			<jsp:include page="/jsp/include/page.jsp"/>
		</div>
		
	</form>
</body>
</html>
