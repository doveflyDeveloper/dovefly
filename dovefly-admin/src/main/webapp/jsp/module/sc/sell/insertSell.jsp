<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二手买卖<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("sellController");
	
	/** 保存 */
	function save_onclick() {
		if(validateForm()){
			if(<%=isModify%>){
				update_onclick();
			} else {
				insert_onclick();
			}
		}
	}
	
	/** 新增保存 */
	function insert_onclick() {
		formHelper.jSubmit(formHelper.buildInsertAction());
	}
	
	/** 修改保存 */
	function update_onclick() {
		formHelper.jSubmit(formHelper.buildUpdateAction());
	}
	
	$(document).ready(function() {
	
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">二手买卖<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/sc_sell"></div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="保存" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</div>
				</div>
			</div>
			<div>
				<table class="insert_table">
					<tr>
						<td class="label"><span class="required_red">* </span>标题：</td>
						<td class="field">
							<input type="text" name="title" class="input" inputName="标题" value="<c:out value="${bean.title }"/>" maxlength="100" validate="notNull"/>
						</td>
						<td class="label">类别：</td>
						<td class="field">
							<input type="text" name="type" class="input" inputName="类别" value="<c:out value="${bean.type }"/>" maxlength="1" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">新旧程度：</td>
						<td class="field">
							<input type="text" name="is_new" class="input" inputName="新旧程度" value="<c:out value="${bean.is_new }"/>" maxlength="1" validate=""/>
						</td>
						<td class="label">出手价格：</td>
						<td class="field">
							<input type="text" name="price" class="input" inputName="出手价格" value="<c:out value="${bean.price }"/>" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">购入价格：</td>
						<td class="field">
							<input type="text" name="old_price" class="input" inputName="购入价格" value="<c:out value="${bean.old_price }"/>" validate=""/>
						</td>
						<td class="label">卖家姓名：</td>
						<td class="field">
							<input type="text" name="seller_name" class="input" inputName="卖家姓名" value="<c:out value="${bean.seller_name }"/>" maxlength="10" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">卖家手机：</td>
						<td class="field">
							<input type="text" name="seller_mobile" class="input" inputName="卖家手机" value="<c:out value="${bean.seller_mobile }"/>" maxlength="11" validate=""/>
						</td>
						<td class="label">卖家qq：</td>
						<td class="field">
							<input type="text" name="seller_qq" class="input" inputName="卖家qq" value="<c:out value="${bean.seller_qq }"/>" maxlength="11" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">卖家微信：</td>
						<td class="field">
							<input type="text" name="seller_weixin" class="input" inputName="卖家微信" value="<c:out value="${bean.seller_weixin }"/>" maxlength="30" validate=""/>
						</td>
						<td class="label">宝贝首图：</td>
						<td class="field">
							<input type="text" name="image" class="input" inputName="宝贝首图" value="<c:out value="${bean.image }"/>" maxlength="50" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">宝贝图片集：</td>
						<td class="field">
							<textarea name="images" class="input" inputName="宝贝图片集">${bean.images }</textarea>
						</td>
						<td class="label">宝贝描述：</td>
						<td class="field">
							<textarea name="description" class="input" inputName="宝贝描述">${bean.description }</textarea>
						</td>
					</tr>
					<tr>
						<td class="label">发布范围：</td>
						<td class="field">
							<input type="text" name="issue_range" class="input" inputName="发布范围" value="<c:out value="${bean.issue_range }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label">发布日期：</td>
						<td class="field">
							<input type="text" name="issue_date" class="input Wdate" inputName="发布日期" value="${fn:substring(bean.issue_date, 0, 19) }" validate=""/>	
						</td>
					</tr>
					<tr>
						<td class="label">截止日期：</td>
						<td class="field">
							<input type="text" name="end_date" class="input Wdate" inputName="截止日期" value="${fn:substring(bean.end_date, 0, 19) }" validate="" format="both"/>	
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
		<input type="hidden" name="create_by" value="<c:out value="${bean.create_by }"/>"/>
		<input type="hidden" name="create_at" value="<c:out value="${bean.create_at }"/>"/>
		<input type="hidden" name="modify_by" value="<c:out value="${bean.modify_by }"/>"/>
		<input type="hidden" name="modify_at" value="<c:out value="${bean.modify_at }"/>"/>
	</form>
</body>
</html>
