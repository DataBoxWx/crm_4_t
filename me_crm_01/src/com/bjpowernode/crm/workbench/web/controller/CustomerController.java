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

import com.bjpowernode.crm.domain.PaginationVo;
import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.util.Const;
import com.bjpowernode.crm.util.DateUtil;
import com.bjpowernode.crm.util.ExcelUtil;
import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.domain.Transaction;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.CustomerRemarkService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TransacitonService;
import com.bjpowernode.crm.workbench.service.impl.ContactsServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerRemarkServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.CustomerServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.TransacitonServiceImpl;

@WebServlet(urlPatterns = { "/workbench/customer/saveCustomer.do","/workbench/customer/displayAll.do",
		"/workbench/customer/getById.do","/workbench/customer/updateCustomer.do","/workbench/customer/deleteCustomer.do",
		"/workbench/customer/export.do","/workbench/customer/import.do","/workbench/customer/detail.do",
		"/workbench/customer/displayRemark.do","/workbench/customer/saveRemark.do","/workbench/customer/getByName.do",
		"/workbench/customer/getTransactionBycustomerId.do","/workbench/customer/displayContacts.do","/workbench/customer/updateRemark.do",
		"/workbench/customer/deleteRemark.do","/workbench/custoemr/deleteContacts.do","/workbench/customer/deleteTran.do"})
