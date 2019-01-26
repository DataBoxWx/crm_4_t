package com.bjpowernode.crm.workbench.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream.PutField;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.service.UserService;
import com.bjpowernode.crm.service.impl.UserServiceImpl;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.ExcelUtil;
import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueActivityRelationServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueRemarkServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

@WebServlet(urlPatterns = { "/workbench/clue/getOwnerClue.do", "/workbench/clue/saveClue.do",
		"/workbench/clue/displayAll.do", "/workbench/clue/detail.do", "/workbench/clue/saveRemark.do",
		"/workbench/clue/displayRemark.do", "/workbench/clue/relationList.do", "/workbench/clue/exportAll.do",
		"/workbench/clue/unbundActivity.do", "/workbench/clue/getByClueId.do",
		"/workbench/clue/RelationByActivityIdAndClueId.do", "/workbench/clue/convert.do",
		"/workbench/clue/getClueById.do", "/workbench/clue/updateClueById.do","/workbench/clue/deleteClueById.do",
		"/workbench/clue/importClue.do","/workbench/clue/updateClueRemark.do","/workbench/clue/deleteRemarkById.do",
		"/workbench/clue/getUser.do","/workbench/clue/deleteClueAndRemarkAndRelationById.do",
		"/workbench/clue/getAllActivity.do"})
