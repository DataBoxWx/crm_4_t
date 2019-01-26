package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.utils.OutJson;
import com.bjpowernode.crm.utils.TransactionHandler;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;

public class CustomerController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/workbench/customer/getByName.do".equals(servletPath)) {
			doGetByName(request, response);
		}
	}

	protected void doGetByName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		CustomerService cs = (CustomerService)new TransactionHandler(new CustomerServiceImpl()).getProxy();
		List<String> customerNames = cs.getByName(name);
		OutJson.print(request, response, customerNames);
	}
}























