<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  	boolean isModify = (request.getAttribute("isModify") != null);
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>歌曲<%=isModify ? "修改" : "新增"%></title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("mvSongController");
	
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
					<div class="table_title">歌曲比赛<%= isModify ? "修改" : "新增"%>&nbsp;</div>
					<div class="table_title_tip" rel="<%=request.getContextPath()%>/helpController/getHelp/mv_show"></div>
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
						<td class="label">城市id：</td>
						<td class="field">
							<input type="text" name="city_id" class="input" inputName="城市id" value="<c:out value="${bean.city_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">城市：</td>
						<td class="field">
							<input type="text" name="city_name" class="input" inputName="城市" value="<c:out value="${bean.city_name }"/>" maxlength="10" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">学校id：</td>
						<td class="field">
							<input type="text" name="school_id" class="input" inputName="学校id" value="<c:out value="${bean.school_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">学校：</td>
						<td class="field">
							<input type="text" name="school_name" class="input" inputName="学校" value="<c:out value="${bean.school_name }"/>" maxlength="30" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>参赛人id：</td>
						<td class="field">
							<input type="text" name="user_id" class="input" inputName="参赛人id" value="<c:out value="${bean.user_id }"/>" integerDigits="0" decimalDigits="0" validate="notNull"/>
						</td>
						<td class="label"><span class="required_red">* </span>参赛人：</td>
						<td class="field">
							<input type="text" name="user_name" class="input" inputName="参赛人" value="<c:out value="${bean.user_name }"/>" maxlength="20" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label">推荐人id：</td>
						<td class="field">
							<input type="text" name="recommend_id" class="input" inputName="推荐人id" value="<c:out value="${bean.recommend_id }"/>" integerDigits="0" decimalDigits="0" validate=""/>
						</td>
						<td class="label">推荐人：</td>
						<td class="field">
							<input type="text" name="recommend_name" class="input" inputName="推荐人" value="<c:out value="${bean.recommend_name }"/>" maxlength="20" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>姓名：</td>
						<td class="field">
							<input type="text" name="name" class="input" inputName="姓名" value="<c:out value="${bean.name }"/>" maxlength="20" validate="notNull"/>
						</td>
						<td class="label">手机号：</td>
						<td class="field">
							<input type="text" name="mobile" class="input" inputName="手机号" value="<c:out value="${bean.mobile }"/>" maxlength="20" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">邮箱：</td>
						<td class="field">
							<input type="text" name="email" class="input" inputName="邮箱" value="<c:out value="${bean.email }"/>" maxlength="20" validate=""/>
						</td>
						<td class="label">院系专业：</td>
						<td class="field">
							<input type="text" name="major" class="input" inputName="院系专业" value="<c:out value="${bean.major }"/>" maxlength="20" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">歌曲名称：</td>
						<td class="field">
							<input type="text" name="mv_name" class="input" inputName="歌曲名称" value="<c:out value="${bean.mv_name }"/>" maxlength="50" validate=""/>
						</td>
						<td class="label">歌曲类型：</td>
						<td class="field">
							<input type="text" name="mv_type" class="input" inputName="歌曲类型" value="<c:out value="${bean.mv_type }"/>" maxlength="10" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label">歌词：</td>
						<td class="field">
							<textarea name="mv_brief" class="input" inputName="歌词">${bean.mv_brief }</textarea>
						</td>
						<td class="label">视频截图：</td>
						<td class="field">
							<input type="text" name="mv_images" class="input" inputName="视频截图" value="<c:out value="${bean.mv_images }"/>" maxlength="200" validate=""/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>汀豆：</td>
						<td class="field">
							<input type="text" name="mv_coin" class="input" inputName="汀豆" value="<c:out value="${bean.mv_coin }"/>" integerDigits="0" decimalDigits="0" validate="notNull"/>
						</td>
						<td class="label">歌曲介绍：</td>
						<td class="field">
							<textarea name="mv_desc" class="input" inputName="歌曲介绍">${bean.mv_desc }</textarea>
						</td>
					</tr>
					<tr>
						<td class="label">视频文件：</td>
						<td class="field">
							<input type="text" name="mv_file" class="input" inputName="视频文件" value="<c:out value="${bean.mv_file }"/>" maxlength="100" validate=""/>
						</td>
						<td class="label">备注：</td>
						<td class="field">
							<input type="text" name="remark" class="input" inputName="备注" value="<c:out value="${bean.remark }"/>" maxlength="100" validate=""/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" name="id" value="<c:out value="${bean.id }"/>"/>
		<input type="hidden" name="sort" value="<c:out value="${bean.sort }"/>"/>
		<input type="hidden" name="status" value="<c:out value="${bean.status }"/>"/>
	</form>
</body>
</html>