public class ClueController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		if ("/workbench/clue/getOwnerClue.do".equals(path)) {
			doGetOwnerClueResource(request, response);
		} else if ("/workbench/clue/saveClue.do".equals(path)) {
			doSaveClue(request, response);
		} else if ("/workbench/clue/displayAll.do".equals(path)) {
			doDisplayClue(request, response);
		} else if ("/workbench/clue/detail.do".equals(path)) {
			goDetailClue(request, response);
		} else if ("/workbench/clue/saveRemark.do".equals(path)) {
			doSaveRemark(request, response);
		} else if ("/workbench/clue/displayRemark.do".equals(path)) {
			doDisplayRemark(request, response);
		} else if ("/workbench/clue/relationList.do".equals(path)) {
			doRelationList(request, response);
		} else if ("/workbench/clue/exportAll.do".equals(path)) {
			doExportAllClue(request, response);
		} else if ("/workbench/clue/unbundActivity.do".equals(path)) {
			doUnbundActivity(request, response);
		} else if ("/workbench/clue/getByClueId.do".equals(path)) {
			doGetByClueId(request, response);
		} else if ("/workbench/clue/RelationByActivityIdAndClueId.do".equals(path)) {
			doRelationByActivityIdAndClueId(request, response);
		} else if ("/workbench/clue/convert.do".equals(path)) {
			doConvertByClueId(request, response);
		} else if ("/workbench/clue/getClueById.do".equals(path)) {
			doGetClueById(request, response);
		} else if ("/workbench/clue/updateClueById.do".equals(path)) {
			doUpdateClueById(request, response);
		}else if ("/workbench/clue/deleteClueById.do".equals(path)) {
			doDeleteClueById(request, response);
		}else if ("/workbench/clue/importClue.do".equals(path)) {
			doImportClue(request, response);
		}else if ("/workbench/clue/updateClueRemark.do".equals(path)) {
			doUpdateClueRemark(request, response);
		}else if ("/workbench/clue/deleteRemarkById.do".equals(path)) {
			doDeleteRemarkById(request, response);
		}else if ("/workbench/clue/getUser.do".equals(path)) {
			doGetUser(request, response);
		}else if ("/workbench/clue/deleteClueAndRemarkAndRelationById.do".equals(path)) {
			doDeleteClueAndRemarkAndRelationById(request, response);
		}else if ("/workbench/clue/getAllActivity.do".equals(path)) {
			doGetAllActivity(request, response);
		}
	}

	private void doGetAllActivity(HttpServletRequest request, HttpServletResponse response) {
		ActivityService activityService = (ActivityService) new TransactionHandler(new ActivityServiceImpl()).getProxy();
		List<Activity> ActivityList = activityService.getAll();
		OutJson.print(request, response, ActivityList);
		
	}

	private void doDeleteClueAndRemarkAndRelationById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		int count = clueService.DeleteClueAndRemarkAndRelationById(id);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count != 0 ){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doGetUser(HttpServletRequest request, HttpServletResponse response) {
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.doList();
		OutJson.print(request, response, userList);
	}

	private void doDeleteRemarkById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ClueRemarkService clueRemarkService = (ClueRemarkService) new TransactionHandler(new ClueRemarkServiceImpl()).getProxy();
		int count = clueRemarkService.deleteRemarkById(id);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1 ){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doUpdateClueRemark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String noteContent = request.getParameter("noteContent");
		ClueRemarkService clueRemarkService = (ClueRemarkService) new TransactionHandler(new ClueRemarkServiceImpl()).getProxy();
		ClueRemark clueRemark = new ClueRemark();
		clueRemark.setId(id);
		clueRemark.setNoteContent(noteContent);
		clueRemark.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		clueRemark.setEditTime(DateUtil.getSysTime());
		clueRemark.setEditFlag("1");
		int count = clueRemarkService.updateRemarkById(clueRemark);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1 ){
			jsonMap.put("clueRemark", clueRemark);
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
		
	}

	private void doImportClue(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
				ExcelUtil<Clue> excelUtil = new ExcelUtil<>();
				List<Clue> clueList = excelUtil.read(excelFilePath, Clue.class);
				
				//调用service保存这些数据
				ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
				Map<String, Object> jsonMap = new HashMap<>();
				if(clueService.saves(clueList) != 0){
					jsonMap.put("success", true);
				}else {
					jsonMap.put("success", false);
				}
				OutJson.print(request, response, jsonMap);;
		
	}

	private void doDeleteClueById(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		int count = clueService.deleteClueById(ids);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count != 0){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", true);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doUpdateClueById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String company = request.getParameter("company");
		String appellation = request.getParameter("appellation");
		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String web = request.getParameter("web");
		String phone = request.getParameter("phone");
		String clueState = request.getParameter("clueStatus");
		String clueFrom = request.getParameter("clueFrom");
		String clueDesc = request.getParameter("clueDesc");
		String relationSummary = request.getParameter("relationSummary");
		String relationTimeNext = request.getParameter("relationTimeNext");
		String address = request.getParameter("address");
		
		Clue clue = new Clue();
		
		clue.setId(id);
		clue.setOwner(owner);
		clue.setCompany(company);
		clue.setAppellation(appellation);
		clue.setName(name);
		clue.setPosition(position);
		clue.setEmail(email);
		clue.setTelephone(telephone);
		clue.setWeb(web);
		clue.setPhone(phone);
		clue.setClueState(clueState);
		clue.setClueFrom(clueFrom);
		clue.setClueDesc(clueDesc);
		clue.setRelationSummary(relationSummary);
		clue.setRelationTimeNext(relationTimeNext);
		clue.setAddress(address);
		clue.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		clue.setEditTime(DateUtil.getSysTime());
		
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		int count = clueService.updateClue(clue);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("clue", clue);
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);

	}

	private void doGetClueById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("clueId");
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		Map<String, Object> jsonMap = clueService.getClueByIdAndUser(id);
		OutJson.print(request, response, jsonMap);
	}

	private void doConvertByClueId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String flags = request.getParameter("flag");
		String clueId = request.getParameter("clueId");
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		
		Transaction transaction = null;
		
		if(flags.equals("1") ){
			String money = request.getParameter("money");
			String company = request.getParameter("company");
			String expectedTime = request.getParameter("expectedTime");
			String stage = request.getParameter("stage");
			String activityId = request.getParameter("activity");
			transaction  = new Transaction();
			transaction.setId(UUIDGenerator.generate());
			
			transaction.setMoney(money);
			transaction.setName(company);
			transaction.setExpectedTime(expectedTime);
			
			transaction.setStage(stage);
			
			transaction.setActivityId(activityId);
			
			transaction.setCreatTime(DateUtil.getSysTime());
			
		}
		String operator = ((User)request.getSession().getAttribute(Const.SESSION_USER)).getName();	
		Boolean flag = clueService.getClueById2(clueId,transaction,operator);
		if (flag) {
			response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
		} else {
			response.getWriter().write("{\"success\" : false}");
		}

	}

	private void doRelationByActivityIdAndClueId(HttpServletRequest request, HttpServletResponse response) {
		String clueId = request.getParameter("clueId");
		String[] activityId = request.getParameterValues("activityId");
		System.out.println(Arrays.toString(activityId));
		List<ClueActivityRelation> list = new ArrayList<>();
		for (String id : activityId) {
			ClueActivityRelation car = new ClueActivityRelation();
			car.setId(UUIDGenerator.generate());
			car.setActivityId(id);
			car.setClueId(clueId);
			list.add(car);
		}

		ClueActivityRelationService clueActivityRelation = (ClueActivityRelationService) new TransactionHandler(
				new ClueActivityRelationServiceImpl()).getProxy();
		int count = clueActivityRelation.saveByActivityIdAndClueId(list);
		Map<String, Object> jsonMap = new HashMap<>();
		if (count != 0) {
			jsonMap.put("success", true);
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doGetByClueId(HttpServletRequest request, HttpServletResponse response) {
		String clueId = request.getParameter("clueId");
		String name = request.getParameter("name");
		ActivityService activityService = (ActivityService) new TransactionHandler(new ActivityServiceImpl())
				.getProxy();
		List<Activity> list = activityService.getByClueId(name, clueId);
		OutJson.print(request, response, list);

	}

	private void doUnbundActivity(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ClueActivityRelationService clueActivityRelation = (ClueActivityRelationService) new TransactionHandler(
				new ClueActivityRelationServiceImpl()).getProxy();
		int count = clueActivityRelation.unbundRelation(id);
		Map<String, Object> jsonMap = new HashMap<>();
		if (count == 1) {
			jsonMap.put("success", true);
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);

	}

	private void doExportAllClue(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		// 设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		// 设置响应头信息
		response.setHeader("Content-disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
		List<Clue> list = clueService.getAllClue();
		HSSFWorkbook workbook = ExcelUtil.export("市场活动", Clue.class, list);

		// 将workbook响应到浏览器（浏览器上呈现的是文件下载！）
		workbook.write(response.getOutputStream());

	}

	private void doRelationList(HttpServletRequest request, HttpServletResponse response) {
		String clueId = request.getParameter("clueId");
		ClueActivityRelationService clueActivityRelation = (ClueActivityRelationService) new TransactionHandler(
				new ClueActivityRelationServiceImpl()).getProxy();
		List<Activity> list = clueActivityRelation.getAllByClueId(clueId);
		OutJson.print(request, response, list);

	}

	private void doDisplayRemark(HttpServletRequest request, HttpServletResponse response) {
		ClueRemarkService clueRemarkService = (ClueRemarkService) new TransactionHandler(new ClueRemarkServiceImpl())
				.getProxy();
		List<ClueRemark> list = clueRemarkService.displayRemark();

		OutJson.print(request, response, list);

	}

	private void doSaveRemark(HttpServletRequest request, HttpServletResponse response) {
		String noteContent = request.getParameter("noteContent");
		String clueId = request.getParameter("clueId");
		ClueRemark clueRemark = new ClueRemark();
		clueRemark.setId(UUIDGenerator.generate());
		clueRemark.setNoteContent(noteContent);
		clueRemark.setCreatBy(((User) request.getSession().getAttribute(Const.SESSION_USER)).getName());
		clueRemark.setCreatTime(DateUtil.getSysTime());
		clueRemark.setEditFlag("0");
		clueRemark.setClueId(clueId);
		ClueRemarkService clueRemarkService = (ClueRemarkService) new TransactionHandler(new ClueRemarkServiceImpl())
				.getProxy();

		Map<String, Object> jsonMap = new HashMap<>();
		int count = clueRemarkService.saveRemark(clueRemark);
		if (count == 1) {
			jsonMap.put("success", true);
			jsonMap.put("clueRemark", clueRemark);
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void goDetailClue(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		Clue clue = clueService.getClueById(id);
		request.setAttribute("clue", clue);
		request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);

	}

	private void doDisplayClue(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String company = request.getParameter("company");
		String telephone = request.getParameter("telephone");
		String source = request.getParameter("source");
		String owner = request.getParameter("owner");
		String phone = request.getParameter("phone");
		String clueState = request.getParameter("clueState");
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int startIndex = (Integer.valueOf(request.getParameter("pageNo")) - 1) * pageSize;

		Map<String, Object> ParMap = new HashMap<>();

		ParMap.put("name", name);
		ParMap.put("company", company);
		ParMap.put("telephone", telephone);
		ParMap.put("source", source);
		ParMap.put("owner", owner);
		ParMap.put("phone", phone);
		ParMap.put("clueState", clueState);
		ParMap.put("startIndex", startIndex);
		ParMap.put("pageSize", pageSize);

		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		Map<String, Object> jsonMap = clueService.display(ParMap);

		OutJson.print(request, response, jsonMap);
	}

	private void doSaveClue(HttpServletRequest request, HttpServletResponse response) {
		String id = UUIDGenerator.generate();
		String owner = request.getParameter("owner");
		String company = request.getParameter("company");
		String appellation = request.getParameter("appellation");
		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String web = request.getParameter("web");
		String phone = request.getParameter("phone");
		String clueState = request.getParameter("clueState");
		String clueFrom = request.getParameter("clueFrom");
		String clueDesc = request.getParameter("clueDesc");
		String relationSummary = request.getParameter("relationSummary");
		String relationTimeNext = request.getParameter("relationTimeNext");
		String address = request.getParameter("address");
		String creatBy = ((User) request.getSession().getAttribute(Const.SESSION_USER)).getName();
		String creatTime = DateUtil.getSysTime();

		Clue clue = new Clue();

		clue.setId(id);
		clue.setOwner(owner);
		clue.setCompany(company);
		clue.setAppellation(appellation);
		clue.setName(name);
		clue.setPosition(position);
		clue.setEmail(email);
		clue.setTelephone(telephone);
		clue.setWeb(web);
		clue.setPhone(phone);
		clue.setClueState(clueState);
		clue.setClueFrom(clueFrom);
		clue.setClueDesc(clueDesc);
		clue.setRelationSummary(relationSummary);
		clue.setRelationTimeNext(relationTimeNext);
		clue.setAddress(address);
		clue.setCreatBy(creatBy);
		clue.setCreatTime(creatTime);

		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		int count = clueService.saveClue(clue);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (count == 1) {
			jsonMap.put("success", true);
		} else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doGetOwnerClueResource(HttpServletRequest request, HttpServletResponse response) {
		ClueService clueService = (ClueService) new TransactionHandler(new ClueServiceImpl()).getProxy();
		Map<String, Object> jsonMap = clueService.getOwnerClueResource();
		if (jsonMap.get(0) != null && jsonMap.get(1) != null) {
			jsonMap.put("success", true);
		} else {
			jsonMap.put("success", true);
		}
		OutJson.print(request, response, jsonMap);
	}
}
