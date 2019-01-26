package com.bjpowernode.jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * {
 * 	"total" : 100,
 *  "dataList" : [{},{},{}]
 * }
 */
public class Test4 {

	public static void main(String[] args) throws Exception {
		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("total", 100);
		List<User> dataList = new ArrayList<>();
		User u1 = new User();
		u1.setUsername("admin");
		u1.setPassword("123");
		dataList.add(u1);
		User u2 = new User();
		u2.setUsername("admin");
		u2.setPassword("123");
		dataList.add(u2);
		jsonMap.put("dataList", dataList);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(jsonMap);
		System.out.println(json);
	}

}














