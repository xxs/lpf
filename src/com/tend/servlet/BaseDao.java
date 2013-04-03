package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class BaseDao {
	//public Integer nexttime = 120000;
	public Integer days = 180;  
	public Integer beforedays = 1;
	
	
	public BaseDao() {
		super();
	}

	public BaseDao(Integer n,Integer d,Integer b ) {
		super();
		//nexttime = n;
		days = d;
		beforedays = b;
		
	}
	
//	public Integer getNexttime() {
//		return nexttime;
//	}

	public Integer getDays() {
		return days;
	}

	public Integer getBeforedays() {
		return beforedays;
	}

	//获取NC数据库连接
	public Connection getConForNC(){
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.11:1521:sndb";
		try{
			Class.forName(dirver);
			con = DriverManager.getConnection(url,"snzs","snzs");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	//获取NC测试数据库连接
	public Connection getConForNCTest(){
		System.out.println("启动测试库连接");
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.71:1521:nctp";
		try{
			Class.forName(dirver);
			con = DriverManager.getConnection(url,"snzs02","snzs02");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	//获取BQ数据库连接
	public Connection getConForBQ(){
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.27:1521:bidb";
		try{
			Class.forName(dirver);
			con = DriverManager.getConnection(url,"bius01","bius_123");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	public static void closeAll(PreparedStatement pst,ResultSet rest,Connection con){
		try{
			if(pst!=null){
				pst.close();
			}
			if(rest!=null){
				rest.close();
			}
			if(con!=null){
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean excuteDelete(String sql){
		Connection conn = getConForBQ();
		PreparedStatement ps = null;
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			result = ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(ps, null, conn);
		}
		return result;
	}
}
