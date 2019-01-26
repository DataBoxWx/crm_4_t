<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css"
	type="text/css" rel="stylesheet" />
<link
	href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript"
	src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#code").blur(function(){
			var code = $.trim(this.value);
			if(code == ""){
				$("#codeErrorMsg").html("编码不能为空");
			}else{
				var regExp = /^[a-zA-Z0-9]+$/; 
				var flag = regExp.test(code);
				if(flag){
					$("#codeErrorMsg").html("");
					$.post(
						"dictionary/type/register.do",
						{"code":code},
						function(data){
							if(data.code == 1){
								$("#codeErrorMsg").html("该编码已经存在");
							}else{
								$(".test").click(function(){
										var name = $("#name").val();
										var discribe = $("#describe").val();  
										$.post(
											"dictionary/type/save.do",
											{"code":code,"name":name,"discribe":discribe},
											function(data){
												if(data=="1"){
													location.href="settings/dictionary/type/index.jsp";
												}
											},
											"text"
										)
								});	
							}
						},
						"json"
					)
				}else{
					$("#codeErrorMsg").html("编码中只能含有字母或中文");
				}
			}
		})
		$("#code").focus(function(){
			$("#codeErrorMsg").html("");
			$("#code").val("");
		})
		
		
	})

</script>
</head>
<body>

	<div style="position: relative; left: 30px;">
		<h3>新增字典类型</h3>
		<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary test" id="save">保存</button>
			<button type="button" class="btn btn-default"
				onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form">

		<div class="form-group">
			<label for="create-code" class="col-sm-2 control-label">编码<span
				style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="code"
					style="width: 200%;" placeholder="编码作为主键，不能是中文"> <span
					id="codeErrorMsg" style="color: red; font-size: 12px;">&nbsp;</span>
			</div>
		</div>

		<div class="form-group">
			<label for="create-name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="name"
					style="width: 200%;">
			</div>
		</div>

		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="describe"
					style="width: 200%;"></textarea>
			</div>
		</div>
	</form>

	<div style="height: 200px;"></div>
</body>
</html>