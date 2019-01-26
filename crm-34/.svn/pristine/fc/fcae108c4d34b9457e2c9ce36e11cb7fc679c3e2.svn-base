package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.TransactionHandler;

public class TranController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/workbench/transaction/create.do".equals(servletPath)) {
			doCreate(request, response);
		}
	}
	
	protected void doCreate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = (UserService)new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.getAll();
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
	}
	
}
























