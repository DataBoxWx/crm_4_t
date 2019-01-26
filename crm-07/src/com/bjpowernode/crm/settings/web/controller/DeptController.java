package com.bjpowernode.crm.settings.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.settings.domain.Dept;
import com.bjpowernode.crm.settings.service.DeptService;
import com.bjpowernode.crm.settings.service.impl.DeptServiceImpl;
import com.bjpowernode.crm.utils.TransactionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeptController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/settings/dept/save.do".equals(servletPath)) {
			doSave(request, response);
		} else if ("/settings/dept/getAll.do".equals(servletPath)) {
			doGetAll(request, response);
		} else if ("/settings/dept/edit.do".equals(servletPath)) {
			doEdit(request, response);
		} else if ("/settings/dept/update.do".equals(servletPath)) {
			doUpdate(request, response);
		} else if ("/settings/dept/delete.do".equals(servletPath)) {
			doDelete(request, response);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//code=1111&code=2222
		String[] codes = request.getParameterValues("code");
		DeptService deptService = (DeptService) new TransactionHandler(new DeptServiceImpl()).getProxy();
		int count = deptService.deleteByCode(codes);
		
		Map<String,Object> jsonMap = new HashMap<>();
		if(count == codes.length){
			jsonMap.put("success", true);
		}else{
			jsonMap.put("success", false);
		}
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(jsonMap);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
			
	}

	protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String manager = request.getParameter("manager");
		String phone = request.getParameter("phone");
		String description = request.getParameter("description");
		
		Dept dept = new Dept();
		dept.setCode(code);
		dept.setName(name);
		dept.setManager(manager);
		dept.setPhone(phone);
		dept.setDescription(description);
		
		DeptService deptService = (DeptService) new TransactionHandler(new DeptServiceImpl()).getProxy();
		
		int count = deptService.update(dept);
		
		Map<String,Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success", true);
		}else{
			jsonMap.put("success", false);
		}
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(jsonMap);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
	}

	protected void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String code = request.getParameter("code");

		DeptService deptService = (DeptService) new TransactionHandler(new DeptServiceImpl()).getProxy();

		Dept dept = deptService.getByCode(code);

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(dept);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
	}

	protected void doGetAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DeptService deptService = (DeptService) new TransactionHandler(new DeptServiceImpl()).getProxy();
		List<Dept> deptList = deptService.getAll();
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(deptList);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
	}

	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收表单参数
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String manager = request.getParameter("manager");
		String phone = request.getParameter("phone");
		String description = request.getParameter("description");

		Dept dept = new Dept();
		dept.setCode(code);
		dept.setName(name);
		dept.setManager(manager);
		dept.setPhone(phone);
		dept.setDescription(description);

		// 获取service代理对象
		DeptService deptService = (DeptService) new TransactionHandler(new DeptServiceImpl()).getProxy();

		// 保存数据
		int count = deptService.save(dept);

		// 响应json
		response.setContentType("text/json;charset=UTF-8");
		if (count == 1) {
			response.getWriter().print("{\"success\" : true}");
		} else {
			response.getWriter().print("{\"success\" : false}");
		}

	}
}
