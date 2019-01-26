<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
		
		$(function() {
			//页面显示方法
			function pageList(){
				$.get(
					"settings/qx/user/list.do",
					function(data){
						html="";
						var lockState = "";
						$.each(data,function(i,n){
							if(n.lockState == "1"){
								lockState = "启用";
							}else {
								lockState = "锁定";
							}
							html += '<tr>';
							html += '<td><input type="checkbox" name="box" value="'+n.id+'"></td>';
							html += '<td>'+(++i)+'</td>';
							html += '<td>'+this.loginAct+'</td>';
							html += '<td>'+this.name+'</td>';
							html += '<td>'+this.deptCode+'</td>';
							html += '<td>'+this.email+'</td>';
							html += '<td>'+this.expireTime+'</td>';
							html += '<td>'+this.allowIp+'</td>';
							html += '<td><a href="javascript:void(0)" onclick="window.confirm("您确定要锁定该用户吗？");" style="text-decoration: none;">'+lockState+'</a></td>';
							html += '<td>'+this.creatBy+'</td>';
							html += '<td>'+this.creatTime+'</td>';
							html += '<td>'+this.editBy+'</td>';
							html += '<td>'+this.editTime+'</td>';
							html += '</tr>';
						})
						$("#tbody").html(html);
						$(":checkbox[name='box']").click(function(){
							$("#qxbox").prop("checked",$(":checkbox[name='box']").size()==$(":checked[name='box']:checked").size());
						})
					}
				)
			}
			//全选
			$("#qxbox").click(function(){
				$(":checkbox[name='box']").prop("checked",this.checked);
			})
			
			pageList();
				//点击创建时取得部门列表
				$("#creatBtn").click(function() {
							$.get("dept/index.do", function(data) {
								var html = "";
								$.each(data.list, function(i, n) {
									html += "<option value="+this.code+">"
											+ this.name + "</option>";
								});
								$("#create-dept").append(html);
								$("#createUserModal").modal("show");
							})
							$("#create-expireTime").datetimepicker({
								language : 'zh-CN',//显示中文
								format : 'yyyy-mm-dd hh:ii:ss',//显示格式
								initialDate : new Date(),//初始化当前日期
								autoclose : true,//选中自动关闭
								todayBtn : true,//显示今日按钮
								clearBtn : true
							})
					})
				//表单验证
			 	$("#create-allowIps").blur(function(){
					 if($.trim(this.value) != ""){
						 var resExp = "^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$"
						var ok = resExp.test($("#create-allowIps").val()) 
						if(ok){
							$("#email-errorMsg").text("");
						}else{
							$("#email-errorMsg").text("正确格式为：A*@000.aaa");
						}
					} 
				})
				$("#create-allowIps").focus(function(){
					$("#email-errorMsg").text("");
				})
				$("#create-confirmPwd").focus(function(){
					$("#confirmPwd-errorMsg").text("");
				})
				$("#create-confirmPwd").blur(function(){
					if(this.value != $("#create-loginPwd").val()){
						$("#confirmPwd-errorMsg").text("两次输入的密码不一致，请重新输入");
					}else{
						$("#confirmPwd-errorMsg").text("");
					}
				})
				$("#create-loginActNo").blur(function() {
					if ($.trim($("#create-loginActNo").val()) == "") {
						$("#loginAct-errorMsg").text("账户不能为空");
					} else {
						$("#loginAct-errorMsg").text("");
						$.post("settings/qx/user/checkAct.do", {
							"loginAct" : $("#create-loginActNo").val()
						}, function(data) {
							if (data.success) {
								$("#loginAct-errorMsg").text("");
							} else {
								$("#loginAct-errorMsg").text("账户已被使用，请重新输入");
							}
						})
					}
				})
				$("#create-loginPwd").blur(function() {
					if ($.trim($("#create-loginPwd").val()) == "") {
						$("#loginPwd-errorMsg").text("密码不能为空");
					} else {
						$("#loginPwd-errorMsg").text("");
					}

				})
				$("#create-loginActNo").focus(function() {
					$("#loginAct-errorMsg").text("");
				})
				$("#create-loginPwd").focus(function() {
					$("#loginPwd-errorMsg").text("");
				}) 
				//新增用户
				 $("#saveBtn").click(function() {
					$.post(
							"settings/qx/user/save.do", 
						{
						"loginAct" : $.trim($("#create-loginActNo").val()),
						"name" : $.trim($("#create-username").val()),
						"loginPwd" : $("#create-loginPwd").val(),
						"email" : $.trim($("#create-email").val()),
						"expireTime" : $("#create-expireTime").val(),
						"lockState" : $("#create-lockStatus").val(),
						"deptCode" : $("#create-dept").val(),
						"allowIp" : $.trim($("#create-allowIps").val())
						},
						function(data){
							if(data.success){
								$("#createUserModal").modal("hide");
								pageList();
							}else{
								alert("保存失敗");
							}
						}
					)
				}) 
				//删除用户
				$("#delBtn").click(function(){
					var temp = $(":checkbox[name='box']:checked");
					if(temp.size() == 0){
						alert("请选择至少一条记录");
					}else{
						if(window.confirm("确认删除吗？")){
							var arg0 = "";
							$.each(temp,function(i,n){
								arg0 += "&id=" + n.value;
							})
							arg1 = arg0.substr(1);
							$.ajax({
								url:"settings/qx/user/delete.do",
								data:arg1,
								success:function(json){
									if(json.success){
										pageList();
									}else{
										alert("删除失败了");
									}
								}
							})
						}
					}
				})
				
			})
