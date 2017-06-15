/** 处理键盘事件 除文本域等输入框之外，禁止后退键（Backspace）的使用 */
function doBackSpace(e) {
	var ev = e || window.event;// 获取event对象
	var obj = ev.target || ev.srcElement;// 获取事件源
	var t = obj.type || obj.getAttribute("type");// 获取事件源类型
	// 获取作为判断条件的事件类型
	var vReadOnly = obj.readOnly;
	var vDisabled = obj.disabled;
	// 处理undefined值情况
	vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
	vDisabled = (vDisabled == undefined) ? true : vDisabled;
	// 当敲Backspace键时，事件源类型为密码或单行、多行文本的，
	// 并且readOnly属性为true或disabled属性为true的，则退格键失效
	var flag1 = ev.keyCode == 8	&& (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
	// 当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
	var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
	// 判断
	if (flag2 || flag1)
		return false;
}
// 禁止退格键 作用于Firefox、Opera
//document.onkeypress = doBackSpace; 
//禁止退格键 作用于IE、Chrome 
//document.onkeydown = doBackSpace; 

//阻止Backspace键的退回上一页
$(document).keydown(function(e) {// 绑定键盘按下事件
	var doPrevent;
	// for IE && Firefox
	var varkey = (e.keyCode) || (e.which) || (e.charCode);
	if (varkey == 8) {
		var d = e.srcElement || e.target;
		if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
			doPrevent = d.readOnly || d.disabled;
			if (d.type.toUpperCase() == 'SUBMIT' || d.type.toUpperCase() == 'RADIO' || d.type.toUpperCase() == 'CHECKBOX' || d.type.toUpperCase() == 'BUTTON') {
				doPrevent = true;
			}
		} else if (d.tagName.toUpperCase() == 'DIV' && d.contentEditable == 'true') {//富文本编辑器
			doPrevent = false;
		} else {
			doPrevent = true;
		}
	} else {
		doPrevent = false;
	}
	if (doPrevent)
		e.preventDefault();
});

/** 初始化表单样式（Form，Button, Input，Textarea等） */
function initForm() {

	// 绑定表单提交校验
	$("form").submit(showWaiting);

	// IE下隐藏按钮按下时文本周围的虚线
	$(".button").attr("hidefocus", true);

	// 设置只读表单域的只读属性
	$("input.readonly").attr("readonly", true);

	// 设置只读表单域的只读属性
	$("input.reference").attr("readonly", true);

	// 设置日期（时间）输入框单击事件
	$("input.Wdate").live("click", function (){
		var format = $(this).attr("format");
		if(format == 'both') {
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
		} else if(format == 'date') {
			WdatePicker({dateFmt:'yyyy-MM-dd'});
		} else if(format == 'time') {
			WdatePicker({dateFmt:'HH:mm:ss'});
		} else {
			WdatePicker();
		}
	});
	
	//查询输入框回车即查询
	$(".query_table :text").keyup(function(event){
		if (event.which == 13) {
			if (window.query_onclick) {
				window.query_onclick();
			}
		}
	});

	// 文本域事件监听
	/*
	 * $(".textarea_limit_words").each(function(){
	 * $(this).focus(function(event){ checkMaxInput(this); });
	 * $(this).keydown(function(event){ checkMaxInput(this); });
	 * $(this).keyup(function(event){ checkMaxInput(this); }); });
	 */
//	$(".textarea_limit_words").htmlarea();

	//自定义单选查询按钮单击查询事件
	$('.radioButtonContainer label').click(function () {
		var labelLength = $('.radioButtonContainer label').length;
		for (var i = 0; i < labelLength; i++) {
			if (this == $('.radioButtonContainer label').get(i)) {
				$('.radioButtonContainer label').eq(i).addClass('red');
			} else {
				$('.radioButtonContainer label').eq(i).removeClass('red');
			}
		}
		if (window.query_onclick) {
			window.query_onclick();
		}
	});

}

/** 页面遮罩，请求等待提示 */
function showWaiting() {
	// 创建半透明遮罩层
	if (!$("#overLay").size()) {
		$('<div id="overLay"><span id="targetFixed" class="target_fixed"></span></div>').prependTo($("body"));
		$("#overLay").css({
			width : "100%",
			backgroundColor : "#000",
			opacity : 0.1,
			position : "absolute",
			left : 0,
			top : 0,
			zIndex : 999
		}).height($(document).height());
	}
	// 居中
	$("#targetFixed").css(
			{
				top : '50%',
				left : '50%',
				margin : '-' + ($('#targetFixed').height() / 2) + 'px 0 0 -' + ($('#targetFixed').width() / 2) + 'px'
			});

	// 表单提交后等待提示
	$("#targetFixed").powerFloat({
		eventType : null,
		targetMode : "doing",
		target : "请求处理中，请稍候...",
		position : "5-7"
	});
	return true;
}

