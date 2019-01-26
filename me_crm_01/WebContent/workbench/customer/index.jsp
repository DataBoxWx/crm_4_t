<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="jquery/bootstrap_3.3.0/css/jquery.bs_pagination.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/en.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/jquery.bs_pagination.min.js"></script>

<script type="text/javascript">

	$(function(){
		
		displayCustomer(1,2);
		//时间插件
		$(".time").datetimepicker({
			language : 'zh-CN',//显示中文
			format : 'yyyy-mm-dd',//显示格式
			initialDate : new Date(),//初始化当前日期
			minView:"month",
			autoclose : true,//选中自动关闭
			todayBtn : true,//显示今日按钮
			clearBtn : true,
			pickerPosition : "top-right"
		})
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		
		//获创建客户时的所有者
		$("#turnSaveModalBtn").click(function(){
			getUserList();
			$("#createCustomerModal").modal("show");
		});
		
		//保存新创建的用户
		$("#saveCustomerBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/customer/saveCustomer.do",
				data :{
					"owner" : $("#create-owner").val(),
					"name" : $.trim( $("#create-name").val()),
					"website" :$.trim( $("#create-website").val()),
					"telephone" : $.trim($("#create-telephone").val()),
					"description" : $.trim($("#create-description").val()),
					"contactSummary" : $.trim($("#create-contactSummary").val()),
					"relationTimeNext" : $.trim($("#create-relationTimeNext").val()),
					"address" : $.trim($("#create-address").val())
				},
				beforeSend : function(){
					if($.trim($("#create-owner").val())==""){
						alert("所有者不能为空");
						return false;
					}else if($.trim($("#create-name").val()) == ""){
						alert("名称不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#createCustomerModal").modal("hide");	
					}else{
						alert("保存失败");
					}
				}
			})
		})
		
		//查询
		$("#inquire").click(function(){
			 $("#h-name").val($.trim($("#s-name").val()));
			 $("#h-owner").val($.trim($("#s-owner").val()));
			 $("#h-telephone").val( $.trim($("#s-telephone").val()));
			 $("#h-website").val($.trim($("#s-website").val()));
			 displayCustomer(1,$("#customerPagination").bs_pagination("getOption","rowsPerPage"));
		})
		
		//g根据id获取编辑信息
		$("#turnEditModalBtn").click(function(){
			$.ajax({
				type : "get",
				url : "settings/qx/user/list.do",
				success : function(data){
					var html = '<option></option>';
					$.each(data,function(i,n){
						html += '<option value="'+n.id+'">'+n.name+'</option>'
					})
					$("#edit-owner").html(html);
				}
			})
			$.ajax({
				type : "get",
				url : "workbench/customer/getById.do",
				data : {
					"id" : $(":checkbox[name='box']:checked").val()
				},
				beforeSend : function(){
					if( $(":checkbox[name='box']:checked").size() == 0){
						alert("请选择一条记录进行编辑");
						return false;
					}else if($(":checkbox[name='box']:checked").size() > 1){
						alert("最多选择一条记录进行编辑");
						return false;
					}
					return true;
				},
				success : function(data){
					var html = '<option></option>';
					$.each(data.userList,function(i,n){
						html += '<option value="'+n.id+'"'+(n.id == data.customer.owner ? "selected" : "")+'>'+n.name+'</option>'
					})
					$("#edit-owner").html(html);
					
					$("#edit-name").val(data.customer.name);
					$("#edit-website").val(data.customer.website);
					$("#edit-telephone").val(data.customer.telephone);
					$("#edit-description").val(data.customer.description);
					$("#edit-contactSummary").val(data.customer.contactSummary);
					$("#edit-relationTimeNext").val(data.customer.relationTimeNext);
					$("#edit-address").val(data.customer.address);
					
					$("#editCustomerModal").modal("show");
				}
			})
		})
		
		//全选
		$("#qxbox").click(function(){
			$(":checkbox[name='box']").prop("checked",this.checked);
		})
		$("#tbody").on("click",":checkbox[name='box']",function(){
			$("#qxbox").prop("checked",$(":checkbox[name='box']").size() == $(":checkbox[name='box']:checked").size())
		})
		
		
		//更新
		$("#updateCustomer").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/customer/updateCustomer.do",
				data : {
					"id" : $(":checkbox[name='box']:checked").val(),
					"owner" : $("#edit-owner").val(),
					"name" : $.trim( $("#edit-name").val()),
					"website" :$.trim( $("#edit-website").val()),
					"telephone" : $.trim($("#edit-telephone").val()),
					"description" : $.trim($("#edit-description").val()),
					"contactSummary" : $.trim($("#edit-contactSummary").val()),
					"relationTimeNext" : $.trim($("#edit-relationTimeNext").val()),
					"address" : $.trim($("#edit-address").val())
				},
				beforeSend : function(){
					if($("#edit-owner").val() ==""){
						alert("所有者不能为空");
						return false;
					}else if($.trim($("#edit-name").val()) ==""){
						alert("名称不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#editCustomerModal").modal("hide");
						displayCustomer(1,$("#customerPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("更新失败");
					}
				}
			})
		})
		
		
		//删除
		$("#turndeleteBtn").click(function(){
			if(window.confirm("您确认删除吗?")){
				var sendData = "";
				$.each($(":checkbox[name='box']:checked"),function(i,n){
					sendData += "&id=" + n.value;
				})
				sendData = sendData.substr(1);
				
				$.ajax({
					type : "post",
					url : "workbench/customer/deleteCustomer.do",
					cache : false,
					data : sendData,
					beforeSend : function(){
						if($(":checkbox[name='box']:checked").size()==0){
							alert("请选择一条记录删除");
							return false;
						}
						return true;
					},
					success : function(data){
						if(data.success){
							displayCustomer(1,$("#customerPagination").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert("删除失败");
						}
					}
				})
			}
		})
		
		//导出
		$("#export").click(function(){
				if(window.confirm("确定导出全部数据吗?")){
					window.location.href="workbench/customer/export.do";
				}
		})
		
		//导入
		$("#import").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/customer/import.do",
				data : new FormData($('#importClueFile')[0]),
				processData: false, 
				contentType: false, 
				success : function(data){
					if(data.success){
						$("#importActivityModal").modal("hide");
						displayCustomer(1,$("#customerPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("导入失败");
					}
				}
				
			})
		})

		
	});
	
	function getUserList(){
		$.ajax({
			type : "get",
			url : "settings/qx/user/list.do",
			success : function(data){
				var html = '<option></option>';
				$.each(data,function(i,n){
					html += '<option value="'+n.id+'" '+(n.id == "${user.id}" ? "selected" : "")+'>'+n.name+'</option>'
				})
				$("#create-owner").html(html);
			}
		})
	}
	
	function displayCustomer(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/customer/displayAll.do",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#s-name").val()),
				"owner" : $.trim($("#s-owner").val()),
				"telephone" : $.trim($("#s-telephone").val()),
				"website" : $.trim($("#s-website").val()),
				
			},
			success : function(data){
					var html = '';
					$.each(data.dataList,function(i,n){
						html += '<tr>';
						html += '<td><input type="checkbox" value="'+n.id+'" name="box"/></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/customer/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
						html += '<td>'+n.owner+'</td>';
						html += '<td>'+n.telephone+'</td>';
						html += '<td>'+n.website+'</td>';
						html += '</tr>';
					})
					$("#tbody").html(html);
					
					$("#qxbox").prop("checked",false)
					//分页
					var totalRows = data.total;
					var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; 
					$("#customerPagination").bs_pagination({
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
							  $("#s-telephone").val($("#h-telephone").val());
							  $("#s-website").val($("#h-website").val());
							  displayCustomer(n.currentPage , n.rowsPerPage);
						  } 
					});
			}
		})
	}
	
