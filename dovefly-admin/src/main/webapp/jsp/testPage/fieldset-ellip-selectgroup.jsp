<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>page1</title>
<style type="text/css">
<!--

fieldset{
	width:100%;
	line-height:35px;
}
div{
	width:200px;
	overflow: hidden; 
	text-overflow:ellipsis;
	white-space:nowrap;
	word-break:keep-all;
}
-->
</style>
</head>
<body>
	<a href="page2.jsp">to page2</a>
	<br />
	<fieldset>
		<legend>我要在****学:</legend>
		<label for="WebDesign">选择一个你在****最想学的</label>
		<select id="WebDesign"
			name="WebDesign">
			<optgroup label="client">
				<option value="HTML">HTML</option>
				<option value="CSS">CSS</option>
				<option value="javascript">javascript</option>
			</optgroup>
			<optgroup label="server">
				<option value="PHP">PHP</option>
				<option value="ASP">ASP</option>
				<option value="JSP">JSP</option>
			</optgroup>
			<optgroup label="database">
				<option value="Access">Access</option>
				<option value="MySQL">MySQL</option>
				<option value="SQLServer">SQLServer</option>
			</optgroup>
		</select>
	</fieldset>
	DIV截断显示：

<div>
从2004年德国施罗德政府颁布《可再生能源法》开始，刺激了全球光伏产业的兴起。中国光伏产业靠着地方政府的支持，在短短几年时间内迅速发展壮大，整个产业链都赚得钵满盆盈。赛维、尚德等企业乘势而起，无锡尚德董事长施正荣曾一度成为中国首富，江西赛维控制人彭小峰也曾是新能源首富。
</div>
<div>
在巨额利润的诱惑下，大量投机跟风导致行业盲目扩张，产能严重过剩。2011年初，行业供给量达到顶峰，当时以电池片计算的产能达到55GW左右，而装机量只有27GW，产能过剩接近100%。
</div>
<div>
供需不平衡导致市场迅速恶化，产品价格大幅下跌，甚至有低于现金成本抛售的现象。在资金不足和滞销的情况下，不少企业难以为继，被迫退出，光伏业陷入寒潮。赛维、尚德都曾面临破产的险境，虽然屡屡虎口脱险，但公司早已失去往日的光彩，遭到资本市场的抛弃。
</div>
<div>
2012年底，中国光伏产品最大的出口市场欧盟发起双反调查，市场担忧这将是压倒骆驼的最后一根稻草。
</div>
<div>
今年上半年，在长时间业绩大幅滑坡后，光伏电池组件产品的毛利率开始触底回升。根据国内上市光伏企业半年报，有七成左右的企业都实现盈利。
</div>

</body>
</html>