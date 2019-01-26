package com.bjpowernode.crm.settings.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.impl.DicTypeServiceImpl;
import com.bjpowernode.crm.settings.service.impl.DicValueServiceImpl;
import com.bjpowernode.crm.utils.TransactionHandler;
import com.bjpowernode.crm.utils.UUIDGenerator;

public class DicValueController extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if ("/settings/dictionary/value/create.do".equals(servletPath)) {
			doCreate(request, response);
		} else if ("/settings/dictionary/value/checkValue.do".equals(servletPath)) {
			doCheckValue(request, response);
		} else if ("/settings/dictionary/value/save.do".equals(servletPath)) {
			doSave(request, response);
		}
	}

	protected void doSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = UUIDGenerator.generate();
		String value = request.getParameter("value");
		String text = request.getParameter("text");
		String orderNo = request.getParameter("orderNo");
		String typeCode = request.getParameter("typeCode");
		
		DicValue dv = new DicValue();
		dv.setId(id);
		dv.setValue(value);
		dv.setText(text);
		dv.setOrderNo(orderNo);
		dv.setTypeCode(typeCode);
		
		DicValueService dvs = (DicValueService) new TransactionHandler(new DicValueServiceImpl()).getProxy();
		int count = dvs.save(dv);
		if(count == 1){
			response.sendRedirect(request.getContextPath() + "/settings/dictionary/value/index.jsp");
		}
	}

	protected void doCheckValue(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String typeCode = request.getParameter("typeCode");
		String value = request.getParameter("value");
		DicValueService dvs = (DicValueService) new TransactionHandler(new DicValueServiceImpl()).getProxy();
		DicValue dv = dvs.getByTypeCodeAndValue(typeCode, value);
		response.setContentType("text/json;charset=UTF-8");
		if (dv == null) {
			response.getWriter().print("{\"success\" : true}");
		} else {
			response.getWriter().print("{\"success\" : false}");
		}
	}

	protected void doCreate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DicTypeService dts = (DicTypeService) new TransactionHandler(new DicTypeServiceImpl()).getProxy();
		List<DicType> dtList = dts.getAll();
		request.setAttribute("dtList", dtList);
		request.getRequestDispatcher("/settings/dictionary/value/save.jsp").forward(request, response);
	}
}
