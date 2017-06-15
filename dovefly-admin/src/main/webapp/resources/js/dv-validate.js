/* ========= 校验功能 ===========
 * 这里添加一些使用说明吧
 *  
 *  
 * 
 *
 */

//给所有需要校验的（拥有validate属性）的表单域添加失去焦点即校验事件
$(document).ready(function() {
	$("[validate]").live('blur', function() {
		validateInput(this);
	});
});

/** 校验整个页面表单 */
function validateForm(formId) {
	var form = formId ? "#" + formId + " " : "";//"#formId "
	setAllDefault();
	var validateResult = true;
	$(form + "[validate]").each(function() {
		var rtValue = validateInput(this);
		if (validateResult && rtValue == false) {
			validateResult = false;
			this.focus();
		}
	});

	return validateResult;
}

/** 校验单个表单域 */
function validateInput(thisInput) {
	setDefault(thisInput);
	var inputValue = $(thisInput).val();
	var s = $.trim(inputValue);
	var validateStr = $(thisInput).attr("validate");
	if (validateStr) {
		var validateTemp = validateStr.split(';');
		for ( var i = 0; i < validateTemp.length; i++) {
			if (validateTemp[i].length == 0) {
				continue;
			}
			try {
				// "javascript:" + validateTemp[i] + "('" + s + "', " + "thisInput)"
				var scriptCode = "javascript:" + validateTemp[i];
				if (validateTemp[i].indexOf("(") < 0 || validateTemp[i].indexOf(")") < 0) {
					scriptCode = "javascript:" + validateTemp[i] + "(s, thisInput)";
				} else {
					var temScriptCode;
					do {
						temScriptCode = scriptCode;
						scriptCode = scriptCode.replace(/(\W)this(\W)/, "$1" + "thisInput" + "$2");
					} while (scriptCode != temScriptCode);

					if (/\(\S+\)/.exec(scriptCode)) {// 有参数
						scriptCode = scriptCode.replace(/\(/, "(s, thisInput, ");
					} else {// 无参数
						scriptCode = scriptCode.replace(/\(/, "(s, thisInput");
					}
				}
				// alert(scriptCode);
				if (!eval(scriptCode)) {
					return false;
				}
			} catch (e) {
				alert("校验函数" + validateTemp[i] + "有异常，请检查！" + "\n" + e.message);
				return false;
			}
		}
	}
	return true;
}

/** 还原所有表单域为原始状态 */
function setAllDefault() {
	$("[validate]").each(function() {
		setDefault(this);
	});
	return true;
}

/** 还原单个表单域为原始状态 */
function setDefault(thisInput) {
	$("#" + $(thisInput).attr("id") + "_error").remove();
	$(thisInput).removeClass("dvValidFailed");
}

/** 设置表单域校验失败样式 */
function setValidStyle(thisInput) {
	$(thisInput).addClass("dvValidFailed");
}

/** 获得表单域的inputName属性值 */
function getInputName(thisInput) {
	return $(thisInput).attr("inputName") || thisInput.name || "";
}

/** 设置表单域校验失败样式 */
function validateFailed(info, thisInput) {
	// 给校验失败的表单设置高亮样式
	setValidStyle(thisInput);
	// 在校验表单域旁写校验错误信息
	showValidError(info, thisInput);
}

/** 设置表单域校验失败样式 */
function showValidError_bak(info, thisInput) { // 写校验信息
	if (!Boolean($(thisInput).attr("id"))) {
		$(thisInput).attr("id", generateRandomId());
	}
	if (!$("#" + thisInput.id + "_error")[0]) {
		if ($(thisInput).css("width").match("px")) {
			left = Number($(thisInput).css("width").replace("px", "")) / 3 + "px";
			left_ie7 = Number($(thisInput).css("width").replace("px", "")) / 3
					- Number($(thisInput).css("width").replace("px", "")) + "px";
		} else if (thisInput.style.width.match("%")) {
			left = Number($(thisInput).css("width").replace("%", "")) / 3 + "%";
			left_ie7 = Number($(thisInput).css("width").replace("%", "")) / 3
					- Number($(thisInput).css("width").replace("%", "")) + "%";
		}

		if (Boolean($(thisInput).css("height"))) {
			height = -Number($(thisInput).css("height").replace("px", "")) - 23 + "px";
			height_ie7 = "-20px";
		}
		zIndex = getZIndex(thisInput);

		html = '<div id="'
				+ thisInput.id
				+ '_error" style="margin-top:'
				+ height
				+ ';*+margin-top:'
				+ height_ie7
				+ ';margin-left:'
				+ left
				+ ';*+margin-left:'
				+ left_ie7
				+ ';z-index:'
				+ zIndex
				+ ';position:absolute;" class="inputErrorInfo">'
				+ '	<table cellspacing="0" cellpadding="0"><tr><td style="vertical-align:top;background-color:transparent"><div class="inputErrorInfoDiv"></div></td><td style="background-color:transparent"><div class="inputErrorInfoSpan">'
				+ info + '</div></td></tr></table>' + '</div>';

		$(thisInput).after(html);
	}
}

/** 设置表单域校验失败样式 */
function showValidError(info, thisInput) { // 写校验信息
	if (!Boolean($(thisInput).attr("id"))) {
		$(thisInput).attr("id", generateRandomId());
	}
	if (!$("#" + thisInput.id + "_error")[0]) {
		var left = $(thisInput).width()/5;
		var height = $(thisInput).height();
		zIndex = getZIndex(thisInput);
/*
		var html = '<div id="' + thisInput.id + '_error" class="inputErrorInfo" style="position:absolute;margin-top:' + (-height - 23) + 'px;margin-left:' + left + 'px;z-index:' + zIndex + ';">'
				+ '	<table cellspacing="0" cellpadding="0"><tr><td style="vertical-align:top;background-color:transparent"><div class="inputErrorInfoDiv"></div></td><td style="background-color:transparent"><div class="inputErrorInfoSpan">'
				+ info + '</div></td></tr></table>' + '</div>';
*/		
		var html = '<div id="' + thisInput.id + '_error" class="inputErrorInfo" style="position:absolute;margin-top:' + (-height - 28) + 'px;margin-left:' + left + 'px;z-index:' + zIndex + ';">' + 
						'<div class="tooltips_box">' + 
							'<div class="tooltips">' + 
								'<div class="tooltips_msg">' + info + '</div>' + 
							'</div>' + 
							'<div class="tooltips_ov"></div>' + 
						'</div>' + 
					'</div>';

		$(thisInput).after(html);
	}
}

/** 本方法用于获取此元素的z-index，没有返回0 */
function getZIndex(thisObj) {
	zIndex = null;
	zIndexObj = thisObj;

	while (true) {
		if (zIndexObj.style.zIndex == "") {
			if (zIndexObj.parentNode.localName != null) {
				zIndexObj = zIndexObj.parentNode;
			} else {
				zIndex = 1;
				break;
			}
		} else {
			zIndex = zIndexObj.style.zIndex - 0 + 1;
			break;
		}
	}
	return zIndex;
}

/** 本方法用于生成一个动态id，16位长度 */
function generateRandomId() {
	var newId = "";
	for ( var i = 0; i < 4; i++) {
		var rand = parseInt(String(Math.random() * 10000));
		newId += String(rand);
	}
	return newId;
}

//--------------以下是自定义校验函数----------------------
/** 不能为空 */
function notNull(s, thisInput) { // 不能为空
	if (s.length == 0) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "不能为空 ", thisInput);
		return false;
	}
	if (thisInput.type) {
		if (thisInput.type.toLowerCase() == "radio"	|| thisInput.type.toLowerCase() == "checkbox") {
			var aInput = document.getElementsByName(thisInput.name);
			var isSelected = false;
			for ( var i = 0; i < aInput.length; i++) {
				if (aInput[i].checked) {
					isSelected = true;
					break;
				}
			}
			if (!isSelected) {
				var inputName = getInputName(thisInput);
				validateFailed(inputName + "不能为空", aInput[aInput.length - 1]);
				return false;
			}
		}
	}
	return true;
}

