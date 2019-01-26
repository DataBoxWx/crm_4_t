<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//页面加载完毕执行回调函数
	/*
	$(function(){
		
	});
	*/
	
	/*
	$(document).ready(function(){
		
	});
	*/
	
	$(callback);
	
	function callback(){
		$("#code").blur(function(){
			//1、不能为空
			//this.value 是dom对象
			//$("#code").val() 是jQuery对象
			//$(this).val() 是jQuery对象
			var code = $.trim(this.value);
			//if(code.length == 0){} //没必要
			if(code == ""){
				$("#codeErrorMsg").text("编码不能为空！");
			}else{
				//2、只能含有数字和字母
				var regExp = /^[a-zA-Z0-9]+$/; //+代表1到多个。
				var ok = regExp.test(code);
				if(ok){
					$("#codeErrorMsg").text("");
					//3、不能重复 ajax
					// ajax get
					// HTTP请求（get、post、put、delete、trace.....）
					/*
						get和post有什么区别？
						
							* get请求在HTTP请求协议的请求行上发送数据，最终提交的数据会显示在浏览器地址栏上。（请求行：请求方式 URI 协议版本号 ）
							* get只能提交普通字符串，并且字符串长度有限制，get请求会被浏览器cache（get请求第一次的时候会和服务器交互，在没有关闭浏览器
							的前提下，第二次发送同一个URL的get请求，会直接走浏览器缓存，降低服务器压力，提高用户体验，走缓存就意味着不会和服务器交互，不会
							拿到最新的数据），怎么避免缓存：在URL后面添加时间戳（URL = uri?_=12312312123）：时间戳获取：new Date().getTime()
							* get请求在HTTP协议中是这样规定的：凡目的是为了从服务器端获取资源，建议使用get。
									
							* post请求 在请求体当中提交数据，不会显示在浏览器的地址栏上。
							* post请求可以发送字符串、流媒体等数据。
							* post请求理论上没有长度限制。
							* post请求是专门为客户端向服务器传送数据而设计的，主要目的是从浏览器向服务器发数据，而不是为了获得服务器端的某个资源。
							* post默认情况下是不支持浏览器缓存机制的。
							
						get和post请求的选择：
							* 有敏感数据使用post
							* 提交大量数据使用post
							* 提交非字符串数据使用post
							* 主要目的是向服务器传送数据建议使用post
							* 其它情况一律使用get
							
						get走缓存的时候，有的情况下客户要求数据必须是实时的最新的，可以取消get缓存机制，在URL后面添加时间戳。
					*/
					//$.get();
					//$.getJSON();
					
					//这是同步ajax，发送一下这个ajax请求的时候，锁定浏览器，浏览器不可操作。
					$.ajaxSetup({
						async : false  //ajax同步请求。
					});
					
					//原始方式
					$.ajax({
						type : "get",
						url : "settings/dictionary/type/checkCode.do",
						cache : false, //避免走get缓存：底层jQuery在实现的时候也是自动添加了一个时间戳。
						data : { //向服务器提交数据的时候可以使用json对象的方式，也可以直接使用字符串，但是字符串的格式必须满足http协议：name=value&name=value&name=value...
							//"code" : code,
							//"_" : new Date().getTime()
							"code" : code
						},
						//dataType : "json", //指定服务器端响应回来的字符串按照json对象的方式处理（也可以在服务端：response.setContentType("text/json;charset=UTF-8");）
						success : function(json){ //响应结束之后的回调函数
							//{"success" : true}
							//{"success" : false}
							if(json.success){
								//没重复
								$("#codeErrorMsg").text("");
							}else{
								//重复了
								$("#codeErrorMsg").text("编码已存在！");
							}
							
							//将ajax恢复为异步方式。
							$.ajaxSetup({
								async : true
							});
						}
					});
				}else{
					$("#codeErrorMsg").text("编码中只能包含数字和字母！");	
				}
			}
		});	
		
		$("#code").focus(function(){
			$("#codeErrorMsg").text("");
			$(this).val("");
		});
		
		$("#saveBtn").click(function(){
			//校验表单
			$("#code").blur();
			//表单合法的时候提交form
			if($("#codeErrorMsg").text() == ""){
				$("#dicTypeForm").submit();
			}
		});
	}
	
</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典类型</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form id="dicTypeForm" method="post" action="settings/dictionary/type/save.do" class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="create-code" class="col-sm-2 control-label">编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="code" name="code" style="width: 200%;" placeholder="编码作为主键，不能是中文">
				<span id="codeErrorMsg" style="color: red;font-size: 12px;"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="name" name="name" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="description" name="description" style="width: 200%;"></textarea>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>