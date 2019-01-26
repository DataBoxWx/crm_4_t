package com.bjpowernode.test;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class Test03 {

	public static void main(String[] args) {
		
		//使用资源绑定器java.util.ResourceBundle
		//资源绑定的时候，该资源被当做类，这里提供的必须是全限定类名。
		ResourceBundle bundle = ResourceBundle.getBundle("com.bjpowernode.resources.Stage2Possibility3"); 
		Enumeration<String> keys = bundle.getKeys();
		
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String value = bundle.getString(key);
			System.out.println(key + "=" + value);
		}
	}

}
