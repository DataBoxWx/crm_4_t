package com.bjpowernode.crm.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjpowernode.crm.util.Const;
@WebFilter(urlPatterns={"*.do","*.jsp","*.html"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		if("/user/login.do".equals(path) || "/login.jsp".equals(path) || "/welcome.do".equals(path)){
			chain.doFilter(req, resp);
		}else {
			HttpSession session = request.getSession(false);
			if(session != null && session.getAttribute(Const.SESSION_USER)!= null ){
				chain.doFilter(req, resp);
			}else {
				response.sendRedirect(request.getContextPath() + "/welcome.do");
			}
		}
	}

}
