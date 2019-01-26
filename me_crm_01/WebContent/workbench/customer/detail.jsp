<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		displayRemark();
		displayTransaction();
		displayContacts();
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$("#showMourse").on("mouseover",".remarkDiv",function(){
			$(this).children("div").children("div").show();
		});
		
		$("#showMourse").on("mouseout",".remarkDiv",function(){
			$(this).children("div").children("div").hide();
		});
		
		$("#showMourse").on("mouseover",".myHref",function(){
			$(this).children("span").css("color","red");
		});
		
		$("#showMourse").on("mouseout",".myHref",function(){
			$(this).children("span").css("color","#E6E6E6");
		});
		
		//保存备注
		$("#saveRemarkBtn").click(function(){
			$.ajax({
				type : "get",
				url : "workbench/customer/saveRemark.do",
				cache : false,
				data : {
					"customerId" : "${customer.id}",
					"noteContent" : $.trim($("#remark").val())
				},
				beforeSend : function(){
					if($.trim($("#remark").val())==""){
						alert("备注内容不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						var html = '';
						
						$("#remark").val("");
						
						html += '<div id="div-'+data.customerRemark.id+'" class="remarkDiv" style="height: 60px;">';
						html += '<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
						html += '<div style="position: relative; top: -40px; left: 40px;" >';
						html += '<h5 id="note'+data.customerRemark.id+'">'+data.customerRemark.noteContent+'</h5>';
						html += '<font color="gray">联系人</font> <font color="gray">-</font> <b>'+data.customerRemark.name+data.customerRemark.appellation+'-'+data.customerRemark.company+'</b> <small style="color: gray;" id="small-'+data.customerRemark.id+'"> '+data.customerRemark.creatTime+' 由'+data.customerRemark.creatBy+'</small>';
						html += '<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
						html += '<a class="myHref" href="javascript:void(0);" onclick="getRemarkId(\''+data.customerRemark.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>';
						html += '&nbsp;&nbsp;&nbsp;&nbsp;';
						html += '<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+data.customerRemark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>';
						html += '</div>';
						html += '</div>';
						html += '</div>';
						
						$("#remarkDiv").before(html);
					}else{
						alert("保存失败");
					}
				}
			})
		})
		
		//点击编辑获取信息
		$("#getMsgBtn").click(function(){
			$.ajax({
				type : "get",
				url : "settings/qx/user/list.do",
				success : function(data){
					var html = '<option></option>';
					$.each(data,function(i,n){
						html += '<option value="'+n.id+'"'+(n.name == "${customer.owner}" ? "selected" : "") +'>'+n.name+'</option>'
					})
					$("#edit-owner").html(html);
				}
			})
			$("#edit-name").val("${customer.name}");
			$("#edit-website").val("${customer.website}");
			$("#edit-telephone").val("${customer.telephone}");
			$("#edit-description").val("${customer.description}");
			$("#edit-contactSummary").val("${customer.contactSummary}");
			$("#edit-relationTimeNext").val("${customer.relationTimeNext}");
			$("#edit-address").val("${customer.address}");
			$("#editCustomerModal").modal("show");
		})
		
		//更新客户
		$("#updateCustomerBtn").click(function(){
			$.ajax({
				type : "get",
				url : "workbench/customer/updateCustomer.do",
				data : {
					"id" : "${customer.id}",
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
						window.location.reload();
						$("#editCustomerModal").modal("hide");
					}else{
						alert("更新失败");
					}
				}
			})
		})
		
		$("#updateRemarkBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/customer/updateRemark.do",
				data : {
					"id" : $("#h-editRemark").val(),
					"noteContent" : $("#edit-noteContentRemark").val()
				},
				beforeSend : function(){
					if($("#edit-noteContentRemark").val() == ""){
						alert("备注内容不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#small-" + $("#h-editRemark").val()).html(data.customerRemark.editTime + '由' + data.customerRemark.editBy);
						$("#note-" + $("#h-editRemark").val()).html(data.customerRemark.noteContent);
						$("#editRemarkModal").modal("hide");
					}else{
						alert("更新备注失败");
					}
				}
			})
		})
		
		
		$("#saveModal").click(function(){
			$.ajax({
				type : "get",
				url : "settings/qx/user/list.do",
				success : function(data){
					var html = '<option></option>';
					$.each(data,function(i,n){
						html += '<option value="'+n.id+'" '+(n.id == "${user.id}" ? "selected" : "")+'>'+n.name+'</option>'
					});
					$("#create-owner").html(html);
					$("#create-customerName").val("${customer.name}");
					
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
						displayContacts();
					}else{
						alert("保存失败");
					}
				}
			})
		})
		
		//删除联系人
		$("#deleteContactsBtn").click(function(){
			$.post(
				"workbench/custoemr/deleteContacts.do",
				{"id" : $("#h-deleteContactsId").val()},
				function(data){
					if(data.success){
						$("#removeContactsModal").modal("hide");
						displayContacts();
					}else{
						alert("删除失败");
					}
				}
			)
			
		})
		
		$("#deleteTranBtn").click(function(){
			$.post(
				"workbench/customer/deleteTran.do",
				{"id" : $("#h-deleteTranId").val()},
				function(data){
					if(data.success){
						$("#removeTransactionModal").modal("hide");
						displayTransaction();
					}else{
						alert("删除失败");
					}
				}
			)
		})
		
	});
	
	//
	function getRemarkId(id){
		$("#h-editRemark").val(id);
		$("#edit-noteContentRemark").val($("#note-" + id).html());
		$("#editRemarkModal").modal("show");
	}
	
	
	//备注显示
	function displayRemark(){
		$.ajax({
			type : "get",
			url : "workbench/customer/displayRemark.do",
			data : {
				"customerId" :"${customer.id}"
			},
			success : function(data){
				var html ='';
				$.each(data,function(i,n){
					html += '<div id="div-'+n.id+'" class="remarkDiv" style="height: 60px;">';
					html += '<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
					html += '<div style="position: relative; top: -40px; left: 40px;" >';
					html += '<h5 id="note-'+n.id+'">'+n.noteContent+'</h5>';
					html += '<font color="gray">联系人</font> <font color="gray">-</font> <b>'+n.name+n.appellation+'-'+n.company+'</b> <small style="color: gray;" id="small-'+n.id+'"> '+(n.editFlag == 1 ? n.editTime : n.creatTime)+' 由'+(n.editFlag == 1 ? n.editBy : n.creatBy)+'</small>';
					html += '<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
					html += '<a class="myHref" href="javascript:void(0);" onclick="getRemarkId(\''+n.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>';
					html += '&nbsp;&nbsp;&nbsp;&nbsp;';
					html += '<a class="myHref" href="javascript:void(0);" onclick="(deleteRemark(\''+n.id+'\'))"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
				})
				$("#remarkDiv").before(html);
			}
		})
	}
	
	function deleteRemark(id){
		if(window.confirm("您确认删除吗？")){
			$.post(
				"workbench/customer/deleteRemark.do",
				{
					"id" : id
				},
				function(data){
					if(data.success){
						$("#div-" + id).remove();
					}else{
						alert("删除失败");
					}
				}
			)
		}
		
	}
	
	function displayTransaction(){
		$.get(
			"workbench/customer/getTransactionBycustomerId.do",
			{
				"customerId" : "${customer.id}"
			},
			function(data){
				var html ='';
				$.each(data,function(i,n){
					html += '<tr>';
					html += '<td><a href="workbench/transaction/detail.jsp" style="text-decoration: none;">'+n.name+'</a></td>';
					html += '<td>'+n.name+'</td>';
					html += '<td>'+n.stage+'</td>';
					html += '<td>'+n.possiblity+'</td>';
					html += '<td>'+n.expectedTime+'</td>';
					html += '<td>'+n.type+'</td>';
					html += '<td><a href="javascript:void(0);" onclick="deleteTran(\''+n.id+'\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>';
					html += '</tr>';
				})
				
				$("#tranTbody").html(html);
			}
		)
	}
	
	function deleteTran(id){
		$("#h-deleteTranId").val(id);
		$("#removeTransactionModal").modal("show");
	}
	
	
	function displayContacts(){
		$.get(
			"workbench/customer/displayContacts.do",
			{"customerId" : "${customer.id}"},
			function(data){
				var html ="";
				$.each(data,function(i,n){
					html += '<tr>';
					html += '<td><a href="workbench/contacts/detail.jsp" style="text-decoration: none;">'+n.name+'</a></td>';
					html += '<td>'+n.email+'</td>';
					html += '<td>'+n.phone+'</td>';
					html += '<td><a href="javascript:void(0);" onclick="deleteContacts(\''+n.id+'\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>';
					html += '</tr>';
				})
				$("#contactsTbody").html(html);
			}
		)
	}
	function deleteContacts(id){
		$("#h-deleteContactsId").val(id);
		$("#removeContactsModal").modal("show");
	}
