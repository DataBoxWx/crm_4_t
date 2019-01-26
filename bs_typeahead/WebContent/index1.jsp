<%@page contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<base href="http://localhost:8080/bs_typeahead/">
<title>bs-3-typeahead</title>

<%-- 引入jQuery --%>
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

<%-- 引入bootstrap --%>
<link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" />
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<%-- bs typeahead --%>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

</head>
<body>
	<div style="margin: 50px 50px">
		<label for="product_search">Product Search: </label> <input
			id="product_search" type="text" data-provide="typeahead"
			data-source='["abc111", "abc211", "abc311"]'>
	</div>
</body>
</html>