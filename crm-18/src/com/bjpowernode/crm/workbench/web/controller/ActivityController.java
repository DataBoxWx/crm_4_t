package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.Const;
import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.OutJson;
import com.bjpowernode.crm.utils.TransactionHandler;
import com.bjpowernode.crm.utils.UUIDGenerator;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

public class ActivityController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/workbench/activity/create.do".equals(servletPath)) {
			doCreate(request, response);
		} else if ("/workbench/activity/sava.do".equals(servletPath)) {
			doSave(request, response);
		} else if ("/workbench/activity/page.do".equals(servletPath)) {
			doPage(request, response);
		}
	}

	protected void doPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//接收参数
		Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String name = request.getParameter("name");
		String owner = request.getParameter("owner");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		//封装Map
		Map<String,Object> conditionMap = new HashMap<>();
		conditionMap.put("startIndex", (pageNo - 1) * pageSize);
		conditionMap.put("pageSize", pageSize);
		conditionMap.put("name", name);
		conditionMap.put("owner", owner);
		conditionMap.put("startDate", startDate);
		conditionMap.put("endDate", endDate);
		
		//获取service
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		PaginationVO<Activity> page = as.getPageByCondition(conditionMap);
		
		//响应JSON
		OutJson.print(request, response, page);
	}

	protected void doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		String createBy = user.getName();
		String createTime = DateUtil.getSysTime();

		Activity activity = new Activity();

		activity.setId(UUIDGenerator.generate());
		activity.setOwner(owner);
		activity.setName(name);
		activity.setStartDate(startDate);
		activity.setEndDate(endDate);
		activity.setCost(cost);
		activity.setDescription(description);
		activity.setCreateBy(createBy);
		activity.setCreateTime(createTime);

		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();

		response.getWriter().print("{\"success\":" + as.save(activity) + "}");
	}

	protected void doCreate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.getAll();
		OutJson.print(request, response, userList);
	}

}
