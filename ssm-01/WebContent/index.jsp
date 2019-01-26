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
	<p>首页</p>
	<img alt="no picture" src="${pageContext.request.contextPath }/images/main.png"><br>
		<a href="${pageContext.request.contextPath }/jump.do?target=addStudent">点击跳转到注册页</a><br>
		<a href="${pageContext.request.contextPath }/jump.do?target=listStudent">点击跳转到学生列表页</a>
	</div>
</body>
</html>