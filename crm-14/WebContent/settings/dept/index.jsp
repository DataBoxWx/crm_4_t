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

	function display(){
		$.ajax({
			type : "get",
			url : "settings/dept/getAll.do",
			cache : false,
			success : function(json){
				//[{"code":"","name":"","manager":"","phone":"","description":""},{},{}]	
				var html = "";
				$.each(json , function(i , n){ //i是下标，n是数组的每一个元素
					html += '<tr>';
					html += '<td><input type="checkbox" name="code" value="'+n.code+'"/></td>';
					html += '<td>'+n.code+'</td>';
					html += '<td>'+n.name+'</td>';
					html += '<td>'+n.manager+'</td>';
					html += '<td>'+n.phone+'</td>';
					html += '<td>'+n.description+'</td>';
					html += '</tr>';
				});
				
				//页面的元素还没有加载，这些复选框还不存在，这个时候绑定失败。
				/*
				$(":checkbox[name='code']").click(function(){
					$("#firstChk").prop("checked" , $(":checkbox[name='code']").size() == $(":checkbox[name='code']:checked").size());
				});
				*/
				
				$("#deptTbody").html(html);
				
				//写到这里可以绑定，因为以上代码 “jQuery对象.html(html)”执行之后，浏览器内存当中就有复选框对象了，可以绑定了。
				/*
				$(":checkbox[name='code']").click(function(){
					$("#firstChk").prop("checked" , $(":checkbox[name='code']").size() == $(":checkbox[name='code']:checked").size());
				});
				*/
			}
		});
	}
	
	$(function(){
		
		$("#firstChk").prop("checked" , false);
		
		//页面加载完毕之后发送ajax get请求，查询所有的部门信息
		display();
		
		//以下这种语法，无法给后期动态生成的元素绑定事件。
		/*
		$(":checkbox[name='code']").click(function(){
			$("#firstChk").prop("checked" , $(":checkbox[name='code']").size() == $(":checkbox[name='code']:checked").size());
		});
		*/
		
		$("#saveBtn").click(function(){
			//表单验证，你自己做！！！！
			//发送ajax post请求
			//$.ajax({});
			//$.post();
			
			$.post(
					"settings/dept/save.do",
					{
						"code" : $.trim($("#code").val()),
						"name" : $.trim($("#name").val()),
						"manager" : $.trim($("#manager").val()),
						"phone" : $.trim($("#phone").val()),
						"description" : $.trim($("#description").val())
					},
					function(json){
						if(json.success){
							$("#createDeptModal").modal("hide");
							//把保存成功之后的数据显示到列表页面上。
							display();
						}else{
							alert("保存部门失败了！");
						}
					}
			);
		});
		
		//jQuery元素的prop()方法的作用是：设置(set)或读取(get)某个属性的值
		//jQuery对象.prop("checked"); 读取
		//jQuery对象.prop("checked" , true); 设置
		//this是dom元素，不是jQuery对象
		$("#firstChk").click(function(){
			$(":checkbox[name='code']").prop("checked" , this.checked);
		});
		
		//jQuery中的on可以给后期动态元素绑定事件
		//$(父元素).on(事件,选择器,回调函数);
		$("#deptTbody").on("click" , ":checkbox[name='code']" , function(){
			$("#firstChk").prop("checked" , $(":checkbox[name='code']").size() == $(":checkbox[name='code']:checked").size());
		});
		
		//以下的两种方式都是给静态元素绑定事件
		//$(选择器).click(function(){});
		//$(选择器).bind("click" , function(){});
		
		//这种方式是专门给后期动态生成的元素绑定事件
		//$(父元素选择器).on("click","给哪些元素绑定事件(选择器)",function(){});
		
		$("#editBtn").click(function(){
			if($(":checkbox[name='code']:checked").size() == 0){
				alert("请选择要修改的部门！");
			}else if($(":checkbox[name='code']:checked").size() > 1){
				alert("一次只能修改一个部门！");
			}else{
				$.get(
						"settings/dept/edit.do",
						{
							"code" : $(":checkbox[name='code']:checked").val(),
							"_" : new Date().getTime()
						}, 
						function(json){
							//{"code":"","name":"","manager":"","phone":"","description":""}
							$("#edit-code").val(json.code);
							$("#edit-name").val(json.name);
							$("#edit-manager").val(json.manager);
							$("#edit-phone").val(json.phone);
							$("#edit-description").val(json.description);
							$("#editDeptModal").modal("show");
						}
				);
			}
		});
		
		$("#updateBtn").click(function(){
			$.post(
					"settings/dept/update.do",
					{
						"code" : $("#edit-code").val(),
						"name" : $.trim($("#edit-name").val()),
						"manager" : $.trim($("#edit-manager").val()),
						"phone" : $.trim($("#edit-phone").val()),
						"description" : $.trim($("#edit-description").val())
					},
					function(json){
						if(json.success){
							$("#editDeptModal").modal("hide");
							display();
						}else{
							alert("更新失败！");
						}
					}
			);
		});
		
		$("#delBtn").click(function(){
			if($(":checkbox[name='code']:checked").size() == 0){
				alert("请选择要删除的数据！");
			}else{
				if(window.confirm("亲，确认要删除数据吗？")){
					var sendData = "";
					var array = $(":checkbox[name='code']:checked");
					$.each(array , function(i , n){ //i是下标，n是每一个元素(n是复选框对象，并且n是dom对象)
						sendData += "&code=" + n.value;
					});
					sendData = sendData.substr(1);
					$.post(
							"settings/dept/delete.do",
							sendData,
							function(json){
								if(json.success){
									display();
								}else{
									alert("删除失败！");
								}
								$("#firstChk").prop("checked" , false);
							}
					);
				}
			}
		});
		
	});
