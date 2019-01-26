package com.bjpowernode.crm.workbench.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bjpowernode.crm.domain.Activity;
import com.bjpowernode.crm.domain.ActivityRemark;
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.ExcelUtil;
import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.PinyinUtil;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityRemarkServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

public class ActivityController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActivityService activityService = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		String path = request.getServletPath();
		if("/workbench/activity/save.do".equals(path)){
			doSave(request,response,activityService);
		}else if ("/workbench/activity/list.do".equals(path)) {
			doList(request,response,activityService);
		}else if ("/workbench/activity/edit.do".equals(path)) {
			doEdit(request,response,activityService);
		}else if ("/workbench/activity/update.do".equals(path)) {
			doUpdate(request,response,activityService);
		}else if ("/workbench/activity/delete.do".equals(path)) {
			doDelete(request,response,activityService);
		}else if ("/workbench/activity/detail.do".equals(path)) {
			goDetail(request,response);
		}else if ("/workbench/activity/remarkList.do".equals(path)) {
			doRemarkList(request,response);
		}else if ("/workbench/activity/remarkSave.do".equals(path)) {
			doRemarkSave(request,response);
		}else if ("/workbench/activity/updateActivity.do".equals(path)) {
			doActivityUpdate(request,response);
		}else if ("/workbench/activity/deleteActivity.do".equals(path)) {
			doActivityDelete(request,response);
		}else if ("/workbench/activity/deleteRemark.do".equals(path)) {
			doRemarkDelete(request,response);
		}else if ("/workbench/activity/updateRemark.do".equals(path)) {
			doRemarkUpdate(request,response);
		}else if ("/workbench/activity/exportAll.do".equals(path)) {
			doExportRemark(request,response);
		}else if ("/workbench/activity/exportPart.do".equals(path)) {
			doExportPartRemark(request,response);
		}else if ("/workbench/activity/importActivity.do".equals(path)) {
			doImportActivity(request,response);
		}
	}

	private void doImportActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1.(apache commons fileupload)获取客户端传过来的文件，并将文件保存到本地服务器
		String excelFilePath = null;
		String path = getServletContext().getRealPath("/files");
		String temPath = getServletContext().getRealPath("/temp");
		DiskFileItemFactory disk = new DiskFileItemFactory();
		disk.setSizeThreshold(1024 * 1024);
		disk.setRepository(new File(temPath));
		ServletFileUpload up =new ServletFileUpload(disk);
		try {
			List<FileItem> list = up.parseRequest(request);
			for (FileItem f : list){
				if(f.isFormField()){
					
				}else {
					 String fileName = f.getName();
					 File excelFile = new File(path + "/" + fileName);
					 excelFilePath = excelFile.getAbsolutePath();
					 f.write(excelFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//2 (apache POI) 使用POI组件对excel文件进行解析，获取数据
		ExcelUtil<Activity> excelUtil = new ExcelUtil<>();
		List<Activity> activitieList = excelUtil.read(excelFilePath, Activity.class);
		
		//调用service保存这些数据
		ActivityService activityService = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		Map<String, Object> jsonMap = new HashMap<>();
		if(activityService.saves(activitieList) != 0){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
		
		
	}

	private void doExportPartRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("id");
		ActivityService activityService = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		//设置响应头信息
		response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");  
		List<Activity> list = activityService.getActivityById(ids);
		HSSFWorkbook workbook = ExcelUtil.export("市场活动", Activity.class, list);
		
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
        workbook.write(response.getOutputStream());
		
	}

	private void doExportRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ActivityService activityService = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		//设置响应头信息
		response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");  
		List<Activity> list = activityService.getAllActivity();
		HSSFWorkbook workbook = ExcelUtil.export("市场活动", Activity.class, list);
		
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
        workbook.write(response.getOutputStream());
		
	}

	private void doRemarkUpdate(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String noteContent = request.getParameter("noteContent");
		String editBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();
		String editTime = DateUtil.getSysTime();
		ActivityRemark activityRemark = new ActivityRemark(); 
		activityRemark.setId(id);
		activityRemark.setNoteContent(noteContent);
		activityRemark.setEditBy(editBy);
		activityRemark.setEditTime(editTime);
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		int count = activityRemarkService.updateRemark(activityRemark);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success", true);
			jsonMap.put("noteContent", activityRemark.getNoteContent());
			jsonMap.put("editBy", activityRemark.getEditBy());
			jsonMap.put("editTime", activityRemark.getEditTime());
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doRemarkDelete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		int  count = activityRemarkService.deleteRemarkById(id);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doActivityDelete(HttpServletRequest request, HttpServletResponse response) {
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		String id = request.getParameter("id");
		int count = activityRemarkService.deleteById(id);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count != 0){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doActivityUpdate(HttpServletRequest request, HttpServletResponse response) {
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		String editBy = request.getParameter("editBy");
		String editTime = DateUtil.getSysTime();
		
		Activity activity = new Activity();
		
		activity.setId(id);
		activity.setOwner(owner);
		activity.setName(name);
		activity.setStartTime(startTime);
		activity.setEndTime(endTime);
		activity.setCost(cost);
		activity.setDescription(description);
		activity.setEditBy(editBy);
		activity.setEditTime(editTime);
		int count = activityRemarkService.updateActivityById(activity);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count ==1 ){
			jsonMap.put("success", true);
			jsonMap.put("activity", activity);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doRemarkSave(HttpServletRequest request, HttpServletResponse response) {
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		String noteContent = request.getParameter("noteContent");
		String activityId = request.getParameter("activityId");
		ActivityRemark activityRemark = new ActivityRemark();
		
		activityRemark.setId(UUIDGenerator.generate());
		activityRemark.setNoteContent(noteContent);
		activityRemark.setCreatBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		activityRemark.setCreatTime(DateUtil.getSysTime());
		activityRemark.setEditFlag("0");
		activityRemark.setActivityId(activityId);
		
		Map<String, Object> jsonMap = new HashMap<>();
		if(activityRemarkService.remarkSave(activityRemark) == 1){
			jsonMap.put("success", true);
			jsonMap.put("activityRemark", activityRemark);
		}else {
			jsonMap.put("success", false);
		}
		
		OutJson.print(request, response, jsonMap);
		
	}

	private void doRemarkList(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("success", activityRemarkService.getAll(id));
		OutJson.print(request, response, jsonMap);
		
	}

	private void goDetail(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		String id = request.getParameter("id");
		ActivityRemarkService activityRemarkService = (ActivityRemarkService) new TransactionHandler(new ActivityRemarkServiceImpl()).getProxy();
		Activity activity = activityRemarkService.getActivityById(id); 
		request.setAttribute("activity", activity);
		request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
	}

	private void doDelete(HttpServletRequest request, HttpServletResponse response, ActivityService activityService) {
		String[] ids = request.getParameterValues("id");
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("success", activityService.dodelete(ids));
		OutJson.print(request, response, jsonMap);
		
	}

	private void doUpdate(HttpServletRequest request, HttpServletResponse response, ActivityService activityService) {
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		String editBy = request.getParameter("editBy");
		String editTime = DateUtil.getSysTime();
		Activity activity = new Activity();
		activity.setId(id);
		activity.setOwner(owner);
		activity.setName(name);
		activity.setStartTime(startTime);
		activity.setEndTime(endTime);
		activity.setCost(cost);
		activity.setDescription(description);
		activity.setEditBy(editBy);
		activity.setEditTime(editTime);
		OutJson.print(request, response, activityService.update(activity));
		
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response, ActivityService activityService) {
		String id = request.getParameter("id");
		Activity activity = activityService.edit(id);
		if(activity != null){
			OutJson.print(request, response, activity);
		}
	}

	private void doList(HttpServletRequest request, HttpServletResponse response, ActivityService activityService) {
		String name = request.getParameter("name");
		String owner = request.getParameter("owner");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("owner", owner);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("startIndex", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		Map<String, Object> jsonMap = activityService.doList(map);
		OutJson.print(request, response, jsonMap);
		
		
	}

	private void doSave(HttpServletRequest request, HttpServletResponse response, ActivityService activityService) {
			String id = UUIDGenerator.generate();
			String owner = request.getParameter("owner");
			String name = request.getParameter("name");
			String namePingYin = PinyinUtil.getPingYin(name);
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String cost = request.getParameter("cost");
			String description = request.getParameter("description");
			String creatBy = request.getParameter("creatBy");
			String creatTime = DateUtil.getSysTime();
			
			Activity activity = new Activity();
			activity.setId(id);
			activity.setOwner(owner);
			activity.setName(name);
			activity.setStartTime(startTime);
			activity.setEndTime(endTime);
			activity.setCost(cost);
			activity.setDescription(description);
			activity.setCreatBy(creatBy);
			activity.setCreatTime(creatTime);
			
			int count = activityService.save(activity);
			Map< String, Object> jsonMap = new HashMap<String, Object>();
			if(count == 1){
				jsonMap.put("success", true);
			}else {
				jsonMap.put("success", false);
			}
			OutJson.print(request, response, jsonMap);
	}
}
