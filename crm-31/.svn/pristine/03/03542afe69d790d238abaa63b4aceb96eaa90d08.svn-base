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
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<link type="text/css" rel="stylesheet" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/localization/en.min.js"></script>

<script type="text/javascript">

	$(function(){
		
		//以下日历插件在FF中存在兼容问题，在IE浏览器中可以正常使用。
		$('.time').datetimepicker({
			 language: 'zh-CN',//显示中文
			 format : 'yyyy-mm-dd',//显示格式
			 initialDate: new Date(),//初始化当前日期
			 minView : "month",
			 autoclose: true,//选中自动关闭
			 todayBtn: true,//显示今日按钮
			 clearBtn : true
		})
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		
		$("#createBtn").click(function(){
			//发送ajax get请求，获取所有的用户（所有者）
			$.get("workbench/activity/create.do",function(json){
				//[{"id":"","name":""},{"id":"","name":""},{"id":"","name":""}]
				var html = "";
				$.each(json , function(i , n){
					html += "<option value='"+n.id+"' " + (n.id == "${user.id}" ? "selected" : "") + ">"+n.name+"</option>";
				});
				$("#owner").html(html);
				$("#createActivityModal").modal("show");
			});
		});
		
		
		$("#saveBtn").click(function () {

			/*
			$.ajax({
				...
				beforeSend : function(){
					//表单验证
					return true;
				},
				...
			});
			*/
			
			$.post(
				"workbench/activity/sava.do",
				{
					"owner" : $("#owner").val(),
					"name" : $.trim($("#name").val()),
					"startDate" : $("#startDate").val(),
					"endDate" : $("#endDate").val(),
					"cost" : $.trim($("#cost").val()),
					"description" : $.trim($("#description").val())
				},
				function (data) {
					if(data.success){
						$("#createActivityModal").modal("hide");
						display(1 , $("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("创建市场活动失败！")
					}
				},
				"json"
			)
		});
		
		//页面加载完毕之后显示第一页数据
		display(1 , 10);
		
		//点击查询
		$("#pageQueryBtn").click(function(){
			
			//更新隐藏域当中的条件
			$("#h-name").val($.trim($("#p-name").val()));
			$("#h-owner").val($.trim($("#p-owner").val()));
			$("#h-startDate").val($.trim($("#p-startDate").val()));
			$("#h-endDate").val($.trim($("#p-endDate").val()));
			
			//alert($("#activityPagination").bs_pagination("getOption","rowsPerPage"));
			display(1 , $("#activityPagination").bs_pagination("getOption","rowsPerPage"));
		});
		
		$("#editBtn").click(function(){
			
			if($(":checkbox[name='id']:checked").size() == 0){
				alert("请选择需要编辑的数据！");
			}else if($(":checkbox[name='id']:checked").size() > 1){
				alert("每次只能编辑一条记录！");
			}else{
				$.post(
					"workbench/activity/edit.do",
					{ 
						"id" : $(":checkbox[name='id']:checked").val()
					},
					function(json){
						//{"userList" : [{},{}] , "activity" : {"name":"","owner":"","startDate":""...}}
						
						var html = "";
						$(json.userList).each(function(){
							html += "<option value='"+this.id+"' " + (this.id == json.activity.owner ? "selected" : "") + ">"+this.name+"</option>";
						});
						$("#edit-owner").html(html);
						
						$("#edit-id").val(json.activity.id);
						$("#edit-name").val(json.activity.name);
						$("#edit-startDate").val(json.activity.startDate);
						$("#edit-endDate").val(json.activity.endDate);
						$("#edit-cost").val(json.activity.cost);
						$("#edit-description").val(json.activity.description);
						
						$("#editActivityModal").modal("show");
					}
				);
			}
		});
		
		$("#firstChk").click(function(){
			$(":checkbox[name='id']").prop("checked" , this.checked);
		});
		
		$("body").on("click" , ":checkbox[name='id']" , function(){
			$("#firstChk").prop("checked" , $(":checkbox[name='id']").size() == $(":checkbox[name='id']:checked").size());
		});
		
		$("#updateBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/activity/update.do",
				data : {
					"id" : $.trim($("#edit-id").val()),
					"owner" : $.trim($("#edit-owner").val()),
					"name" : $.trim($("#edit-name").val()),
					"startDate" : $.trim($("#edit-startDate").val()),
					"endDate" : $.trim($("#edit-endDate").val()),
					"cost" : $.trim($("#edit-cost").val()),
					"description" : $.trim($("#edit-description").val())
				},
				beforeSend : function(){
					//表单验证
					return true; //false
				},
				success : function(json){
					if(json.success){
						$("#editActivityModal").modal("hide");
						display($("#activityPagination").bs_pagination("getOption","currentPage") , $("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("更新失败！");
					}
					$("#firstChk").prop("checked" , false);
				}
			});
		});
		
		$("#delBtn").click(function(){
			if($(":checkbox[name='id']:checked").size() === 0){
				alert("请选择要删除的数据！");
			}else{
				if(window.confirm("您确认要删除数据吗？")){
					var sendData = "";
					$.each( $(":checkbox[name='id']:checked") , function(i , n){
						sendData += "&id=" + n.value;
					});
					sendData = sendData.substr(1);
					$.post(
							"workbench/activity/delete.do",
							sendData,
							function(json){
								if(json.success){
									display(1 , $("#activityPagination").bs_pagination("getOption","rowsPerPage"));
								}else{
									alert("删除失败！");
								}
							}
					);
				}
			}
		});
		
		$("#exportAllBtn").click(function(){
			//传统请求
			if(window.confirm("您确定要导出所有数据吗？")){
				document.location.href = "workbench/activity/exportAll.do";
			}
		});
		
		
		$("#exportCheckedBtn").click(function(){
			if(window.confirm("您确定要导出选中的数据吗？")){
				var sendData = "";
				$.each($(":checkbox[name='id']:checked") , function(i , n){
					sendData += "&id=" + n.value;
				});
				sendData = sendData.substr(1);
				document.location.href = "workbench/activity/exportCheckedAll.do?" + sendData;
			}
		});
		
		$("#importBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/activity/import.do",
				data : new FormData($('#importActivityForm')[0]),
				processData: false, 
	            contentType: false, //不再使用传统的普通表单方式提交数据，采用multipart/form-data方式。
				success : function(json){
					if(json.success){
						$("#importActivityModal").modal("hide");
						display(1 , $("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("导入失败!");
					}
				}
			});
		});
		
	});
	
	function display(pageNo , pageSize){
		$.ajax({
			type : "get",
			cache : false,
			url : "workbench/activity/page.do",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $("#h-name").val(),
				"owner" : $("#h-owner").val(),
				"startDate" : $("#h-startDate").val(),
				"endDate" : $("#h-endDate").val()
			}, 
			success : function(json){
				//{"total" : 100 , "dataList" : [{"id":"","name":"","owner":"","startDate":"","endDate":""},{}]}
				var html = "";
				$.each(json.dataList, function(i , n){
					html += '<tr class="active">';
					html += '<td><input type="checkbox" name="id" value="'+n.id+'"/></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					html += '<td>'+n.owner+'</td>';
					html += '<td>'+n.startDate+'</td>';
					html += '<td>'+n.endDate+'</td>';
					html += '</tr>';
				});
				$("#activityTbody").html(html);
				
				//显示卡片
				var totalRows = json.total;
				var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; 
				$("#activityPagination").bs_pagination({
					  currentPage: pageNo, //页码pageNo
					  rowsPerPage: pageSize, //每页显示多少条记录 pageSize
					  totalPages: totalPages, //总页数
					  totalRows: totalRows, //总记录条数
					  visiblePageLinks: 3, //显示的卡片个数
					  showGoToPage: true,
					  showRowsPerPage: true,
					  showRowsInfo: true,
					  onChangePage : function(event , data){
						  
						  //将隐藏域中的查询条件归位
						  $("#p-name").val($("#h-name").val());
						  $("#p-owner").val($("#h-owner").val());
						  $("#p-startDate").val($("#h-startDate").val());
						  $("#p-endDate").val($("#h-endDate").val());
						  
						  display(data.currentPage , data.rowsPerPage);
					  }
				});
				
				$("#firstChk").prop("checked" , false);
				
			}
		});
	}
	
