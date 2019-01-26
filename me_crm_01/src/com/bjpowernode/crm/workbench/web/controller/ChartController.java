package com.bjpowernode.crm.workbench.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjpowernode.crm.util.OutJson;
import com.bjpowernode.crm.util.TransactionHandler;
import com.bjpowernode.crm.workbench.service.ChartService;
import com.bjpowernode.crm.workbench.service.impl.ChartServiceImpl;
@WebServlet(urlPatterns={"/workbench/chart/transaction/getCountByStage.do"})
public class ChartController extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		if("/workbench/chart/transaction/getCountByStage.do".equals(path)){
			doGetCountByStage(request,response);
		}
	}

	private void doGetCountByStage(HttpServletRequest request, HttpServletResponse response) {
		ChartService chartService = (ChartService)new TransactionHandler(new ChartServiceImpl()).getProxy();
		List<Map<String, Object>> jsonList = chartService.getCountByStage();
		OutJson.print(request, response, jsonList);
	}
}
