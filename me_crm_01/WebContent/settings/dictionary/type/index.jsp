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
		dictionaryTypeList();
		function dictionaryTypeList(){
			$("#tbody").html("");
			$.post(
				"dictionary/type/list.do",
				function(data){
					$(data.list).each(function(i){
						$("#tbody").append("<tr><td><input type='checkbox' name='edit' value='"+this.code
								+"'/></td><td>"+(i+1)+"</td><td>"+this.code
								+"</td><td>"+this.name+"</td><td>"+this.discription
								+"</td></tr>");
					})
				},
				"json"
			)
		}
	})
	function edit(){
		var $x = $("input[name='edit']:checked")
		if($x.length==0){
			$("#codeErrorMsg").html("请选择一条信息");
		}else if($x.length > 1){
			$("#codeErrorMsg").html("只能选择一条信息进行编辑");
		}else{
			$("#codeErrorMsg").html("");
			var code = $x.val();
			window.location.href="settings/dictionary/type/edit.jsp?code="+code;
		}
	}
	function doDelete(){
		var $x = $("input[name='edit']:checked");
		if($x.length==0){
			$("#codeErrorMsg").html("请至少选择一条信息");
		}else{
			var path = "dictionary/type/delete.do?"
			for(var i=0;i<$x.length;i++){
				path += "ids=" +$x[i].value;
				if(i<$x.length-1){
					path += "&";
				}
			}
			location.href=path;
		}
	}
</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/save.jsp'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-default" onclick="edit()"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger" onclick="doDelete()"><span class="glyphicon glyphicon-minus"></span> 删除</button><br/>
		  <span id="codeErrorMsg" style="color: red; font-size: 12px;">&nbsp;</span>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>序号</td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody id="tbody">
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>sex</td>
					<td>性别</td>
					<td>性别包括男和女</td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>