<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/jsp/include/dvGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>商品管理</title>
<style type="text/css">
<!--
-->
</style>
<script type="text/javascript">
	var formHelper = new DvFormHelper("goodsMController");
	
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
		
		var id = $(sels[0]).val();
		formHelper.jSubmit(formHelper.buildCopyAction(id));
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
	
	/** 上架销售 */
	function onSale_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || !ids.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		dvConfirm("您确定要上架销售这些商品吗？", 
			function() {
				formHelper.submit(formHelper.buildAction("goodsMController", "onSale", ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 下架停售 */
	function offSale_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || !ids.length) {
	  		dvAlert("请先选择记录！");
	  		return false;
		}
		dvConfirm("您确定要下架停售这些商品吗？", 
			function() {
				formHelper.submit(formHelper.buildAction("goodsMController", "offSale", ids));
			}, 
			function() {
				//alert("干嘛要取消啊？");
			}
		);
	}
	
	/** 排序 */
	function query4Sort_onclick() {
		dvOpenDialog(context_path + '/goodsMController/query4Sort?status=1', '商品排序', 900, 450, null);
	}
	
	/** 导出列表 */
	function exportList_onclick() {
		var ids = findSelections("dv_checkbox", "value");
		if(!ids || ids.length == 0) ids = "all";
		formHelper.submit(formHelper.buildAction("goodsMController", "exportList", ids));
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
					<div class="table_title_tip" title="不会操作？点我吧！" rel="<%=request.getContextPath()%>/helpController/getHelp/mm_goods_w"></div>
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
					<c:if test="${sessionScope.DV_USER_VO.headquartersRole }">
					<tr>
						<td class="label">城市：</td>
						<td class="field">
							<dv:select name="city_id" dicKeyword="DIC_SYS_CITY" defaultValue="${param.city_id }" ignoreValues="1" hasEmpty="true"/>
						</td>
						<td class="label"></td>
						<td class="field"></td>
						<td class="label"></td>
						<td class="field"></td>
					</tr>
					</c:if>
					<tr>
						<td class="label">商品名称：</td>
						<td class="field">
							<input type="text" name="name" class="input" maxlength="100" value="<c:out value="${param.name }"/>"/>
						</td>
						<td class="label">商品条形码：</td>
						<td class="field">
							<input type="text" name="barcode" class="input" maxlength="20" value="<c:out value="${param.barcode }"/>"/>
						</td>
						<td class="label">商品分类：</td>
						<td class="field">
							<dv:select name="category_id" dicKeyword="DIC_GOODS_CATEGORY" hasEmpty="true" defaultValue="${param.category_id }"/>
						</td>
					</tr>
					<tr>
						<td class="label">销售状态：</td>
						<td class="field">
							<dv:radio name="status" dicKeyword="DIC_GOODS_STATUS" defaultValue="${param.status }" hasEmpty="true"/>
						</td>
						<td class="label">价格异常：</td>
						<td class="field">
							<dv:radio name="priceExp" options="{\"1\":\"利润低于10%的商品\"}" defaultValue="${param.priceExp }" hasEmpty="true"/>
						</td>
						<td class="label">库存告警：</td>
						<td class="field">
							<dv:radio name="safe_line_num" options="{\"1\":\"库存量低于安全库存\"}" defaultValue="${param.safe_line_num }" hasEmpty="true"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="space_h15_div"></div>
		<div class="border_div" >
			<div class="header_div">
				<div class="left_div">
					<div class="table_title">商品列表&nbsp;</div>
				</div>
				<div class="right_div">
					<div class="right_menu">
						<c:if test="${!sessionScope.DV_USER_VO.shopkeeperRole}">
						<input type="button" name="insert" value="新增" class="button" onclick="add_onclick();"/>
						</c:if>
						<input type="button" name="update" value="修改" class="button" onclick="find_onclick();"/>
						<!-- <input type="button" name="copy" value="复制" class="button" onclick="copy_onclick();"/> -->
						<input type="button" name="query4Sort" value="排序" class="button" onclick="query4Sort_onclick();"/>
						<input type="button" name="onSale" value="上架销售" class="button" onclick="onSale_onclick();"/>
						<input type="button" name="offSale" value="下架停售" class="button" onclick="offSale_onclick();"/>
						<c:if test="${sessionScope.DV_USER_VO.headquartersRole || sessionScope.DV_USER_VO.cityManagerRole}">
						<input type="button" name="exportList" value="导出商品" class="button" onclick="exportList_onclick();"/>
						</c:if>
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
						<th width="5%">超市</th>
						<th width="5%">缩略图</th>
						<th width="10%">条形码</th>
						<th width="22%">商品名称</th>
						<th width="7%">采购成本价</th>
						<th width="7%">店长进货价</th>
						<th width="7%">店长零售价</th>
						<th width="7%">库存量</th>
						<th width="7%">锁定库存</th>
						<th width="7%">安全库存</th>
						<th width="5%">状态</th>
						<!-- <th width="5%">操作</th> -->
					</tr>
					<c:forEach var="bean" varStatus="status" items="${beans }">
					<tr>
						<td>
							<input type="checkbox" name="dv_checkbox" value="<c:out value="${bean.id }"/>" onclick="selectCheckBox(this)"/>
						</td>
						<td>${status.count }</td>
						<td><c:out value="${bean.market_name }"/></td>
						<td><img class="thumbnail" src="<c:out value="${bean.image }"/>?imageView2/0/w/50" rel="<c:out value="${bean.image }"/>?imageView2/0/w/200"/></td>
						<td><c:out value="${bean.barcode }"/></td>
						<td><a href="javascript:detail_onclick('<c:out value="${bean.id }"/>')"><c:out value="${bean.name }"/></a></td>
						<td><c:out value="${bean.cost_price }"/></td>
						<td><c:out value="${bean.sale_price }"/></td>
						<td><c:out value="${bean.market_price }"/></td>
						<td>
							<span id="span_stock_sum_${bean.id }"><c:out value="${bean.stock_sum }"/></span>
							<input id="input_stock_sum_${bean.id }" type="text" class="half_input hidden" old_stock_sum="${bean.stock_sum }" value="${bean.stock_sum }"/>
						</td>
						<td><c:out value="${bean.stock_locked }"/></td>
						<c:if test="${bean.stock_sum >= bean.safe_line }"><td><c:out value="${bean.safe_line }"/></td></c:if>
						<c:if test="${bean.stock_sum < bean.safe_line }"><td><c:out value="${bean.safe_line }"/><br><span class="left_ts">(库存告警)</span></td></c:if>
						<td><dv:display dicKeyword="DIC_GOODS_STATUS" value="${bean.status }"/></td>
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
