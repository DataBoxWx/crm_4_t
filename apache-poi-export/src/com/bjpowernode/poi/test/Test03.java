package com.bjpowernode.poi.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bjpowernode.poi.domain.Activity;
import com.bjpowernode.poi.util.SqlSessionUtil;

public class Test03 {

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionUtil.getCurrentSqlSession();
			
			//从数据库当中取出所有的市场活动
			List<Activity> activityList = sqlSession.selectList("getAll");		
			//System.out.println(activityList.size());
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("市场活动表一");
			
			HSSFRow hssfRow = sheet.createRow(0);
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			HSSFCell headCell0 = hssfRow.createCell(0);
			headCell0.setCellValue("id");
			headCell0.setCellStyle(cellStyle);
			
			HSSFCell headCell1 = hssfRow.createCell(1);
			headCell1.setCellValue("owner");
			headCell1.setCellStyle(cellStyle);
			
			HSSFCell headCell2 = hssfRow.createCell(2);
			headCell2.setCellValue("name");
			headCell2.setCellStyle(cellStyle);
			
			HSSFCell headCell3 = hssfRow.createCell(3);
			headCell3.setCellValue("startDate");
			headCell3.setCellStyle(cellStyle);
			
			HSSFCell headCell4 = hssfRow.createCell(4);
			headCell4.setCellValue("endDate");
			headCell4.setCellStyle(cellStyle);
			
			HSSFCell headCell5 = hssfRow.createCell(5);
			headCell5.setCellValue("cost");
			headCell5.setCellStyle(cellStyle);
			
			HSSFCell headCell6 = hssfRow.createCell(6);
			headCell6.setCellValue("description");
			headCell6.setCellStyle(cellStyle);
			
			HSSFCell headCell7 = hssfRow.createCell(7);
			headCell7.setCellValue("createBy");
			headCell7.setCellStyle(cellStyle);
			
			HSSFCell headCell8 = hssfRow.createCell(8);
			headCell8.setCellValue("createTime");
			headCell8.setCellStyle(cellStyle);
			
			HSSFCell headCell9 = hssfRow.createCell(9);
			headCell9.setCellValue("editBy");
			headCell9.setCellStyle(cellStyle);
			
			HSSFCell headCell10 = hssfRow.createCell(10);
			headCell10.setCellValue("editTime");
			headCell10.setCellStyle(cellStyle);
			
			for (int i = 0; i < activityList.size(); i++) {
				
				HSSFRow row = sheet.createRow((int) i + 1);
				Activity activity = activityList.get(i);

				// 创建单元格，并设置值
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(activity.getId());
				cell.setCellStyle(cellStyle);

				cell = row.createCell(1);
				cell.setCellValue(activity.getOwner());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(2);
				cell.setCellValue(activity.getName());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(3);
				cell.setCellValue(activity.getStartDate());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(4);
				cell.setCellValue(activity.getEndDate());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(5);
				cell.setCellValue(activity.getCost());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(6);
				cell.setCellValue(activity.getDescription());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(7);
				cell.setCellValue(activity.getCreateBy());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(8);
				cell.setCellValue(activity.getCreateTime());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(9);
				cell.setCellValue(activity.getEditBy());
				cell.setCellStyle(cellStyle);
				
				cell = row.createCell(10);
				cell.setCellValue(activity.getEditTime());
				cell.setCellStyle(cellStyle);
				
			}

			// 保存Excel文件
			try {
				OutputStream outputStream = new FileOutputStream("D:/activity.xls");
				workbook.write(outputStream);
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			sqlSession.commit();
		} catch (Exception e) {
			SqlSessionUtil.rollback(sqlSession);
			e.printStackTrace();
		} finally{
			SqlSessionUtil.close(sqlSession);
		}
	}

}
