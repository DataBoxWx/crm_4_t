package com.bjpowernode.crm.workbench.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.ExcelUtil;
import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.PinyinUtil;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;
import com.bjpowernode.crm.workbench.domain.ContactsRemark;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.service.ContactsActivityRelationService;
import com.bjpowernode.crm.workbench.service.ContactsRemarkService;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.impl.ContactsActivityRelationServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ContactsRemarkServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ContactsServiceImpl;

@WebServlet(urlPatterns = { "/workbench/contacts/displayContacts.do", "/workbench/contacts/getByName.do",
		"/workbench/contacts/saveContacts.do","/workbench/contacts/getById.do","/workbench/contacts/update.do",
		"/workbench/contacts/export.do","/workbench/contacts/delete.do","/workbench/contacts/import.do",
		"/workbench/contacts/detail.do","/workbench/contacts/displayRemark.do","/workbench/contacts/saveRemark.do",
		"/workbench/contacts/updateRemark.do","/workbench/contacts/deleteReamrk.do","/workbench/contacts/displayTransaction.do",
		"/workbench/contacts/displayActivity.do","/workbench/contacts/getActivityById.do","/workbench/contacts/bundActivity.do",
		"/workbench/contacts/unbund.do"})
public class ContactsController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		if ("/workbench/contacts/displayContacts.do".equals(path)) {
			doDisplayContacts(request, response);
		} else if ("/workbench/contacts/getByName.do".equals(path)) {
			doGetByName(request, response);
		}else if ("/workbench/contacts/saveContacts.do".equals(path)) {
			doSaveContacts(request, response);
		}else if ("/workbench/contacts/getById.do".equals(path)) {
			doGetById(request, response);
		}else if ("/workbench/contacts/update.do".equals(path)) {
			doUpdate(request, response);
		}else if ("/workbench/contacts/export.do".equals(path)) {
			doExport(request, response);
		}else if ("/workbench/contacts/delete.do".equals(path)) {
			doDeleteContacts(request, response);
		}else if ("/workbench/contacts/import.do".equals(path)) {
			doImport(request, response);
		}else if ("/workbench/contacts/detail.do".equals(path)) {
			doDetail(request, response);
		}else if ("/workbench/contacts/displayRemark.do".equals(path)) {
			doDisplayRemark(request, response);
		}else if ("/workbench/contacts/saveRemark.do".equals(path)) {
			doSaveRemark(request, response);
		}else if ("/workbench/contacts/updateRemark.do".equals(path)) {
			doUpdateRemark(request, response);
		}else if ("/workbench/contacts/deleteReamrk.do".equals(path)) {
			doDeleteReamrk(request, response);
		}else if ("/workbench/contacts/displayTransaction.do".equals(path)) {
			doDisplayTransaction(request, response);
		}else if ("/workbench/contacts/displayActivity.do".equals(path)) {
			doDisplayActivity(request, response);
		}else if ("/workbench/contacts/getActivityById.do".equals(path)) {
			doGetActivityById(request, response);
		}else if ("/workbench/contacts/bundActivity.do".equals(path)) {
			doBundActivity(request, response);
		}else if ("/workbench/contacts/unbund.do".equals(path)) {
			doUnbund(request, response);
		}
	}

	private void doUnbund(HttpServletRequest request, HttpServletResponse response) {
		String activityId = request.getParameter("activityId");
		String contactsId = request.getParameter("contactsId");
		ContactsActivityRelationService cars = (ContactsActivityRelationService)new TransactionHandler(new ContactsActivityRelationServiceImpl()).getProxy();
		Boolean success = cars.unbund(activityId,contactsId);
		OutJson.printMap(request, response, success);
	}

	private void doBundActivity(HttpServletRequest request, HttpServletResponse response) {
		String[] activityId = request.getParameterValues("activityId");
		String contactsId = request.getParameter("contactsId");
		List<ContactsActivityRelation> list = new ArrayList<>();
		for(int i = 0;i < activityId.length;i++){
			ContactsActivityRelation car = new ContactsActivityRelation();
			car.setId(UUIDGenerator.generate());
			car.setActivityId(activityId[i]);
			car.setContactsId(contactsId);
			list.add(car);
		}
		ContactsActivityRelationService cars = (ContactsActivityRelationService)new TransactionHandler(new ContactsActivityRelationServiceImpl()).getProxy();
		Boolean success = cars.bundRelarion(list);
		OutJson.printMap(request, response, success);
	}

	private void doGetActivityById(HttpServletRequest request, HttpServletResponse response) {
		String contactsId = request.getParameter("contactsId");
		String name = request.getParameter("name");
		String pingYin = PinyinUtil.getPingYin(name);
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl()).getProxy();
		List<Activity> activityList = contactsService.getActivityById(contactsId,name,pingYin);
		OutJson.print(request, response, activityList);
		
	}

	private void doDisplayActivity(HttpServletRequest request, HttpServletResponse response) {
		String contactsId = request.getParameter("contactsId");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl()).getProxy();
		List<Activity> activityList = contactsService.getActivityByContactsId(contactsId);
		OutJson.print(request, response, activityList);
	}

	private void doDisplayTransaction(HttpServletRequest request, HttpServletResponse response) {
		String contactsId = request.getParameter("contactsid");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl()).getProxy();
		List<Transaction> tranList = contactsService.getByContactsId(contactsId);
		Map<String, Object> possiblityMap = (Map<String, Object>) request.getServletContext().getAttribute("possibly");
		for (Transaction transaction : tranList) {
			transaction.setPossiblity((String)possiblityMap.get(transaction.getStage()));
		}
		OutJson.print(request, response, tranList);
	}

	private void doDeleteReamrk(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ContactsRemarkService contactsRemarkService = (ContactsRemarkService)new TransactionHandler(new ContactsRemarkServiceImpl()).getProxy();
		Boolean success = contactsRemarkService.deleteRemark(id);
		OutJson.printMap(request, response, success);
		
	}

	private void doUpdateRemark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String noteContent = request.getParameter("noteContent");
		ContactsRemark contactsRemark = new ContactsRemark();
		contactsRemark.setId(id);
		contactsRemark.setNoteContent(noteContent);
		contactsRemark.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		contactsRemark.setEditFlag("1");
		contactsRemark.setEditTime(DateUtil.getSysTime());
		ContactsRemarkService contactsRemarkService = (ContactsRemarkService)new TransactionHandler(new ContactsRemarkServiceImpl()).getProxy();
		Boolean success = contactsRemarkService.update(contactsRemark);
		Map<String, Object> jsonMap = new HashMap<>();
		if(success){
			jsonMap.put("success", success);
			jsonMap.put("contactsRemark", contactsRemark);	
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doSaveRemark(HttpServletRequest request, HttpServletResponse response) {
		String noteContent = request.getParameter("noteContent");
		String contactsId = request.getParameter("contactsId");
		
		ContactsRemark contactsRemark = new ContactsRemark();
		contactsRemark.setId(UUIDGenerator.generate());
		contactsRemark.setNoteContent(noteContent);
		contactsRemark.setCreatBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		contactsRemark.setCreatTime(DateUtil.getSysTime());
		contactsRemark.setEditFlag("0");
		contactsRemark.setContactId(contactsId);
		
		ContactsRemarkService contactsRemarkService = (ContactsRemarkService)new TransactionHandler(new ContactsRemarkServiceImpl()).getProxy();
		Boolean success = contactsRemarkService.saveRemark(contactsRemark);
		Map<String, Object> jsonMap = new HashMap<>();
		if(success){
			jsonMap.put("success", success);
			jsonMap.put("contactsRemark", contactsRemark);	
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doDisplayRemark(HttpServletRequest request, HttpServletResponse response) {
		String contactsId = request.getParameter("contactsId");
		ContactsRemarkService contactsRemarkService = (ContactsRemarkService)new TransactionHandler(new ContactsRemarkServiceImpl()).getProxy();
		List<ContactsRemark> contactsRemarksList = contactsRemarkService.displayRemark(contactsId);
		OutJson.print(request, response, contactsRemarksList);
	}

	private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		Contacts contacts = contactsService.getById2(id);
		request.setAttribute("contacts", contacts);
		request.getRequestDispatcher("/workbench/contacts/detail.jsp").forward(request, response);
		
	}

	private void doImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		ExcelUtil<Contacts> excelUtil = new ExcelUtil<>();
		List<Contacts> contactsList = excelUtil.read(excelFilePath, Contacts.class);
		
		//调用service保存这些数据
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		Map<String, Object> jsonMap = new HashMap<>();
		if(contactsService.saves(contactsList) != 0){
			jsonMap.put("success", true);
		}else {
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);;
		
	}

	private void doDeleteContacts(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		Boolean success = contactsService.delete(ids);
		OutJson.printMap(request, response, success);
		
	}

	private void doExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		
		//设置响应头信息
		response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");  
		List<Contacts> list = contactsService.getAll();
		HSSFWorkbook workbook = ExcelUtil.export("联系人", Contacts.class, list);
			
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
	        workbook.write(response.getOutputStream());
		
	}

	private void doUpdate(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String source = request.getParameter("source");
		String name = request.getParameter("name");
		String appellation = request.getParameter("appellation");
		String position = request.getParameter("position");
		String phone = request.getParameter("phone");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String customerName = request.getParameter("customerName");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String relationTimeNext = request.getParameter("relationTimeNext");
		String address = request.getParameter("address");
		
		Contacts contacts = new Contacts();
		
		contacts.setId(id);
		contacts.setOwner(owner);
		contacts.setSource(source);
		contacts.setName(name);
		contacts.setAppellation(appellation);
		contacts.setPosition(position);
		contacts.setPhone(phone);
		contacts.setBirthday(birthday);
		contacts.setEmail(email);
		
		contacts.setDescription(description);
		contacts.setContactSummary(contactSummary);
		contacts.setRelationTimeNext(relationTimeNext);
		contacts.setAddress(address);
		contacts.setEditTime( DateUtil.getSysTime());
		contacts.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		Boolean success = contactsService.update(contacts,customerName);
		OutJson.printMap(request, response, success);
	}

	private void doGetById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		Map<String, Object> jsonMap =  contactsService.getById(id);
		OutJson.print(request, response, jsonMap);
	}

	private void doSaveContacts(HttpServletRequest request, HttpServletResponse response) {
		String id = UUIDGenerator.generate();
		String owner = request.getParameter("owner");
		String source = request.getParameter("source");
		String name = request.getParameter("name");
		String appellation = request.getParameter("appellation");
		String position = request.getParameter("position");
		String phone = request.getParameter("phone");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String customerName = request.getParameter("customerName");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String relationTimeNext = request.getParameter("relationTimeNext");
		String address = request.getParameter("address");
		
		Contacts contacts = new Contacts();
		
		contacts.setId(id);
		contacts.setOwner(owner);
		contacts.setSource(source);
		contacts.setName(name);
		contacts.setAppellation(appellation);
		contacts.setPosition(position);
		contacts.setPhone(phone);
		contacts.setBirthday(birthday);
		contacts.setEmail(email);
		
		contacts.setDescription(description);
		contacts.setContactSummary(contactSummary);
		contacts.setRelationTimeNext(relationTimeNext);
		contacts.setAddress(address);
		contacts.setCreatTime( DateUtil.getSysTime());
		contacts.setCreatBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		Boolean success = contactsService.save(contacts,customerName);
		OutJson.printMap(request, response, success);
	}

	private void doGetByName(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		List<String> nameList = contactsService.getCustomerNameByName(name);
		OutJson.print(request, response, nameList);
	}

	private void doDisplayContacts(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String customerName = request.getParameter("customerName");
		String source = request.getParameter("source");
		String birthday = request.getParameter("birthday");
		Map<String, Object> selectMap = new HashMap<>();
		selectMap.put("owner", owner);
		selectMap.put("name", name);
		selectMap.put("customerName", customerName);
		selectMap.put("source", source);
		selectMap.put("birthday", birthday);
		selectMap.put("pageSize", pageSize);
		selectMap.put("startIndex", (pageNo - 1) * pageSize);
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		PaginationVo<Contacts> vo = contactsService.doDisplayContacts(selectMap);
		OutJson.print(request, response, vo);
	}

}
