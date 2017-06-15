/**
 * 构建表单提交工具类
 * 
 * @param controller
 *            提交给的Controller
 * @param formId
 *            要提交的表单的ID或name，如果为空，默认值为"form"，如果获取不到对应的FORM，则取Document.forms[0]
 * @returns {DvFormHelper}
 */
function DvFormHelper(controller, formId) {

	this.formId = formId || "form";
	this.controller = controller;
	this.createMethod = "create";
	this.copyMethod = "copy";
	this.findMethod = "find";
	this.insertMethod = "insert";
	this.updateMethod = "update";
	this.deleteMethod = "delete";
	this.deleteMultiMethod = "deleteMulti";
	this.detailMethod = "detail";
	this.queryMethod = "query";
	this.referenceMethod = "reference";
	this.downloadTemplateMethod = "downloadTemplate";
	this.importExcelMethod = "importExcel";
	this.exportListMethod = "exportList";
	this.exportDetailMethod = "exportDetail";

	/**
	 * 取得当前form，如果获取不到，则取Document.forms[0]
	 */
	this.getForm = function() {
		return document.forms[this.formId] || document.forms[0];
	};
	
	/**
	 * 设置当前form
	 */
	this.setForm = function(formId) {
		this.formId = formId || "form";
		return this;
	};

	/**
	 * 获取上下文路径，效果等同于<%=request.getContextPath()%>
	 */
	this.getContextPath = function() {
		/*
		 * hash 设置或返回从井号 (#) 开始的 URL（锚）。 
		 * host 设置或返回主机名和当前 URL 的端口号。 
		 * hostname 设置或返回当前 URL 的主机名。 
		 * href 设置或返回完整的 URL。 
		 * pathname 设置或返回当前 URL 的路径部分。
		 * port 设置或返回当前 URL 的端口号。 
		 * protocol 设置或返回当前 URL 的协议。 
		 * search 设置或返回从问号 (?) 开始的 URL（查询部分）。
		 */
		// 此处有问题，默认为有端口号时有工程名，无端口号时无工程名字。
		var basePath = location.protocol + "//" + location.host + (location.port ? "/" + location.pathname.split("/")[1] : "");
		return basePath;
	};

	/**
	 * 删除左右两端的空格
	 */
	this.trim = function(str) {
		if (!str)
			return "";
		if (str instanceof Array) {// 是数组
			str = str.join(",");
		}
		return str.replace(/(^\\*)|(\\*$)/g, "");
	};

	/**
	 * 创建URL路径
	 */
	this.buildAction = function(controller, method, key) {
		return this.buildURL(controller, method, key);
	};

	/**
	 * 创建URL路径（基础方法）
	 */
	this.buildURL = function(controller, method, key) {
		var action = this.getContextPath();
		for ( var i = 0; i < arguments.length; i++) {
			var arg = arguments[i];
			if (arguments[i] instanceof Array) {// 是数组
				arg = arguments[i].join(",");
			}
			if (this.trim(arg)) {
				action += "/" + this.trim(arg);
			}
		}
		return action;
	};

	/**
	 * 创建新增URL路径
	 */
	this.buildCreateAction = function() {
		return this.buildAction(this.controller, this.createMethod, "");
	};

	/**
	 * 创建复制新增URL路径
	 */
	this.buildCopyAction = function(id) {
		return this.buildAction(this.controller, this.copyMethod, id);
	};

	/**
	 * 创建修改URL路径
	 */
	this.buildFindAction = function(id) {
		return this.buildAction(this.controller, this.findMethod, id);
	};

	/**
	 * 创建新增保存URL路径
	 */
	this.buildInsertAction = function() {
		return this.buildAction(this.controller, this.insertMethod, "");
	};

	/**
	 * 创建更新保存URL路径
	 */
	this.buildUpdateAction = function() {
		return this.buildAction(this.controller, this.updateMethod, "");
	};

	/**
	 * 创建删除URL路径
	 */
	this.buildDeleteAction = function(id) {
		return this.buildAction(this.controller, this.deleteMethod, id);
	};

	/**
	 * 创建批量删除URL路径
	 */
	this.buildDeleteMultiAction = function(ids) {
		if (ids)
			ids = ids + "";
		return this.buildAction(this.controller, this.deleteMultiMethod, ids);
	};

	/**
	 * 创建查看URL路径
	 */
	this.buildDetailAction = function(id) {
		return this.buildAction(this.controller, this.detailMethod, id);
	};

	/**
	 * 创建查询URL路径
	 */
	this.buildQueryAction = function() {
		return this.buildAction(this.controller, this.queryMethod, "");
	};

	/**
	 * 创建参照查询URL路径
	 */
	this.buildReferenceAction = function() {
		return this.buildAction(this.controller, this.referenceMethod, "");
	};

	/**
	 * 创建下载模板URL路径
	 */
	this.buildDowloadTemplateAction = function() {
		return this.buildAction(this.controller, this.downloadTemplateMethod, "");
	};

	/**
	 * 创建导入Excel的URL路径
	 */
	this.buildImportExcelAction = function() {
		return this.buildAction(this.controller, this.importExcelMethod, "");
	};

	/**
	 * 创建批量导出Excel的URL路径
	 */
	this.buildExportListAction = function() {
		return this.buildAction(this.controller, this.exportListMethod, "");
	};

	/**
	 * 创建导出Excel的URL路径
	 */
	this.buildExportDetailAction = function() {
		return this.buildAction(this.controller, this.exportDetailMethod, "");
	};
	
	/**
	 * 创建其他自定义的URL路径
	 */
	this.buildCustomAction = function(method) {
		return this.buildAction(this.controller, method, "");
	};

	/**
	 * 普通方式表单提交
	 */
	this.submit = function(action, post) {
		var form = this.getForm();
		var oldAction = form.action;
		var oldMethod = form.method;
		if (action) {
			form.action = action;
		}
		if (post) {
			form.method = post;
		}
		form.submit();
		form.action = oldAction;
		form.method = oldMethod;
	};

	/**
	 * jquery方式表单提交
	 */
	this.jSubmit = function(action, post) {
		var form = this.getForm();
		var oldAction = form.action;
		var oldMethod = form.method;
		if (action) {
			form.action = action;
		}
		if (post) {
			form.method = post;
		}
		$(form).submit();
		form.action = oldAction;
		form.method = oldMethod;
	};

}
