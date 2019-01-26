<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<script type="text/javascript">
	$(function(){
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
			clearBtn : true
		})
		
		$("#updateBtn").click(function(){
			if($("#edit-owner").val()==""){
				alert("所有者不能为空");
			}else if($.trim($("#edit-owner").val())==""){
				alert("名称不能为空");
			}else if($("#edit-expectedTime").val()==""){
				alert("预计成交日期不能为空");
			}else if($.trim($("#edit-customerName").val())==""){
				alert("客户名称不能为空");
			}else if($("#edit-stage").val()==""){
				alert("阶段不能为空");
			}else{
				$("#updateform").submit();
			}
		})
		
		$("#findContactsText").keydown(function(event){
			if(event.keyCode == 13){
				$.ajax({
					type : "get",
					url : "workbench/transaction/getContactsByName.do",
					cache : false ,
					data : {
						"name" : $.trim($("#findContactsText").val())
					},
					success : function(data){
						var html ='';
						$.each(data,function(i,n){
							html += '<tr>';
							html += '<td><input type="radio" name="activity" onclick="getUer(\''+n.id+'\',\''+n.name+'\')"/></td>';
							html += '<td>'+n.name+'</td>';
							html += '<td>'+n.email+'</td>';
							html += '<td>'+n.phone+'</td>';
							html += '</tr>';
						})
						
						$("#sure-body").html(html);
					}
				})
				
				return false;
			} 
		})
		
		$("#findActivity").keydown(function(event){
			if(event.keyCode == 13){
				$.ajax({
					type : "get",
					url : "workbench/transaction/getActivityByName.do",
					cache : false ,
					data : {
						"name" : $.trim($("#findActivity").val())
					},
					success : function(data){
						var html ='';
						$.each(data,function(i,n){
							html += '<tr>';
							html += '<td><input type="radio" name="activity" onclick="getActivity(\''+n.id+'\',\''+n.name+'\')"/></td>';
							html += '<td>'+n.name+'</td>';
							html += '<td>'+n.startTime+'</td>';
							html += '<td>'+n.endTime+'</td>';
							html += '<td>'+n.owner+'</td>';
							html += '</tr>';
						})
						
						$("#a-body").html(html);
					}
				})
				
				return false;
			}
		})
		
	})
	
	function getUer(id,name){
		$("#edit-contactsId2").val(id);
		$("#edit-contactsId1").val(name);
		$("#findContacts").modal("hide");
	}
	function getActivity(id,name){
		$("#edit-activityId2").val(id);
		$("#edit-activityId1").val(name);
		$("#findMarketActivity").modal("hide");
	}
</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="findActivity" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable4" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="a-body">
							<!-- <tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="findContactsText" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="sure-body">
							<!-- <tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>更新交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" action="workbench/transaction/updateTransaction.do" role="form" style="position: relative; top: -30px;" id="updateform">
		<div class="form-group">
			<input type="hidden" name="edit-id" value="${transaction.id}">
			<label for="edit-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="edit-owner" name="edit-owner">
				 <c:forEach items="${userList}" var="a">
			  		<option value="${a.id}" ${a.name eq transaction.owner ? "selected" : ""}>${a.name}</option>
			  	</c:forEach>
				</select>
			</div>
			<label for="edit-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-money" name="edit-money" value="${transaction.money}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-name"  name="edit-name" value="${transaction.name}">
			</div>
			<label for="edit-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time1" id="edit-expectedTime" name="edit-expectedTime" value="${transaction.expectedTime}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-customerName" name="edit-customerName" value="${transaction.customerId}" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="edit-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="edit-stage" name="stage">
			  	<option></option>
			  	<c:forEach items="${stage}" var="stage">
			  	<option value="${stage.value}" ${stage.value eq transaction.stage ? "selected" : ""}>${stage.text}</option>
			  	</c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="edit-type" name="edit-type">
				  <option></option>
				  <c:forEach items="${transactionType}" var="type">
			  	<option value="${type.value}" ${type.value eq transaction.type ? "selected" : ""}>${type.text}</option>
			  	</c:forEach>
				</select>
			</div>
			<label for="edit-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-possibility" name="edit-possiblity" value="${transaction.possiblity}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="edit-source" name="edit-source">
				  <option></option>
				  <c:forEach items="${source}" var="source">
			  	<option value="${source.value}" ${source.value eq transaction.source ? "selected" : ""}>${source.text}</option>
			  	</c:forEach>
				</select>
			</div>
			<label for="edit-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-activityId1" value="${transaction.activityId}">
				<input type="hidden" id="edit-activityId2"  name="edit-activityId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="edit-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="edit-contactsId1" value="${transaction.contactsId}">
				<input type="hidden" id="edit-contactsId2"  name="edit-contactsId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="edit-description" name="edit-description" value="${transaction.description}">
					${transaction.description}
				</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="edit-contactSummary" name="edit-contactSummary" value="${transaction.contactSummary}">${transaction.contactSummary}
				</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="edit-relationTimeNext" name="edit-relationTimeNext" value="${transaction.relationTimeNext}">
			</div>
		</div>
		
	</form>
</body>
</html>