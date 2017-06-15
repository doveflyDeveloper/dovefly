/**
 * 返回选中的checkbox或radio数组，如果参数attrName不为空，则返回的是其属性值数组
 * 
 * @param name
 *            DOM对象的name
 * @param attrName
 *            DOM对象的属性名
 * @returns {Array}
 */
function findSelections(name, attrName) {
	var array = [];
	if (!name)
		name = "dv_checkbox";
	if (!attrName) {
		array = $("input[name='" + name + "']:checked").get();
	} else {
		array = $("input[name='" + name + "']:checked").map(function() {
			return $(this).attr(attrName);
		}).get();
	}
	return array;
}

/**
 * 校验属性值是否等于特定值，如果selections是数组，则校验数组中每个对象的attrName属性是否等于attrValue
 * 
 * @param selections
 *            被校验的Dom对象或对象数组
 * @param attrName
 *            对象的属性名
 * @param attrValue
 *            对象的属性值
 * @returns {Boolean}
 */
function validateSelections(selections, attrName, attrValue) {
	var rtValue = true;
	if ($.isArray(selections)) {
		$.each(selections, function(i, n) {
			rtValue = rtValue && ($(n).attr(attrName) == attrValue);
		});
	} else {
		rtValue = rtValue && ($(selections).attr(attrName) == attrValue);
	}
	return rtValue;
}

/**
 * 自定义alert
 * 
 * @param msg
 *            提示信息
 * @param title
 *            标题（可省略）
 */
function dvAlert(msg, title) {
	$.jBox.info(msg, title);
}

/**
 * 自定义confirm
 * 
 * @param msg
 *            提示信息
 * @param title
 *            标题（可省略）
 * @param ok_func
 *            点击确定后执行的函数
 * @param cancel_func
 *            点击取消后执行的函数
 */
function dvConfirm(msg, title, ok_func, cancel_func) {
	if (title && (title instanceof Function)) {// 如果title被省略，则参数前移，title实际为ok_func
		cancel_func = ok_func;
		ok_func = title;
		title = "提示";
	}

	$.jBox.confirm(msg, title, function(v, h, f) {
		if (v == 'ok') {
			if (ok_func && (ok_func instanceof Function)) {
				ok_func();
			}
		} else if (v == 'cancel') {
			if (cancel_func && (cancel_func instanceof Function)) {
				cancel_func();// 取消
			}
		}
		return true; // 始终返回true，表示关闭确认框
	});
}

/**
 * 自定义Prompt
 * 
 * @param content
 *            自定义表单
 * @param title
 *            标题（可省略）
 * @param submit
 *            点击确定后执行的函数
 */
function dvPrompt(content, title, options) {
	$.jBox.prompt(content, title, 'none', options);
}

/**
 * 打开自定义dialog窗口
 * 
 * @param title
 *            标题
 * @param msg
 *            提示信息
 */
