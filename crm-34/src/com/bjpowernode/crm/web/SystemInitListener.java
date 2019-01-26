package com.bjpowernode.crm.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.impl.DicValueServiceImpl;
import com.bjpowernode.crm.utils.TransactionHandler;

/**
 * 系统初始化监听器
 * @author Administrator
 *
 */
public class SystemInitListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("--------------------系统开始初始化---------------------------");
		ServletContext application = sce.getServletContext();
		/*
		 * Map<String , List<DicValue>> 
		 * key					value
		 * -----------------------------------
		 * "appellationList"		appellationList
		 * "clueStateList"			clueStateList
		 * .....
		 */
		DicValueService dicValueService = (DicValueService)new TransactionHandler(new DicValueServiceImpl()).getProxy();	
		Map<String,List<DicValue>> dicValueMap = dicValueService.getAll();
		Set<String> keys = dicValueMap.keySet();
		for(String key : keys){
			application.setAttribute(key, dicValueMap.get(key));
		}
		
		//读取资源文件
		ResourceBundle bundle = ResourceBundle.getBundle("com.bjpowernode.crm.resources.Stage2Possibility");
		Enumeration<String> ks = bundle.getKeys();
		Map<String,String> possibilityMap = new HashMap<>();
		while(ks.hasMoreElements()){
			String key = ks.nextElement();
			String value = bundle.getString(key);
			possibilityMap.put(key, value);
		}
		application.setAttribute("possibilityMap", possibilityMap);
		
		System.out.println("--------------------系统初始化完毕---------------------------");
	}
	
}




















