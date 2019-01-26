package com.bjpowernode.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjpowernode.Service.UserService;

public class test {
	@Test
	public void test01(){
		String configLocation = "applicationContext.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(configLocation);
		UserService userService = (UserService) ac.getBean("userServiceImpl");
		userService.addUser();
	}
}
