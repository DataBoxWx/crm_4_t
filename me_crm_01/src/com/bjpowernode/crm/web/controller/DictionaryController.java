package com.bjpowernode.crm.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.domain.DictionaryType;
import com.bjpowernode.crm.service.DictionaryService;
import com.bjpowernode.crm.service.impl.DictionaryServiceImpl;
import com.bjpowernode.crm.util.TransactionHandler;
@WebServlet(urlPatterns={"/dictionary/type/register.do","/dictionary/type/save.do","/dictionary/type/list.do","/dictionary/type/edit.do","/dictionary/type/update.do","/dictionary/type/delete.do"})
public class DictionaryController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		DictionaryService dictionaryService = (DictionaryService) new TransactionHandler(new DictionaryServiceImpl()).getProxy();
		String path = request.getServletPath();
		if("/dictionary/type/register.do".equals(path)){
			register(request,response,dictionaryService);
		}else if ("/dictionary/type/save.do".equals(path)) {
			save(request,response,dictionaryService);
			//response.sendRedirect(request.getContextPath() + "/settings/dictionary/type/index.jsp");
		}else if("/dictionary/type/list.do".equals(path)){
			list(request,response,dictionaryService);
		}else if("/dictionary/type/edit.do".equals(path)){
			edit(request,response,dictionaryService);
		}else if("/dictionary/type/update.do".equals(path)){
			update(request,response,dictionaryService);
			response.sendRedirect(request.getContextPath() + "/settings/dictionary/type/index.jsp");
		}else if("/dictionary/type/delete.do".equals(path)){
			delete(request,response,dictionaryService);
			response.sendRedirect(request.getContextPath() + "/settings/dictionary/type/index.jsp");
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response, DictionaryService dictionaryService) {
		String[] ids = request.getParameterValues("ids");
		dictionaryService.delete(ids);
		
	}

	private void update(HttpServletRequest request, HttpServletResponse response, DictionaryService dictionaryService) {
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String discribe = request.getParameter("describe");
		
		
		dictionaryService.update(code,name,discribe);
		
	}

	private void edit(HttpServletRequest request, HttpServletResponse response, DictionaryService dictionaryService) throws IOException {
		String code = request.getParameter("code");
		String str = dictionaryService.edit(code);
		PrintWriter out = response.getWriter();
		out.print(str);
		out.close();
	}

	private void list(HttpServletRequest request, HttpServletResponse response, DictionaryService dictionaryService) throws IOException {
		StringBuffer buffer = dictionaryService.list();
		PrintWriter out = response.getWriter();
		out.print(buffer);
		out.close();
	}

	private void save(HttpServletRequest request, HttpServletResponse response, DictionaryService dictionaryService) throws IOException {
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String discribe = request.getParameter("discribe");
		int count = dictionaryService.save(code,name,discribe);
		PrintWriter out = response.getWriter();
		out.print(count);
		out.close();
	}

	private void register(HttpServletRequest request, HttpServletResponse response,
			DictionaryService dictionaryService) throws IOException {
		String code =request.getParameter("code");
		int count = dictionaryService.register(code);
		PrintWriter out = response.getWriter();
		String countStr  = "{\"code\":"+count+"}";
		out.print(countStr);
		out.close();
	}

}
