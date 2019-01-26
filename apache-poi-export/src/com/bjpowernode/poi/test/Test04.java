package com.bjpowernode.poi.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bjpowernode.poi.domain.Dept;
import com.bjpowernode.poi.util.ExcelUtil;
import com.bjpowernode.poi.util.SqlSessionUtil;

public class Test04 {

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = SqlSessionUtil.getCurrentSqlSession();
			/*
			List<Activity> activityList = sqlSession.selectList("getAll");		
			HSSFWorkbook workbook = ExcelUtil.export("市场活动", Activity.class, activityList);
			try {
				OutputStream outputStream = new FileOutputStream("D:/activity11111.xls");
				workbook.write(outputStream);
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
			
			List<Dept> deptList = sqlSession.selectList("getAll2");		
			HSSFWorkbook workbook = ExcelUtil.export("部门", Dept.class, deptList);
			try {
				OutputStream outputStream = new FileOutputStream("D:/deptList.xls");
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
