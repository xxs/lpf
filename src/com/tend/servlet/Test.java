package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
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
    	try{
    	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
    	String dbUrl="jdbc:mysql://100.100.1.72:3336/TD_OA"; 
    	String dbUser="root"; 
    	String dbPwd="myoa888"; 
    	System.out.println("555");
    	Connection conn1 = DriverManager.getConnection(dbUrl,dbUser,dbPwd); 
    	System.out.println("Database conn success"); 
    	System.out.println("33333");
    	conn1.close(); 
    	}catch(Exception e){
    		e.printStackTrace();
    	}System.out.println("888");
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
    }
}
