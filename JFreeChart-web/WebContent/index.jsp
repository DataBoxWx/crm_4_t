<%@page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
	<head>
		<title>jfreechart</title>
	</head>
	<body>
		<%--页面打开的时候 从网络中加载图片 --%>
		<img width="400px" alt="水果产量图" src="${pageContext.request.contextPath}/chart.do" />
	</body>
</html>