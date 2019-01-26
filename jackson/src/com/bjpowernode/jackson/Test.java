package com.bjpowernode.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws Exception{
		User u1 = new User();
		u1.setUsername("zhangsan");
		u1.setPassword("123");
		
		//jackson插件当中的类
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(u1);
		System.out.println(json);
	}

}
