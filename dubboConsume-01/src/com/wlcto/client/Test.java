package com.wlcto.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("dubbo-consume.xml");
		
		InvokeService is = (InvokeService) ctx.getBean("invoekService");
		is.printService();

	}

}
