package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.service.UserService;
import com.bjpowernode.crm.service.impl.UserServiceImpl;
import com.bjpowernode.crm.util.ChangeStageException;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.ExcelUtil;
import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.domain.TransactionHistory;
import com.bjpowernode.crm.workbench.domain.TransactionRemark;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.TransacitonRemarkService;
import com.bjpowernode.crm.workbench.service.TransacitonService;
import com.bjpowernode.crm.workbench.service.TransactionHistoryService;
import com.bjpowernode.crm.workbench.service.impl.ContactsServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TransacitonRemarkServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TransacitonServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TransactionHistoryServiceImpl;

@WebServlet(urlPatterns = { "/workbench/transaction/getActivityByName.do",
		"/workbench/transaction/getContactsByName.do", "/workbench/transaction/save.do",
		"/workbench/transaction/saveTransaction.do", "/workbench/transaction/displayTransaction.do",
		"/workbench/transaction/detail.do", "/workbench/transaction/getDistory.do",
		"/workbench/transaction/changeStage.do", "/workbench/transaction/edit.do",
		"/workbench/transaction/updateTransaction.do","/wrokbench/transaction/deleteTran.do",
		"/workbench/transaction/export.do","/workbench/transaction/saveRemark.do",
		"/workbench/transaction/displayRemark.do"})
