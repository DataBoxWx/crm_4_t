package com.bjpowernode.crm.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.domain.Dept;
import com.bjpowernode.crm.service.DeptService;
import com.bjpowernode.crm.service.impl.DeptServiceImpl;
import com.bjpowernode.crm.util.TransactionHandler;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = { "/dept/index.do", "/dept/checkCode.do", "/dept/save.do",
		"/dept/edit.do","/dept/update.do","/dept/delete.do"})
public class DeptController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DeptService deptService = (DeptService) new TransactionHandler(new DeptServiceImpl()).getProxy();
		String path = request.getServletPath();
		if ("/dept/index.do".equals(path)) {
			index(request, response, deptService);
		} else if ("/dept/checkCode.do".equals(path)) {
			checkCode(request, response, deptService);
		} else if ("/dept/save.do".equals(path)) {
			doSave(request, response, deptService);
		}else if ("/dept/edit.do".equals(path)) {
			doEdit(request, response, deptService);
		}else if ("/dept/update.do".equals(path)) {
			doUpdate(request, response, deptService);
		}else if ("/dept/delete.do".equals(path)) {
			doDelete(request, response, deptService);
		}
	}

	private void doDelete(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		String[] codesStr = request.getParameterValues("code");
		int count = deptService.doDelete(codesStr);
		if(count != 0){
			response.getWriter().print("{\"success\":true}");
		 }else {
			 response.getWriter().print("{\"success\":false}");
		}
		
	}

	private void doUpdate(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		 String codeStr =request.getParameter("code");
		 int code = Integer.valueOf(codeStr);
		 String name =request.getParameter("name");
		 String manager =request.getParameter("manager");
		 String phone =request.getParameter("phone");
		 String description = request.getParameter("description");
		 Dept dept = new Dept(code, name, manager, phone, description);
		 int count = deptService.update(dept);
		 if(count==1){
			response.getWriter().print("{\"success\":true}");
		 }else {
			 response.getWriter().print("{\"success\":false}");
		}
		
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws JsonGenerationException, JsonMappingException, IOException {
		String codeStr = request.getParameter("code");
		int code = Integer.valueOf(codeStr);
		Dept dept = deptService.doEdit(code);
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(dept);
		response.getWriter().print(json);
	}

	private void doSave(HttpServletRequest request, HttpServletResponse response, DeptService deptService) throws IOException {
		 String codeStr =request.getParameter("code");
		 int code = Integer.valueOf(codeStr);
		 String name =request.getParameter("name");
		 String manager =request.getParameter("manager");
		 String phone =request.getParameter("phone");
		 String description = request.getParameter("description");
		 Dept dept = new Dept(code, name, manager, phone, description);
		 int count = deptService.doSave(dept);
		 if(count==1){
			response.getWriter().print("{\"success\":true}");
		 }else {
			 response.getWriter().print("{\"success\":false}");
		}
		
	}

	private void checkCode(HttpServletRequest request, HttpServletResponse response, DeptService deptService)
			throws IOException {
		String code = request.getParameter("code");
		int count = deptService.checkCode(code);
		if (count == 0) {
			response.getWriter().print("{\"success\":true}");
		} else {
			response.getWriter().print("{\"success\":false}");
		}
	}

	private void index(HttpServletRequest request, HttpServletResponse response, DeptService deptService)
			throws IOException {
		StringBuffer buffer = deptService.index();
		response.getWriter().print(buffer);
	}
}
