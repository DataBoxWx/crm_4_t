<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="jquery/bootstrap_3.3.0/css/jquery.bs_pagination.css">


<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/en.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/jquery.bs_pagination.min.js"></script>

<script type="text/javascript">
	var pageNo = 1;
	var pageSize = 2;
	$(function(){
		
		//导出全部
		$("#export-all").click(function(){
			 if(window.confirm("确定导出全部数据吗?")){
				window.location.href="workbench/activity/exportAll.do";
			} 
		});
		//导出部分
		$("#export-part").click(function(){
			 if(window.confirm("确定导出選中的数据吗?")){
				 var sendData = "";
				 for(var i =0;i < $(":checkbox[name='box']:checked").size();i++ ){
					 sendData += "&id=" + $(":checkbox[name='box']:checked")[i].value;
				 }
				 sendData = sendData.substr(1);
				window.location.href="workbench/activity/exportPart.do?" + sendData;
			} 
		});
		
		
		pageList(1,2);
		//以下日历插件在FF中存在兼容问题，在IE浏览器中可以正常使用。
		
		$(".time").datetimepicker({
								language : 'zh-CN',//显示中文
								format : 'yyyy-mm-dd',//显示格式
								initialDate : new Date(),//初始化当前日期
								minView:"month",
								autoclose : true,//选中自动关闭
								todayBtn : true,//显示今日按钮
								clearBtn : true
							})
		
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		$("#creatAct").click(function(){
			$.get(
				"settings/qx/user/list.do",
				function(data){
					var html ="";
					$.each(data,function(i,n){
						html += "<option value='"+n.id+"' "+("${user.id}"==n.id ? "selected":"")+">"+n.name+"</option>";
					})
					$("#create-marketActivityOwner").html(html);
					$("#createActivityModal").modal("show");
				}
			)
		})
		$("#create-marketActivityName").blur(function(){
			if($.trim($("#create-marketActivityName").val()).length==0){
				$("#nameErrorMsg").text("名称不能为空");
			}
		})
		$("#create-marketActivityName").focus(function(){
			$("#nameErrorMsg").text("");
		})
		$("#saveBtn").click(function(){
			$.ajax({
				type:"post",
				url:"workbench/activity/save.do",
				data:{
					"owner":$("#create-marketActivityOwner").val(),
					"name":$.trim($("#create-marketActivityName").val()),
					"startTime":$("#create-startTime").val(),
					"endTime":$("#create-endTime").val(),
					"cost":$.trim($("#create-cost").val()),
					"description":$.trim($("#create-describe").val()),
					"creatBy":"${user.name}"
				},
				beforeSend:function(){
					if($("#nameErrorMsg").text().length==0){
						return true;
					}
					return false;
				},
				success:function(data){
					if(data.success){
						$("#createActivityModal").modal("hide");
						pageList(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("保存失败");
					}
				}
			})
		})
		$("#editBtn").click(function(){
			var $edit = $(":checkbox[name='box']:checked");
			if($edit.length == 0){
				alert("请选择一条记录进行编辑");
			}else if($edit.length > 1){
				alert("只能选择一条记录进行编辑");
			}else{
				
				
				 $.ajax({
					type : "get",
					url : "workbench/activity/edit.do",
					data : {
						"id" : $edit.val()
					},
					success : function(data){
						$.get(
								"settings/qx/user/list.do",
								function(json){
									var html ="";
									$.each(json,function(i,n){
										html += "<option value='"+n.id+"'"+(data.owner == n.id ? "selected" : "")+">"+n.name+"</option>";
									})
									$("#edit-marketActivityOwner").html(html);
									
								}
							);
						
						//$("#edit-marketActivityOwner").prop("selected",(data.owner == $("#edit-marketActivityOwner").val() ? "selected" : false));
						$("#edit-marketActivityName").val(data.name);
						$("#edit-startTime").val(data.startTime);
						$("#edit-endTime").val(data.endTime);
						$("#edit-cost").val(data.cost);
						$("#edit-describe").val(data.description);
						$("#editActivityModal").modal("show");
						
					}
				}) 
			}
		})
		$("#qxbox").click(function(){
			$(":checkbox[name='box']").prop("checked",this.checked);
		})
		//全选
		$("#tbody").on("click",":checkbox[name='box']",function(){
			$("#qxbox").prop("checked",$(":checkbox[name='box']").size() ==$(":checkbox[name='box']:checked").size())
		})
		//条件查询
		$("#inquireBtn").click(function(){
			$("#h-name").val($.trim($("#inquire-name").val())); 
			$("#h-owner").val($.trim($("#inquire-owner").val())); 
			$("#h-startTime").val($("#inquire-startTime").val()); 
			$("#h-endTime").val($("#inquire-endTime").val()); 
			
			pageList(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
		})
		//更新
		$("#updateBtn").click(function(){
			var id= $(":checkbox[name='box']:checked").val();
			$.ajax({
				type : "post",
				url : "workbench/activity/update.do",
				data : {
					"id" : id,
					"owner" : $("#edit-marketActivityOwner").val(),
					"name" : $("#edit-marketActivityName").val(),
					"startTime" : $("#edit-startTime").val(),
					"endTime" : $("#edit-endTime").val(),
					"cost" : $("#edit-cost").val(),
					"description" : $("#edit-describe").val(),
					"editBy" : "${user.name}"
				},
				success : function(data){
					if(data){
						$("#editActivityModal").modal("hide");
						pageList(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("更新失败");
					}
				}
				
			})
			
			
			
			
		})
		//刪除操作
		$("#deleteBtn").click(function(){
			if($(":checkbox[name='box']:checked").size() == 0){
				alert("请至少选择一条记录进行删除");
			}else{
				if(window.confirm("确认删除吗？")){
					var sendPath = "";
					$.each($(":checkbox[name='box']:checked"),function(i,n){
						sendPath += "&id=" + n.value;
					});
					sendPath = sendPath.substr(1);
					$.post(
						"workbench/activity/delete.do",
						sendPath,
						function(data){
							if(data.success){
								pageList(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
							}else{
								alert("删除失败");
							}
						}							
					)
				}
			}
		})
		
		
		//导入activity
		$("#importActivityBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/activity/importActivity.do",
				data : new FormData($('#activityImport')[0]),
				processData: false, 
	            contentType: false, 
	            success : function(data){
	            	if(data.success){
	            		$("#importActivityModal").modal("hide");
	            		pageList(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
	            	}else{
	            		alert("导入失败");
	            	}
	            }
				
			})
		})
		
	});
	
	//页面显示
	function pageList(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/activity/list.do",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $("#h-name").val(),
				"owner" : $("#h-owner").val(),
				"startTime" :$("#h-startTime").val(),
				"endTime" : $("#h-endTime").val()
 			},
			success : function(data){
				var html="";
				$.each(data.list,function(i,n){
					html += "<tr>";
					html += "<td><input type='checkbox' value='"+n.id+"'name='box'></td>";
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='+'\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					html += "<td>"+n.owner+"</td>";
					html += "<td>"+n.startTime+"</td>";
					html += "<td>"+n.endTime+"</td>";
					html += "</tr>";
				})
				$("#tbody").html(html);
				$("#qxbox").prop("checked",false);
				
				//页面显示
				var totalRows = data.total;
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
					  onChangePage : function(event , n){
						  $("#inquire-name").val($("#h-name").val()); 
						  $("#inquire-owner").val($("#h-owner").val()); 
						  $("#inquire-startTime").val($("#h-startTime").val()); 
						  $("#inquire-endTime").val($("#h-endTime").val()); 
						  pageList(n.currentPage , n.rowsPerPage);
					  }
				});
			}
		})
	}
	