/** 是邮政编码 */
function isPost(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /^[a-zA-Z0-9 ]{3,12}$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "不是合法的邮政编码格式", thisInput);
		return false;
	}
	return true;
}

/** 是电话普通电话、传真号码：可以“+”开头，除数字外，可含有“-” */
function isTel(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed("请输入合法的" + inputName + "！", thisInput);
		return false;
	}
	return true;
}

/** 是手机号码（13、14、15、18开头共11位的数字）*/
function isMobile(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /^1[3|4|5|8][0-9]\d{8}$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "不是合法的手机号码！", thisInput);
		return false;
	}
	return true;
}

/** 是电话普通电话、传真号码：可以“+”开头，除数字外，可含有“-” */
function isFax(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed("请输入合法的" + inputName + "！", thisInput);
		return false;
	}
	return true;
}

/** 是中文 */
function isChinese(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /^[\u4e00-\u9fa5]+$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + '只能输入中文', thisInput);
		return false;
	}
	return true;
}

/** 不含中文 */
function notChinese(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /[\u4e00-\u9fa5]+/;
	if (pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "不能输入中文", thisInput);
		return false;
	}
	return true;
}

/** 是数字(所有数值) */
function isNumber(s, thisInput) {
	if (s.length == 0)
		return true;
	if (isNaN(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "必须是合法的数字！", thisInput);
		return false;
	}
	return true;
}