</script>

</head>
<body>

	<!-- 删除联系人的模态窗口 -->
	<div class="modal fade" id="removeContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">删除联系人</h4>
				</div>
				<input type = "hidden" id="h-deleteContactsId">
				<div class="modal-body">
					<p>您确定要删除该联系人吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" id="deleteContactsBtn">删除</button>
				</div>
			</div>
		</div>
	</div>
    <!-- 删除交易的模态窗口 -->
    <div class="modal fade" id="removeTransactionModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 30%;">
            <div class="modal-content">
            <input type="hidden" id="h-deleteTranId">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">删除交易</h4>
                </div>
                <div class="modal-body">
                    <p>您确定要删除该交易吗？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-danger" id="deleteTranBtn">删除</button>
                </div>
            </div>
        </div>
    </div>
	<!-- 修改备注模态 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="$('#editRemarkModal').modal('hide');">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">修改备注</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address1" class="col-sm-2 control-label">内容</label>
                                <div class="col-sm-10" style="width: 81%;">
                                <input type="hidden" id="h-editRemark">
                                    <textarea class="form-control" rows="1" id="edit-noteContentRemark"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateRemarkBtn">保存</button>
				</div>
			</div>
		</div>
	</div>	
	

	
	<!-- 创建联系人的模态窗口 -->
	<div class="modal fade" id="createContactsModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="$('#createContactsModal').modal('hide');">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建联系人</h4>
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
								<input type="text" class="form-control" id="create-birthday">
							</div>
						</div>
						
						<div class="form-group" style="position: relative;">
							<label for="create-customerName" class="col-sm-2 control-label">客户名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName" readOnly placeholder="支持自动补全，输入客户不存在则新建">
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
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="creat-contactSummary">这个线索即将被转换</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="creat-relationTimeNext" value="2017-05-01">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="creat-address">北京大兴区大族企业湾</textarea>
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
                                    <input type="text" class="form-control" id="edit-relationTimeNext">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateCustomerBtn">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;" id="showCustomer">
		<div class="page-header">
			<h3 >${customer.name} <small><a href="${customer.website}" target="_blank">${customer.website}</a></small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" id="getMsgBtn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${customer.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">公司网站</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.website}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${customer.telephone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${customer.creatBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${customer.creatTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${customer.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${customer.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 40px;">
            <div style="width: 300px; color: gray;">联系纪要</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                   	${customer.contactSummary}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
        <div style="position: relative; left: 40px; height: 30px; top: 50px;">
            <div style="width: 300px; color: gray;">下次联系时间</div>
            <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${customer.relationTimeNext}</b></div>
            <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
        </div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${customer.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 70px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b>
                    	${customer.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 10px; left: 40px;" id="showMourse">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
	<!-- 	
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">联系人</font> <font color="gray">-</font> <b>李四先生-北京动力节点</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		备注2
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">联系人</font> <font color="gray">-</font> <b>李四先生-北京动力节点</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		 -->
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" id="saveRemarkBtn">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 交易 -->
	<div>
		<div style="position: relative; top: 20px; left: 40px;">
			<div class="page-header">
				<h4>交易</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable2" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>金额</td>
							<td>阶段</td>
							<td>可能性</td>
							<td>预计成交日期</td>
							<td>类型</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="tranTbody">
						<!-- <tr>
							<td><a href="workbench/transaction/detail.jsp" style="text-decoration: none;">动力节点-交易01</a></td>
							<td>5,000</td>
							<td>谈判/复审</td>
							<td>90</td>
							<td>2017-02-07</td>
							<td>新业务</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#removeTransactionModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="workbench/transaction/save.do" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>新建交易</a>
			</div>
		</div>
	</div>
	
	<!-- 联系人 -->
	<div>
		<div style="position: relative; top: 20px; left: 40px;">
			<div class="page-header">
				<h4>联系人</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>邮箱</td>
							<td>手机</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="contactsTbody">
						<!-- <tr>
							<td><a href="contacts/detail.html" style="text-decoration: none;">李四</a></td>
							<td>lisi@bjpowernode.com</td>
							<td>13543645364</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#removeContactsModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>删除</a></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0);" id="saveModal" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>新建联系人</a>
			</div>
		</div>
	</div>
	
	<div style="height: 200px;"></div>
</body>
</html>