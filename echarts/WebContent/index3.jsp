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
                    text: '漏斗图',
                    subtext: '纯属虚构'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c}%"
                },
                toolbox: {
                    feature: {
                        dataView: {readOnly: false},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                legend: {
                    data: ['展现','点击','访问','咨询','订单']
                },
                calculable: true,
                series: [
                    {
                        name:'漏斗图',
                        type:'funnel',
                        left: '10%',
                        top: 60,
                        //x2: 80,
                        bottom: 60,
                        width: '80%',
                        // height: {totalHeight} - y - y2,
                        min: 0,
                        max: 150,
                        minSize : '0%',  //最小边界
                        maxSize : '100%', //最大边界（假设容器本身的宽度是100cm的话，20%表示漏斗最大的宽度是20cm）
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
                        data: [
                            {value: 20, name: '成交'},
                            {value: 59, name: '咨询'},
                            {value: 60, name: '订单'},
                            {value: 99, name: '点击'},
                            {value: 100, name: '展现'}
                        ]
                    }
                ]
            });
    	});
    	
    </script>
</head>
<body>
    <div id="main" style="width: 600px;height:400px;background-color: gray;"></div>
</body>
</html>