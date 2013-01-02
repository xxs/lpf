package com.tend.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsSendServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBconn dbconn = new DBconn();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String form_id = req.getParameter("form_id");
		String content = req.getParameter("content");
		String sendtime = req.getParameter("sendtime");
		String to_id = req.getParameter("to_id");
		String body_id = "";
		String remind_time = req.getParameter("remind_time");
		try{
			conn = dbconn.getDBConn();
			stmt = conn.createStatement();
			String sql = "insert into sms_body(from_id,sms_type,content,send_time) values('"+form_id+"','0','"+content+"','"+sendtime+"')";
			stmt.executeUpdate(sql);
			String sql1 = "select body_id from sms_body where from_id='"+form_id+"' and send_time='"+sendtime+"' and content='"+content+"'";
			rs = stmt.executeQuery(sql1);
			while(rs.next()){
				body_id = rs.getString("body_id");
			}
			String sql2 = "insert into sms(to_id,remind_flag,delete_flag,body_id,remind_time)";
			sql2 += " values('"+to_id+"','1','0','"+body_id+"','"+remind_time+"')";
			stmt.executeUpdate(sql2);
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				dbconn.closeDBconn();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

}