public class TransactionCotroller extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		if ("/workbench/transaction/getActivityByName.do".equals(path)) {
			doGetActivityByName(request, response);
		} else if ("/workbench/transaction/getContactsByName.do".equals(path)) {
			doGetContactsByName(request, response);
		} else if ("/workbench/transaction/save.do".equals(path)) {
			doGetUser(request, response);
		} else if ("/workbench/transaction/saveTransaction.do".equals(path)) {
			dosaveTransaction(request, response);
		} else if ("/workbench/transaction/displayTransaction.do".equals(path)) {
			doDisplayTransaction(request, response);
		} else if ("/workbench/transaction/detail.do".equals(path)) {
			doDetail(request, response);
		} else if ("/workbench/transaction/getDistory.do".equals(path)) {
			doGetDistory(request, response);
		} else if ("/workbench/transaction/changeStage.do".equals(path)) {
			doChangeStage(request, response);
		} else if ("/workbench/transaction/edit.do".equals(path)) {
			doEdit(request, response);
		} else if ("/workbench/transaction/updateTransaction.do".equals(path)) {
			doUpdateTransaction(request, response);
		} else if ("/wrokbench/transaction/deleteTran.do".equals(path)) {
			doDeleteTran(request, response);
		} else if ("/workbench/transaction/export.do".equals(path)) {
			doExport(request, response);
		} else if ("/workbench/transaction/saveRemark.do".equals(path)) {
			doSaveRemark(request, response);
		} else if ("/workbench/transaction/displayRemark.do".equals(path)) {
			doDisplayRemark(request, response);
		}
	}

	private void doDisplayRemark(HttpServletRequest request, HttpServletResponse response) {
		TransacitonRemarkService transacitonRemarkService = (TransacitonRemarkService) new TransactionHandler(
				new TransacitonRemarkServiceImpl()).getProxy();
		List<TransactionRemark> remarklist = transacitonRemarkService.displayRemark();
		OutJson.print(request, response, remarklist);
	}

	private void doSaveRemark(HttpServletRequest request, HttpServletResponse response) {
		String noteContent = request.getParameter("noteContent");
		String transactionId = request.getParameter("transactionId");
		TransactionRemark transactionRemark = new TransactionRemark();
		transactionRemark.setId(UUIDGenerator.generate());
		transactionRemark.setNoteContent(noteContent);
		transactionRemark.setCreatBy(((User) request.getSession().getAttribute(Const.SESSION_USER)).getName());
		transactionRemark.setCreatTime(DateUtil.getSysTime());
		transactionRemark.setEditFlag("0");
		transactionRemark.setTransactionId(transactionId);
		TransacitonRemarkService transacitonRemarkService = (TransacitonRemarkService) new TransactionHandler(
				new TransacitonRemarkServiceImpl()).getProxy();
		int count = transacitonRemarkService.save(transactionRemark);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count ==1){
			jsonMap.put("success", true);
			jsonMap.put("tranRemark", transactionRemark);
		}else{
			jsonMap.put("success", false);
		}
		OutJson.print(request, response, jsonMap);
		
	}

	private void doExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		
		//设置响应头信息
		response.setHeader("Content-disposition", "attachment;filename=trans"+System.currentTimeMillis()+".xls");  
		List<Transaction> list = transacitonService.getAll();
		HSSFWorkbook workbook = ExcelUtil.export("联系人", Transaction.class, list);
			
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
	        workbook.write(response.getOutputStream());

	}

	private void doDeleteTran(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");

		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		Boolean success = transacitonService.deletes(ids);
		OutJson.printMap(request, response, success);
		
	}

	private void doUpdateTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("edit-id");
		String owner = request.getParameter("edit-owner");
		String money = request.getParameter("edit-money");
		String name = request.getParameter("edit-name");
		String expectedTime = request.getParameter("edit-expectedTime");
		String customerName = request.getParameter("edit-customerName");
		String stage = request.getParameter("edit-stage");
		String type = request.getParameter("edit-type");
		String source = request.getParameter("edit-source");
		String activityId = request.getParameter("edit-activityId");
		String contactsId = request.getParameter("edit-contactsId");
		String description = request.getParameter("edit-description");
		String contactSummary = request.getParameter("edit-contactSummary");
		String relationTimeNext = request.getParameter("edit-relationTimeNext");
		
		Transaction transaction = new Transaction();
		transaction.setId(id);
		transaction.setOwner(owner);
		transaction.setMoney(money);
		transaction.setName(name);
		transaction.setExpectedTime(expectedTime);
		transaction.setCustomerId(customerName);
		transaction.setStage(stage);
		transaction.setType(type);
		transaction.setSource(source);
		transaction.setActivityId(activityId);
		transaction.setContactsId(contactsId);
		transaction.setDescription(description);
		transaction.setContactSummary(contactSummary);
		transaction.setRelationTimeNext(relationTimeNext);
		transaction.setEditTime(DateUtil.getSysTime());
		transaction.setEditBy(((User) request.getSession().getAttribute(Const.SESSION_USER)).getName());
		
		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		Boolean success = transacitonService.update(transaction,customerName);
		if(success){
			request.getRequestDispatcher("/workbench/transaction/index.jsp").forward(request, response);
		}
	}

	private void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		Transaction transaction = transacitonService.getById(id);
		Map<String, Object> possiblityMap = (Map<String, Object>) request.getServletContext().getAttribute("possibly");
		transaction.setPossiblity((String) possiblityMap.get(transaction.getStage()));
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.doList();
		request.setAttribute("transaction", transaction);
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/workbench/transaction/edit.jsp").forward(request, response);
	}

	private void doChangeStage(HttpServletRequest request, HttpServletResponse response) {
		String transactionId = request.getParameter("transactionId");
		String stage = request.getParameter("stage");
		String money = request.getParameter("money");
		String expectedTime = request.getParameter("expectedTime");
		Transaction transaction = new Transaction();
		transaction.setId(transactionId);
		transaction.setStage(stage);
		transaction.setMoney(money);
		transaction.setExpectedTime(expectedTime);
		transaction.setEditBy(((User) request.getSession().getAttribute(Const.SESSION_USER)).getName());
		transaction.setEditTime(DateUtil.getSysTime());

		Map<String, Object> jsonMap = new HashMap<>();
		Boolean success = false;
		try {
			TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
					new TransacitonServiceImpl()).getProxy();

			success = transacitonService.changeStage(transaction);
			Map<String, Object> possiblityMap = (Map<String, Object>) request.getServletContext()
					.getAttribute("possibly");
			transaction.setPossiblity((String) possiblityMap.get(transaction.getStage()));

			jsonMap.put("transaction", transaction);
			jsonMap.put("success", success);
		} catch (ChangeStageException e) {
			jsonMap.put("success", success);
			jsonMap.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			jsonMap.put("success", success);
			jsonMap.put("errorMsg", "更新失敗");
		}

		OutJson.print(request, response, jsonMap);
	}

	private void doGetDistory(HttpServletRequest request, HttpServletResponse response) {
		String transactionId = request.getParameter("transactionId");
		TransactionHistoryService transactionHistoryService = (TransactionHistoryService) new TransactionHandler(
				new TransactionHistoryServiceImpl()).getProxy();
		List<TransactionHistory> transactionHistoryList = transactionHistoryService.getDistory(transactionId);
		Map<String, Object> possiblityMap = (Map<String, Object>) request.getServletContext().getAttribute("possibly");
		for (int i = 0; i < transactionHistoryList.size(); i++) {
			TransactionHistory th = transactionHistoryList.get(i);
			th.setPossiblity((String) possiblityMap.get(th.getStage()));
		}

		OutJson.print(request, response, transactionHistoryList);
	}

	private void doDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		Transaction transaction = transacitonService.getById(id);

		Map<String, Object> possiblityMap = (Map<String, Object>) request.getServletContext().getAttribute("possibly");
		transaction.setPossiblity((String) possiblityMap.get(transaction.getStage()));

		request.setAttribute("transaction", transaction);
		request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
	}

	private void doDisplayTransaction(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		int startIndex = (pageNo - 1) * pageSize;
		String name = request.getParameter("name");
		String owner = request.getParameter("name");
		String customerName = request.getParameter("name");
		String stage = request.getParameter("name");
		String type = request.getParameter("name");
		String source = request.getParameter("name");
		String contactsName = request.getParameter("name");
		Map<String, Object> inquireMap = new HashMap<>();
		inquireMap.put("startIndex", startIndex);
		inquireMap.put("pageSize", pageSize);
		inquireMap.put("name", name);
		inquireMap.put("owner", owner);
		inquireMap.put("customerName", customerName);
		inquireMap.put("stage", stage);
		inquireMap.put("type", type);
		inquireMap.put("source", source);
		inquireMap.put("contactsName", contactsName);

		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		Map<String, Object> jsonMap = transacitonService.displayTransaction(inquireMap);
		OutJson.print(request, response, jsonMap);
	}

	private void dosaveTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String owner = request.getParameter("create-owner");
		String money = request.getParameter("create-money");
		String name = request.getParameter("create-name");
		String customerName = request.getParameter("create-customerName");
		String expectedTime = request.getParameter("create-expectedTime");
		String stage = request.getParameter("create-stage");
		String type = request.getParameter("create-type");
		String source = request.getParameter("create-source");
		String activityId = request.getParameter("create-activityId");
		String contactsId = request.getParameter("create-contactsId");
		String description = request.getParameter("create-description");
		String contactSummary = request.getParameter("create-contactSummary");
		String relationTimeNext = request.getParameter("create-relationTimeNext");

		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();

		Transaction transaction = new Transaction();

		transaction.setId(UUIDGenerator.generate());
		transaction.setOwner(owner);
		transaction.setMoney(money);
		transaction.setName(name);
		transaction.setExpectedTime(expectedTime);
		transaction.setStage(stage);
		transaction.setType(type);
		transaction.setSource(source);
		transaction.setActivityId(activityId);
		transaction.setContactsId(contactsId);
		transaction.setDescription(description);
		transaction.setContactSummary(contactSummary);
		transaction.setRelationTimeNext(relationTimeNext);
		transaction.setCreatTime(DateUtil.getSysTime());
		transaction.setCreatBy(((User) request.getSession().getAttribute(Const.SESSION_USER)).getName());

		Boolean success = transacitonService.saveTransaction(transaction, customerName);
		if (success) {
			response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
		} else {
			System.out.println("保存失败");
		}
	}

	private void doGetUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService userService = (UserService) new TransactionHandler(new UserServiceImpl()).getProxy();
		List<User> userList = userService.doList();
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);

	}

	private void doGetContactsByName(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl())
				.getProxy();
		List<Contacts> contactsList = contactsService.getContactsByName(name);
		OutJson.print(request, response, contactsList);

	}

	private void doGetActivityByName(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		TransacitonService transacitonService = (TransacitonService) new TransactionHandler(
				new TransacitonServiceImpl()).getProxy();
		List<Transaction> transactionsList = transacitonService.getActivityByName(name);
		OutJson.print(request, response, transactionsList);
	}
}
