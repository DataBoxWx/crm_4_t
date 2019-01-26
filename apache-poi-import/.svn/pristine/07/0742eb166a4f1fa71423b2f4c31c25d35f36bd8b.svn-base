package com.bjpowernode.poi.test;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) throws IOException {
		ExcelReader reader = new ExcelReader();
		ArrayList<ArrayList<String>> arr = reader.xlsx_reader("activity.xls", 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10); // 后面的参数代表需要输出哪些列，参数个数可以任意
		for (int i = 0; i < arr.size(); i++) {
			ArrayList<String> row = arr.get(i);
			for (int j = 0; j < row.size(); j++) {
				System.out.print(row.get(j) + " ");
			}
			System.out.println("");
		}
	}
}