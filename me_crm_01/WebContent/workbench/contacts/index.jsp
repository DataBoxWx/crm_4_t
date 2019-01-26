<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>



<script type="text/javascript">

	$(function(){
		displayContacts(1,2);
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
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
		$(".time1").datetimepicker({
			language : 'zh-CN',//显示中文
			format : 'yyyy-mm-dd',//显示格式
			initialDate : new Date(),//初始化当前日期
			minView:"month",
			autoclose : true,//选中自动关闭
			todayBtn : true,//显示今日按钮
			clearBtn : true,
		})
		
		$("#s-inquire").click(function(){
			$("#h-owner").val($.trim($("#s-owner").val()));
			$("#h-name").val($.trim($("#s-name").val()));
			$("#h-customerName").val($.trim($("#s-customerName").val()));
			$("#h-source").val($.trim($("#s-source").val()));
			$("#h-birthday").val($.trim($("#s-birthday").val()));
			displayContacts(1,$("#contactsPagination").bs_pagination("getOption","rowsPerPage"));
		})
		//全选
		$("#qxbox").click(function(){
			$(":checkbox[name='box']").prop("checked",this.checked);
		})
		$("#table").on("click",":checkbox[name='box']",function(){
			$("#qxbox").prop("checked",$(":checkbox[name='box']").size() == $(":checkbox[name='box']:checked").size());
		})
		
		//创建
		$("#saveBtn").click(function(){
			$.ajax({
				type : "get",
				url : "settings/qx/user/list.do",
				success : function(data){
					var html = '<option></option>';
					$.each(data,function(i,n){
						html += '<option value="'+n.id+'" '+(n.id == "${user.id}" ? "selected" : "")+'>'+n.name+'</option>'
					});
					$("#create-owner").html(html);
					
					$("#createContactsModal").modal("show");
				}
			})
		})
		$('#create-customerName').typeahead({
			source : function(query , process){
				$.post(
						"workbench/contacts/getByName.do",
						{"name" : query},
						function(json){
							process(json);
						}
				);
			},
			delay : 500,
			items : 8
		});
		
		$("#saveContactsBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/contacts/saveContacts.do",
				data : {
					"owner" : $("#create-owner").val(),
					"source" : $("#create-source").val(),
					"name" : $.trim($("#create-name").val()),
					"appellation" : $.trim($("#create-appellation").val()),
					"position" : $.trim($("#create-position").val()),
					"phone" : $.trim($("#create-phone").val()),
					"birthday" : $.trim($("#create-birthday").val()),
					"email" : $.trim($("#create-email").val()),
					"customerName" : $.trim($("#create-customerName").val()),
					"description" : $.trim($("#create-description").val()),
					"contactSummary" : $.trim($("#create-contactSummary").val()),
					"relationTimeNext" : $.trim($("#create-relationTimeNext").val()),
					"address" : $.trim($("#create-address").val())
				},
				beforeSend : function(){
					if($("#create-owner").val() ==""){
						alert("所有者不能为空");
						return false;
					}else if($.trim($("#create-name").val()) ==""){
						alert("名称不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#createContactsModal").modal("hide");
						displayContacts(1,$("#contactsPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("保存失败");
					}
				}
			})
		})
		
		//删除
		$("#deleteBtn").click(function(){
			if(window.confirm("您确认删除吗?")){
				var sendData = "";
				$.each($(":checkbox[name='box']:checked"),function(i,n){
					sendData += "&id=" + n.value;
				})
				sendData = sendData.substr(1);
				$.post(
					"workbench/contacts/delete.do",
					sendData,
					function(data){
						if(data.success){
							displayContacts(1,$("#contactsPagination").bs_pagination("getOption","rowsPerPage"));
						}else{
							alert("删除失败");
						}
					}
				)
			}
		})
		
		//编辑信息获取
		$("#editBtn").click(function(){
			$.get(
				"workbench/contacts/getById.do",
				{
					"id" : $(":checkbox[name='box']:checked").val()
				},
				function(data){
					var html = '';
					$.each(data.userList,function(i,n){
						html += '<option value="'+n.id+'">'+n.name+'</option>'
					});
					$("#edit-owner").html(html);
					
					$("#edit-owner").val(data.contacts.owner),
					$("#edit-source").val(data.contacts.source),
					$("#edit-name").val(data.contacts.name),
					$("#edit-appellation").val(data.contacts.appellation),
					$("#edit-position").val(data.contacts.position),
					$("#edit-phone").val(data.contacts.phone),
					$("#edit-birthday").val(data.contacts.birthday),
					$("#edit-email").val(data.contacts.email),
					$("#edit-customerName").val(data.contacts.customerId),
					$("#edit-description").val(data.contacts.description),
					$("#edit-contactSummary").val(data.contacts.contactSummary),
					$("#edit-relationTimeNext").val(data.contacts.relationTimeNext),
					$("#edit-address").val(data.contacts.address)
					
					$("#editContactsModal").modal("show");
				}
			)
		})
		$('#edit-customerName').typeahead({
			source : function(query , process){
				$.post(
						"workbench/contacts/getByName.do",
						{"name" : query},
						function(json){
							process(json);
						}
				);
			},
			delay : 500,
			items : 8
		});
		$("#updateBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/contacts/update.do",
				data : {
					"id" : $(":checkbox[name='box']:checked").val(),
					"owner" : $("#edit-owner").val(),
					"source" : $("#edit-source").val(),
					"name" : $.trim($("#edit-name").val()),
					"appellation" : $.trim($("#edit-appellation").val()),
					"position" : $.trim($("#edit-position").val()),
					"phone" : $.trim($("#edit-phone").val()),
					"birthday" : $.trim($("#edit-birthday").val()),
					"email" : $.trim($("#edit-email").val()),
					"customerName" : $.trim($("#edit-customerName").val()),
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
						$("#editContactsModal").modal("hide");
						displayContacts(1,$("#contactsPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("保存失败");
					}
				}
			})
		})
		
		//导出
		$("#export").click(function(){
				if(window.confirm("确定导出全部数据吗?")){
					window.location.href="workbench/contacts/export.do";
				}
		})
		
		//导入
		$("#import").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/contacts/import.do",
				data : new FormData($('#importContactsFile')[0]),
				processData: false, 
				contentType: false, 
				success : function(data){
					if(data.success){
						$("#importContactsModal").modal("hide");
						displayContacts(1,$("#contactsPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("导入失败");
					}
				}
				
			})
		})
		
		
	});
	function displayContacts(pageNo,pageSize){
		$.get(
			"workbench/contacts/displayContacts.do",
			{
				"pageSize" : pageSize,
				"pageNo" : pageNo,
				"owner" : $("#h-owner").val(),
				"name" : $("#h-name").val(),
				"customerName" : $("#h-customerName").val(),
				"source" : $("#h-source").val(),
				"birthday" : $("#h-birthday").val()
			},
			function(data){
				var html = '';
				$.each(data.dataList,function(i,n){
					html += '<tr>';
					html += '<td><input type="checkbox" value="'+n.id+'" name="box" /></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/contacts/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
					html += '<td>'+n.customerId+'</td>';
					html += '<td>'+n.owner+'</td>';
					html += '<td>'+n.source+'</td>';
					html += '<td>'+n.birthday+'</td>';
					html += '</tr>';
				})
				$("#tbody").html(html);
				$("#qxbox").prop("checked",false)
				//分页
				var totalRows = data.total;
				var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; 
				$("#contactsPagination").bs_pagination({
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
						  $("#s-customerName").val($("#h-customreName").val());
						  $("#s-source").val($("#h-source").val());
						  $("#s-birthday").val($("#h-birthday").val());
						  displayContacts(n.currentPage , n.rowsPerPage);
					  } 
				});
			}
		)
	}
	