</script>
</head>
<body>

	<!-- 我的资料 -->
	<div class="modal fade" id="myInformation" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">我的资料</h4>
				</div>
				<div class="modal-body">
					<div style="position: relative; left: 40px;">
						姓名：<b>张三</b><br><br>
						登录帐号：<b>zhangsan</b><br><br>
						组织机构：<b>1005，市场部，二级部门</b><br><br>
						邮箱：<b>zhangsan@bjpowernode.com</b><br><br>
						失效时间：<b>2017-02-14 10:10:10</b><br><br>
						允许访问IP：<b>127.0.0.1,192.168.100.2</b>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改密码的模态窗口 -->
	<div class="modal fade" id="editPwdModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 70%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="oldPwd" class="col-sm-2 control-label">原密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="oldPwd" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="newPwd" class="col-sm-2 control-label">新密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="newPwd" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="confirmPwd" class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="confirmPwd" style="width: 200%;">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='login.html';">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 退出系统的模态窗口 -->
	<div class="modal fade" id="exitModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">离开</h4>
				</div>
				<div class="modal-body">
					<p>您确定要退出系统吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='login.html';">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 顶部 -->
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
		<div style="position: absolute; top: 15px; right: 15px;">
			<ul>
				<li class="dropdown user-dropdown">
					<a href="javascript:void(0)" style="text-decoration: none; color: white;" class="dropdown-toggle" data-toggle="dropdown">
						<span class="glyphicon glyphicon-user"></span> zhangsan <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="workbench/index.html"><span class="glyphicon glyphicon-home"></span> 工作台</a></li>
						<li><a href="index.html"><span class="glyphicon glyphicon-wrench"></span> 系统设置</a></li>
						<li><a href="javascript:void(0)" data-toggle="modal" data-target="#myInformation"><span class="glyphicon glyphicon-file"></span> 我的资料</a></li>
						<li><a href="javascript:void(0)" data-toggle="modal" data-target="#editPwdModal"><span class="glyphicon glyphicon-edit"></span> 修改密码</a></li>
						<li><a href="javascript:void(0);" data-toggle="modal" data-target="#exitModal"><span class="glyphicon glyphicon-off"></span> 退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	
	<!-- 创建部门的模态窗口 -->
	<div class="modal fade" id="createDeptModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-plus"></span> 新增部门</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-code" class="col-sm-2 control-label">编号<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="code" style="width: 200%;" placeholder="编号为四位数字，不能为空，具有唯一性">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-name" class="col-sm-2 control-label">名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="name" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-manager" class="col-sm-2 control-label">负责人</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="manager" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">电话</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="phone" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 55%;">
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
	
	<!-- 修改部门的模态窗口 -->
	<div class="modal fade" id="editDeptModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-edit"></span> 编辑部门</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-code" class="col-sm-2 control-label">编号<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" readonly id="edit-code" style="width: 200%;" placeholder="编号为四位数字，不能为空，具有唯一性">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-name" class="col-sm-2 control-label">名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-name" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-manager" class="col-sm-2 control-label">负责人</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-manager" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">电话</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 55%;">
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
	
	<div style="width: 95%">
		<div>
			<div style="position: relative; left: 30px; top: -10px;">
				<div class="page-header">
					<h3>部门列表</h3>
				</div>
			</div>
		</div>
		<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px; top:-30px;">
			<div class="btn-group" style="position: relative; top: 18%;">
			  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createDeptModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
			  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			  <button type="button" class="btn btn-danger" id="delBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
			</div>
		</div>
		<div style="position: relative; left: 30px; top: -10px;">
			<table class="table table-hover">
				<thead>
					<tr style="color: #B3B3B3;">
						<td><input type="checkbox" id="firstChk"/></td>
						<td>编号</td>
						<td>名称</td>
						<td>负责人</td>
						<td>电话</td>
						<td>描述</td>
					</tr>
				</thead>
				<tbody id="deptTbody">
					<%--
					<tr class="active">
						<td><input type="checkbox" /></td>
						<td>1110</td>
						<td>财务部</td>
						<td>张飞</td>
						<td>010-84846005</td>
						<td>description info</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td>
						<td>1120</td>
						<td>销售部</td>
						<td>关羽</td>
						<td>010-84846006</td>
						<td>description info</td>
					</tr>
					--%>
				</tbody>
			</table>
		</div>
		
		<div style="height: 50px; position: relative;top: 0px; left:30px;">
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
						<li><a href="#">下一页</a></li>
						<li class="disabled"><a href="#">末页</a></li>
					</ul>
				</nav>
			</div>
		</div>
			
	</div>
	
</body>
</html>