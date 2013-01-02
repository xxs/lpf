package com.nc.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.wm.common.UserInfo;

import org.springframework.beans.factory.BeanFactory;

public class RfLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -6416346557468711855L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        this.login(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    response.setContentType("text/html;charset=gb2312");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        this.login(request, response);
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("pwd");
		PrintWriter out = response.getWriter();
		try{
			UserInfo huniton = BeanFactory.getUserInfoInstance().getUserInfo(username,password);
			if(huniton != null){
				request.getSession().setAttribute("username", huniton);
				response.sendRedirect("/student/");
			}else{
				out.println("<script>window.alert(\"用户名或密码不正确，请重新登陆！\");document.location=\"/\";</script>");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		request.getSession().invalidate();
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
