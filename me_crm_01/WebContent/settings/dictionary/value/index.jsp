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
		//显示字典值列表
		valueList();
		function valueList(){
			$("#tbody").html("");
			$.ajaxSetup({
				async:false
			})
			$.get(
				"dictionary/value/index.do",
				function(data){
					$(data.list).each(function(i){
						$("#tbody").append("<tr><td><input type='checkbox' name='box' value='"+this.id
								+"'/></td><td>"+(i+1)+"</td><td>"+this.value+"</td><td>"+this.text
								+"</td><td>"+this.orderNo+"</td><td>"+this.typeCode
								+"</td></tr>")
					})
				},
				"json"
			)
			$.ajaxSetup({
				async:true
			})
		}
		//进入编辑页面
		$("#edit").click(function(){
			var $box = $("input[name='box']:checked");
			if($box.length==0){
				$("#editErrorMes").html("请选择一条记录");
			}else if($box.length > 1){
				$("#editErrorMes").html("最多选择一条记录进行编辑");
			}else{
				$("#editErrorMes").html("");
				var id = $box.val();
				window.location.href=" settings/dictionary/value/edit.jsp?id="+id;
			}
		})
		//执行删除操作
		$("#delete").click(function(){
			var $box = $("input[name='box']:checked");
			if($box.length==0){
				$("#editErrorMes").html("请选择一条记录");
			}else{
				var path= "dictionary/value/delete.do?";
				for(var i=0;i<$box.length;i++){
					path += "id=" + $box[i].value;
					if(i<$box.length-1){
						path+="&"
					}
				}
				window.location.href=path;
			}
		})
	})
</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='dictionary/value/creat.do'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-default" id="edit"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger" id="delete"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
		  <span style="font-size: 12px;color: red;margin-left: 50px" id="editErrorMes"></span>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody id="tbody">
				
			</tbody>
		</table>
	</div>
	
</body>
</html>