</script>
</head>
<body>

	<input type="hidden" id="h-name">
	<input type="hidden" id="h-owner">
	<input type="hidden" id="h-startDate">
	<input type="hidden" id="h-endDate">

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="owner">
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<input type="hidden" id="edit-id">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startDate">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endDate" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 导入市场活动的模态窗口 -->
	<div class="modal fade" id="importActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
				</div>
				<div class="modal-body" style="height: 350px;">
					<div style="position: relative;top: 20px; left: 50px;">
						请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
					</div>
					<div style="position: relative;top: 40px; left: 50px;">
						<form id="importActivityForm">
						<input type="file" name="filename">
						</form>
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
					<button type="button" class="btn btn-primary" id="importBtn">导入</button>
				</div>
			</div>
		</div>
	</div>
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="p-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="p-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="p-startDate"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="p-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="pageQueryBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button type="button" class="btn btn-default" id="exportCheckedBtn"><span class="glyphicon glyphicon-export"></span> 导出选中</button>
				  <button type="button" class="btn btn-default" id="exportAllBtn"><span class="glyphicon glyphicon-export"></span> 导出全部</button>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="firstChk"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityTbody">
					<%--
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>
                         --%>
					</tbody>
				</table>
			</div>
			
			<div id="activityPagination"></div>
			
		</div>
		
	</div>
</body>
</html>