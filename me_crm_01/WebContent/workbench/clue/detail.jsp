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

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		
		
		//调用备注显示方法
		displayRemark();
		
		//時間插件
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
		//调用市场活动方法
		displayActivity()
		
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
		
		$("#remarkFather").on("mouseover",".remarkDiv",function(){
			$(this).children("div").children("div").show();
		});
		
		$("#remarkFather").on("mouseout",".remarkDiv",function(){
			$(this).children("div").children("div").hide();
		});
		
		$("#remarkFather").on("mouseover",".myHref",function(){
			$(this).children("span").css("color","red");
		});
		
		$("#remarkFather").on("mouseout",".myHref",function(){
			$(this).children("span").css("color","#E6E6E6");
		});
		
		
		//保存备注
		$("#saveRemarkBtn").click(function(){
			
			$.ajax({
				type : "get",
				url : "workbench/clue/saveRemark.do",
				data : {
					"noteContent" : $("#remark").val(),
					"clueId" : "${clue.id}"
				},
				beforeSend : function(){
					if($("#remark").val() == ""){
						alert("备注内容不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#remark").val("");
						var html = '';
						
						html += '<div class="remarkDiv" style="height: 60px;">';
						html += '<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
						html += '<div style="position: relative; top: -40px; left: 40px;" >';
						html += '<h5 id="n-'+data.clueRemark.id+'">'+data.clueRemark.noteContent+'</h5>';
						html += '<font color="gray">线索</font> <font color="gray">-</font> <b>'+"${clue.name}${clue.appellation}"+'-'+"${clue.company}"+'</b> <small id="s-'+data.clueRemark.id+'" style="color: gray;"> '+(data.clueRemark.editFlag == 1 ? data.clueRemark.editTime : data.clueRemark.creatTime)+' 由'+(data.clueRemark.editFlag == 1 ? data.clueRemark.editBy : data.clueRemark.creatBy)+'</small>';
						html += '<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
						html += '<a class="myHref" href="javascript:void(0);" onclick="editClueRemark(\''+data.clueRemark.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>';
						html += '&nbsp;&nbsp;&nbsp;&nbsp;';
						html += '<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+data.clueRemark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>';
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
		
		 $("#bundActivity").click(function(){
			$("#ActivityRelationtbody").html("");
			$(":checkbox").prop("checked",false);
			$("#activityByClueId").val("");
			$("#bundModal").modal("show");
			
		}) 
		$("#activityByClueId").keydown(function(event){
			if(event.keyCode == 13){
				 $.ajax({
					type : "get",
					url : "workbench/clue/getByClueId.do",
					data : {
						"clueId" : "${clue.id}",
						"name" : $("#activityByClueId").val()
					},
					success : function(data){
						var html ='';
						$.each(data,function(i,n){
							html += '<tr>';
							html += '<td><input type="checkbox" name="box" value="'+n.id+'"/></td>';
							html += '<td>'+n.name+'</td>';
							html += '<td>'+n.startTime+'</td>';
							html += '<td>'+n.endTime+'</td>';
							html += '<td>'+n.owner+'</td>';
							html += '</tr>';
						})
						$("#ActivityRelationtbody").html(html);
					}
				
				});
				 return false;
			}
		
		})
		
		//全选
		$("#qxbox").click(function(){
			$(":checkbox[name='box']").prop("checked",this.checked);
		});
		$("#ActivityRelationtbody").on("click",":checkbox[name='box']",function(){
			$("#qxbox").prop("checked",$(":checkbox[name='box']").size() ==$(":checkbox[name='box']:checked").size());
		})
		
		
		//关联市场活动
		
		$("#bundActivityByClueId").click(function(){
			  var sendData = "clueId=${clue.id}";
			  $.each($(":checkbox[name=box]:checked"),function(i,n){
				  sendData += "&activityId=" + n.value;
			  }); 
			  $.ajax({
				  type : "post",
				  url : "workbench/clue/RelationByActivityIdAndClueId.do",
				  data : sendData,
				  beforeSend : function(){
					  if($(":checkbox[name=box]:checked").size()==0){
						  alert("请至少选择一条记录进行关联");
						  return false;
					  }
					  return true;
				  },
			 	 success : function(data){
			 		 if(data.success){
			 			$("#bundModal").modal("hide");
			 			displayActivity();
			 		 }else{
			 			 alert("关联失败");
			 		 }
			 	 }
			  }) 
		})
		//解除绑定按钮
		$("#sureBtn").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/unbundActivity.do",
				data : {
					"id" : $("#h-unbund").val()
				},
				success : function(data){
					if(data.success){
						$("#unbundModal").modal("hide");
						$("#" + $("#h-unbund").val()).remove();
						//displayActivity();
					}else{
						alert("解绑失败");
					}
				}
			})
		})
		
		//更新备注信息
		$("#updateClueRemark").click(function(){
			$.ajax({
				type : "post",
				url : "workbench/clue/updateClueRemark.do",
				data : {
					"id" :  $("#editRemarkHidden").val(),
					"noteContent" : $("#edit-remark").val(),
					"_" : new Date().getTime()
				},
				beforeSend : function(){
					if($("#edit-remark").val() == ""){
						alert("备注信息不能为空");
						return false;
					}
					return true;
				},
				success : function(data){
					if(data.success){
						$("#editRemarkModal").modal("hide");
						$("#n-" + $("#editRemarkHidden").val()).html($("#edit-remark").val());
						$("#s-" + $("#editRemarkHidden").val()).html(data.clueRemark.editTime +' 由'+data.clueRemark.editBy);
					}else{
						alert("更新失败");
					}	
				}
			})
		})
	
		//编辑信息获取
		$("#editClue").click(function(){
			$.ajax({
				type : "get",
				url : "workbench/clue/getUser.do",
				success : function(data){
					var html = '<option></option>';
					 $(data).each(function(i){
						html += '<option value="'+this.id+'" '+(this.id == "${user.id}" ? "selected" : "")+'>'+this.name+'</option>';
					}) 
					$("#edit-clueOwner").html(html);
					 
					$("#edit-company").val("${clue.company}");
					$("#edit-call").val("${clue.appellation}");
					$("#edit-surname").val("${clue.name}");
					$("#edit-job").val("${clue.position}");
					$("#edit-email").val("${clue.email}");
					$("#edit-phone").val("${clue.telephone}");
					$("#edit-website").val("${clue.web}");
					$("#edit-mphone").val("${clue.phone}");
					$("#edit-status").val("${clue.clueState}");
					$("#edit-source").val("${clue.clueFrom}");
					$("#edit-describe").val("${clue.clueDesc}");
					$("#edit-contactSummary").val("${clue.relationSummary}");
					$("#edit-nextContactTime").val("${clue.relationTimeNext}");
					$("#edit-address").val("${clue.address}");
					 
				}
				
			})
			$("#editClueModal").modal("show");
		})
		
		
		//更新线索信息
		$("#detail-updateClue").click(function(){
			$.ajax({
				type : "get",
				url : "workbench/clue/updateClueById.do",
				data : {
					"id" : "${clue.id}",
					"owner" : $("#edit-clueOwner").val(),
					"company" : $("#edit-company").val(),
					"appellation" : $("#edit-call").val(),
					"name" : $("#edit-surname").val(),
					"position" : $("#edit-job").val(),
					"email" : $("#edit-email").val(),
					"telephone" : $("#edit-phone").val(),
					"web" : $("#edit-website").val(),
					"phone" : $("#edit-mphone").val(),
					"clueState" : $("#edit-status").val(),
					"clueFrom" : $("#edit-source").val(),
					"clueDesc" : $("#edit-describe").val(),
					"relationSummary" : $("#edit-contactSummary").val(),
					"relationTimeNext" : $("#edit-nextContactTime").val(),
					"address" : $("#edit-address").val()
				},
				success : function(data){
					if(data.success){
						$("#u-title").html("&nbsp;" +$("#edit-surname").val() +$("#edit-call").val()+ "<small>" +$("#edit-company").val() +"</small>");
						$("#u-name").html("&nbsp;" +$("#edit-surname").val() +$("#edit-call").val());
						$("#u-owner").html($("#edit-clueOwner").val());
						$("#u-company").html("&nbsp;"  +$("#edit-company").val());
						$("#u-position").html("&nbsp;"+ $("#edit-job").val());
						$("#u-email").html("&nbsp;" + $("#edit-email").val());
						$("#u-telephone").html("&nbsp;" +$("#edit-phone").val());
						$("#u-web").html("&nbsp;"  +$("#edit-website").val());
						$("#u-phone").html($("#edit-mphone").val());
						$("#u-clueState").html("&nbsp;" +$("#edit-status").val());
						$("#u-clueFrom").html("&nbsp;" +$("#edit-source").val());
						$("#u-editBy").html("${user.name}");
						$("#u-editTime").html("&nbsp;" +data.clue.editTime);
						$("#u-clueDesc").html("&nbsp;" +$("#edit-describe").val());
						$("#u-relationSummary").html("&nbsp;" +$("#edit-contactSummary").val());
						$("#u-relationTimeNext").html("&nbsp;" +$("#edit-nextContactTime").val());
						$("#u-address").html("&nbsp;" +$("#edit-address").val());
						
						$("#editClueModal").modal("hide");
					}else{
						alert("更新失败");
					}
				}
				
			})
			
		})
		
		//删除信息
		$("#deleteClue").click(function(){
			if(window.confirm("您确认删除吗？")){
				$.ajax({
					type : "post",
					url : "workbench/clue/deleteClueAndRemarkAndRelationById.do",
					data : {
						id : "${clue.id}"
					},
					success : function(data){
						if(data.success){
							window.location.href="workbench/clue/index.jsp";
						}else{
							alert("删除失败");
						}
					}
				})
			}
		})
		
		
	});
	
	//删除备注
	function deleteRemark(id){
		if(window.confirm("您确认删除吗?")){
			$.ajax({
				type : "post",
				url : "workbench/clue/deleteRemarkById.do",
				data : {"id" : id },
				success : function(data){
					if(data.success){
						$("#d-"+ id).remove();
					}else{
						alert("更新失败");
					}
				}
			})
		}
	}
	
	//编辑备注
	function editClueRemark(id){
		$("#editRemarkModal").modal("show");
		$("#editRemarkHidden").val(id);
		$("#edit-remark").val($("#n-" + id).html())
	}
	
	//线索备注方法显示
	function displayRemark(){
		$.ajax({
			type : "get",
			url : "workbench/clue/displayRemark.do",
			success : function(data){
				$.each(data,function(i,n){
					var html = '';
					
					html += '<div id="d-'+n.id+'" class="remarkDiv" style="height: 60px;">';
					html += '<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
					html += '<div style="position: relative; top: -40px; left: 40px;" >';
					html += '<h5 id="n-'+n.id+'">'+n.noteContent+'</h5>';
					html += '<font color="gray">线索</font> <font color="gray">-</font> <b>'+"${clue.name}${clue.appellation}"+'-'+"${clue.company}"+'</b> <small id="s-'+n.id+'" style="color: gray;"> '+(n.editFlag == 1 ? n.editTime : n.creatTime)+' 由'+(n.editFlag == 1 ? n.editBy : n.creatBy)+'</small>';
					html += '<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
					html += '<a class="myHref" href="javascript:void(0);" onclick="editClueRemark(\''+n.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>';
					html += '&nbsp;&nbsp;&nbsp;&nbsp;';
					html += '<a class="myHref" href="javascript:void(0);" onclick="deleteRemark(\''+n.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>';
					html += '</div>';
					html += '</div>';
					html += '</div>';
					
					$("#remarkDiv").before(html);
				})
			}
		})
	}
	
	//市场活动列表
	function displayActivity(){
		$.ajax({
			type : "get",
			url : "workbench/clue/relationList.do",
			data : {
				"clueId" : "${clue.id}"
			},
			success : function(data){
				
				var html = '';
				$.each(data,function(i,n){
					
					html+='	<tr id="'+n.id+'">';
					html+='	<td>'+n.name+'</td>';
					html+='<td>'+n.startTime+'</td>';
					html+='<td>'+n.endTime+'</td>';
					html+='<td>'+n.owner+'</td>';
					html+='<td><a href="javascript:void(0);" onclick="unbandRelation(\''+n.id+'\')" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>';
					html+='</tr>';
					
				})
					$("#tbody").html(html);
				
			}
		})
	
		
	}
	
	function unbandRelation(id){
		$("#h-unbund").val(id);
		$("#unbundModal").modal("show");
	}
	
