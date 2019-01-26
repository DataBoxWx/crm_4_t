package com.bjpowernode.crm.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutJson extends HttpServlet {
	private OutJson (){
		
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param success
	 */
	public static void printMap(HttpServletRequest request,HttpServletResponse response,Boolean success) {
		Map<String, Object> jsonMap  = new HashMap<>();
		jsonMap.put("success", success);
		print(request, response, jsonMap);
	}
	
	
	public static void print(HttpServletRequest request,HttpServletResponse response,Object jsonMap) {
		try {
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(jsonMap);
			request.setAttribute("data", json);
			request.getRequestDispatcher("/jsp/json.jsp").forward(request, response);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
}
