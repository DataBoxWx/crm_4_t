<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		//用户名文本框获取焦点
		$("#loginAct").focus();
		
		//用户点击登录按钮的时候发送登录请求
		$("#loginBtn").click(function(){
			login();
		});
		
		//回车键登录：键值13
		//window dom对象
		//$(window) jQuery对象
		$(window).keydown(function(event){ //event是一个引用，指向了堆内存当中的事件对象。 
			if(event.keyCode === 13){ //键盘事件都有一个keyCode属性可以用来获取键值（回车键13，ESC键是27）
				login();
			}
		});
		
		$(".clear").focus(function(){
			$("#errorMsg").text("");
		});
		
	});
	
	function login(){
		
		$.ajax({
			type : "post",
			url : "login.do",
			data : {
				"loginAct" : $("#loginAct").val(),
				"loginPwd" : $("#loginPwd").val()
			},
			beforeSend : function(){ 
				//这个回调函数在ajax请求发送之前被自动调用。
				//这个函数“return false;”表示放弃发送该请求
				//这个函数“return true;”表示继续发送该请求
				//所以，一般都是在这个回调函数当中进行表单验证。
				var loginAct = $.trim($("#loginAct").val());
				var loginPwd = $("#loginPwd").val();
				if(loginAct != "" && loginPwd != ""){
					return true;
				}
				$("#errorMsg").text("用户名和密码不能为空！");
				return false;
			},
			success : function(json){
				//{"success" : true}
				//{"success" : false , "errorMsg" : "错误信息"}
				if(json.success){
					document.location.href = "workbench/index.jsp";
				}else{
					$("#errorMsg").text(json.errorMsg);
				}
			}
		});
		
	}
	
</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control clear" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control clear" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input type="checkbox"> 十天内免登录
							<span id="errorMsg" style="color: red;font-size: 12px"></span>
						</label>
					</div>
					<button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>