</script>

</head>
<body>

	<!-- 解除关联的模态窗口 -->
	<div class="modal fade" id="unbundModal" role="dialog">
		<input type="hidden" id="h-unbund">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">解除关联</h4>
				</div>
				<div class="modal-body">
					<p>您确定要解除该关联关系吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" id="sureBtn">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 关联市场活动的模态窗口 -->
	<div class="modal fade" id="bundModal" role="dialog">
		
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">关联市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="activityByClueId" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead >
							<tr style="color: #B3B3B3;">
								<td><input type="checkbox" id="qxbox"/></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="ActivityRelationtbody">
							<!-- <tr>
								<td><input type="checkbox"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="checkbox"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr> -->
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="bundActivityByClueId">关联</button>
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
                    <h4 class="modal-title" id="myModalLabel">修改线索</h4>
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
                                <input type="text" class="form-control" id="edit-company">
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
                                <input type="text" class="form-control" id="edit-job">
                            </div>
                            <label for="edit-email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-phone" >
                            </div>
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-mphone" class="col-sm-2 control-label">手机</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-mphone" >
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
                                <textarea class="form-control" rows="3" id="edit-describe"></textarea>
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
                                    <input type="text" class="form-control time" id="edit-nextContactTime" >
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address"></textarea>
                                </div>
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="detail-updateClue">更新</button>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 编辑备注模态 -->
    <div class="modal fade" id="editRemarkModal" role="dialog">
    	<input type="hidden" id="editRemarkHidden"> 
        <div class="modal-dialog" role="document" style="width: 90%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                               <textarea id="edit-remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
                            </div>
                        </div>
                        
                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateClueRemark">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3 id="u-title">${clue.name}${clue.appellation}<small>${clue.company}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 500px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default"><span class="glyphicon glyphicon-envelope"></span> 发送邮件</button>
			<button type="button" class="btn btn-default" onclick="window.location.href='workbench/clue/convert.jsp?clueId=${clue.id}&name=${clue.name}&appellation=${clue.appellation}&owner=${clue.owner}&company=${clue.company}';"><span class="glyphicon glyphicon-retweet"></span> 转换</button>
			<button type="button" class="btn btn-default" id="editClue"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger" id="deleteClue"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="u-name">${clue.name}${clue.appellation}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="u-owner">${clue.owner}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">公司</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="u-company">${clue.company}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">职位</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="u-position">${clue.position}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">邮箱</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="u-email">${clue.email}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">公司座机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="u-telephone">${clue.telephone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">公司网站</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="u-web">${clue.web}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">手机</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="u-phone">${clue.phone}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">线索状态</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="u-clueState">${clue.clueState}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">线索来源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b id="u-clueFrom">${clue.clueFrom}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b >${clue.creatBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${clue.creatTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="u-editBy">${clue.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;" id="u-editTime">${clue.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b id="u-clueDesc">
					${clue.clueDesc}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b id="u-relationSummary">
					${clue.relationSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b id="u-relationTimeNext">${clue.relationTimeNext}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px; "></div>
		</div>
        <div style="position: relative; left: 40px; height: 30px; top: 100px;">
            <div style="width: 300px; color: gray;">详细地址</div>
            <div style="width: 630px;position: relative; left: 200px; top: -20px;">
                <b id="u-address">
                    ${clue.address}
                </b>
            </div>
            <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
        </div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 40px; left: 40px;" id="remarkFather">
		<div class="page-header">
			<h4>备注</h4>
		</div>
	<!-- 	
		备注1
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-动力节点</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
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
				<font color="gray">线索</font> <font color="gray">-</font> <b>李四先生-动力节点</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div> -->
		
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
	
	<!-- 市场活动 -->
	<div>
		<div style="position: relative; top: 60px; left: 40px;">
			<div class="page-header">
				<h4>市场活动</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>名称</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>所有者</td>
							<td></td>
						</tr>
					</thead>
					<tbody id="tbody">
				<!-- 		<tr>
							<td>发传单</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
							<td>zhangsan</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#unbundModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
						</tr>
						<tr>
							<td>发传单</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
							<td>zhangsan</td>
							<td><a href="javascript:void(0);" data-toggle="modal" data-target="#unbundModal" style="text-decoration: none;"><span class="glyphicon glyphicon-remove"></span>解除关联</a></td>
						</tr> -->
					</tbody>
				</table>
			</div>
			
			<div>
				<a href="javascript:void(0);" id="bundActivity" style="text-decoration: none;"><span class="glyphicon glyphicon-plus"></span>关联市场活动</a>
			</div>
		</div>
	</div>
	
	
	<div style="height: 200px;"></div>
</body>
</html>