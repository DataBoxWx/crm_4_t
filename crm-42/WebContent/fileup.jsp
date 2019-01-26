<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
</head>
<body>

	<form action="workbench/activity/import.do" method="post" enctype="multipart/form-data">
		<input type="file" name="filename">
		<input type="submit" value="提交">
	</form> 
	
</body>
</html>
