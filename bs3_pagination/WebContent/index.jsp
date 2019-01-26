<%@page contentType="text/html; charset=UTF-8"%>
<!doctype html>

<html>
	<head>
		<title>bs pagination</title>
		
		<%--jQuery --%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.11.1-min.js"></script>
		
		<%--bootstrap 3 --%>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/jquery/bootstrap_3.3.0/css/bootstrap.min.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
		
		<%-- bs pagination --%>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/jquery/bs_pagination/jquery.bs_pagination.min.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/bs_pagination/localization/en.min.js"></script>
		
		<script type="text/javascript"> 
			$(function(){
				var pageNo = 1;
				var pageSize = 3;
				var totalRows = 100;
				var totalPages = totalRows % pageSize == 0 ? totalRows / pageSize : parseInt(totalRows / pageSize) + 1; 
				$("#demo_pag1").bs_pagination({
					  currentPage: pageNo, //页码pageNo
					  rowsPerPage: pageSize, //每页显示多少条记录 pageSize
					  totalPages: totalPages, //总页数
					  totalRows: totalRows, //总记录条数
					  visiblePageLinks: 3, //显示的卡片个数
					  showGoToPage: true,
					  showRowsPerPage: true,
					  showRowsInfo: true,
					  onChangePage : function(event , data){
						  alert(data.currentPage + "----" + data.rowsPerPage);
					  }
				});
			});
		</script>
		
	</head>
	<body>
		<div id="demo_pag1"></div>
	</body>
</html>