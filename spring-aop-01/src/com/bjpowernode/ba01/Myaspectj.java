package com.bjpowernode.ba01;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Myaspectj {
	
	@Before(value="execution(void  com.bjpowernode.ba01.SomeServiceImpl.*())")
	public void method1(JoinPoint jp) {
		System.out.println("加在前面的方法");
		System.out.println(jp.getSignature());
	}
}
