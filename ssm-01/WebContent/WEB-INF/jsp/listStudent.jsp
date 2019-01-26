<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.1-min.js"></script>
<script type="text/javascript">
  $(function(){
	  loadStudent();
  })

  function loadStudent(){
	  $.ajax({
		  url:"/ssm-01/student/listStudent.do",
		  type:"post",
		  success:function(data){
			 $("#stubody").html("");
			 $.each(data,function(i,n){
				 $("#stubody").append("<tr>");
				 $("#stubody").append("<td>"+n.id+"</td>");
				 $("#stubody").append("<td>"+n.name+"</td>");
				 $("#stubody").append("<td>"+n.age+"</td>");
				 $("#stubody").append("</tr>");
			 })
			 
		  }
	  })
  }
</script>
<title>Insert title here</title>
</head>
<body>
<div align="center">
	<p>浏览学生</p>
	<table>
		<thead>
			<tr>
			  <td>id</td>
			  <td>姓名</td>
			  <td>年龄</td>
			</tr>
		</thead>
		<tbody id="stubody">
		
		</tbody>
	</table>
</div>

</body>
</html>