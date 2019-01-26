package com.bjpowernode.jfreechart;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 用于演示饼图的生成
 */
public class Test02 {
	
    public static void main(String[] args) throws IOException {
    	
        DefaultPieDataset data = getDataSet();
        JFreeChart chart = ChartFactory.createPieChart3D("水果产量图", // 图表标题
                data, // 数据集
                true, // 是否显示图例(对于简单的柱状图必须是 false)
                false, // 是否生成工具
                false // 是否生成 URL 链接
                );

        //中文乱码
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setLabelFont(new Font("黑体", Font.PLAIN, 20));
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

        // 写图表对象到文件，参照柱状图生成源码
        FileOutputStream fos_jpg = null;
        try {
            fos_jpg = new FileOutputStream("D:\\Pie3DChart.jpg");
            ChartUtilities.writeChartAsJPEG(fos_jpg, 1.0f, chart, 400, 300,
                    null);
        } finally {
            try {
                fos_jpg.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取一个演示用的简单数据集对象
     * 
     * @return
     */
    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("苹果", 100);
        dataset.setValue("梨子", 200);
        dataset.setValue("葡萄", 300);
        dataset.setValue("香蕉", 400);
        dataset.setValue("荔枝", 50);
        return dataset;
    }
}