</script>

</head>
<body>

	<!-- 创建用户的模态窗口 -->
	<div class="modal fade" id="createUserModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增用户</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">

						<div class="form-group">
							<label for="create-loginActNo" class="col-sm-2 control-label">登录帐号<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-loginActNo">
								<span style="color: red; font-size: 12px" id="loginAct-errorMsg"></span>
							</div>
							<label for="create-username" class="col-sm-2 control-label">用户姓名</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-username">
							</div>
						</div>
						<div class="form-group">
							<label for="create-loginPwd" class="col-sm-2 control-label">登录密码<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="password" class="form-control" id="create-loginPwd">
								<span style="color: red; font-size: 12px" id="loginPwd-errorMsg"></span>
							</div>
							<label for="create-confirmPwd" class="col-sm-2 control-label">确认密码<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="password" class="form-control"
									id="create-confirmPwd">
								<span style="color: red; font-size: 12px" id="confirmPwd-errorMsg"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
								<span style="color: red; font-size: 12px" id="email-errorMsg"></span>
							</div>
							<label for="create-expireTime" class="col-sm-2 control-label">失效时间</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time"
									id="create-expireTime">
							</div>
						</div>
						<div class="form-group">
							<label for="create-lockStatus" class="col-sm-2 control-label">锁定状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-lockStatus">
									<option></option>
									<option value="0">启用</option>
									<option value="1">锁定</option>
								</select>
							</div>
							<label for="create-org" class="col-sm-2 control-label">部门<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-dept">
									<option></option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="create-allowIps" class="col-sm-2 control-label">允许访问的IP</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-allowIps"
									style="width: 280%" placeholder="多个用逗号隔开">
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


	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>用户列表</h3>
			</div>
		</div>
	</div>

	<div class="btn-toolbar" role="toolbar" style="position: relative; height: 80px; left: 30px; top: -10px;">
		<form class="form-inline" role="form" style="position: relative; top: 8%; left: 5px;">

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">用户姓名</div>
					<input class="form-control" type="text" >
				</div>
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">部门名称</div>
					<input class="form-control" type="text" >
				</div>
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">锁定状态</div>
					<select class="form-control" >
						<option></option>
						<option>锁定</option>
						<option>启用</option>
					</select>
				</div>
			</div>
			<br> <br>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">失效时间</div>
					<input class="form-control" type="text" id="startTime" />
				</div>
			</div>

			

			<div class="form-group">
				<div class="input-group">
					<input class="form-control" type="text" id="endTime" />
				</div>
			</div>

			<button type="submit" class="btn btn-default">查询</button>

		</form>
	</div>


	<div class="btn-toolbar" role="toolbar"
		style="background-color: #F7F7F7; height: 50px; position: relative; left: 30px; width: 110%; top: 20px;">
		<div class="btn-group" style="position: relative; top: 18%;">
			<button type="button" class="btn btn-primary" id="creatBtn">
				<span class="glyphicon glyphicon-plus"></span> 创建
			</button>
			<button type="button" class="btn btn-danger" id="delBtn">
				<span class="glyphicon glyphicon-minus"></span> 删除
			</button>
		</div>
		<div class="btn-group"
			style="position: relative; top: 18%; left: 5px;">
			<button type="button" class="btn btn-default">设置显示字段</button>
			<button type="button" class="btn btn-default dropdown-toggle"
				data-toggle="dropdown">
				<span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
			</button>
			<ul id="definedColumns" class="dropdown-menu" role="menu">
				<li><a href="javascript:void(0);"><input type="checkbox" />
						登录帐号</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						用户姓名</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						部门名称</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						邮箱</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						失效时间</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						允许访问IP</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						锁定状态</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						创建者</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						创建时间</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						修改者</a></li>
				<li><a href="javascript:void(0);"><input type="checkbox" />
						修改时间</a></li>
			</ul>
		</div>
	</div>

	<div style="position: relative; left: 30px; top: 40px; width: 110%">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qxbox"/></td>
					<td>序号</td>
					<td>登录帐号</td>
					<td>用户姓名</td>
					<td>部门名称</td>
					<td>邮箱</td>
					<td>失效时间</td>
					<td>允许访问IP</td>
					<td>锁定状态</td>
					<td>创建者</td>
					<td>创建时间</td>
					<td>修改者</td>
					<td>修改时间</td>
				</tr>
			</thead>
			<tbody id="tbody">
				<!-- <tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td><a href="detail.html">zhangsan</a></td>
					<td>张三</td>
					<td>市场部</td>
					<td>zhangsan@bjpowernode.com</td>
					<td>2017-02-14 10:10:10</td>
					<td>127.0.0.1,192.168.100.2</td>
					<td><a href="javascript:void(0);"
						onclick="window.confirm('您确定要锁定该用户吗？');"
						style="text-decoration: none;">启用</a></td>
					<td>admin</td>
					<td>2017-02-10 10:10:10</td>
					<td>admin</td>
					<td>2017-02-10 20:10:10</td>
				</tr>
				<tr>
					<td><input type="checkbox" /></td>
					<td>2</td>
					<td><a href="detail.html">lisi</a></td>
					<td>李四</td>
					<td>市场部</td>
					<td>lisi@bjpowernode.com</td>
					<td>2017-02-14 10:10:10</td>
					<td>127.0.0.1,192.168.100.2</td>
					<td><a href="javascript:void(0);"
						onclick="window.confirm('您确定要启用该用户吗？');"
						style="text-decoration: none;">锁定</a></td>
					<td>admin</td>
					<td>2017-02-10 10:10:10</td>
					<td>admin</td>
					<td>2017-02-10 20:10:10</td>
				</tr> -->
			</tbody>
		</table>
	</div>

	<div style="height: 50px; position: relative; top: 30px; left: 30px;">
		<div>
			<button type="button" class="btn btn-default"
				style="cursor: default;">
				共<b>50</b>条记录
			</button>
		</div>
		<div class="btn-group"
			style="position: relative; top: -34px; left: 110px;">
			<button type="button" class="btn btn-default"
				style="cursor: default;">显示</button>
			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					10 <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">20</a></li>
					<li><a href="#">30</a></li>
				</ul>
			</div>
			<button type="button" class="btn btn-default"
				style="cursor: default;">条/页</button>
		</div>
		<div style="position: relative; top: -88px; left: 285px;">
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
	</div>

</body>
</html>