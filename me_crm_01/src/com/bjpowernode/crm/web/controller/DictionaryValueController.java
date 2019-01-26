 package com.bjpowernode.crm.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.domain.DictionaryType;
import com.bjpowernode.crm.domain.DictionaryValue;
import com.bjpowernode.crm.service.DictionaryService;
import com.bjpowernode.crm.service.DictionaryValueService;
import com.bjpowernode.crm.service.impl.DictionaryServiceImpl;
import com.bjpowernode.crm.service.impl.DictionaryValueServiceImpl;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.util.UUIDGenerator;
@WebServlet(urlPatterns={"/dictionary/value/creat.do","/dictionary/value/checkValue.do","/dictionary/value/save.do","/dictionary/value/index.do",
		"/dictionary/value/edit.do","/dictionary/value/update.do","/dictionary/value/delete.do"})
public class DictionaryValueController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		DictionaryValueService dictionaryValueService = (DictionaryValueService) new TransactionHandler(new DictionaryValueServiceImpl()).getProxy();
		if("/dictionary/value/creat.do".equals(path)){
			doCreat(request,response);
		}else if ("/dictionary/value/checkValue.do".equals(path)) {
			checkValue(request,response);
		}else if ("/dictionary/value/save.do".equals(path)) {
			save(request,response,dictionaryValueService);
		}else if ("/dictionary/value/index.do".equals(path)) {
			index(request,response,dictionaryValueService);
		}else if ("/dictionary/value/edit.do".equals(path)) {
			edit(request,response,dictionaryValueService);
		}else if ("/dictionary/value/update.do".equals(path)) {
			update(request,response,dictionaryValueService);
		}else if ("/dictionary/value/delete.do".equals(path)) {
			delete(request,response,dictionaryValueService);
		}
	}



	private void delete(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictionaryValueService) throws IOException {
		String[] ids = request.getParameterValues("id");
		System.out.println(Arrays.toString(ids));
		dictionaryValueService.delete(ids);
		response.sendRedirect(request.getContextPath() + "/settings/dictionary/value/index.jsp");
	}



	private void update(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictionaryValueService) throws IOException {
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		String text = request.getParameter("text");
		String orderNo = request.getParameter("orderNo");
		String typeCode = request.getParameter("typeCode");
		DictionaryValue dictionaryValue = new DictionaryValue(id, value, text, orderNo, typeCode);
		dictionaryValueService.update(dictionaryValue);
		response.sendRedirect(request.getContextPath() + "/settings/dictionary/value/index.jsp");
	}



	private void edit(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictionaryValueService) throws IOException {
		String id = request.getParameter("id");
		StringBuffer buffer = dictionaryValueService.getOne(id);
		response.getWriter().print(buffer);
		response.sendRedirect(request.getContextPath() + "/settings/dictionary/value/index.jsp");
	}



	private void index(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictionaryValueService) throws IOException {
		StringBuffer buffer = dictionaryValueService.index();
		response.getWriter().print(buffer);
	}



	private void save(HttpServletRequest request, HttpServletResponse response,
			DictionaryValueService dictionaryValueService) throws IOException {
		String typeCode = request.getParameter("typeCode");
		String value = request.getParameter("value");
		String text = request.getParameter("text");
		String orderNo = request.getParameter("orderNo");
		DictionaryValue dictionaryValue = new DictionaryValue(UUIDGenerator.generate(), value, text, orderNo, typeCode);
		dictionaryValueService.save(dictionaryValue);
		response.sendRedirect(request.getContextPath() + "/settings/dictionary/value/index.jsp");
	}



	private void checkValue(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String typeCode = request.getParameter("typeCode");
		String value = request.getParameter("value");
		DictionaryValueService dictionaryValueService = (DictionaryValueService) new TransactionHandler(new DictionaryValueServiceImpl()).getProxy();
		int count = dictionaryValueService.checkValue(typeCode,value);
		PrintWriter out = response.getWriter();
		if(count == 0){
			out.print("{\"success\":true}");
		}else {
			out.print("{\"success\":false}");
		}
		out.close();
	}

	private void doCreat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DictionaryService dictionaryController = (DictionaryService) new TransactionHandler(new DictionaryServiceImpl()).getProxy();
		List<DictionaryType> list = dictionaryController.getAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/settings/dictionary/value/save.jsp").forward(request, response);;
	}
}
