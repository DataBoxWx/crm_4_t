package com.bjpowernode.crm.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.service.UserService;
import com.bjpowernode.crm.service.impl.UserServiceImpl;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.LoginException;
import com.bjpowernode.crm.util.TransactionHandler;
@WebServlet(urlPatterns={"/welcome.do"})
public class WelcomeController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		String loginAct = null;
		String loginPwd = null;
		if(cookies != null){
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if("loginAct".equals(name)){
					loginAct = cookie.getValue();
				}else if("loginPwd".equals(name)){
					loginPwd = cookie.getValue();
				}
			}
		}
		if(loginAct != null && loginPwd != null){
			UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
			try {
				User user = userService.login(loginAct, loginPwd, request.getRemoteAddr());
				request.getSession().setAttribute(Const.SESSION_USER, user);
				response.sendRedirect(request.getContextPath() + "/workbench/index.jsp");
			} catch (LoginException e) {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
}	
