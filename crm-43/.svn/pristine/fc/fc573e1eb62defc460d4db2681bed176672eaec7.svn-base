package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.exception.ChangeStageException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.Const;
import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.OutJson;
import com.bjpowernode.crm.utils.TransactionHandler;
import com.bjpowernode.crm.utils.UUIDGenerator;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.impl.TranHistoryServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TranServiceImpl;

public class TranController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/workbench/transaction/create.do".equals(servletPath)) {
			doCreate(request, response);
		} else if ("/workbench/transaction/save.do".equals(servletPath)) {
			doSave(request, response);
		} else if ("/workbench/transaction/detail.do".equals(servletPath)) {
			doDetail(request, response);
		} else if("/workbench/transaction/listHistory.do".equals(servletPath)){
			doListHistory(request,response);
		} else if("/workbench/transaction/changeStage.do".equals(servletPath)){
			doChangeStage(request,response);
		} else if("/workbench/chart/transaction/getCountByStage.do".equals(servletPath)){
			doGetCountByStage(request,response);
		}
	}

	protected void doGetCountByStage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TranService tranService = (TranService)new TransactionHandler(new TranServiceImpl()).getProxy();
		//List<Map<String,Object>> jsonList = tranService.getCountByStage();
		//OutJson.print(request, response, jsonList);
		
		List<Map<String,Object>> jsonList = new ArrayList<>();
		ServletContext application = this.getServletContext();
		Map<String,String> possibilityMap = (Map<String,String>)application.getAttribute("possibilityMap");
		List<String> stageList = new ArrayList<>(possibilityMap.keySet());
		Random r = new Random();
		for(int i=0;i<stageList.size();i++){
			Map<String,Object> map = new HashMap<>();
			map.put("name", stageList.get(i));
			map.put("value", r.nextInt(101));
			jsonList.add(map);
		}
		OutJson.print(request, response, jsonList);
		
	}
	
	protected void doChangeStage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String stage = request.getParameter("stage");
		String money = request.getParameter("money");
		String expectedDate = request.getParameter("expectedDate");
		
		Tran tran = new Tran();
		tran.setMoney(money);
		tran.setExpectedDate(expectedDate);
		tran.setId(id);
		tran.setStage(stage);
		tran.setEditTime(DateUtil.getSysTime());
		tran.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		
		Map<String,Object> jsonMap = new HashMap<>();
		try {
			TranService ts = (TranService) new TransactionHandler(new TranServiceImpl()).getProxy();
			ts.update(tran);
			jsonMap.put("success", true);
			jsonMap.put("stage", tran.getStage());
			ServletContext application = this.getServletContext();
			Map<String,String> possibilityMap = (Map<String,String>)application.getAttribute("possibilityMap");
			jsonMap.put("possibility", possibilityMap.get(tran.getStage()));
			jsonMap.put("editBy", tran.getEditBy());
			jsonMap.put("editTime", tran.getEditTime());
		} catch (ChangeStageException e) {
			jsonMap.put("success", false);
			jsonMap.put("errorMsg", e.getMessage());
 		} catch (Exception e){
 			jsonMap.put("success", false);
 			jsonMap.put("errorMsg", "更新阶段失败！");
 		}
		OutJson.print(request, response, jsonMap);
	}
	
	protected void doListHistory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tranId = request.getParameter("tranId");
		TranHistoryService ths = (TranHistoryService)new TransactionHandler(new TranHistoryServiceImpl()).getProxy();
		List<TranHistory> thList = ths.getByTranId(tranId);
		ServletContext application = getServletContext();
		Map<String,String> possibilityMap = (Map<String,String>)application.getAttribute("possibilityMap");
		for(TranHistory th : thList){
			th.setPossibility(possibilityMap.get(th.getStage()));
		}
		OutJson.print(request, response, thList);
	}
	
	protected void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		TranService ts = (TranService) new TransactionHandler(new TranServiceImpl()).getProxy();
		Tran tran = ts.getById(id);
		ServletContext application = getServletContext();
		Map<String,String> possibilityMap = (Map<String,String>)application.getAttribute("possibilityMap");
		tran.setPossibility(possibilityMap.get(tran.getStage()));
		request.setAttribute("tran", tran);
		request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
	}
	
	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = UUIDGenerator.generate();
		String owner = request.getParameter("owner");
		String money = request.getParameter("money");
		String name = request.getParameter("name");
		String expectedDate = request.getParameter("expectedDate");
		String customerName = request.getParameter("customerName");
		String stage = request.getParameter("stage");
		String type = request.getParameter("type");
		String source = request.getParameter("source");
		String activityId = request.getParameter("activityId");
		String contactsId = request.getParameter("contactsId");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String nextContactTime = request.getParameter("nextContactTime");
		String createTime = DateUtil.getSysTime();
		String createBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();

		Tran tran = new Tran();
		tran.setId(id);
		tran.setOwner(owner);
		tran.setMoney(money);
		tran.setName(name);
		tran.setExpectedDate(expectedDate);
		tran.setStage(stage);
		tran.setType(type);
		tran.setSource(source);
		tran.setActivityId(activityId);
		tran.setContactsId(contactsId);
		tran.setDescription(description);
		tran.setContactSummary(contactSummary);
		tran.setNextContactTime(nextContactTime);
		tran.setCreateTime(createTime);
		tran.setCreateBy(createBy);

		TranService ts = (TranService) new TransactionHandler(new TranServiceImpl()).getProxy();
		boolean saveSuccess = ts.save(tran, customerName);

		if (saveSuccess) {
			response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
		}

	}

	protected void doCreate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.getAll();
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
	}

}