/** 文本域字数检测 */
function checkMaxInput(thisObj) {
	if (!thisObj)
		return false;
	var maxLength = 500;
	if (!$(thisObj).attr("maxLength")) {
		maxLength = $(thisObj).attr("maxLength");
	}
	var value = $(thisObj).val();
	var currLength = value.length;
	var afterStr;
	if (currLength >= maxLength) {
		$(thisObj).val(value.substring(0, maxLength));
	}
	$("#" + $(thisObj).attr("id") + "_error").remove();
	showValidError("您还能输入" + (maxLength - currLength) + "字！", thisObj);
}

/** 初始化查询条件区域的默认显示或隐藏 */
function initQueryFlag() {
	if ($("#query_state").attr("checked")) {// 查询条件已全部展开
		$(".query_table tr.hidden").show();
	} else {// 查询条件部分隐藏
		$(".query_table tr.hidden").hide();
	}

	$("#query_state").click(function() {
		if ($(this).attr("checked")) {
			$(".query_table tr.hidden").show();
		} else {
			$(".query_table tr.hidden").hide();
		}
	});
}

/** 绑定Tip标志 */
function initTip() {
	$(".table_title_tip").powerFloat({
		eventType : "click",
		targetMode : "ajax"
	});
	
	$(".list_table .thumbnail").powerFloat({
		targetMode : "ajax"
	});
	
}

/** 初始化列表表格样式 */
function initListTable() {
	// 表格行鼠标移动高亮显示
	$(".list_table tr").hover(function() {
		$(this).children().addClass("tr_hover");
	}, function() {
		$(this).children().removeClass("tr_hover");
	});

	// 表格行鼠标双击，当前行高亮显示，并反选当前行的checkbox
	$(".list_table tr").dblclick(function() {
		var checkbox = $(this).find("input[name='dv_checkbox']").get(0);
		if (checkbox) {// 是复选框
			if ($(checkbox).attr("checked")) {
				$(checkbox).attr("checked", false);
				$(this).children().removeClass("tr_select");
				$("input[name='dv_checkbox_all']").attr("checked", false);
			} else {
				$(checkbox).attr("checked", true);
				$(this).children().addClass("tr_select");
				// 检查全选按钮是否需选中
				var selectAll = true;
				$("input[name='dv_checkbox']").each(function() {
					selectAll = selectAll && $(this).attr("checked");
					return selectAll;
				});
				$("input[name='dv_checkbox_all']").attr("checked", selectAll);
			}
		} else {// 是单选框
			var radio = $(this).find("input[name='dv_radio']").get(0);
			if (radio) {
				$(radio).attr("checked", true);
				$(".list_table tr td").removeClass("tr_select");
				$(this).children().addClass("tr_select");
			}
		}
	});
}

/** 初始化查看详细表格样式（隔行换色） */
function initDetailTable() {
	$(".detail_table tr:even").children().addClass("even");
	$(".detail_table tr:odd").children().addClass("odd");
}

/** 初始化查看子表标签页样式 */
function initSubTable() {
	window.mytab = $("#sub_tab").KandyTabs({
		trigger : "click"
	});
	$("#sub_tab").find("iframe").each(function(){
		$(this).attr("src", $(this).attr("rel"));
	});
}

/** 全选 */
function selectAll(obj) {
	if ($(obj).attr("checked")) {
		$("input[name='dv_checkbox']").attr("checked", true);
		$(".list_table tr td").addClass("tr_select");
	} else {
		$("input[name='dv_checkbox']").attr("checked", false);
		$(".list_table tr td").removeClass("tr_select");
	}
}

/** checkBox选中时，当前行选中样式，全选联动监控 */
function selectCheckBox(obj) {
	if ($(obj).attr("checked")) {
		$(obj).parent().parent().children().addClass("tr_select");
		// 检查全选按钮是否需选中
		var selectAll = true;
		$("input[name='dv_checkbox']").each(function() {
			selectAll = selectAll && $(this).attr("checked");
			return selectAll;
		});
		$("input[name='dv_checkbox_all']").attr("checked", selectAll);
	} else {
		$(obj).parent().parent().children().removeClass("tr_select");
		$("input[name='dv_checkbox_all']").attr("checked", false);
	}
}

/** radio选中时，当前行选中样式 */
function selectRadio(obj) {
	$(".list_table tr td").removeClass("tr_select");
	$(obj).parent().parent().children().addClass("tr_select");
}

/** 页面加载后需绑定的事件 */
$(document).ready(function() {
	initQueryFlag();

	initListTable();

//	initDetailTable();

	initForm();

	initSubTable();

	initTip();
	
});


