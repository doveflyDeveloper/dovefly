<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.deertt.quickbundle.vo.Project" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单表生成</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("codeGenerateController");
	
	function loadTable_onclick() {
		if(validateForm()){
			formHelper.jSubmit(formHelper.buildAction("codeGenerateController", "loadTable"));
		}
	}

	function connectDB_onclick() {
		var v = $("#table_name").val();
		$("#table_name").removeAttr("validate");
		if(validateForm()){
			$("#table_name").attr("validate", "notNull");
			$.ajax({
				type: "POST",
				async: true,
				dataType: "json",
				url: context_path + "/codeGenerateController/connectDB",
				data: $("form").serialize(),
				success: function(result){
					if (result && result != 'null'){//存在
						$("#table_name").empty();//清空下拉框
						$("<option value=''>--请选择--</option>").appendTo("#table_name");
						for(var i in result){
							$("<option value='" + result[i] + "'>" + result[i] + "</option>").appendTo("#table_name");//添加下拉框的option
						}
						dvTip("数据库连接成功！", "success");
						$("#table_name").val(v).focus();
					} else {
						dvTip("数据库连接失败！", "error");
					}
				},
				error: function() {
					dvTip("数据库连接失败！", "error");
				}
			});
		}
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
					<div class="table_title">数据库信息</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<input type="button" name="save" value="加载表信息" class="button" onclick="loadTable_onclick();"/>
					</div>
				</div>
			</div>
			<div>
				<table class="query_table">
					<tr>
						<td class="label"><span class="required_red">* </span>数据库：</td>
						<td class="field">
							<select name='dbProductName' class='' inputName='数据库' validate='notNull' >
								<option value='Oracle'>Oracle</option>
								<option value='Sybase'>Sybase</option>
								<option value='Microsoft SQL Server'>Microsoft SQL Server</option>
								<option value='MySQL' selected='selected'>MySQL</option>
								<option value='DB2'>DB2</option>
								<option value='Access'>Access</option>
							</select>
						</td>
						<td class="label"><span class="required_red">* </span>数据库驱动：</td>
						<td class="field">
							<select name='driver' class='' inputName='数据库驱动' validate='notNull' >
								<option value='com.mysql.jdbc.Driver' selected='selected' >com.mysql.jdbc.Driver</option>
								<option value='net.sourceforge.jtds.jdbc.Driver'>net.sourceforge.jtds.jdbc.Driver</option>
								<option value='sun.jdbc.odbc.JdbcOdbcDriver'>sun.jdbc.odbc.JdbcOdbcDriver</option>
								<option value='com.ibm.db2.jcc.DB2Driver'>com.ibm.db2.jcc.DB2Driver</option>
								<option value='oracle.jdbc.driver.OracleDriver'>oracle.jdbc.driver.OracleDriver</option>
								<option value='com.sybase.jdbc2.jdbc.SybDriver'>com.sybase.jdbc2.jdbc.SybDriver</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>数据库地址：</td>
						<td class="field" colspan="3">
							<input type="text" name="url" class="full_input" maxlength="200" value="jdbc:mysql://139.129.96.176:3306/deertt?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true" inputName="数据库地址" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>用户名：</td>
						<td class="field">
							<input type="text" name="userName" class="input" maxlength="50" value="deertt01" inputName="用户名" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>密码：</td>
						<td class="field">
							<input type="password" name="password" class="input" maxlength="50" value="deertt001" inputName="密码" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>数据库表：</td>
						<td class="field">
							<select id="table_name" name="table_name" inputName="数据库表" validate="notNull">
								<option value="">--请选择--</option>
							</select>
						</td>
						<td class="label"></td>
						<td class="field">
							<input type="button" name="connect" value="连接数据库" class="button" onclick="connectDB_onclick();" />
						</td>
					</tr>
					<tr>
						<td class="label"></td>
						<td class="field">
						</td>
						<td class="label"></td>
						<td class="field">
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>项目路径：</td>
						<td class="field" colspan="3">
							<input type="text" name="baseProjectPath" class="full_input" maxlength="200" value="E:/IDE/workspace/deertt-admin" inputName="项目路径" validate="notNull"/>
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>web应用名：</td>
						<td class="field">
							<input type="text" name="webAppName" class="input" maxlength="50" value="WebContent" inputName="web应用名" validate="notNull"/>
						</td>
						<td class="label">注释作者：</td>
						<td class="field">
							<input type="text" name="authorName" class="input" maxlength="50" value="<c:out value="${bean.authorName }"/>" inputName="web应用名" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>java基本包名：</td>
						<td class="field">
							<input type="text" name="javaPackageName" class="input" maxlength="100" value="<c:out value="${bean.javaPackageName }"/>" inputName="java基本包名" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>java源文件基目录：</td>
						<td class="field">
							<input type="text" name="javaFileRealPath" class="input" maxlength="100" value="<c:out value="${bean.javaFileRealPath }"/>" inputName="java源文件基目录" validate="notNull"/>	
						</td>
					</tr>
					<tr>
						<td class="label"><span class="required_red">* </span>jsp文件基目录：</td>
						<td class="field">
							<input type="text" name="jspSourcePath" class="input" maxlength="100" value="<c:out value="${bean.jspSourcePath }"/>" inputName="jsp文件基目录" validate="notNull"/>	
						</td>
						<td class="label"><span class="required_red">* </span>EXCEL模板基目录：</td>
						<td class="field">
							<input type="text" name="xlsTemplateBasePath" class="input" maxlength="200" value="<c:out value="${bean.xlsTemplateBasePath }"/>" inputName="EXCEL模板基目录" validate="notNull"/>	
						</td>
					</tr>
				</table>
			</div>			
		</div>
	</form>
</body>
</html>
