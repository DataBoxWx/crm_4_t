package com.bjpowernode.crm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {

	/**
	 * 导出excel
	 * @param sheetName 表名
	 * @param c 导出数据时封装的java对象对应的全限定类名
	 * @param dataList 数据来源
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static HSSFWorkbook export(String sheetName, Class c, List dataList){
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			HSSFSheet sheet = workbook.createSheet(sheetName);
			HSSFRow hssfRow = sheet.createRow(0);
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			Field[] fields = c.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				HSSFCell cell = hssfRow.createCell(i);
				cell.setCellValue(fieldName);
				cell.setCellStyle(cellStyle);
			}
			for (int i = 0; i < dataList.size(); i++) {
				HSSFRow row = sheet.createRow((int) i + 1);
				Object o = dataList.get(i);
				for (int j = 0; j < fields.length; j++) {
					HSSFCell cell = row.createCell(j);
					String fieldName = fields[j].getName();
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method getMethod = c.getDeclaredMethod(getMethodName);
					cell.setCellValue(getMethod.invoke(o) == null ? "" : getMethod.invoke(o).toString());
					cell.setCellStyle(cellStyle);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return workbook;
	}

}