</script>
</head>
<body>

	
	<!-- 创建联系人的模态窗口 -->
	<div class="modal fade" id="createContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="$('#createContactsModal').modal('hide');">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabelx">创建联系人</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-contactsOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  
								</select>
							</div>
							<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
								 <c:forEach items="${source}" var="source">
									 <option value="${source.value}">${source.text}</option>	
								 </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-name">
							</div>
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-appellation">
								  <option></option>
								  <c:forEach items="${appellation}" var="appellation">
									 <option value="${appellation.value}">${appellation.text}</option>	
								 </c:forEach>
								</select>
							</div>
							
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-position">
							</div>
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
							<label for="create-birth" class="col-sm-2 control-label">生日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time1" id="create-birthday">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-customerName" class="col-sm-2 control-label">客户名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary1" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime1" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control time" id="create-relationTimeNext">
								</div>
							</div>
						</div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveContactsBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改联系人的模态窗口 -->
	<div class="modal fade" id="editContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">修改联系人</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-contactsOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								  
								</select>
							</div>
							<label for="edit-clueSource1" class="col-sm-2 control-label">来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								  <option></option>
									<c:forEach items="${source}" var="source">
										 <option value="${source.value}">${source.text}</option>	
									 </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-name" value="李四">
							</div>
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-appellation">
								  <option></option>
								  <c:forEach items="${appellation}" var="appellation">
										 <option value="${appellation.value}">${appellation.text}</option>	
									 </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-position" value="CTO">
							</div>
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="12345678901">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
							</div>
							<label for="edit-birth" class="col-sm-2 control-label">生日</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-birthday">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-customerName" class="col-sm-2 control-label">客户名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-customerName" placeholder="支持自动补全，输入客户不存在则新建" value="动力节点">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-relationTimeNext">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address2" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
                                </div>
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
	
	
	<!-- 导入联系人的模态窗口 -->
	<div class="modal fade" id="importContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">导入联系人</h4>
				</div>
				<div class="modal-body" style="height: 350px;">
					<div style="position: relative;top: 20px; left: 50px;">
						请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
					</div>
					<div style="position: relative;top: 40px; left: 50px;">
						<form id="importContactsFile">
							<input type="file" name="importContactsFile">						
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
				<h3>联系人列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  <input type="hidden" id="h-owner"> 
				  <input type="hidden" id="h-name"> 
				  <input type="hidden" id="h-customerName"> 
				  <input type="hidden" id="h-source"> 
				  <input type="hidden" id="h-birthday"> 
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="s-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">姓名</div>
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
				      <div class="input-group-addon">生日</div>
				      <input class="form-control" type="text" id="s-birthday">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="s-inquire">查询</button>
				  
				</form>
			</div>
			
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="saveBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importContactsModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button type="button" class="btn btn-default" id="export"><span class="glyphicon glyphicon-export"></span> 导出</button>
				</div>
				
			</div>
			<div style="position: relative;top: 20px;">
				<table class="table table-hover" id="table">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qxbox"/></td>
							<td>姓名</td>
							<td>客户名称</td>
							<td>所有者</td>
							<td>来源</td>
							<td>生日</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<!-- <tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">李四</a></td>
							<td>动力节点</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>2000-10-10</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">李四</a></td>
                            <td>动力节点</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>2000-10-10</td>
                        </tr> -->
					</tbody>
				</table>
			</div>
			
		<!-- 	<div style="height: 50px; position: relative;top: 10px;">
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
			<div id="contactsPagination"></div>
		</div>
		
	</div>
</body>
</html>