<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.quickbundle.vo.Project" %>
<%@ page import="com.deertt.quickbundle.vo.Table" %>
<%@ page import="com.deertt.quickbundle.vo.Column" %>
<%

%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单表代码自动生成</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/JQuery_zTree_v3.5.15/js/jquery.ztree.excheck-3.5.min.js"></script>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("codeGenerateController");
	
	function save_onclick() {
		if(validateForm()){
			formHelper.jSubmit(formHelper.buildAction("codeGenerateController", "generateTable"));
		}
	}

	var zNodes = [
	<c:forEach var="valid" varStatus="status" items="${validateBeans }">
		{id:"${valid.dic_key }", pId:"0", name:"${valid.dic_value }"}<c:if test="${!status.last }">,</c:if>
	</c:forEach>  
	];
	
	var setting = {
		check: {
			enable: true,
			chkboxType: { "Y" : "", "N" : "" }
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onCheck: onCheck
		}
	};

	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		nodes = zTree.getCheckedNodes(true);
		var v = "";
		for ( var i = 0; i < nodes.length; i++) {
			v += nodes[i].name + ";";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var targetId = $("#menuContent").attr("targetId");
		$("#" + targetId).val(v);
	}
	
	function showMenu(input) {
		var val = $(input).val();
		var vals = [];
		if(val){
			vals = val.split(";");
		}
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkAllNodes(false);
		for (var i in vals) {
			var node = zTree.getNodeByParam("id", vals[i], null);
			zTree.checkNode(node, true, false, false);
		}

		var inputOffset = $(input).offset();
		$("#menuContent").attr("targetId", $(input).attr("id"));
//		.css({
//			left : inputOffset.left + "px",
//			top : inputOffset.top + $(input).outerHeight() + "px"
//		}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}

	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}

	function onBodyDown(event) {
		if (!(event.target.id == $("#menuContent").attr("targetId")
				|| event.target.id == "menuContent" || $(event.target).parents(
				"#menuContent").length > 0)) {
			hideMenu();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		$("input[name$='validateType']").each(function() {
			$(this).powerFloat({
				eventType: "click",
				target: function() {
					showMenu(this);
					return $("#menuContent");
				}
			});
		});
	});
