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

<script type="text/javascript">
	$(function(){
		$('#product_search').typeahead({
			//source: ["aaa", "bbb", "aaabbbccc"]  //固定的，可以使用。
			source: function(query , process){ //query是用户输入的信息；process是一个函数
				//return ["xxx", "yyy", "zzzxxx"]; //不好用
				var dataArray = ["xxx", "yyy", "zzzxxx"];
				process(dataArray); //process回调函数可以将数组处理成typeahead可以识别的数据。
			}
		});
	});
</script>

</head>
<body>

<div style="margin: 50px 50px">
<label for="product_search">Product Search: </label>
<input id="product_search" type="text" data-provide="typeahead">
</div>

</body>
</html>