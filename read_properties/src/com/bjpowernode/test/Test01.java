package com.bjpowernode.test;

import java.io.FileReader;
import java.util.Properties;

public class Test01 {

	public static void main(String[] args) throws Exception{
		//该属性文件没有放到类路径下（src或bin目录才是类路径）
		FileReader reader = new FileReader("Stage2Possibility.properties");
		Properties pro = new Properties();
		pro.load(reader);
		reader.close();
		
		String value = pro.getProperty("成交");
		System.out.println(value);
	}

}
