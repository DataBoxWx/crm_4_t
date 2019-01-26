<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
   	<p>学生注册页面</p>
   	<form action="${pageContext.request.contextPath }/student/add.do" method="get">
   	 <table>
   	 	<tr>
   	 		<td>姓名：</td>
   	 		<td><input type="text" name="name"></td>
   	 	</tr>
   	 	<tr>
   	 		<td>年龄：</td>
   	 		<td><input type="text" name="age"></td>
   	 	</tr>
   	 	<tr>
   	 		<td>提交：</td>
   	 		<td><input type="submit" value="注册学生"></td>
   	 	</tr>
   	 
   	 </table>
   	
   	</form>
   
   </div>
</body>
</html>