<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>兼职招聘信息</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("jobController");
	
	/** 修改 */
	function find_onclick() {
		formHelper.jSubmit(formHelper.buildFindAction("${bean.id }"));
	}
	
	/** 发布 */
	function issue_onclick() {
		formHelper.jSubmit(formHelper.buildAction("jobController", "issue", "${bean.id }"));
	}
	
	/** 取消发布 */
	function cancelIssue_onclick() {
		formHelper.jSubmit(formHelper.buildAction("jobController", "cancelIssue", "${bean.id }"));
	}
	
	/** 联系 */
	function deal_onclick(id) {
		dvPrompt(window.dealDivHtml, '联系应聘者', {
			top: '5%',
			width: 700,
			bottomText: '<span class="left_ts"></span>',
			loaded: function (h) {
				
			},
			submit: function(v, h, f) {
				if (validateForm("deal_insert_table")) {
					$.ajax({
						type: "POST",
						async: false,
						dataType: "json",
						url: context_path + "/jobController/changeDealStatus/" + id,
						data: {
							"accept_status": f['accept_status'],
							"remark":f['remark']
						},
						success: function(result){
							if(result){//存在
								if(result.success){
									//$(img).closest("tr").find("input[name='remark']").val(result.data.remark);
									dvTip(result.message, "success");
									window.location.reload();
								} else {
									dvTip(result.message, "error");
								}
							}
						}
					});
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	$(document).ready(function() {
		window.dealDivHtml = $("#dealDiv").html();
		$("#dealDiv").html("");
	});
	
</script>
</head>
<body>
	<form id="form" name="form" method="get">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">兼职招聘信息&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sc_job"></div>
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
						<c:if test="${bean.issue_status == '2'}">
						<input type="button" name="find" value="修改" class="button" onclick="find_onclick();"/>
						<input type="button" name="issue" value="发表" class="button" onclick="issue_onclick();"/>
						</c:if>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div class="padding_2_div">
				<table class="detail_table">
					<tr>
						<td class="label">标题：</td>
						<td class="field"><c:out value="${bean.title }"/></td>
						<td class="label">招聘公司：</td>
						<td class="field"><c:out value="${bean.company }"/></td>
					</tr>
					<tr>
						<td class="label">职位薪资：</td>
						<td class="field">
						<c:out value="${bean.salary }"/>
						<dv:display dicKeyword="DIC_SC_JOB_Salary_Unit" value="${bean.salary_unit }" />
						</td>
						<td class="label">处理人：</td>
						<td class="field"><c:out value="${bean.user_name }"/></td>
					</tr>
					<tr>
					<td class="label">工作类型：</td>
					<td class="field"><dv:display dicKeyword="DIC_SC_JOB_TYPE" value="${bean.job_type }"/></td>
					</tr>
					<tr>
						<td class="label">工作地点：</td>
						<td class="field"><c:out value="${bean.work_place }"/></td>
					</tr>
					<tr colspan="3">
						<td class="label" >职位描述：</td>
						<td class="field"><c:out value="${bean.description }" escapeXml="false"/></td>
					</tr>
					<tr>
						<td class="label">日期：</td>
						<td class="field">${fn:substring(bean.begin_date, 0, 10) }</td>
						<td class="label">结束时间：</td>
						<td class="field">${fn:substring(bean.end_date, 0, 19) }</td>
					</tr>
				</table>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>申请人信息明细</DT>
					<DD>
						<div>
							<table id="detail_list_table" class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="15%">申请人地址</th>
									<th width="15%">申请人学校</th>
									<th width="10%">申请人</th>
									<th width="15%">申请人手机号</th>
									<th width="15%">是否已联系</th>
									<th width="5%">是否同意参加</th>
									<th width="10%">是否完成任务</th>
									<th width="10%">备注</th>
								</tr>
								<c:forEach var="detail" varStatus="status" items="${bean.details }">
								<tr>
									<td>${status.count }</td>
									<td><c:out value="${detail.address }"/></td>
									<td><c:out value="${detail.school }"/></td>
									<td><c:out value="${detail.name }"/></td>
									<td><c:out value="${detail.mobile }"/></td>
									<td>
										<c:if test="${detail.deal_status == '0' }"><input type="button" name="deal" value="马上联系" class="button" onclick="deal_onclick('${detail.id }');"/></c:if>
										<c:if test="${detail.deal_status == '1' }"><dv:display dicKeyword="DIC_JOB_APPLY_DEAL_STATUS" value="${detail.deal_status  }" /></c:if>
									</td>
									<td><dv:display dicKeyword="DIC_YES_NO" value="${detail.accept_status }" /></td>
									<td><dv:display dicKeyword="DIC_FINISH_STATUS" value="${detail.finish_status }" /></td>
									<td><c:out value="${detail.remark }"/></td>
								</tr>
								</c:forEach>
							</table>
						</div>
					</DD>
				</DL>
			</div>
		</div>
	</form>
	<div id="dealDiv" style="display:none">
		<table id="deal_insert_table" class="insert_table">
			<tr>
				<td class="label"><span class="required_red">* </span>联系结果：</td>
				<td class="field">
					<label><input class="radio" name="accept_status" value="1" type="radio" />同意参加</label>&nbsp;&nbsp;
					<label><input class="radio" name="accept_status" value="0" type="radio" />放弃机会</label>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td class="field">
					<input type="text" name="remark" class="input" inputName="备注" value="" maxlength="100"/>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
