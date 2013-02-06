package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOracleconn {
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
    public static Connection getDBConn() {
    	String url="jdbc:oracle:thin:@100.100.1.71:1521:nctp";
    	//String url="jdbc:oracle:thin:@100.100.1.11:1521:sndb";
        String user = "ben01";
        String password = "benben";
        String DBDriver = "oracle.jdbc.driver.OracleDriver";
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
    	DBOracleconn db = new DBOracleconn();
    	Connection conn = db.getDBConn();
    	try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select 'aa' xx from dual");
			while(rs.next()){
				System.out.println(rs.getString("xx"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
