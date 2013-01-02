package com.tend.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetGroupPrice extends HttpServlet {
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	String sql = "";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String cpcode = req.getParameter("cpcode");
		String iii = req.getParameter("iii");
		try{
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
}
