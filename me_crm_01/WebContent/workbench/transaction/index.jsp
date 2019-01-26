<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap_3.3.0/css/jquery.bs_pagination.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/jquery.bs_pagination.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/en.js"></script>

<script type="text/javascript">



	$(function(){
		displayTransaction(1,2);
	
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		
		$("#inquier").click(function(){
			$("#h-owner").val($.trim($("#s-owner").val()));
			$("#h-name").val($.trim($("#s-name").val()));
			$("#h-customerName").val($.trim($("#s-customerName").val()));
			$("#h-stage").val($.trim($("#s-stage").val()));
			$("#h-type").val($.trim($("#s-type").val()));
			$("#h-source").val($.trim($("#s-source").val()));
			$("#h-contactsName").val($.trim($("#s-contactsName").val()));
			displayTransaction(1,$("#transactionPagination").bs_pagination("getOption","rowsPerPage"));
		})
		
		$("#qxbox").click(function(){
			$(":checkbox[name='box']").prop("checked",this.checked);
		})
		$("body").on("click",":checkbox[name='box']",function(){
			$("#qxbox").prop("checked",$(":checkbox[name='box']").size() == $(":checkbox[name='box']:checked").size());
		})
		
		//导出
		$("#export").click(function(){
				if(window.confirm("确定导出全部数据吗?")){
					window.location.href="workbench/transaction/export.do";
				}
		})
		//删除
		$("#deleteBtn").click(function(){
			var sendData ="";
			$.each($(":checkbox[name='box']:checked"),function(i,n){
				sendData += "&id=" + n.value;
			})
			sendData = sendData.substr(1);
			$.ajax({
				type : "post",
				url : "wrokbench/transaction/deleteTran.do",
				data : sendData,
				beforeSend : function(){
					if($(":checkbox[name='box']:checked").size() == 0){
						alert("请选择一条记录进行删除");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						displayTransaction(1,$("#transactionPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("删除失败");
					}
				}
			})
		})
		
	});
	
	function editMsg(){
		if($(":checkbox[name='box']:checked").size() == 0){
			alert("请选择一条记录进行编辑");
		}else if($(":checkbox[name='box']:checked").size() > 1){
			alert("只能选择一条记录进行编辑");
		}else{
			window.location.href="workbench/transaction/edit.do?id=" + $(":checkbox[name='box']:checked").val() ;
		}
	}
	
	
	function displayTransaction(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/transaction/displayTransaction.do",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#s-name").val()),
				"owner" : $.trim($("#s-owner").val()),
				"customerName" : $.trim($("#s-customerName").val()),
				"stage" : $.trim($("#s-stage").val()),
				"type" : $.trim($("#s-type").val()),
				"source" : $.trim($("#s-source").val()),
				"contactsName" : $.trim($("#s-contactsName").val()),
				
			},
			success : function(data){
					var html = '';
					$.each(data.dataList,function(i,n){
						 html += '<tr>';
						 html += '<td><input type="checkbox" name="box" value="'+n.id+'"/></td>';
						 html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/transaction/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
						 html += '<td>'+n.customerId+'</td>';
						 html += '<td>'+n.stage+'</td>';
						 html += '<td>'+n.type+'</td>';
						 html += '<td>'+n.owner+'</td>';
						 html += '<td>'+n.source+'</td>';
						 html += '<td>'+n.contactsId+'</td>';
						 html += '</tr>';
					})
					$("#tbody").html(html);
					
					$("#qxbox").prop("checked",false)
					//分页
					var totalRows = data.total;
					var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; 
					$("#transactionPagination").bs_pagination({
						  currentPage: pageNo, //页码pageNo
						  rowsPerPage: pageSize, //每页显示多少条记录 pageSize
						  totalPages: totalPages, //总页数
						  totalRows: totalRows, //总记录条数
						  visiblePageLinks: 2, //显示的卡片个数
						  showGoToPage: true,
						  showRowsPerPage: true,
						  showRowsInfo: true,
						  onChangePage : function(event , n){
							  $("#s-name").val($("#h-name").val());
							  $("#s-owner").val($("#h-owner").val());
							  $("#s-customerName").val($("#h-customerName").val());
							  $("#s-stage").val($("#h-stage").val());
							  $("#s-type").val($("#h-type").val());
							  $("#s-source").val($("#h-source").val());
							  $("#s-contactsName").val($("#h-contactsName").val());
							  displayTransaction(n.currentPage , n.rowsPerPage);
						  } 
					});
			}
		})
	}
	
</script>
</head>
<body>

	<!-- 导入交易的模态窗口 -->
	<div class="modal fade" id="importClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">导入交易</h4>
				</div>
				<div class="modal-body" style="height: 350px;">
					<div style="position: relative;top: 20px; left: 50px;">
						请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
					</div>
					<div style="position: relative;top: 40px; left: 50px;">
						<input type="file">
					</div>
					<div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
						<h3>重要提示</h3>
						<ul>
							<li>给定文件的第一行将视为字段名。</li>
							<li>请确认您的文件大小不超过5MB。</li>
							<li>从XLS/XLSX文件中导入全部重复记录之前都会被忽略。</li>
							<li>复选框值应该是1或者0。</li>
							<li>日期值必须为MM/dd/yyyy格式。任何其它格式的日期都将被忽略。</li>
							<li>日期时间必须符合MM/dd/yyyy hh:mm:ss的格式，其它格式的日期时间将被忽略。</li>
							<li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
							<li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
						</ul>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">导入</button>
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="s-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="s-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="s-customerName">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="s-stage">
					  	<option></option>
					  	<c:forEach items="${stage}" var="stage">
					  		<option value="${stage.value}">${stage.text}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="s-type">
					  	<option></option>
					  	<c:forEach items="${transactionType}" var="type">
					  		<option value="${type.value}">${type.text}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="s-source">
						  <option></option>
						  <c:forEach items="${source}" var="source">
					  		<option value="${source.value}">${source.text}</option>
					  	</c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="s-contactsName">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="inquier">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/save.do';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="editMsg()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importClueModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button type="button" class="btn btn-default" id="export"><span class="glyphicon glyphicon-export"></span> 导出</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
			<input type="hidden" id="h-owner">
			<input type="hidden" id="h-name">
			<input type="hidden" id="h-customerName">
			<input type="hidden" id="h-stage">
			<input type="hidden" id="h-type">
			<input type="hidden" id="h-source">
			<input type="hidden" id="h-contactsName">
			
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qxbox" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<!-- <tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/transaction/detail.jsp';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/transaction/detail.jsp';">动力节点-交易01</a></td>
                            <td>动力节点</td>
                            <td>谈判/复审</td>
                            <td>新业务</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>李四</td>
                        </tr> -->
					</tbody>
				</table>
			</div>
			
			<!-- <div style="height: 50px; position: relative;top: 20px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div> -->
			<div id="transactionPagination"></div>
		</div>
		
	</div>
</body>
</html>