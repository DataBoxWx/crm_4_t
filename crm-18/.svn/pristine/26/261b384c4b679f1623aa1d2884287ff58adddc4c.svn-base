package com.bjpowernode.crm.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjpowernode.crm.utils.Const;

public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String servletPath = request.getServletPath();
		if("/login.do".equals(servletPath) || "/login.jsp".equals(servletPath) || "/welcome.do".equals(servletPath)){
			chain.doFilter(request, response);
		}else{
			HttpSession session = request.getSession(false);
			if(session != null && session.getAttribute(Const.SESSION_USER) != null){
				chain.doFilter(request, response);
			}else{
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
		}
	}

}
