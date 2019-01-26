package com.bjpowernode.test;

import java.io.InputStream;
import java.util.Properties;

public class Test02 {
	//把文件放到了类的根路径下。
	public static void main(String[] args) throws Exception{
		
		//这种方式默认就是从类的根路径下开始加载。
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("Stage2Possibility2.properties");
		Properties pro = new Properties();
		pro.load(in);
		in.close();
		
		String value = pro.getProperty("资质审查");
		System.out.println(value);
	}

}
