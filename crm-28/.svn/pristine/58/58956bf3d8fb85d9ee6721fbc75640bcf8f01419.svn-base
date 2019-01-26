<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	$(function(){
		
		$("#value").focus(function(){
			$("#valueError").text("");
		});
		
		$("#value").blur(function(){
			//判断字典类型编码是否为空
			var typeCode = $("#typeCode").val();
			if(typeCode == ""){
				//字典类型编码为空，提示用户
				$("#typeCodeError").text("字典类型编码不能为空！");
			}else{
				//字典类型编码不为空，继续判断字典值是否为空
				var value = $.trim(this.value);
				if(value == ""){
					$("#valueError").text("字典值不能为空！");
				}else{
					//字典值不为空，并且字典类型编码同时也不为空
					$.ajaxSetup({
						async : false
					});
					
					$.get(
							"settings/dictionary/value/checkValue.do",
							{
								"typeCode" : typeCode,
								"value" : value,
								"_" : new Date().getTime()
							},
							function(json){
								//{"success" : true}
								//{"success" : false}
								if(json.success){
									//可以使用
									$("#valueError").text("");
								}else{
									//不能使用
									$("#valueError").text("在该字典类型下字典值已存在！");
								}
								
								$.ajaxSetup({
									async : true
								});
							}
					);
				}
			}
		});
		
		$("#typeCode").change(function(){
			if(this.value != ""){
				$("#typeCodeError").text("");
			}else{
				$("#typeCodeError").text("字典类型编码不能为空！");
			}
		});
		
		$("#orderNo").focus(function(){
			$("#orderNoError").text("");
		});
		
		$("#orderNo").blur(function(){
			if($.trim(this.value) != ""){
				//必须是正整数
				//正则表达式
				var regExp = /^[1-9][0-9]*$/;
				var ok = regExp.test($.trim(this.value));
				if(ok){
					$("#orderNoError").text("");
				}else{
					$("#orderNoError").text("排序号必须是正整数！");
				}
			}
		});
		
		$("#saveBtn").click(function(){
			//触发所有控件的blur
			$("#value").blur();
			$("#typeCode").change();
			$("#orderNo").blur();
			if($("#valueError").text() == "" && $("#typeCodeError").text() == "" && $("#orderNoError").text() == ""){
				$("#dicValueForm").submit();
			}
		});
	});
</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典值</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form id="dicValueForm" method="post" action="settings/dictionary/value/save.do" class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="create-dicTypeCode" class="col-sm-2 control-label">字典类型编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="typeCode" name="typeCode" style="width: 200%;">
					<option value=""></option>
					<c:forEach items="${dtList }" var="dt">
					<option value="${dt.code}">${dt.name}</option>	
					</c:forEach>
				</select>
				<span id="typeCodeError" style="color: red;font-size: 12px;"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-dicValue" class="col-sm-2 control-label">字典值<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="value" name="value" style="width: 200%;">
				<span id="valueError" style="color: red;font-size: 12px;"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-text" class="col-sm-2 control-label">文本</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="text" name="text" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-orderNo" class="col-sm-2 control-label">排序号</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="orderNo" name="orderNo" style="width: 200%;">
				<span id="orderNoError" style="color: red;font-size: 12px;"></span>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>