package com.tend.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUserSession implements Filter {
	protected FilterConfig filterConfig; 
	public void destroy() {
		// TODO Auto-generated method stub
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request; 
		HttpServletResponse res = (HttpServletResponse)response; 
		try{
			if(req.getSession().getAttribute("com_newapp_web_user_Info") != null){
				chain.doFilter(req, res);
			}else{
				//res.sendRedirect("/login.jsp");
				PrintWriter out = res.getWriter();
				out.println("<script language=\"javascript\" >top.location.href=\"/login.jsp\";</script>");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
	}

}