function isLimit(s, thisInput, precision, scale) { // 校验运行里程,小数,整数部分最多为10-2
	if (s.length == 0) {
		return true;
	}
	if (scale == undefined) {
		scale = 2;
	}
	if (precision == undefined) {
		precision = 16;
	}
	if (isNumber(s, thisInput)) {
		if (s.indexOf(".") > 0) {// 浮点数
			if (s.indexOf(".") <= precision	&& (Math.round(s * (pow(10, scale))) < (pow(10,	(precision + scale))))) {
				return true;
			} else {
				validateFailed("整数部分最大为" + (precision - scale) + "位！", thisInput);
				return false;
			}
		} else {// 整数
			if (s.length <= precision) {
				return true;
			} else {
				validateFailed("整数部分最大为" + (precision - scale) + "位！！", thisInput);
				return false;
			}
		}
	} else {
		return false;
	}
}

/** 是邮箱 */
function isEmail(s, thisInput) {
	if (s.length == 0)
		return true;
	var pattern = /^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "不是有效合法的邮箱地址", thisInput);
		return false;
	}
	return true;
}

/** 是IP */
function isIP(s, thisInput) {
	var pattern = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	if (!pattern.exec(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + '不是合法的IP值', thisInput);
		return false;
	}
	return true;
}

/**
 * 比较日期、校验ip 和网段**************start********
 */
function isAfter(s, thisInput, comName) {
	var comInput = document.getElementsByName(comName);
	if ((s == null || s == "")
			&& (comInput[0].value == null || comInput[0].value == "")) {
		return true;
	}
	if (comInput[0].value == null || comInput[0].value == "") {
		validateFailed("不能为空", comInput[0]);
		return false;
	}
	if (s == null || s == "") {
		validateFailed("不能为空", thisInput);
		return false;
	}
	if (comInput[0].value > s) {
		validateFailed("起始日期大于终止日期", thisInput);
		return false;
	}
	return true;
}

/**
 * 比较日期 函数位置写在应该大的输入值处
 */
function isDateCompare(s, thisInput, comName) {
	var comInput = document.getElementsByName(comName);
	if ((s == null || s == "") || (comInput[0].value == null || comInput[0].value == "")) {
		return true;
	}
	if (comInput[0].value > s) {
		validateFailed("结束日期不能早于开始日期", thisInput);
		return false;
	}
	return true;
}

/**
 * 比较金额 函数位置写在应该大的输入值处
 */
function isMoneyCompare(s, thisInput, comName) {
	var comInput = document.getElementsByName(comName);
	if ((s == null || s == "") || (comInput[0].value == null || comInput[0].value == "")) {
		return true;
	}
	if (Number(comInput[0].value) > Number(s)) {
		validateFailed("结束金额不能小于开始金额", thisInput);
		return false;
	}
	return true;
}

/**
 * 比较数值  函数位置写在应该大的输入值处  
 */
function isNumberCompare(s,thisInput,comName){
	var comInput = document.getElementsByName(comName);
	if((s==null||s=="")||(comInput[0].value==null||comInput[0].value=="")){
		return true;
	}
	if(Number(comInput[0].value)>Number(s)){
		validateFailed("结束数值不能小于开始数值",thisInput);
		return false;
	}
	return true;
}

/**
 * 大于等于
 */
function gte(s, thisInput, number) {
	if ((s == null || s == "") || (number == null || number == "")) {
		return true;
	}
	if (Number(s) < Number(number)) {
		validateFailed("数值必须大于等于" + number, thisInput);
		return false;
	}
	return true;
}

/**
 * 小于等于
 */
function lte(s, thisInput, number) {
	if ((s == null || s == "") || (number == null || number == "")) {
		return true;
	}
	if (Number(s) > Number(number)) {
		validateFailed("数值必须小于等于" + number, thisInput);
		return false;
	}
	return true;
}

/** 是非负数 */
function isPositive(s, thisInput) {
	if (s.length == 0)
		return true;
	if (isNaN(s) || new Number(s) <= 0) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "必须是合法的正整数！", thisInput);
		return false;
	}
	return true;
}

/**
 * 正整数
 */