function dvOpenDialog(url, title, width, height, options) {
	
	var settings = $.extend({
		top : '10%',
		buttons : {
			'确定' : 'ok',
			'关闭' : true
		},
		submit: function (v, h, f) {
			/* 点击窗口按钮后的回调函数，返回true时表示关闭窗口，参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
		    if (v == 'ok') {
		    	var ifr = h.find("iframe").get(0);
		    	var win = ifr.window || ifr.contentWindow;
		    	if (win.ok_onclick) {
		    		return win.ok_onclick();
		    	}
		    }
			return true; 
		}
	}, options);
	
	$.jBox.open("iframe:" + url, title, width, height, settings);
}

/**
 * 关闭最前面打开的自定义dialog窗口，如果是true显示关闭所有已打开的自定义dialog窗口
 * 
 * @param token
 * @returns
 */
function dvCloseDialog(token) {
	$.jBox.close(token);
}

/**
 * 显示操作结果提示
 * 
 * @param message
 * @param type
 *    可选值有'info'、'success'、'warning'、'error'、'loading'，默认值为'info'，当为'loading'时，timeout值会被设置为0，表示不会自动关闭。
 */
function dvTip(message, type) {
	if (!message)
		message = "操作成功！";
	if (!type)
		type = "success";
	$.jBox.tip(message, type);
}

/**
 * 返回按钮，点击退回上一步，对于拥有iframe子页面的页面通过document.referrer进行返回，避免子页面的退回。
 */
function back_onclick() {
	if (window.frames && window.frames.length) {
		var referer = document.referrer;
		if (document.referrer) {
			window.location.href = document.referrer;
		} else {
			window.history.back();
		}
	} else {
		window.history.back();
	}
}

/**
 * 打开导入文件上传窗口
 * 
 * @param title
 *            标题
 * @param msg
 *            提示信息
 */
function dvOpenImportDialog(redirectUrl, downloadUrl, title) {
	var url = context_path + "/jsp/include/upload/fileUpload.jsp?redirectUrl=" + redirectUrl + "&downloadUrl=" + downloadUrl;
	var width = 600;
	var height = 150;

	var options = {
		buttons : {
			'下载模板' : 'download',
			'导入数据' : 'import',
			'关闭' : false
		},
		submit: function (v, h, f) {
			/* 点击窗口按钮后的回调函数，返回true时表示关闭窗口，参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
		    if (v == 'download') {
		    	var ifr = h.find("iframe").get(0);
		    	var win = ifr.window || ifr.contentWindow;
		    	if (win.download_onclick) {
		    		return win.download_onclick();
		    	}
		    } else if (v == 'import') {
		    	var ifr = h.find("iframe").get(0);
		    	var win = ifr.window || ifr.contentWindow;
		    	if (win.import_onclick) {
		    		return win.import_onclick();
		    	}
		    }
			return true; 
		}
	};
	dvOpenDialog(url, title, width, height, options);
}

/**
 * 参照人员信息
 * 
 * @param options
 *    参数默认值及可选值如下：<br/> var options = { url: context_path +
 *    "/jsp/module/auth/tauemployee/reference/referenceEmpFrame.jsp",//----请求地址<br/>
 *    title: "人员参照", //----标题<br/> width: 600, //----宽度<br/> height:
 *    400, //----高度<br/> callback: "referenceEmp_callback",
 *    //----回调函数，如果自定义，请务必包含如下代码：dvCloseDialog();以关闭参照窗口<br/>
 *    inputType: "radio", //----可选参数："radio"：单选模式, "checkbox"：多选模式<br/>
 *    referType: "tree" //----可选参数："tree"：以树状模式展现, "search"：以搜索列表模式展现,
 *    "all"：同时包含前两者的页签<br/> };
 */
function referenceEmp(options) {
	var settings = $.extend({
		url : context_path + "/jsp/module/auth/tauemployee/reference/referenceEmpFrame.jsp",
		title : "人员参照",
		width : 300,
		height : 400,
		callback : "referenceEmp_callback",
		inputType : "radio",
		referType : "tree"
	}, options);

	reference(settings.url, settings.title, settings.width, settings.height, settings.callback, settings.inputType, settings.referType);
}

/**
 * 参照人员信息默认回调函数，包含着对参照窗口的关闭操作
 * 
 * @param datas
 */
function referenceEmp_callback(datas) {
	dvCloseDialog();// 关闭参照窗口

	// 枚举（循环）对象的所有属性
	for (i in datas) {
		var obj = datas[i];
		$("input[name=emp_id]").val(obj.emp_id);
		$("input[name=emp_code]").val(obj.emp_code);
		$("input[name=emp_name]").val(obj.emp_name);
	}
}

/**
 * 参照组织信息
 * 
 * @param options
 *    参数默认值及可选值如下：<br/> var options = { url: context_path +
 *    "/jsp/module/sys/org/reference/referenceOrgFrame.jsp",//----请求地址<br/>
 *    title: "人员参照", //----标题<br/> width: 300, //----宽度<br/> height:
 *    400, //----高度<br/> callback: "referenceOrg_callback",
 *    //----回调函数，如果自定义，请务必包含如下代码：dvCloseDialog();以关闭参照窗口<br/>
 *    inputType: "radio", //----可选参数："radio"：单选模式, "checkbox"：多选模式<br/>
 *    referType: "tree" //----可选参数："tree"：以树状模式展现, "search"：以搜索列表模式展现,
 *    "all"：同时包含前两者的页签<br/> };
 */
function referenceOrg(options) {
	var settings = $.extend({
		url : context_path + "/jsp/module/sys/org/reference/referenceOrgFrame.jsp",
		title : "组织参照",
		width : 300,
		height : 400,
		callback : "referenceOrg_callback",
		inputType : "radio",
		referType : "tree"
	}, options);

	reference(settings.url, settings.title, settings.width, settings.height, settings.callback, settings.inputType, settings.referType);
}

/**
 * 参照组织信息默认回调函数，包含着对参照窗口的关闭操作
 * 
 * @param datas
 */
function referenceOrg_callback(datas) {
	dvCloseDialog();// 关闭参照窗口

	// 枚举（循环）对象的所有属性
	for (i in datas) {
		var obj = datas[i];
		$("input[name=org_id]").val(obj.id);
		$("input[name=org_code]").val(obj.code);
		$("input[name=org_name]").val(obj.name);
	}
}

/**
 * 参照区域信息
 * 
 * @param options
 *    参数默认值及可选值如下：<br/> var options = { url: context_path +
 *    "/jsp/module/sys/org/reference/referenceOrgFrame.jsp",//----请求地址<br/>
 *    title: "人员参照", //----标题<br/> width: 300, //----宽度<br/> height:
 *    400, //----高度<br/> callback: "referenceOrg_callback",
 *    //----回调函数，如果自定义，请务必包含如下代码：dvCloseDialog();以关闭参照窗口<br/>
 *    inputType: "radio", //----可选参数："radio"：单选模式, "checkbox"：多选模式<br/>
 *    referType: "tree" //----可选参数："tree"：以树状模式展现, "search"：以搜索列表模式展现,
 *    "all"：同时包含前两者的页签<br/> };
 */
function referenceRegion(options) {
	var settings = $.extend({
		url : context_path + "/regionController/referenceRegion",
		title : "区域参照",
		width : 300,
		height : 500,
		callback : "referenceRegion_callback",
		inputType : "radio",
		referType : "tree"
	}, options);

	reference(settings.url, settings.title, settings.width, settings.height, settings.callback, settings.inputType, settings.referType);
}

/**
 * 参照组织信息默认回调函数，包含着对参照窗口的关闭操作
 * 
 * @param datas
 */
function referenceRegion_callback(datas) {
//	dvCloseDialog();// 关闭参照窗口

	// 枚举（循环）对象的所有属性
	for (i in datas) {
		var obj = datas[i];
		$("input[name=region_id]").val(obj.id);
		$("input[name=region_code]").val(obj.code);
		$("input[name=region_name]").val(obj.name);
		$("input[name=region_full_name]").val(obj.full_name);
	}
}

/**
 * 打开参照窗口
 * 
 * @param url
 * @param title
 * @param width
 * @param height
 * @param callback
 * @param inputType
 * @param referType
 */
function reference(url, title, width, height, callback, inputType, referType, options) {
	var settings = $.extend({
		top : '5%',
		buttons : {
			'确定' : 'ok',
			'清空' : 'clear',
			'关闭' : true
		},
		submit: function (v, h, f) {
			/* 点击窗口按钮后的回调函数，返回true时表示关闭窗口，参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
		    if (v == 'ok') {
		    	var ifr = h.find("iframe").get(0);
		    	var win = ifr.window || ifr.contentWindow;
		    	if (win.ok_onclick) {
		    		return win.ok_onclick();
		    	}
		    } else if (v == 'clear') {
		    	var ifr = h.find("iframe").get(0);
		    	var win = ifr.window || ifr.contentWindow;
		    	if (win.clear_onclick) {
		    		return win.clear_onclick();
		    	}
		    }
			return true; 
		}
	}, options);
	
	if (url) {
		if (/\?/.test(url)) {// 原URL已带有参数
			url += "&callback=" + callback + "&inputType=" + inputType + "&referType=" + referType;
		} else {// 原URL无参数
			url += "?callback=" + callback + "&inputType=" + inputType + "&referType=" + referType;
		}
	}	
	
	dvOpenDialog(url, title, width, height, settings);
}

function E(id) {
	return document.getElementById(id);
}

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}