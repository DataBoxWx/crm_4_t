<%@page import="java.util.Map,java.util.Set"%>
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
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

<script type="text/javascript">
	var possibilityJson = {
		<%	Map<String,String> map = (Map<String,String>)application.getAttribute("possibly");
			Set<String> keys =map.keySet();
			int count = 0;
			for(String key : keys){
				String value = map.get(key);
				if(count <keys.size()-1){
		%>
			"<%=key%>" : <%=value %>,
		<% 
				}else{
		%>
			"<%=key%>" : <%=value %>
		<% 
				}
				count ++;
			}
		%>
	}
	$(function(){
		$(".time1").datetimepicker({
			language : 'zh-CN',//显示中文
			format : 'yyyy-mm-dd',//显示格式
			initialDate : new Date(),//初始化当前日期
			minView:"month",
			autoclose : true,//选中自动关闭
			todayBtn : true,//显示今日按钮
			clearBtn : true,
			pickerPosition : "top-right"
		})
		$(".time").datetimepicker({
			language : 'zh-CN',//显示中文
			format : 'yyyy-mm-dd',//显示格式
			initialDate : new Date(),//初始化当前日期
			minView:"month",
			autoclose : true,//选中自动关闭
			todayBtn : true,//显示今日按钮
			clearBtn : true,
		})
		
		
		$("#create-stage").change(function(){
			$("#create-possibility").val(possibilityJson[this.value]);		
		})
		
		$('#create-customerName').typeahead({
			source : function(query , process){
				$.post(
						"workbench/customer/getByName.do",
						{"name" : query},
						function(json){
							process(json);
						}
				);
			},
			delay : 500,
			items : 8
		});
		
		$("#s-activity").keydown(function(event){
			if(event.keyCode == 13){
				$.ajax({
					type : "get",
					url : "workbench/transaction/getActivityByName.do",
					cache : false ,
					data : {
						"name" : $.trim($("#s-activity").val())
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
		
		$("#s-contactName").keydown(function(event){
			if(event.keyCode == 13){
				$.ajax({
					type : "get",
					url : "workbench/transaction/getContactsByName.do",
					cache : false ,
					data : {
						"name" : $.trim($("#s-contactName").val())
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
						
						$("#user-body").html(html);
					}
				})
				
				return false;
			}
		})
		$("#saveBtn").click(function(){
			if($("#creat-owner").val()==""){
				alert("所有者不能为空");
			}else if($.trim($("#create-name").val())==""){
				alert("名称不能为空");
			}else if($("#create-expectedTime").val()==""){
				alert("预计成交日期不能为空");
			}else if($.trim($("#create-customerName").val())==""){
				alert("客户名称不能为空");
			}else if($("#create-stage").val()==""){
				alert("阶段不能为空");
			}else{
				$("#submitForm").submit();
			}
		})
		
	})
	
	function getUer(id,name){
		$("#create-contactsId").val(name);
		$("#h-contactsName").val(id);
		$("#findContacts").modal("hide");
	}
	
	function getActivity(id,name){
		$("#create-activityId").val(name);
		$("#h-activityId").val(id);
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
						    <input type="text" class="form-control" id="s-activity" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
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
						    <input type="text" class="form-control" id="s-contactName" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
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
						<tbody id="user-body">
							<tr>
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
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" id="submitForm" action="workbench/transaction/saveTransaction.do" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-owner" name="create-owner">
				  <c:forEach items="${userList}" var="u">
				   <option value="${u.id}" ${u.id eq user.id ? "selected" : ""}>${u.name}</option>
				  </c:forEach>
				 
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-money" name="create-money">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-name" name="create-name">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="create-expectedTime" name="create-expectedTime">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" name="create-customerName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-stage" name="create-stage">
			  	<option></option>
			  	<c:forEach items="${stage}" var="stage">
			  		<option value="${stage.value}">${stage.text}</option>
			  	</c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-type" name="create-type">
				  <option></option>
				  <c:forEach items="${transactionType}" var="type">
			  		<option value="${type.value}">${type.text}</option>
			  	</c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility" name="create-possibility">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-source" name="create-source">
				  <option></option>
				  <c:forEach items="${source}" var="source">
			  		<option value="${source.value}">${source.text}</option>
			  	</c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activityId">
				<input type="hidden" name="create-activityId" id="h-activityId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-contactsId">
				<input type="hidden" id="h-contactsName" name="create-contactsId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-description" name="create-description"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary" name="create-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time1" id="create-relationTimeNext" name="create-relationTimeNext">
			</div>
		</div>
		
	</form>
</body>
</html>