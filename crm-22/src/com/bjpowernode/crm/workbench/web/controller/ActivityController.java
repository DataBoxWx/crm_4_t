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
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityRemarkServiceImpl;
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
		} else if ("/workbench/activity/edit.do".equals(servletPath)) {
			doEdit(request, response);
		} else if ("/workbench/activity/update.do".equals(servletPath)) {
			doUpdate(request, response);
		} else if ("/workbench/activity/delete.do".equals(servletPath)) {
			doDelete(request, response);
		} else if ("/workbench/activity/detail.do".equals(servletPath)) {
			doDetail(request, response);
		} else if ("/workbench/activity/listRemark.do".equals(servletPath)) {
			doListRemark(request, response);
		} else if ("/workbench/activity/saveRemark.do".equals(servletPath)) {
			doSaveRemark(request, response);
		}    
	}

	protected void doSaveRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String noteContent = request.getParameter("noteContent");
		String activityId = request.getParameter("activityId");
		String id = UUIDGenerator.generate();
		String createTime = DateUtil.getSysTime();
		String createBy = ((User)request.getSession().getAttribute(Const.SESSION_USER)).getName();
		String editFlag = "0";
		
		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setCreateTime(createTime);
		ar.setCreateBy(createBy);
		ar.setEditFlag(editFlag);
		ar.setActivityId(activityId);
		
		ActivityRemarkService ars = (ActivityRemarkService)new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		int count = ars.save(ar);
		
		Map<String,Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success", true);
			jsonMap.put("activityRemark", ar);
		}else{
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
		

	}
	
	protected void doListRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String activityId = request.getParameter("activityId");
		ActivityRemarkService ars = (ActivityRemarkService)new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		List<ActivityRemark> activityRemarkList = ars.getByActivityId(activityId);
		OutJson.print(request, response, activityRemarkList);
	}
	
	protected void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		Activity activity = (Activity)as.getById2(id);
		request.setAttribute("activity", activity);
		request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ids = request.getParameterValues("id");
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		OutJson.printMap(request, response, as.deleteById(ids));
	}
	
	protected void doUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		String editBy = ((User)request.getSession().getAttribute(Const.SESSION_USER)).getName();
		String editTime = DateUtil.getSysTime();
		
		Activity a = new Activity();
		a.setId(id);
		a.setOwner(owner);
		a.setName(name);
		a.setStartDate(startDate);
		a.setEndDate(endDate);
		a.setCost(cost);
		a.setDescription(description);
		a.setEditBy(editBy);
		a.setEditTime(editTime);
		
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		
		OutJson.printMap(request, response, as.update(a));
	}

	protected void doEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		Map<String, Object> activityMap = as.getById(id);
		OutJson.print(request, response, activityMap);
	}

	protected void doPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收参数
		Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String name = request.getParameter("name");
		String owner = request.getParameter("owner");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// 封装Map
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put("startIndex", (pageNo - 1) * pageSize);
		conditionMap.put("pageSize", pageSize);
		conditionMap.put("name", name);
		conditionMap.put("owner", owner);
		conditionMap.put("startDate", startDate);
		conditionMap.put("endDate", endDate);

		// 获取service
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		PaginationVO<Activity> page = as.getPageByCondition(conditionMap);

		// 响应JSON
		OutJson.print(request, response, page);
	}

	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
