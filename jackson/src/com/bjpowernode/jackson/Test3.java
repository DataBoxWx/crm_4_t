package com.bjpowernode.jackson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test3 {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
		User user = new User();
		user.setUsername("abc");
		user.setPassword("xxxxx");
		
		Map<String,Object> jsonMap = new HashMap<>();
		jsonMap.put("x", 100);
		jsonMap.put("y", "fdsafdsafd");
		jsonMap.put("user", user);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(jsonMap);
		System.out.println(json);
	}

}
