<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

	$(function(){
		
		displayClue(1,2);
		
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
		
		//创建线索
		$("#creatClueBtn").click(function(){
			
			$.ajax({
				type : "get",
				url : "workbench/clue/getOwnerClue.do",
				success : function(data){
						var html = '<option></option>';
					$.each(data.user,function(i,n){
						html += '<option value="'+n.id+'" '+(n.name=="${user.name}" ? "selected" : "")+'>'+n.name+'</option>';	
					})
						$("#create-clueOwner").html(html);
					$("#createClueModal").modal("show");
				}
			})
		})
		
		//保存线索
		$("#saveClueBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/saveClue.do",
				data : {
					"owner" : $("#create-clueOwner").val(),
					"company" : $("#create-company").val(),
					"appellation" : $("#create-call").val(),
					"name" : $("#create-surname").val(),
					"position" : $("#create-job").val(),
					"email" : $("#create-email").val(),
					"telephone" : $("#create-phone").val(),
					"web" : $("#create-website").val(),
					"phone" : $("#create-mphone").val(),
					"clueState" : $("#create-status").val(),
					"clueFrom" : $("#create-source").val(),
					"clueDesc" : $("#create-describe").val(),
					"relationSummary" : $("#create-contactSummary").val(),
					"relationTimeNext" : $("#create-nextContactTime").val(),
					"address" : $("#create-address").val()
				},
				beforeSend : function(){
					if($("#create-clueOwner").val() == ""){
						alert("所有者不能为空");
						return false;
					}
					if($("#create-company").val() == ""){
						alert("公司不能为空");
						return false;
					}
					if($("#create-surname").val() == ""){
						alert("姓名不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#createClueModal").modal("hide");
						displayClue(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
						  
					}else{
						alert("保存失败");
					}
				}
				
			})
		})
		
		//查询条件
		$("#selBtn").click(function(){
			$("#h-name").val($.trim($("#sel-name").val()));
			$("#h-compangy").val($.trim($("#sel-compangy").val()));
			$("#h-telephone").val($.trim($("#sel-telephone").val()));
			$("#h-source").val($.trim($("#sel-source").val()));
			$("#h-owner").val($.trim($("#sel-owner").val()));
			$("#h-phone").val($.trim($("#sel-phone").val()));
			$("#h-clueState").val($.trim($("#sel-clueState").val()));
			displayClue(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
		})
		
		
		//导出全部
		$("#exportAll").click(function(){
			if(window.confirm("确定导出全部数据吗?")){
				window.location.href="workbench/clue/exportAll.do";
			}
		})
		
		//全選
		$("#qxbox").click(function(){
			$(":checkbox[name='box']").prop("checked",this.checked);
		})
		$("#tbody").on("click",":checkbox[name='box']",function(){
			$("#qxbox").prop("checked",$(":checkbox[name='box']").size() == $(":checkbox[name='box']:checked").size());
		})
		
		//点击修改时跳转模态窗口
		$("#editClueBtn").click(function(){
			var clueId = $(":checkbox[name='box']:checked").val()
			$.ajax({
				type : "get",
				url : "workbench/clue/getClueById.do",
				data : {
					"clueId" : clueId,
					"_" : new Date().getTime()
				},
				dataType : "json",
				beforeSend : function(){
					if($(":checkbox[name='box']:checked").size() == 0){
						alert("请选择一条记录进行修改");
						return false;
					}else if($(":checkbox[name='box']:checked").size() > 1){
						alert("请选择一条记录进行修改");
						return false;
					}
					return true;
				},
				success : function(data){
					var html = '<option></option>';
					 $(data.user).each(function(i){
						html += '<option value="'+this.id+'" '+(this.id == data.clue.owner ? "selected" : "")+'>'+this.name+'</option>';
					}) 
					$("#edit-clueOwner").html(html);
					$("#edit-company").val(data.clue.company);
					$("#edit-call").val(data.clue.appellation); 
					$("#edit-surname").val(data.clue.name);
					$("#edit-job").val(data.clue.position);
					$("#edit-email").val(data.clue.email);
					$("#edit-phone").val(data.clue.telephone);
					$("#edit-website").val(data.clue.web);
					$("#edit-mphone").val(data.clue.phone);
					$("#edit-status").val(data.clue.clueState);
					$("#edit-source").val(data.clue.clueFrom);
					$("#edit-describe").val(data.clue.clueDesc);
					$("#edit-contactSummary").val(data.clue.relationSummary);
					$("#edit-nextContactTime").val(data.clue.relationTimeNext);
					$("#edit-address").val(data.clue.address); 
					
					$("#editClueModal").modal("show");
				}
			})
			
		})
		
		//更新綫索信息
		$("#updateBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/updateClueById.do",
				cache : false,
				data : {
					"id" : $(":checkbox[name='box']:checked").val(),
					"owner" : $.trim($("#edit-clueOwner").val()),
					"company" : $.trim($("#edit-company").val()),
					"appellation" : $.trim($("#edit-call").val()),
					"name" : $.trim($("#edit-surname").val()),
					"position" : $.trim($("#edit-job").val()),
					"email" : $.trim($("#edit-email").val()),
					"telephone" : $.trim($("#edit-phone").val()),
					"web" : $.trim($("#edit-website").val()),
					"phone" : $.trim($("#edit-mphone").val()),
					"clueStatus" : $.trim($("#edit-status").val()),
					"clueFrom" : $.trim($("#edit-source").val()),
					"clueDesc" : $.trim($("#edit-describe").val()),
					"relationSummary" : $.trim($("#edit-contactSummary").val()),
					"relationTimeNext" : $.trim($("#edit-nextContactTime").val()),
					"address" : $.trim($("#edit-address").val())
				},
				beforeSend : function(){
					if($("#edit-clueOwner").val() == ""){
						alert("所有者不能為空");
						return false;
					}
					if($("#edit-company").val() == ""){
						alert("公司不能為空");
						return false;
					}
					if($("#edit-surname").val() == ""){
						alert("姓名不能為空");
						return false;
					}
					return true;
				},
				success : function(data){
					
					if(data.success){
						$("#editClueModal").modal("hide");
						displayClue(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
					}else{
						alert("更新失败");
					} 
				}
			})
		})
		
		//线索的删除
		$("#delClueBtn").click(function(){
			var delId = $(":checkbox[name='box']:checked");
			sendDate = "";
			$.each(delId,function(i,n){
				sendDate += "&id=" + this.value;
			})
			sendDate = sendDate.substr(1);
			if(delId.size()==0){
				alert("请选择一条记录进行删除");
			}else{
				if(window.confirm("您确认删除吗？")){
					$.ajax({
						type : "post",
						url : "workbench/clue/deleteClueById.do",
						data :sendDate,
						success : function(data){
							if(data.success){
								displayClue(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
							}else{
								alert("删除失败");
							}
						}
					})
				}
			}
		})
		
		//导入
		$("#importClue").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/importClue.do",
				data : new FormData($('#importClueFile')[0]),
				processData: false, 
	            contentType: false, 
	            success : function(data){
	            	if(data.success){
	            		$("#importClueModal").modal("hide");
	            		displayClue(1,$("#activityPagination").bs_pagination("getOption","rowsPerPage"));
	            	}else{
	            		alert("导入失败");
	            	}
	            }
				
			})
		})
		
	});
	
	
	//页面显示
	function displayClue(pageNo,pageSize){
		$.ajax({
			type : "get",
			url : "workbench/clue/displayAll.do",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#sel-name").val()),
				"company" : $.trim($("#sel-compangy").val()),
				"telephone" : $.trim($("#sel-telephone").val()),
				"source" : $.trim($("#sel-source").val()),
				"owner" : $.trim($("#sel-owner").val()),
				"phone" : $.trim($("#sel-phone").val()),
				"clueState" : $.trim($("#sel-clueState").val()),
			},
			success : function(data){
					var html = '';
					$.each(data.list,function(i,n){
						html += '<tr>';
						html += '<td><input type="checkbox" value="'+n.id+'" name="box"/></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/clue/detail.do?id='+n.id+'\';">'+(n.name+n.appellation)+'</a></td>';
						html += '<td>'+n.company+'</td>';
						html += '<td>'+n.telephone+'</td>';
						html += '<td>'+n.phone+'</td>';
						html += '<td>'+n.clueFrom+'</td>';
						html += '<td>'+n.owner+'</td>';
						html += '<td>'+n.clueState+'</td>';
						html += '</tr>';
					})
					$("#tbody").html(html);
					
					$("#qxbox").prop("checked",false)
					//分页
					var totalRows = data.total;
					var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; 
					$("#activityPagination").bs_pagination({
						  currentPage: pageNo, //页码pageNo
						  rowsPerPage: pageSize, //每页显示多少条记录 pageSize
						  totalPages: totalPages, //总页数
						  totalRows: totalRows, //总记录条数
						  visiblePageLinks: 2, //显示的卡片个数
						  showGoToPage: true,
						  showRowsPerPage: true,
						  showRowsInfo: true,
						  onChangePage : function(event , n){
							  $("#sel-name").val($("#h-name").val());
							  $("#sel-compangy").val($("#h-compangy").val());
							  $("#sel-telephone").val($("#h-telephone").val());
							  $("#sel-source").val($("#h-source").val());
							  $("#sel-owner").val($("#h-owner").val());
							  $("#sel-phone").val($("#h-phone").val());
							  $("#sel-clueState").val($("#h-clueState").val());
							  displayClue(n.currentPage , n.rowsPerPage);
						  } 
					});
			}
		})
	}
	
