package com.bjpowernode.crm.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.bjpowernode.crm.service.DictionaryService;
import com.bjpowernode.crm.service.impl.DictionaryServiceImpl;
import com.bjpowernode.crm.util.TransactionHandler;

public class SystemInitLister implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("-------初始化开始1---------");
		ServletContext application = sce.getServletContext();
		DictionaryService dictionaryService = (DictionaryService) new TransactionHandler(new DictionaryServiceImpl()).getProxy();
		Map<String, Object> map = dictionaryService.getAlls();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			application.setAttribute(key, map.get(key));
		}
		System.out.println("--------初始化开始2------------");
		ResourceBundle resourceBundle = ResourceBundle.getBundle("com.bjpowernode.crm.resource.stagePossibility");
		Enumeration<String> keys = resourceBundle.getKeys();
		Map<String, String> possibly = new TreeMap<>(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				
				return o1.compareTo(o2);
			}
		});
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String value = resourceBundle.getString(key);
			possibly.put(key, value);
		}
		application.setAttribute("possibly", possibly);
		
		System.out.println("-------初始化完毕1---------");
	}
}
