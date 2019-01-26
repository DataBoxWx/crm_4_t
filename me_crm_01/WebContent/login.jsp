<%@page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	if(window.top != window.self){
		window.top.location = window.self.location;
	}
	$(function(){
		$(window).keydown(function(event){
			if(event.keyCode==13){
				login();
			}
		})
		//加载时，用户名获取焦点
		$("#loginAct").focus();
		//点击提交
		$("#loginBtn").click(function(){
			login();
		})
		
	})
	
	function login(){
			var tenDaysFree = "0";
			if($("#tenDaysFree").prop("checked")){
				tenDaysFree = "1";
			}
		$.ajax({
			url:"user/login.do",
			data:{
				"loginAct":$("#loginAct").val(),
				"loginPwd":$("#loginPwd").val(),
				"tenDaysFree":tenDaysFree
			},
			beforeSend: function(){
				if($.trim($("#loginAct").val()) != "" && $("#loginPwd").val() != ""){
					$("#errorMsg").text("");
					return true; 
				}
				$("#errorMsg").text("账户名或密码不能为空");
				return false;
				
			},
			success: function(json){
				if(json.success){
					window.location.href="workbench/index.jsp";
				}else{
					$("#errorMsg").text(json.errorMsg);
				}
			}
		})
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
			<form class="form-horizontal" role="form" id="loginForm">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input type="checkbox" id="tenDaysFree"> 十天内免登录
							<span id= "errorMsg" style="font-size:12px;color:red"></span>
						</label>
					</div>
					<button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>