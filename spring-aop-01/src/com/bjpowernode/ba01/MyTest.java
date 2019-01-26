package com.bjpowernode.ba01;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
	
	@Test
	public  void test01() {
		String configLocation = "applicationContext.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(configLocation);
		SomeService someService = (SomeService) ac.getBean("someService");
		someService.doSome();
	}
}
