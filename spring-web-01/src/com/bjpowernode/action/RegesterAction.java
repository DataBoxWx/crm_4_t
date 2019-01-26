package com.bjpowernode.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bjpowernode.bean.Student;
import com.bjpowernode.service.RegesterService;

@WebServlet(urlPatterns={"/register.do"})
public class RegesterAction extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		Student student =new Student();
		student.setId("A1111");
		student.setName(name);
		student.setAge(age);
		
		String configLocation = "application.xml";
		//使用框架提供的方法， 获取容器对象
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		System.out.println("容器对象ctx:" + ctx);
		RegesterService regesterService = (RegesterService) ctx.getBean("studentService");
		regesterService.addStudent(student);
		response.sendRedirect(request.getContextPath()+"/success.jsp");
		
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