</script>
</head>
<body>

	<!-- 创建客户的模态窗口 -->
	<div class="modal fade" id="createCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  
								</select>
							</div>
							<label for="create-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-name">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-website">
                            </div>
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-telephone">
							</div>
						</div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control time" id="create-relationTimeNext">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCustomerBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改客户的模态窗口 -->
	<div class="modal fade" id="editCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
							<label for="edit-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-name" value="动力节点">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
                            </div>
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-telephone" value="010-84846003">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary1" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime2" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control time" id="edit-relationTimeNext">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateCustomer">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 导入客户的模态窗口 -->
	<div class="modal fade" id="importActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">导入客户</h4>
				</div>
				<div class="modal-body" style="height: 350px;">
					<div style="position: relative;top: 20px; left: 50px;">
						请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
					</div>
					<div style="position: relative;top: 40px; left: 50px;">
						<form id="importClueFile">
							<input type="file" name="importClueFile">
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
					<button type="button" class="btn btn-primary" id="import">导入</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>客户列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <input type="hidden" id="h-name">
				  <input type="hidden" id="h-owner">
				  <input type="hidden" id="h-telephone">
				  <input type="hidden" id="h-website">
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="s-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="s-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="s-telephone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司网站</div>
				      <input class="form-control" type="text" id="s-website">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="inquire">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="turnSaveModalBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="turnEditModalBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="turndeleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button type="button" class="btn btn-default" id="export"><span class="glyphicon glyphicon-export"></span> 导出</button>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qxbox"/></td>
							<td>名称</td>
							<td>所有者</td>
							<td>公司座机</td>
							<td>公司网站</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/customer/detail.jsp';">动力节点</a></td>
							<td>zhangsan</td>
							<td>010-84846003</td>
							<td>http://www.bjpowernode.com</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/customer/detail.jsp';">动力节点</a></td>
                            <td>zhangsan</td>
                            <td>010-84846003</td>
                            <td>http://www.bjpowernode.com</td>
                        </tr>
					</tbody>
				</table>
			</div>
			
			<!-- <div style="height: 50px; position: relative;top: 30px;">
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
			<div id="customerPagination"></div>
		</div>
		
	</div>
</body>
</html>