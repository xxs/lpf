package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBconn {
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
    public static Connection getDBConn() {
    	String url="jdbc:mysql://localhost:3336/TD_OA";
        String user = "root";
        String password = "myoa888";
        String DBDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(DBDriver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
        }
        return conn;
    }

    public static  void closeDBconn() throws Exception{
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
    }
    public static void main(String[] args){
    	/*try{
    	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	String dbUrl="jdbc:mysql://localhost:3336/TD_OA"; 
    	String dbUser="root"; 
    	String dbPwd="myoa888"; 
    	Connection conn1 = DriverManager.getConnection(dbUrl,dbUser,dbPwd); 
    	System.out.println("Database conn success"); 
    	conn1.close(); 
    	}catch(Exception e){
    		e.printStackTrace();
    	}*/
    	/*conn = getDBConn();
    	try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(sms) from sms");
			while(rs.next()){
				String to_id = rs.getString("to_id");
				System.out.println(to_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			closeDBconn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	DBconn dbconn = new DBconn();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String form_id = "admin";
		String content = "您的IT报修申请已处理完毕！请尽进入OA系统IT报修平台给维修人员";
		String sendtime ="2011-02-18 11:02:48";
		String to_id = "jiangkun";
		String body_id = "";
		String remind_time = "1298027158";
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
}
