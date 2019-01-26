package com.bjpowernode.crm.settings.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.MD5;
import com.bjpowernode.crm.utils.OutJson;
import com.bjpowernode.crm.utils.TransactionHandler;
import com.bjpowernode.crm.utils.UUIDGenerator;

public class UserController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/settings/qx/user/save.do".equals(servletPath)) {
			doSave(request, response);
		} else if("/login.do".equals(servletPath)){
			doLogin(request,response);
		}
	}

	protected void doLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String loginAct = request.getParameter("loginAct");
		String loginPwd = MD5.get(request.getParameter("loginPwd"));
		String clientIp = request.getRemoteAddr();
		UserService userService = (UserService)new TransactionHandler(new UserServiceImpl()).getProxy();
		User user = userService.login(loginAct,loginPwd,clientIp);
		request.getSession().setAttribute("user", user);
	}
	
	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = UUIDGenerator.generate();
		String loginAct = request.getParameter("loginAct");
		String name = request.getParameter("name");
		String loginPwd = MD5.get(request.getParameter("loginPwd"));
		String email = request.getParameter("email");
		String expireTime = request.getParameter("expireTime");
		String lockState = request.getParameter("lockState");
		String deptcode = request.getParameter("deptcode");
		String allowIps = request.getParameter("allowIps");
		String createTime = DateUtil.getSysTime();
		String createBy = "admin";
		
		User user = new User();
		user.setId(id);
		user.setLoginAct(loginAct);
		user.setName(name);
		user.setLoginPwd(loginPwd);
		user.setEmail(email);
		user.setExpireTime(expireTime);
		user.setLockState(lockState);
		user.setDeptcode(deptcode);
		user.setAllowIps(allowIps);
		user.setCreateTime(createTime);
		user.setCreateBy(createBy);
		
		UserService userService = (UserService)new TransactionHandler(new UserServiceImpl()).getProxy();
		int count = userService.save(user);
		
		Map<String,Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success",true);
		}else{
			jsonMap.put("success",false);
		}
		
		OutJson.print(request, response, jsonMap);
	}
}
















