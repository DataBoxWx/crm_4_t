package com.bjpowernode.crm.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutJson {
	
	private OutJson(){}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param success
	 */
	public static void printMap(HttpServletRequest request,HttpServletResponse response , Boolean success){
		Map<String,Boolean> jsonMap = new HashMap<>();
		jsonMap.put("success", success);
		print(request, response, jsonMap);
	}
	
	/**
	 * 响应json
	 * @param request
	 * @param response
	 * @param jsonMap
	 */
	public static void print(HttpServletRequest request,HttpServletResponse response,Object jsonMap){
		try {
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(jsonMap);
			request.setAttribute("data", json);
			request.getRequestDispatcher("/data.jsp").forward(request,response);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
