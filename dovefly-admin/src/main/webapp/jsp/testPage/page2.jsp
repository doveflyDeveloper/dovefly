<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>page2</title>
<script type="text/javascript">
/* 1.执行时机
window.onload方法是在网页中的所有的元素（包括元素的所有关联文件）都完全加载到浏览器之后才执行。而通过jQuery中的$(document).ready()方法注册的事件处理程序，只要在DOM完全就绪时，就可以调用了，比如一张图片只要<img>标签完成，不用等这个图片加载完成，就可以设置图片的宽高的属性或样式等。
2.$(document).ready()方法可以多次使用而注册不同的事件处理程序，而window.onload一次只能保存对一个函数的引用，多次绑定函数只会覆盖前面的函数。
 */
	function bodyLoadTest() {
		//alert("bodyLoadTest");
	}
	
	function documentReadyTest() {
		//alert("documentReadyTest");
	}
	
	function windowReadyTest() {
		//alert("windowReadyTest");
	}
	
	function getEleByIdTest() {
		alert(document.getElementById("name").value);//第一个
	}
	
	function testGetValue() {
		
		alert(document.form["form_id"].age.value);
		alert(document.forms["form_name"].age.value);
		alert(document.form_id.age.value);
		alert(document.form_name.age.value);
	}
	
 	
	$(window).ready(function() {
		windowReadyTest();
	});
	
	$(document).ready(function() {
		documentReadyTest();
	}); 
	
	
	
</script>
<body onload="bodyLoadTest();">
	<a href="javascript:getEleByIdTest();">获取重复ID的表单域的值</a>
	<a href="javascript:testGetValue();">获取重复ID的表单域的值</a>
	<img src="http://www.mianwww.com/html/2013/04/18292.jpg" />
	<form name="form_name" id="form_id">
		<input id="name" name="name" value="李四">
		<input id="name" name="name" value="张三">
		<input id="age" name="age" value="16">
	</form>
	<form name="form_name2" id="form_id2">
		<input id="name" name="name" value="张三">
		<input id="age" name="age" value="16">
		<input id="name" name="name" value="李四">
		<input id="age" name="age" value="18">
	</form>
</body>
</html>