<%@page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
	<head>
		<title>file upload</title>
	</head>
	<body>
		
		<%-- 
			get方式的话：http://192.168.146.2:8080/apache-commons-fileupload/fileup.do?filename=1527155319704.xls
			get方法只能提交普通的字符串，而且长度有限制，数据量有限制，而文件不是普通的字符串，并且文件有可能会很大，所以文件上传不能使用get请求。必须使用post请求。
			另外使用post请求的时候，文件上传要求设置enctype为：
				multipart/form-data
				enctype的默认值是：application/x-www-form-urlencoded，这个表示普通的form表单数据提交。
				
			以上是HTTP协议中规定的。
		--%>
		<form action="${pageContext.request.contextPath }/fileup.do" method="post" enctype="multipart/form-data">
			<%-- 普通form元素 --%> 
			用户名<input type="text" name="username">  <%-- FileItem --%>
			<br>
			<%-- 文件上传控件 type="file" --%>
			文件 <input type="file" name="filename1">  <%-- FileItem --%>
			<br>
			文件 <input type="file" name="filename2">  <%-- FileItem --%>
			<br>
			<input type="submit" value="提交">
		</form>
		
		<%-- 传文件的时候，form表单以FileItem为基本单元进行提交。 --%>
		
	</body>
</html>