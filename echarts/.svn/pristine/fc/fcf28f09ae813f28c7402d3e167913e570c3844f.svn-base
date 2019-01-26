<%@page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<base href="http://localhost:8080/echarts/">
    <meta charset="utf-8">
    <title>ECharts</title>
    <script src="js/echarts.min.js"></script>
    <script src="js/jquery-1.11.1-min.js"></script>
    <script type="text/javascript">
    	$(function(){
            var myChart = echarts.init($("#main")[0]);
            myChart.setOption({
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                legend: {
                    data:['销量']
                },
                xAxis: {
                    data: ["衬衫","羊毛衫","雪纺衫","老北京布鞋","袜子"]
                },
                yAxis: {},
                series: [{
                    name: '销量',
                    type: 'bar',
                    data: [5, 20, 36, 10, 20]
                }]
            });
    	});
    </script>
</head>
<body>
    <div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>