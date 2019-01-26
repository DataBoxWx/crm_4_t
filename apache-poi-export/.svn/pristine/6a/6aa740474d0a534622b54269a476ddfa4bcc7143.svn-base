package com.bjpowernode.poi.test;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Test01 {

	public static void main(String[] args) throws Exception {
		// 创建新工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 新建工作表
		HSSFSheet sheet = workbook.createSheet("hello");
		// 创建行,行号作为参数传递给createRow()方法,第一行从0开始计算
		HSSFRow row = sheet.createRow(0);
		// 创建单元格,row已经确定了行号,列号作为参数传递给createCell(),第一列从0开始计算
		HSSFCell cell = row.createCell(2);
		// 设置单元格的值,即C1的值(第一行,第三列)
		cell.setCellValue("hello sheet");
		// 输出到磁盘中
		FileOutputStream fos = new FileOutputStream(new File("D:\\test.xls"));
		workbook.write(fos);
		workbook.close();
		fos.close();
	}

}
