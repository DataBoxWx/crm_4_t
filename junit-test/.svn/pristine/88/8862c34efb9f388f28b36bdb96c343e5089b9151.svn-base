package com.bjpowernode.junit.service.impl;

import org.junit.Test;

import com.bjpowernode.junit.service.MathService;

import static org.junit.Assert.assertEquals;
//import static java.lang.System.out;  //jdk7的新特性（静态导入）
//import static java.lang.System.currentTimeMillis;

/**
 * 这是一个测试用例：junit test case
 * 被测试的类是：MathServiceImpl
 * @author Administrator
 *
 */
public class MathServiceImplTest {
	
	@Test
	public void testSum(){
		//实际值和期望值
		MathService ms = new MathServiceImpl();
		int actual = ms.sum(1, 2); //实际值
		int expected = 3; //期望值
		//断言机制（重点）
		assertEquals(expected, actual);
		
		/*
		out.print("hello world!");
		out.println(currentTimeMillis());
		*/
	}

	
	@Test
	public void testDivide(){
		//实际值和期望值
		MathService ms = new MathServiceImpl();
		int actual = ms.divide(10, 3);
		int expected = 3;
		assertEquals(expected, actual);
	}
}























