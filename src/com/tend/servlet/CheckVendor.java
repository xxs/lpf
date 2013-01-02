package com.tend.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nc.bean.TenderUserBean;
import com.nc.comm.AppException;
import com.nc.service.TenderUserService;

public class CheckVendor extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置接收信息的字符集
		System.out.println("beging------------------");
    	request.setCharacterEncoding("gb2312");
    	//接收浏览器端提交的信息
		String uname = request.getParameter("uname");
	    System.out.println("000000000"+uname+"))))");
		//设置输出信息的格式及字符集        
        response.setContentType("text/xml; charset=gb2312");
        response.setHeader("Cache-Control", "no-cache");
        //创建输出流对象
        PrintWriter out = response.getWriter();
        //依据验证结果输出不同的数据信息
        TenderUserService tus = new TenderUserService();
        TenderUserBean tub = null;
		try {
			tub = tus.findTenderUserByUserName(uname);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out.println("<response>");		
		if(tub.getUsername() != null && tub.getUsername().equals("")){
			out.println("<res>" + uname +"已存在!</res>");
		}
		out.println("</response>");
		out.close();
	}

}
