<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script>
	$(function(){
		$("#create-dicTypeCode").change(function(){
			var vl = $.trim(this.value);
			if(vl == ""){
				$("#TypeErrorMes").text("字典类型不能为空");
			}else{
				$("#TypeErrorMes").text("");
			}
		});
		$("#create-dicValue").blur(function(){
			var typeCode = $.trim($("#create-dicTypeCode").val());
			if(typeCode == ""){
				$("#TypeErrorMes").text("字典类型不能为空");
			}else{
				$("#TypeErrorMes").text("");
				var value = $.trim($("#create-dicValue").val());
				if(value ==""){
					$("#ValueErrorMes").text("字典值不能为空");
				}else{
					$("#ValueErrorMes").text("");
					var typeCode =  $("#create-dicTypeCode").val();
					 $.ajaxSetup({
						async:false 
					 })
					$.get(
						"dictionary/value/checkValue.do",
						{"typeCode":typeCode,"value":value},
						function(data){
							if(data.success){
								$("#ValueErrorMes").text("");
							}else{
								$("#ValueErrorMes").text("该字典类型编码下已存在该字典值");
							}
						},
						"json"
					)
					$.ajaxSetup({
						async:true
					})
				}
			}
		})
		$("#create-orderNo").blur(function(){
			var orderNo = $.trim(this.value);
			if(orderNo != ""){
				var regExp = /^[1-9][0-9]*$/;
				var ok = regExp.test(orderNo);
				if(ok){
					$("#NoErrorMes").text("");
				}else{
					$("#NoErrorMes").text("排序号只能为正整数或空");
				}
			}else{
				$("#NoErrorMes").text("");
			}
		})
		$("#saveValue").click(function(){
			$("#create-dicTypeCode").change();
			$("#create-orderNo").blur();
			$("#create-dicValue").blur(); 
			if($("#TypeErrorMes").text()=="" && $("#ValueErrorMes").text()=="" && $("#NoErrorMes").text()==""){
				$("#valueForm").submit();
			}
		})
		
	})
</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典值</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveValue">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" id="valueForm" action="dictionary/value/save.do">
					
		<div class="form-group">
			<label for="create-dicTypeCode" class="col-sm-2 control-label">字典类型编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-dicTypeCode" style="width: 200%;" name="typeCode">
				  <option></option>
				  <c:forEach items="${list}" var="list">
				  	<option value="${list.code}">${list.name}</option>
				  </c:forEach>
				</select>
				<span style="font-size:12px;color:red" id="TypeErrorMes"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-dicValue" class="col-sm-2 control-label">字典值<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-dicValue" style="width: 200%;" name="value">
				<span style="font-size:12px;color:red" id="ValueErrorMes"></span>
			</div>		
		</div>
		
		<div class="form-group">
			<label for="create-text" class="col-sm-2 control-label">文本</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-text" style="width: 200%;" name="text">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-orderNo" class="col-sm-2 control-label">排序号</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-orderNo" style="width: 200%;" name="orderNo">
				<span style="font-size:12px;color:red" id="NoErrorMes"></span>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>