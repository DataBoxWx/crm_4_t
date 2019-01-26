package com.bjpowernode.crm.settings.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.impl.DicTypeServiceImpl;
import com.bjpowernode.crm.utils.OutJson;
import com.bjpowernode.crm.utils.TransactionHandler;

public class DicTypeController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if("/settings/dictionary/type/checkCode.do".equals(servletPath)){
			doCheckCode(request,response);
		} else if("/settings/dictionary/type/save.do".equals(servletPath)){
			doSave(request,response);
		}
	}
	
	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		DicType dt = new DicType();
		dt.setCode(code);
		dt.setName(name);
		dt.setDescription(description);
		
		DicTypeService dts = (DicTypeService)new TransactionHandler(new DicTypeServiceImpl()).getProxy();
		int count = dts.save(dt);
		if(count == 1){
			response.sendRedirect(request.getContextPath() + "/settings/dictionary/type/index.jsp");
		}
	}
	
	protected void doCheckCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		DicTypeService dts = (DicTypeService)new TransactionHandler(new DicTypeServiceImpl()).getProxy();
		DicType dt = dts.getByCode(code);
		
		Map<String,Object> jsonMap = new HashMap<>();
		if(dt == null){
			jsonMap.put("success", true);
		}else{
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}
}