</script>
</head>
<body>

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
								<select class="form-control" id="create-marketActivityOwner">
								
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                                <span id="nameErrorMsg" style="font-size:12px;color:red"></span>
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
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
		<input type="hidden" id="h-name">
		<input type="hidden" id="h-owner">
		<input type="hidden" id="h-startTime">
		<input type="hidden" id="h-endTime">
	
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
								<select class="form-control" id="edit-marketActivityOwner">
								<option></option>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" value="2020-10-20">
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
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"  id="updateBtn">更新</button>
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
						<form id="activityImport">
						<input type="file" name="activityImport">
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
					<button type="button" class="btn btn-primary" id="importActivityBtn">导入</button>
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
				      <input class="form-control" type="text" id= "inquire-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id= "inquire-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="inquire-startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="inquire-endTime">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="inquireBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="creatAct"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button type="button" class="btn btn-default" id="export-part"><span class="glyphicon glyphicon-export"></span> 部分导出</button>
				  <button type="button" class="btn btn-default" id="export-all"><span class="glyphicon glyphicon-export"></span> 全部导出</button>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qxbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>
					</tbody>
				</table>
			</div>
			
	<!-- 		<div style="height: 50px; position: relative;top: 30px;">
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
			<div id="activityPagination"></div>
		</div>
		
	</div>
</body>
</html>