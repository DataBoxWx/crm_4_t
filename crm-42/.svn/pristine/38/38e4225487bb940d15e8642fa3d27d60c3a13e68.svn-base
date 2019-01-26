package com.bjpowernode.crm.workbench.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.Const;
import com.bjpowernode.crm.utils.DateUtil;
import com.bjpowernode.crm.utils.ExcelUtil;
import com.bjpowernode.crm.utils.OutJson;
import com.bjpowernode.crm.utils.PinyinUtil;
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
		} else if ("/workbench/activity/deleteRemark.do".equals(servletPath)) {
			doDeleteRemark(request, response);
		} else if ("/workbench/activity/updateRemark.do".equals(servletPath)) {
			doUpdateRemark(request, response);
		} else if ("/workbench/activity/exportAll.do".equals(servletPath)) {
			doExportAll(request, response);
		} else if ("/workbench/activity/exportCheckedAll.do".equals(servletPath)) {
			doExportCheckedAll(request, response);
		} else if("/workbench/activity/import.do".equals(servletPath)){
			doImport(request,response);
		}
	}
	
	protected void doImport(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//1、（apache commons fileupload）获取客户端传过来的文件，并将文件保存到本地服务器。
		String excelFilePath = null;
	    String path = getServletContext().getRealPath("/files");
	    String tmpPath = getServletContext().getRealPath("/tmp");
	    DiskFileItemFactory disk = new DiskFileItemFactory();
	    disk.setSizeThreshold(1024 * 1024); //超1MB之后生成临时文件
	    disk.setRepository(new File(tmpPath));
	    ServletFileUpload up = new ServletFileUpload(disk);
	    try{
	      List<FileItem> list = up.parseRequest(request);
	      for(FileItem file:list){
	    	  if(file.isFormField()){
	    	  }else{
	  	        String fileName = file.getName();
	  	        File excelFile = new File(path+"/"+fileName);
	  	        excelFilePath = excelFile.getAbsolutePath();
	  	        file.write(excelFile);	  
	    	  }
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    
		//2、（apache POI）使用POI组件对excel文件进行解析，获取数据
	    ExcelUtil<Activity> excelUtil = new ExcelUtil<>();
	    List<Activity> activityList = excelUtil.read(excelFilePath, Activity.class);
	    
		//3、调用service保存这些数据
	    ActivityService activityService = (ActivityService)new TransactionHandler(new ActivityServiceImpl()).getProxy();
	    OutJson.printMap(request, response, activityService.save2(activityList));
	}
	
	protected void doExportCheckedAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		//设置响应头信息
        response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");    
        
		ActivityService activityService = (ActivityService)new TransactionHandler(new ActivityServiceImpl()).getProxy();
		String[] ids = request.getParameterValues("id");
		List<Activity> activityList = activityService.getById3(ids);
		HSSFWorkbook workbook = ExcelUtil.export("市场活动表一", Activity.class, activityList);
		
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
        workbook.write(response.getOutputStream());
	}
	

	protected void doExportAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		//设置响应头信息
        response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");    
        
		ActivityService activityService = (ActivityService)new TransactionHandler(new ActivityServiceImpl()).getProxy();
		List<Activity> activityList = activityService.getAll();
		HSSFWorkbook workbook = ExcelUtil.export("市场活动表一", Activity.class, activityList);
		
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
        workbook.write(response.getOutputStream());
	}
	
	protected void doUpdateRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String noteContent = request.getParameter("noteContent");
		String editTime = DateUtil.getSysTime();
		String editBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();
		String editFlag = "1";

		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setEditBy(editBy);
		ar.setEditTime(editTime);
		ar.setEditFlag(editFlag);

		ActivityRemarkService ars = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl())
				.getProxy();
		boolean ok = ars.update(ar);

		Map<String, Object> jsonMap = new HashMap<>();
		if (ok) {
			jsonMap.put("success", true);
			jsonMap.put("noteContent", ar.getNoteContent());
			jsonMap.put("editTime", ar.getEditTime());
			jsonMap.put("editBy", ar.getEditBy());
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	protected void doDeleteRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ActivityRemarkService ars = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl())
				.getProxy();
		OutJson.printMap(request, response, ars.deleteById(id));
	}

	protected void doSaveRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String noteContent = request.getParameter("noteContent");
		String activityId = request.getParameter("activityId");
		String id = UUIDGenerator.generate();
		String createTime = DateUtil.getSysTime();
		String createBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();
		String editFlag = "0";

		ActivityRemark ar = new ActivityRemark();
		ar.setId(id);
		ar.setNoteContent(noteContent);
		ar.setCreateTime(createTime);
		ar.setCreateBy(createBy);
		ar.setEditFlag(editFlag);
		ar.setActivityId(activityId);

		ActivityRemarkService ars = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl())
				.getProxy();
		int count = ars.save(ar);

		Map<String, Object> jsonMap = new HashMap<>();
		if (count == 1) {
			jsonMap.put("success", true);
			jsonMap.put("activityRemark", ar);
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);

	}

	protected void doListRemark(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String activityId = request.getParameter("activityId");
		ActivityRemarkService ars = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl())
				.getProxy();
		List<ActivityRemark> activityRemarkList = ars.getByActivityId(activityId);
		OutJson.print(request, response, activityRemarkList);
	}

	protected void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ActivityService as = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		Activity activity = (Activity) as.getById2(id);
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
		String editBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();
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
		String namepinyin = PinyinUtil.getPingYin(name); 
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
		activity.setNamepinyin(namepinyin);

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
