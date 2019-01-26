package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueActivityRelationServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

public class ClueController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/workbench/clue/create.do".equals(servletPath)) {
			doCreate(request, response);
		} else if ("/workbench/clue/save.do".equals(servletPath)) {
			doSave(request, response);
		} else if ("/workbench/clue/detail.do".equals(servletPath)) {
			doDetail(request, response);
		} else if ("/workbench/clue/getActivityByClueId.do".equals(servletPath)) {
			doGetActivityByClueId(request, response);
		} else if ("/workbench/clue/unbund.do".equals(servletPath)) {
			doUnbund(request, response);
		} else if("/workbench/clue/searchByActivityNameAndClueId.do".equals(servletPath)){
			doSearchByActivityNameAndClueId(request,response);
		} else if("/workbench/clue/bundActivity.do".equals(servletPath)){
			doBundActivity(request,response);
		}
	}

	protected void doBundActivity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String clueId = request.getParameter("clueId");
		String[] activityIds = request.getParameterValues("activityId");
		List<ClueActivityRelation> carList = new ArrayList<>();
		for(String activityId : activityIds){
			ClueActivityRelation car = new ClueActivityRelation();
			car.setId(UUIDGenerator.generate());
			car.setClueId(clueId);
			car.setActivityId(activityId);
			carList.add(car);
		}
		
		ClueActivityRelationService cars = (ClueActivityRelationService)new TransactionHandler(new ClueActivityRelationServiceImpl()).getProxy();
		OutJson.printMap(request, response, cars.save(carList));
	}
	
	protected void doSearchByActivityNameAndClueId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String activityName = request.getParameter("activityName");
		String clueId = request.getParameter("clueId");
		ActivityService as = (ActivityService)new TransactionHandler(new ActivityServiceImpl()).getProxy();
		List<Activity> activityList = as.getByNameAndClueId(activityName , clueId);
		OutJson.print(request, response, activityList);
	}
	
	protected void doUnbund(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String relationId = request.getParameter("id");
		ClueActivityRelationService cars = (ClueActivityRelationService)new TransactionHandler(new ClueActivityRelationServiceImpl()).getProxy();
		OutJson.printMap(request, response, cars.deleteById(relationId));
	}
	
	protected void doGetActivityByClueId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String clueId = request.getParameter("clueId");
		ClueActivityRelationService cars = (ClueActivityRelationService)new TransactionHandler(new ClueActivityRelationServiceImpl()).getProxy();
		List<Activity> activityList = cars.getActivityByClueId(clueId);
		OutJson.print(request, response, activityList);
	}
	
	protected void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		Clue clue = clueService.getById(id);
		request.setAttribute("clue", clue);
		request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
	}
	
	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = UUIDGenerator.generate();
		String owner = request.getParameter("owner");
		String company = request.getParameter("company");
		String appellation = request.getParameter("appellation");
		String fullname = request.getParameter("fullname");
		String job = request.getParameter("job");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String website = request.getParameter("website");
		String mphone = request.getParameter("mphone");
		String state = request.getParameter("state");
		String source = request.getParameter("source");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String nextContactTime = request.getParameter("nextContactTime");
		String address = request.getParameter("address");
		String createTime = DateUtil.getSysTime();
		String createBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();

		Clue clue = new Clue();
		clue.setId(id);
		clue.setOwner(owner);
		clue.setCompany(company);
		clue.setAppellation(appellation);
		clue.setFullname(fullname);
		clue.setJob(job);
		clue.setEmail(email);
		clue.setPhone(phone);
		clue.setWebsite(website);
		clue.setMphone(mphone);
		clue.setState(state);
		clue.setSource(source);
		clue.setDescription(description);
		clue.setContactSummary(contactSummary);
		clue.setNextContactTime(nextContactTime);
		clue.setAddress(address);
		clue.setCreateTime(createTime);
		clue.setCreateBy(createBy);

		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		OutJson.printMap(request, response, clueService.save(clue));
	}

	protected void doCreate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.getAll();
		OutJson.print(request, response, userList);
	}

}