</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueOwner">
								 
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call">
								  <option></option>
								 <c:forEach items="${appellation}" var="a">
								 	<option value="${a.value}">${a.text}</option>
								 </c:forEach>
								</select>
							</div>
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-status">
								  <option></option>
								  <c:forEach items="${clueState}" var="a">
								  	<option value ="${a.value}">${a.text}</option>
								  </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
								  <c:forEach items="${source}" var="s">
								  	<option value="${s.value}">${s.text}</option>
								  </c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
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
									<input type="text" class="form-control time" id="create-nextContactTime">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveClueBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueOwner">
								  
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call">
									 <option></option>
									 <c:forEach items="${appellation}" var="a">
								 		<option value="${a.value}">${a.text}</option>
								 	 </c:forEach>
								</select>
							</div>
							<label for="edit-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-surname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" >
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" >
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status">
								 	 <option></option>
								  	<c:forEach items="${clueState}" var="a">
								  		<option value ="${a.value}">${a.text}</option>
								 	 </c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								 	<option></option>
								  	<c:forEach items="${source}" var="s">
								  		<option value="${s.value}">${s.text}</option>
								  	</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
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
	
	
	<!-- 导入线索的模态窗口 -->
	<div class="modal fade" id="importClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">导入线索</h4>
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
					<button type="button" class="btn btn-primary" id= "importClue">导入</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	<input type="hidden" id="h-name">
	<input type="hidden" id="h-company">
	<input type="hidden" id="h-telephone">
	<input type="hidden" id="h-source">
	<input type="hidden" id="h-owner">
	<input type="hidden" id="h-phone">
	<input type="hidden" id="h-clueState">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="sel-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="sel-company">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="sel-telephone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="sel-source">
					  	  <option></option>
					  	  <c:forEach items="${source}" var="s">
					  	  	<option value="${s.value}">${s.text}</option>
					  	  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="sel-owner">
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="sel-clueState">
					  	<option></option>
					  	<c:forEach items="${clueState}" var="cl">
					  		<option value="${cl.value}">${cl.text}</option>
					  	</c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="selBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="creatClueBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editClueBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delClueBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importClueModal"><span class="glyphicon glyphicon-import"></span> 导入</button>
				  <button type="button" class="btn btn-default" id="exportAll"><span class="glyphicon glyphicon-export"></span> 导出</button>
				</div>
				
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qxbox"/></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="tbody">
						<!-- <tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/clue/detail.jsp';">李四先生</a></td>
							<td>动力节点</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/clue/detail.jsp';">李四先生</a></td>
                            <td>动力节点</td>
                            <td>010-84846003</td>
                            <td>12345678901</td>
                            <td>广告</td>
                            <td>zhangsan</td>
                            <td>已联系</td>
                        </tr> -->
					</tbody>
				</table>
				<br>
			</div>
			<br>
			 <div id="activityPagination"></div>
		</div>
		
	</div>
</body>
</html>