</script>
</head>
<body>
	<form id="form" name="form" method="post">
		<div style="height: 4px"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">单表代码自动生成</div>
				</div>
				<div class="right_div">
					<span class="right_menu">
						<input type="button" name="save" value="生成代码" class="button" onclick="save_onclick();"/>
						<input type="button" name="back" value="返回" class="button" onclick="back_onclick();" />
					</span>
				</div>
			</div>
			<div>
				<table class="query_table">
					<tr>
						<td class="label"><span class="required_red">* </span>表名：</td>
						<td class="field">
							<input type="text" name="tableName" class="input" maxlength="50" inputName="表名" value="<c:out value="${bean.tableName }"/>" validate="notNull" title="表名"/>	
						</td>
						<td class="label"><span class="required_red">* </span>汉化表名：</td>
						<td class="field">
							<input type="text" name="tableNameChinese" class="input" maxlength="50" inputName="汉化表名" value="<c:out value="${bean.tableNameChinese }"/>" validate="notNull" title="表的中文名称"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>主键列名：</td>
						<td class="field">
							<input type="text" name="tablePk" class="input" maxlength="50" inputName="主键列名" value="<c:out value="${bean.tablePk }"/>" validate="notNull" title="表的主键字段"/>
						</td>
						<td class="label"><span class="required_red">* </span>关键列名：</td>
						<td class="field">
							<select name="keyColumn" class="" inputName="关键列名" validate="notNull" title="表的关键字段，如公告表的标题字段，单据表的单据名字段等，在查询列表此字段显示为超链接，用以单击查看详细。">
								<c:forEach var="col" varStatus="status" items="${bean.columns }">
								<option value="${col.columnName }" <c:if test="${col.columnName == bean.keyColumn }">selected="selected"</c:if> >${col.columnName }</option>
								</c:forEach>  
							</select>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>是否带附件：</td>
						<td class="field">
							<dv:select name="hasFiles" dicKeyword="DIC_YES_NO" defaultValue="${bean.hasFiles }" attributes="inputName='是否带附件功能' validate='notNull' title='单据是否包含附件上传功能'"/>
						</td>
						<td class="label"><span class="required_red">* </span>是否带子页签：</td>
						<td class="field">
							<dv:select name="hasSubTabs" dicKeyword="DIC_YES_NO" defaultValue="${bean.hasSubTabs }" attributes="inputName='是否带子页签功能' validate='notNull' title='单据是否子页签功能，存在主子表关系的子表页签'"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>是否包含导入导出功能：</td>
						<td class="field">
							<dv:select name="hasImpExp" dicKeyword="DIC_YES_NO" defaultValue="${bean.hasImpExp }" attributes="inputName='是否带导入导出功能' validate='notNull' title='单据是否包含导入导出功能'"/>
						</td>
						<td class="label"></td>
						<td class="field">
							
						</td>
					</tr>
				</table>
			</div>
			<div class="padding_2_div">
				<DL id="sub_tab">
					<DT>表字段处理</DT>
					<DD>
						<div style="overflow: auto;">
							<table class="list_table">
								<tr>
									<th width="5%">序号</th>
									<th width="10%" title="勾选后，则在增删改查等页面生成相应的表单域，一些默认、备用字段无需勾选。">创建列</th>
									<th width="10%" title="勾选后，在列表查询页面的查询条件区域生成相应的表单域，并在查询列表显示该列。">列表列</th>
									<th width="10%" title="字段名称，用以生成实体对象的各个属性。"><span class="required_red">* </span>字段名</th>
									<th width="10%" title="各字段的中文名称，在增删改查等页面的表单域前显示。"><span class="required_red">* </span>中文名</th>
									<th width="10%" title="生成实体对象时的各个属性的数据类型。"><span class="required_red">* </span>java标准类型</th>
									<th width="10%" title="生成表单域的maxlength属性。"><span class="required_red">* </span>最大宽度</th>
									<th width="10%" title="数值的整数部分的最大长度，数据类型为数字时有效。">整数宽度</th>
									<th width="10%" title="数值的小数部分的最大长度，数据类型为数字时有效。">浮点宽度</th>
									<th width="10%" title="生成表单域的样式（下拉框，单选框，复选框，文本域，富文本编辑框，组织机构参照，人员参照等）。">表单域类型</th>
									<th width="10%" title="数据字典关键词，规范的写法是以DIC开头的下划线隔开的大写的字符串：DIC_XXX_XXX...。“表单域类型”项选择以dv.dictionary开头的项时需填写此项。">数据字典关键词</th>
									<th width="10%" title="生成表单域的校验规则（非空，数字，邮箱，电话，长度限制等）。">字段校验规则</th>
								</tr>
								<c:forEach var="column" varStatus="status" items="${bean.columns }">
								<tr>
									<td>${status.count }</td>
									<td><input type="checkbox" name="columns[${status.index }].isBuild" inputName="创建列" value="${column.isBuild }" <c:if test="${column.isBuild == 1 }">checked="checked"</c:if>/></td>
									<td><input type="checkbox" name="columns[${status.index }].isBuild_list" inputName="列表列" value="${column.isBuild_list }" <c:if test="${column.isBuild_list == 1 }">checked="checked"</c:if>/></td>
									<td><input type="text" name="columns[${status.index }].columnName" class="readonly" style="width: 100px" maxlength="50" inputName="字段名" value="${column.columnName }" validate="notNull"/></td>
									<td><input type="text" name="columns[${status.index }].columnNameChinese" class="input" style="width: 100px" maxlength="50" inputName="中文名" value="${column.columnNameChinese }" validate="notNull"/></td>
									<td><dv:select name="columns[${status.index }].dataType" dicKeyword="DIC_QB_DATA_TYPE" defaultValue="${column.dataType }" attributes="inputName='java标准类型' style='width: 130px' validate='notNull'"/></td>
									<td><input type="text" name="columns[${status.index }].columnSize" class="input" style="width: 25px" maxlength="50" inputName="最大宽度" value="${column.columnSize }" validate="notNull"/></td>
									<td><input type="text" name="columns[${status.index }].integerDigits" class="input" style="width: 25px" maxlength="50" inputName="整数宽度" value="${column.integerDigits }"/></td>
									<td><input type="text" name="columns[${status.index }].decimalDigits" class="input" style="width: 25px" maxlength="50" inputName="浮点宽度" value="${column.decimalDigits }"/></td>
									<td><dv:select name="columns[${status.index }].inputType" dicKeyword="DIC_QB_INPUT_TYPE" defaultValue="${column.inputType }" attributes="inputName='表单域类型' style='width: 140px'"/></td>
									<td><input type="text" name="columns[${status.index }].dicTypeKeyword" class="input" style="width: 100px" maxlength="50" inputName="数据字典关键词" value="${column.dicTypeKeyword }"/></td>
									<td><input type="text" id="validateType${status.index }" name="columns[${status.index }].validateType" class="input" style="width: 100px" maxlength="100" inputName="字段校验规则" value="${column.validateType }"></td>
								</tr>
								</c:forEach>  
							</table>
						</div>
					</DD>
				</DL>
			</div>
			<div id="menuContent" class="border_div" style="background-color: #f6f6f6; display: none; position: absolute;">
				<ul id="treeDemo" class="ztree" style="margin-top:0; width:140px; height: 200px;"></ul>
			</div>
		</div>
		<!-- Project对象参数 -->
		<input type="hidden" name="dbProductName" value="${project.dbProductName }"/>
		<input type="hidden" name="driver" value="${project.driver }"/>
		<input type="hidden" name="url" value="${project.url }"/>
		<input type="hidden" name="userName" value="${project.userName }"/>
		<input type="hidden" name="password" value="${project.password }"/>
		<input type="hidden" name="baseProjectPath" value="${project.baseProjectPath }"/>
		<input type="hidden" name="webAppName" value="${project.webAppName }"/>
		<input type="hidden" name="authorName" value="${project.authorName }"/>
		<input type="hidden" name="javaPackageName" value="${project.javaPackageName }"/>
		<input type="hidden" name="javaFileRealPath" value="${project.javaFileRealPath }"/>
		<input type="hidden" name="jspSourcePath" value="${project.jspSourcePath }"/>
		<input type="hidden" name="xlsTemplateBasePath" value="${project.xlsTemplateBasePath }"/>
	</form>
</body>
</html>