public class CustomerController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		System.out.println(path);
		if ("/workbench/customer/saveCustomer.do".equals(path)) {
			doSaveCustomer(request, response);
		}else if ("/workbench/customer/displayAll.do".equals(path)) {
			doDisplayAll(request,response);
		}else if ("/workbench/customer/getById.do".equals(path)) {
			doGetById(request,response);
		}else if ("/workbench/customer/updateCustomer.do".equals(path)) {
			doUpdateCustomer(request,response);
		}else if ("/workbench/customer/deleteCustomer.do".equals(path)) {
			doDeleteCustomer(request,response);
		}else if ("/workbench/customer/export.do".equals(path)) {
			doexport(request,response);
		}else if ("/workbench/customer/import.do".equals(path)) {
			doImport(request,response);
		}else if ("/workbench/customer/detail.do".equals(path)) {
			doDetail(request,response);
		}else if ("/workbench/customer/displayRemark.do".equals(path)) {
			doDisplayRemark(request,response);
		}else if ("/workbench/customer/saveRemark.do".equals(path)) {
			doSaveRemark(request,response);
		}else if ("/workbench/customer/getByName.do".equals(path)) {
			doGetByName(request,response);
		}else if ("/workbench/customer/getTransactionBycustomerId.do".equals(path)) {
			doGetTransactionBycustomerId(request,response);
		}else if ("/workbench/customer/displayContacts.do".equals(path)) {
			doDisplayContacts(request,response);
		}else if ("/workbench/customer/updateRemark.do".equals(path)) {
			doUpdateRemark(request,response);
		}else if ("/workbench/customer/deleteRemark.do".equals(path)) {
			doDeleteRemark(request,response);
		}else if ("/workbench/custoemr/deleteContacts.do".equals(path)) {
			doDeleteContacts(request,response);
		}else if ("/workbench/customer/deleteTran.do".equals(path)) {
			doDeleteTran(request,response);
		}
	
	}

	private void doDeleteTran(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		TransacitonService transactionService = (TransacitonService) new TransactionHandler(new TransacitonServiceImpl()).getProxy();
		
		boolean success = transactionService.delete(id);
		OutJson.printMap(request, response, success);
	}

	private void doDeleteContacts(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl()).getProxy();
		Boolean success =  contactsService.delete2(id);
		OutJson.printMap(request, response, success);
		
	}

	private void doDeleteRemark(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		CustomerRemarkService customerService = (CustomerRemarkService) new TransactionHandler(new CustomerRemarkServiceImpl()).getProxy();
		Boolean success = customerService.deleteRemark(id);
		OutJson.printMap(request, response, success);
	}

	private void doUpdateRemark(HttpServletRequest request, HttpServletResponse response) {
		String noteContent = request.getParameter("noteContent");
		String id = request.getParameter("id");
		CustomerRemark customerRemark = new CustomerRemark();
		customerRemark.setId(id);
		customerRemark.setEditFlag("1");
		customerRemark.setNoteContent(noteContent);
		customerRemark.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		customerRemark.setEditTime(DateUtil.getSysTime());
		
		CustomerRemarkService customerService = (CustomerRemarkService) new TransactionHandler(new CustomerRemarkServiceImpl()).getProxy();
		int count = customerService.updateRemark(customerRemark);
		Map<String, Object> jsonMap = new HashMap<>();
		if(count == 1){
			jsonMap.put("success", true);
			jsonMap.put("customerRemark", customerRemark);
		}else {
			jsonMap.put("success", true);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doDisplayContacts(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		ContactsService contactsService = (ContactsService) new TransactionHandler(new ContactsServiceImpl()).getProxy();
		List<Contacts> contactsList = contactsService.getContactsByCustomerId(customerId);
		OutJson.print(request, response, contactsList);
	}

	private void doGetTransactionBycustomerId(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		List<Transaction> transactionsList = customerService.getTransactionBycustomerId(customerId);
		
		Map<String, Object> possiblityMap = (Map<String, Object>) request.getServletContext().getAttribute("possibly");
		for (Transaction transaction : transactionsList) {
			transaction.setPossiblity((String)possiblityMap.get(transaction.getStage()));
		}
		OutJson.print(request, response, transactionsList);
		
	}

	private void doGetByName(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		List<String> nameList = customerService.getByName(name);
		OutJson.print(request, response, nameList);
	}

	private void doSaveRemark(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		String noteContent = request.getParameter("noteContent");
		
		CustomerRemark customerRemark = new CustomerRemark();
		
		customerRemark.setId(UUIDGenerator.generate());
		customerRemark.setNoteContent(noteContent);
		customerRemark.setEditFlag("0");
		customerRemark.setCreatBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		customerRemark.setCreatTime(DateUtil.getSysTime());
		customerRemark.setCustomerId(customerId);
		
		
		CustomerRemarkService customerService = (CustomerRemarkService) new TransactionHandler(new CustomerRemarkServiceImpl()).getProxy();
		Map<String, Object> jsonMap = customerService.saveRemark(customerRemark);
		OutJson.print(request, response, jsonMap);
	}

	private void doDisplayRemark(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		CustomerRemarkService customerService = (CustomerRemarkService) new TransactionHandler(new CustomerRemarkServiceImpl()).getProxy();
		List<CustomerRemark> crList = customerService.getCustomerRemarkByCustomerId(customerId);
		OutJson.print(request, response, crList);
	}

	private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		Customer customer = customerService.getById2(id);
		
		request.setAttribute("customer", customer);
		request.getRequestDispatcher("/workbench/customer/detail.jsp").forward(request, response);
		
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
				ExcelUtil<Customer> excelUtil = new ExcelUtil<>();
				List<Customer> clueList = excelUtil.read(excelFilePath, Customer.class);
				
				//调用service保存这些数据
				CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
				Map<String, Object> jsonMap = new HashMap<>();
				if(customerService.saves(clueList) != 0){
					jsonMap.put("success", true);
				}else {
					jsonMap.put("success", false);
				}
				OutJson.print(request, response, jsonMap);;
		
	}

	private void doexport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		//设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		
		//设置响应头信息
		response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");  
		List<Customer> list = customerService.getAll();
		HSSFWorkbook workbook = ExcelUtil.export("市场活动", Customer.class, list);
			
		//将workbook响应到浏览器（浏览器上呈现的是文件下载！）
	        workbook.write(response.getOutputStream());
		
	}

	private void doDeleteCustomer(HttpServletRequest request, HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		Boolean success = customerService.deleteCustomer(ids);
		OutJson.printMap(request, response, success);
		
	}

	private void doUpdateCustomer(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String website = request.getParameter("website");
		String telephone = request.getParameter("telephone");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String relationTimeNext = request.getParameter("relationTimeNext");
		String address = request.getParameter("address");
		
		Customer customer = new Customer();
		
		customer.setId(id);
		customer.setOwner(owner);
		customer.setName(name);
		customer.setWebsite(website);
		customer.setTelephone(telephone);
		customer.setDescription(description);
		customer.setContactSummary(contactSummary);
		customer.setRelationTimeNext(relationTimeNext);
		customer.setAddress(address);
		customer.setEditBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		customer.setEditTime(DateUtil.getSysTime());
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		Boolean success = customerService.updateCustomer(customer);
		Map<String, Object> jsonMap = new HashMap<>();
		if(success){
			jsonMap.put("success", success);
			jsonMap.put("customer", customer);
		}else{
			jsonMap.put("success", success);
		}
		OutJson.print(request, response, jsonMap);
	}

	private void doGetById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		Map<String, Object> jsonMap = customerService.getById(id);
		OutJson.print(request, response, jsonMap);
	}

	private void doDisplayAll(HttpServletRequest request, HttpServletResponse response) {
		int pageNo = Integer.valueOf(request.getParameter("pageNo"));
		int pageSize = Integer.valueOf(request.getParameter("pageSize"));
		String name = request.getParameter("name");
		String owner = request.getParameter("owner");
		String telephone = request.getParameter("telephone");
		String website = request.getParameter("website");
		
		Map<String, Object> pageMap = new HashMap<>();
		
		pageMap.put("pageSize", pageSize);
		pageMap.put("startIndex", (pageNo-1) * pageSize);
		pageMap.put("name", name);
		pageMap.put("owner", owner);
		pageMap.put("telephone", telephone);
		pageMap.put("website", website);
		
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		
		PaginationVo<Customer>  vo= customerService.displayAll(pageMap);
		OutJson.print(request, response, vo);
	}

	private void doSaveCustomer(HttpServletRequest request, HttpServletResponse response) {
		String owner = request.getParameter("owner");
		String name = request.getParameter("name");
		String website = request.getParameter("website");
		String telephone = request.getParameter("telephone");
		String description = request.getParameter("description");
		String contactSummary = request.getParameter("contactSummary");
		String relationTimeNext = request.getParameter("relationTimeNext");
		String address = request.getParameter("address");
		
		Customer customer = new Customer();
		
		customer.setId(UUIDGenerator.generate());
		customer.setOwner(owner);
		customer.setName(name);
		customer.setWebsite(website);
		customer.setTelephone(telephone);
		customer.setDescription(description);
		customer.setContactSummary(contactSummary);
		customer.setRelationTimeNext(relationTimeNext);
		customer.setAddress(address);
		customer.setCreatBy(((User)request.getSession().getAttribute(Const.SESSION_USER)).getName());
		customer.setCreatTime(DateUtil.getSysTime());
		CustomerService customerService = (CustomerService) new TransactionHandler(new CustomerServiceImpl()).getProxy();
		Boolean success = customerService.saveCustomer(customer);
		OutJson.printMap(request, response, success);
	}
}