function isPositiveInteger(s, thisInput) {
	if (s == null || s == "") {
		return true;
	}
	if (isNaN(s) || !/^[1-9]\d*$/.test(s)) {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "必须是合法的正整数！", thisInput);
		return false;
	}
	
	return true;
}

function isSpecial_df(s, thisInput) {  //不能输入非法字符
	if(s.length == 0 ) 
		return true;
	
	//汉字、英文、半角括号“(”、“)”、半角句号“.”、半角连接号“-”、全角顿号“、”、半角书名号“《”、“》”\u4e00-\u9fa5为汉字
	//2013-5-22杨少奎增加/《》三个特殊符号校验通过
	//正则在js中支持不好用 replace代替
	s = AreplaceAll(s, "\\u0028");
	s = AreplaceAll(s, "\\u0029");
	var patrn=/^([\u4e00-\u9fa5A-Za-z0-9_.<>\-、()\/《》])*$/;
	
	if (!patrn.test(s)){
		writeValidateInfo_df('输入含有非法字符，请重新输入',thisInput);
		return false;
	}
	return true ;
}

function AreplaceAll(s, flag) {
	s = s.replace(flag, 'w');
	if(s.replace(flag, 'w') != s) {
		AreplaceAll(s);
	} else {
		return s;
	}
}

/**
 * 比较等值
 */
function equalsTo(s, thisInput, comName) {
	var comInput = document.getElementsByName(comName);
	if (comInput[0] && comInput[0].value == s) {
		return true;
	} else {
		var inputName = getInputName(thisInput);
		validateFailed(inputName + "必须与" + getInputName(comInput[0]) + "相同！", thisInput);
		return false;
	}
	return true;
}

/**
 * 采购规则录入校验
 */
function checkBuyRule(s, thisInput) {
	if (s == null || s == "") {
		return true;
	}
	//大于等于：>=12
	if (!(/^>=\d+$/.test(s) || /^<=\d+$/.test(s) || /^=\d+\*n$/.test(s))) {
		validateFailed("仅支持三种规则：“>=数字”，“<=数字”，“=数字*n”", thisInput);
		return false;
	}
	
	return true;
}

/**
 * 采购规则
 */
function buy_rule(s, thisInput, rule) {
	if ((s == null || s == "") || (rule == null || rule == "")) {
		return true;
	}
	//大于等于：>=12
	if (/^>=\d+$/.test(rule)) {
		return gte(s, thisInput, rule.match(/\d+/));
	}
	//小于等于：<=12
	if (/^<=\d+$/.test(rule)) {
		return lte(s, thisInput, rule.match(/\d+/));
	}
	//是某数的倍数：=12*n(是12的倍数)
	if (/^=\d+\*n$/.test(rule)) {
		var num = rule.match(/\d+/);
		if (new Number(s) % new Number(num) != 0) {
			validateFailed("数值必须是" + num + "的整数倍数", thisInput);
			return false;
		}
	}
	
	return true;
}

/**
 * 是否已存在此账号existsAccount
 */
function existsAccount(s, thisInput) {
	var ret = true;
	if (s == null || s == "" || s == thisInput.defaultValue) {
		return true;
	}
	
	$.ajax({
		type: "GET",
		async: false,
		dataType: "json",
		url: context_path + "/userController/existsAccount",
		data: {"account":s},
		success: function(result){
			if(result && result.success && result.data){//账号已存在
				ret = false;
			}
		}
	});
	
	if (!ret) {
		validateFailed("此账号已存在！", thisInput);
	}
	return ret;
}

/**
 * 是否已存在此条形码existsBarcode
 */
function existsBarcode(s, thisInput) {
	var ret = true;
	if (s == null || s == "" || s == thisInput.defaultValue) {
		return true;
	}
	
	$.ajax({
		type: "GET",
		async: false,
		dataType: "json",
		url: context_path + "/goodsController/existsBarcode",
		data: {"barcode":s},
		success: function(result){
			if(result && result.success && result.data){//已存在
				ret = false;
			}
		}
	});
	
	if (!ret) {
		validateFailed("此条形码已存在！", thisInput);
	}
	return ret;
}

/**
 * 货品库和网络货品库同时校验
 */
function existsBarcode(s, thisInput) {
	var ret = true;
	if (s == null || s == "" || s == thisInput.defaultValue) {
		return true;
	}
	
	$.ajax({
		type: "GET",
		async: false,
		dataType: "json",
		url: context_path + "/mmItemController/existsBarcode",
		data: {"barcode":s},
		success: function(result){
			if(result && result.success && result.data){//已存在
				ret = false;
			}
		}
	});
	
	if (!ret) {
		validateFailed("此条形码已存在！", thisInput);
	}
	return ret;
}

