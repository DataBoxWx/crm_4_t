package com.bjpowernode.crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil<T> {

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
	
	/**
	 * 
	 * @param excelFilePath
	 * @param c
	 * @return
	 * @throws IOException
	 */
	public List<T> read(String excelFilePath , Class c) throws IOException {
		List<T> dataList = new ArrayList<>();
		try {
			InputStream is = new FileInputStream(new File(excelFilePath));
			HSSFWorkbook xssfWorkbook = new HSSFWorkbook(is);
			if (xssfWorkbook == null) {
				System.out.println("未读取到内容,请检查路径！");
				return null;
			}
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				HSSFRow row0 = xssfSheet.getRow(0);
				String[] propertyNames = new String[row0.getLastCellNum()];
				for (int i = 0; i < row0.getLastCellNum(); i++) {
					HSSFCell cell = row0.getCell(i);
					String propertyName = trim(getValue(cell));
					propertyNames[i] = propertyName;
				}
				
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					Object obj = c.newInstance();	
					HSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow == null){
						continue;
					}
					for (int j = 0; j < xssfRow.getLastCellNum(); j++) {
						HSSFCell cell = xssfRow.getCell(j);
						String propertyValue = trim(getValue(cell));
						Method setMethod = c.getDeclaredMethod("set" + propertyNames[j].substring(0, 1).toUpperCase() + propertyNames[j].substring(1), String.class);
						setMethod.invoke(obj, propertyValue);
					}
					dataList.add((T)obj);
				}
				
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "---";
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			double cur = hssfCell.getNumericCellValue();
			long longVal = Math.round(cur);
			Object inputValue = null;
			if (Double.parseDouble(longVal + ".0") == cur)
				inputValue = longVal;
			else
				inputValue = cur;
			return String.valueOf(inputValue);
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BLANK
				|| hssfCell.getCellType() == hssfCell.CELL_TYPE_ERROR) {
			return "---";
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	private static String trim(String str) {
		if (str == null)
			return null;
		return str.replaceAll("[\\s\\?]", "").replace("　", "");
	}

}
