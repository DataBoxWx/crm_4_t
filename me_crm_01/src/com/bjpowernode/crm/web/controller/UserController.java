package com.bjpowernode.crm.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.MD5;
import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;

@WebServlet(urlPatterns = { "/settings/qx/user/checkAct.do", "/settings/qx/user/save.do","/settings/qx/user/list.do" ,
		"/settings/qx/user/delete.do","/user/login.do"})
public class UserController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		String path = request.getServletPath();
		if ("/settings/qx/user/checkAct.do".equals(path)) {
			doCheckAct(request, response, userService);
		} else if ("/settings/qx/user/save.do".equals(path)) {
			doSave(request, response, userService);
		}else if ("/settings/qx/user/list.do".equals(path)) {
			doList(request, response, userService);
		}else if ("/settings/qx/user/delete.do".equals(path)) {
			doDelete(request, response, userService);
		}else if ("/user/login.do".equals(path)) {
			doLogin(request,response,userService);
		}
	}

	private void doLogin(HttpServletRequest request, HttpServletResponse response, UserService userService) {
		String loginAct = request.getParameter("loginAct");
		String loginPwd = MD5.get(request.getParameter("loginPwd"));
		String allowIp = request.getRemoteAddr();
		String tenDaysFree = request.getParameter("tenDaysFree");
		
		Map<String, Object> jsonMap = new HashMap<>(); 
		try {
			User user = userService.login(loginAct,loginPwd,allowIp);
			if("1".equals(tenDaysFree)){
				Cookie cookie1 = new Cookie("loginAct", loginAct);
				Cookie cookie2 = new Cookie("loginPwd", loginPwd);
				cookie1.setMaxAge(60 * 60 * 24 * 10);
				cookie2.setMaxAge(60 * 60 * 24 * 10);
				cookie1.setPath(request.getContextPath());
				cookie2.setPath(request.getContextPath());
				response.addCookie(cookie1);
				response.addCookie(cookie2);
			}
			
			request.getSession().setAttribute(Const.SESSION_USER, user);
			jsonMap.put("success", true);
			
		} catch (Exception e) {
			jsonMap.put("success", false);
			jsonMap.put("errorMsg",e.getMessage());
		}
		OutJson.print(request, response, jsonMap);
		
	}

	private void doDelete(HttpServletRequest request, HttpServletResponse response, UserService userService) {
		String[] ids = request.getParameterValues("id");
		int count = userService.delete(ids);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count != 0){
			jsonMap.put("success", true);
		}else{
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
		
	}

	private void doList(HttpServletRequest request, HttpServletResponse response, UserService userService) {
		List<User> list = userService.doList();
		
		OutJson.print(request, response, list);
	}

	private void doSave(HttpServletRequest request, HttpServletResponse response, UserService userService) {
		String id = UUIDGenerator.generate();
		String loginAct = request.getParameter("loginAct");
		String name = request.getParameter("name");
		String loginPwd = MD5.get(request.getParameter("loginPwd"));
		String email = request.getParameter("email");
		String expireTime = request.getParameter("expireTime");
		String lockState = request.getParameter("lockState");
		String deptCode = request.getParameter("deptCode");
		String allowIp = request.getParameter("allowIp");
		String creatTime = DateUtil.getSysTime();
		String creatBy = "admin";
		String editTime = request.getParameter("editTime");
		String editBy = request.getParameter("editBy");
		User user = new User();
		user.setId(id);
		user.setLoginAct(loginAct);
		user.setName(name);
		user.setLoginPwd(loginPwd);
		user.setEmail(email);
		user.setExpireTime(expireTime);
		user.setLockState(lockState);
		user.setDeptCode(deptCode);
		user.setAllowIp(allowIp);
		user.setCreatTime(creatTime);
		user.setCreatBy(creatBy);
		user.setEditTime(editTime);
		user.setEditBy(editBy);
		int count = userService.doSave(user);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doCheckAct(HttpServletRequest request, HttpServletResponse response, UserService userService) {
		String loginAct = request.getParameter("loginAct");
		int count = userService.checkAct(loginAct);
		Map<String, Object> jsonMap = new HashMap<>();
		if (count == 0) {
			jsonMap.put("success", true);
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}
}
