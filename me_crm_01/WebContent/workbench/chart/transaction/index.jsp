<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/echarts/echarts.min.js"></script>

<script type="text/javascript">
$(function(){
	
	//发送ajax请求
	$.ajax({
		type : "get",
		url : "workbench/chart/transaction/getCountByStage.do",
		cache : false,
		success : function(json){
			var myChart = echarts.init($("#countTransactionStage")[0]);
	        myChart.setOption({
	        	title: {
	                text: '交易漏斗图',
	                subtext: '交易各阶段数量比例图'
	            },
	            tooltip: {
	                trigger: 'item',
	                formatter: "{a} <br/>{b} : {c}"
	            },
	            calculable: true,
	            series: [
	                {
	                    name:'漏斗图',
	                    type:'funnel',
	                    left: '20%',
	                    top: 60,
	                    bottom: 60,
	                    width: '80%',
	                    min: 0,
	                    max: 100,
	                    minSize: '0%',
	                    maxSize: '100%',
	                    sort: 'descending',
	                    gap: 2,
	                    label: {
	                        normal: {
	                            show: true,
	                            position: 'inside'
	                        },
	                        emphasis: {
	                            textStyle: {
	                                fontSize: 20
	                            }
	                        }
	                    },
	                    labelLine: {
	                        normal: {
	                            length: 10,
	                            lineStyle: {
	                                width: 1,
	                                type: 'solid'
	                            }
	                        }
	                    },
	                    itemStyle: {
	                        normal: {
	                            borderColor: '#fff',
	                            borderWidth: 1
	                        }
	                    },
	                    data: json
	                }
	            ]
	        });
		}
	});
});
</script>
</head>
<body>
	<div id="countTransactionStage" style="width: 800px;height:550px;"></div>
</body>
</html>